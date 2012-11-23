package com.jeelu.android.crazysocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.util.Log;

import com.jeelu.android.Exceptions.SocketUnconnectedException;
import com.jeelu.android.Exceptions.SocketUninitializedException;

/**
 * 一个超级简单的socket客户端
 * 
 * @author HuangYang
 * 
 */
public class EasySocketClient
{
	private String _Host;
	private int _Port;
	private String _EnCode = "UTF-8";
	/**
	 * 与服务器的连接
	 */
	private Socket _Socket = null;
	private PrintWriter out;
	private BufferedReader in;
	private final int _DefaultTimeout = 5000;
	private final int _DefaultReceiveBufferSize = 1024;

	public EasySocketClient(final String host, final int port)
	{
		_Host = host;
		_Port = port;
	}

	public EasySocketClient(final String host, final int port, final String encode)
	{
		_Host = host;
		_Port = port;
		_EnCode = encode;
	}

	@Override
	protected void finalize()
	{
		if (_Socket != null)
		{
			if (_Socket.isConnected())
			{
				try
				{
					_Socket.close();
				}
				catch (final IOException e)
				{
				}
			}
		}
	}

	/**
	 * 建立Socket并连接
	 * 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void BuildSocket(final String host, final int port) throws UnknownHostException, IOException
	{
		_Host = host;
		_Port = port;
		_Socket = new Socket(host, port);
		Log.d("SimpleSocketClient.BuildSocket", "Build成功" + _Host + ":" + _Port);
	}

	/**
	 * 建立Socket并以连接默认服务端
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void BuildSocket() throws UnknownHostException, IOException
	{
		BuildSocket(_Host, _Port);
	}

	/**
	 * 判断Socket连接状态
	 * 
	 * @return
	 * @throws SocketUninitializedException
	 */
	public boolean IsConnected() throws SocketUninitializedException
	{
		return !(_Socket.isClosed() || !_Socket.isConnected());
	}

	/**
	 * 发起连接
	 * 
	 * @return
	 */
	public boolean Connect()
	{
		// TODO:连接实现
		// _Socket.connect(InetAddress.getByName(_Host),_Port);
		return true;
	}

	/**
	 * 发送，默认发送后不中断socket
	 * 
	 * @param sendContent
	 * @return
	 * @throws IOException
	 * @throws SocketUnconnectedException
	 */
	public void Send(final String sendContent) throws SocketUninitializedException, UnsupportedEncodingException, IOException, SocketUnconnectedException
	{
		Send(sendContent, false);
	}

	/**
	 * 发送
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws SocketUnconnectedException
	 * @throws Throwable
	 */
	public void Send(final String sendContent, final boolean disconnectAfterSend) throws SocketUninitializedException, UnsupportedEncodingException, IOException // throws
			, SocketUnconnectedException
	// IOException
	{
		if (_Socket == null)
		{
			Log.d("SimpleSocketClient.Send", "_Socket == null，执行BuildSocket");
			throw new SocketUninitializedException();
		}
		if (!IsConnected())
		{
			Log.d("SimpleSocketClient.Send", "_Scket未连接，需执行Connect()");
			throw new SocketUnconnectedException();
		}
		try
		{
			// 向服务器发送消息
			Log.d("SimpleSocketClient.Send", "开始向服务器发送消息：" + sendContent);
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(_Socket.getOutputStream(), _EnCode)), true);
			out.println(sendContent);
			out.flush();
			Log.d("SimpleSocketClient.Send", "向服务器发送消息完成");
		}
		finally
		{
			if (disconnectAfterSend)
			{
				// 关闭流
				out.close();
				// 关闭Socket
				_Socket.close();
				Log.d("SimpleSocketClient.Send", "socket释放");
			}
		}
	}

	/**
	 * 接收
	 * 
	 * @param rev
	 * @param timeout
	 * @param buffersize
	 * @param receiveUntilTimeout
	 * @param disconnectAfterReceive
	 * @throws IOException
	 * @throws SocketUninitializedException
	 * @throws SocketUnconnectedException
	 */
	public void Receive(final StringBuffer rev, final int timeout, final int buffersize, final boolean receiveUntilTimeout, final boolean disconnectAfterReceive) throws IOException, SocketUninitializedException, SocketUnconnectedException
	{
		if (_Socket == null)
		{
			Log.d("SimpleSocketClient.Send", "_Socket == null，执行BuildSocket");
			throw new SocketUninitializedException();
		}
		if (!IsConnected())
		{
			Log.d("SimpleSocketClient.Send", "_Scket未连接，需执行Connect()");
			throw new SocketUnconnectedException();
		}
		try
		{
			_Socket.setSoTimeout(timeout);
			_Socket.setTcpNoDelay(true);
			final boolean on = true;
			final int linger = 10;
			_Socket.setSoLinger(on, linger);
			Log.d("SimpleSocketClient.Send", "开始从服务器接收消息");
			// 接收来自服务器的消息
			in = new BufferedReader(new InputStreamReader(_Socket.getInputStream(), _EnCode));
			try
			{
				char[] temp = new char[buffersize];
				int numFlag = in.read(temp);
				char[] data;
				while (numFlag != -1)
				{
					data = new char[numFlag];
					System.arraycopy(temp, 0, data, 0, numFlag);
					rev.append(data);
					if (!receiveUntilTimeout)
					{
						break;
					}
					numFlag = in.read(temp);
				}
			}
			catch (SocketTimeoutException ex) // 接收超时
			{
			}
			Log.d("SimpleSocketClient.Send", "从服务器接收消息:" + rev.toString());
		}
		finally
		{
			if (disconnectAfterReceive)
			{
				in.close();
				_Socket.close();
			}
		}
	}

	/**
	 * 接收
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 * @throws SocketUninitializedException
	 * @throws SocketUnconnectedException
	 */
	public void Receive(final StringBuffer rev) throws IOException, SocketUninitializedException, SocketUnconnectedException
	{
		Receive(rev, _DefaultTimeout, _DefaultReceiveBufferSize, false, true);
	}
	
	public void Receive(final StringBuffer rev,boolean disconnectAfterReceive) throws IOException, SocketUninitializedException, SocketUnconnectedException
	{
		Receive(rev, _DefaultTimeout, _DefaultReceiveBufferSize, false, disconnectAfterReceive);
	}
}

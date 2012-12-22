package com.jeelu.android.app.socketplus.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AblySocketServer
{
	public Selector _Selector = null;
	public ServerSocketChannel _Server = null;
	public SocketChannel _SocketChannel = null;
	public int _Port = 9000;
	String _Result = null;

	public AblySocketServer()
	{
	}

	public AblySocketServer(final int port)
	{
		_Port = port;
	}

	protected void initializeOperations() throws IOException, UnknownHostException
	{
		_Selector = Selector.open();
		_Server = ServerSocketChannel.open();
		_Server.configureBlocking(false);
		InetAddress ia = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(ia, _Port);
		_Server.socket().bind(isa);
		Log.i("Socket", "AblySocketServer initialized...");
	}

	public void startServer(final Handler handler) throws IOException
	{
		initializeOperations();
		SelectionKey acceptKey = _Server.register(_Selector, SelectionKey.OP_ACCEPT);

		while (acceptKey.selector().select() > 0)
		{
			Set<SelectionKey> readyKeys = _Selector.selectedKeys();
			Iterator<SelectionKey> it = readyKeys.iterator();

			while (it.hasNext())
			{
				SelectionKey key = it.next();
				it.remove();

				if (key.isAcceptable())
				{
					System.out.println("Key is Acceptable");
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					_SocketChannel = ssc.accept();
					_SocketChannel.configureBlocking(false);
					_SocketChannel.register(_Selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				}
				if (key.isReadable())
				{
					String ret = readMessage(key);
					if (ret.length() > 0)
					{
						Message msg = Message.obtain();
						msg.obj = ret;
						handler.handleMessage(msg);
						replyMessage(_SocketChannel, ret);
					}
				}
				if (key.isWritable())
				{
					// System.out.println("THe key is writable");
					String ret = readMessage(key);
					_SocketChannel = (SocketChannel) key.channel();
					if (_Result.length() > 0)
					{
						replyMessage(_SocketChannel, ret);
					}
				}
			}
		}
	}

	public void replyMessage(final SocketChannel socket, final String ret)
	{
		try
		{
			Charset set = Charset.forName("GB2312");
			CharsetDecoder dec = set.newDecoder();
			CharBuffer charBuf = dec.decode(ByteBuffer.wrap(ret.getBytes()));
			int nBytes = socket.write(ByteBuffer.wrap((charBuf.toString()).getBytes()));
			Log.d("Ably-Server", "replyMessage length = " + nBytes);
			_Result = null;
		}
		catch (Exception e)
		{
			Log.w("Ably-Server", "返回数据时异常:" + e.getMessage());
		}

	}

	protected String readMessage(final SelectionKey key)
	{
		_SocketChannel = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		try
		{
			_SocketChannel.read(buf);
			buf.flip();
			Charset charset = Charset.forName("GB2312");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuffer = decoder.decode(buf);
			_Result = charBuffer.toString();
			if ((_Result != null) && (_Result.length() > 0))
			{
				Log.d("Ably-Server", "收:" + _Result);
			}
		}
		catch (IOException e)
		{
			Log.w("Ably-Server", "接收数据时异常:" + e.getMessage());
		}
		return _Result;
	}
}

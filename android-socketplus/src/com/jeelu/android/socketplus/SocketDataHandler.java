package com.jeelu.android.socketplus;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SocketDataHandler implements IDataHandler, IConnectHandler, IDisconnectHandler
{
	private final Set<INonBlockingConnection> sessions = Collections.synchronizedSet(new HashSet<INonBlockingConnection>());

	private Handler _UIHandler;

	public void setUIHandler(final Handler handler)
	{
		_UIHandler = handler;
	}

	public boolean onData(final INonBlockingConnection nbc) throws IOException, BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException
	{
		try
		{
			String data = nbc.readStringByDelimiter("\0", "gbk");
			if ((data != null) && (data.trim().length() > 0))
			{
				Log.d("app", "Incoming data: " + data);
				Message msg = Message.obtain();
				msg.what = 0;
				msg.obj = data;
				_UIHandler.sendMessage(msg);
				String[] message = data.split("~");
				sendMessageToAll(message[0], message[1]);
			}
		}
		catch (Exception ex)
		{
			System.out.println("onData: " + ex.getMessage());
		}

		return true;
	}

	private void sendMessageToAll(final String user, final String message)
	{
		try
		{
			synchronized (sessions)
			{
				Iterator<INonBlockingConnection> iter = sessions.iterator();

				while (iter.hasNext())
				{
					INonBlockingConnection nbConn = iter.next();

					if (nbConn.isOpen())
					{
						nbConn.write("<b>" + user + "</b>: " + message + "<br />\0");
					}
				}
			}

			System.out.println("Outgoing data: " + user + ": " + message);
		}
		catch (Exception ex)
		{
			System.out.println("sendMessageToAll: " + ex.getMessage());
		}
	}

	public boolean onConnect(final INonBlockingConnection nbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException
	{
		try
		{
			synchronized (sessions)
			{
				sessions.add(nbc);
			}

			System.out.println("onConnect");
		}
		catch (Exception ex)
		{
			System.out.println("onConnect: " + ex.getMessage());
		}

		return true;
	}

	public boolean onDisconnect(final INonBlockingConnection nbc) throws IOException
	{
		try
		{
			synchronized (sessions)
			{
				sessions.remove(nbc);
			}

			System.out.println("onDisconnect");
		}
		catch (Exception ex)
		{
			System.out.println("onDisconnect: " + ex.getMessage());
		}

		return true;
	}
}
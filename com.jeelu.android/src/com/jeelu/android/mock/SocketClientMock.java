package com.jeelu.android.mock;

import com.jeelu.android.Interfaces.ISocketClient;

public class SocketClientMock implements ISocketClient
{
	public boolean buildSocket(String ipAddress, int port)
	{
		// TODO 自动生成的方法存根
		return false;
	}

	public String sendMessage(String message)
	{
		// TODO 自动生成的方法存根
		return null;
	}

	public boolean beginSendMessage(String message)
	{
		// TODO 自动生成的方法存根
		return false;
	}

	public String beginAysnReplay()
	{
		// TODO 自动生成的方法存根
		return null;
	}

	public boolean close()
	{
		// TODO 自动生成的方法存根
		return false;
	}
}

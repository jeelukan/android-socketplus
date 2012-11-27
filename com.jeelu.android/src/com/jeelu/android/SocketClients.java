package com.jeelu.android;

import com.jeelu.android.Enums.ClientType;
import com.jeelu.android.Interfaces.ISocketClient;
import com.jeelu.android.mock.SocketClientMock;

public class SocketClients
{
	public ISocketClient getClient(final String ipAddress, final int port, final ClientType clientType)
	{
		switch (clientType)
		{
			default:
				return new SocketClientMock();
		}
	}
}

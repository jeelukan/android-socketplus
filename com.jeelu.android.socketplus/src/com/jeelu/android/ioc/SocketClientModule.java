package com.jeelu.android.ioc;

import com.google.inject.AbstractModule;
import com.jeelu.android.Interfaces.ISocketClient;
import com.jeelu.android.mock.SocketClientMock;

public class SocketClientModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(ISocketClient.class).to(SocketClientMock.class); // bind(IGreetingService.class).to(HelloChina.class); } }
	}
}

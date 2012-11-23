package com.jeelu.android.Exceptions;

public class SocketUnconnectedException extends ExceptionBase
{
	private static final long serialVersionUID = 3318329096595712285L;

	public SocketUnconnectedException()
	{
		super("Socket没有连接");
	}
}

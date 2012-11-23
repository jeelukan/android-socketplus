package com.jeelu.android.Exceptions;

public class SocketUninitializedException extends ExceptionBase
{
	private static final long serialVersionUID = -3412589004422288643L;

	public SocketUninitializedException()
	{
		super("Socket未初始化");
	}
}

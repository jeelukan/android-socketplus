package com.jeelu.android.Exceptions;

public class NullParamException extends ExceptionBase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3881538007411368946L;

	public NullParamException()
	{
		super("参数不能为null");
	}
}

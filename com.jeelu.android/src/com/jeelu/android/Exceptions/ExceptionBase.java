package com.jeelu.android.Exceptions;

/**
 * 本框架应用异常树的根，系统内一般异常的基类。<BR>
 * 在没有特殊情况下，系统内所有创建的异常都是用此异常类。<BR>
 * 在该类之下派生两个类型的异常：<BR>
 * 1. BusinessException <BR>
 * 2. TechnicalException
 */
@SuppressWarnings("serial")
public class ExceptionBase extends Exception
{
	/**
	 * @param message
	 *            记录日志的消息
	 */
	public ExceptionBase(final String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 *            引起异常的原始异常
	 * @param message
	 *            记录日志的消息
	 */
	public ExceptionBase(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param cause
	 *            引起异常的原始异常
	 */
	public ExceptionBase(final Throwable cause)
	{
		super(cause);
	}
}

package com.jeelu.android.crazysocket;

import com.jeelu.android.Enums.SocketCommunicationDirection;

public abstract class SocketActionBase
{
	private SocketCommunicationDirection _Direction;

	public SocketCommunicationDirection getDirection()
	{
		return _Direction;
	}

	private boolean _DisconnectAfterAction;

	public boolean getDisconnectAfterAction()
	{
		return _DisconnectAfterAction;
	}

	public void setDisconnectedAfterAction(final boolean disconnectedAfterAction)
	{
		_DisconnectAfterAction = disconnectedAfterAction;
	}

	/**
	 * 记录动作执行是否成功
	 */
	private boolean _ActionResult;

	public boolean getActionResult()
	{
		return _ActionResult;
	}

	public void setActionResult(final boolean actionResult)
	{
		_ActionResult = actionResult;
	}
}

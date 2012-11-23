package com.jeelu.android.crazysocket;

import com.jeelu.android.Enums.SocketCommunicationDirection;


/**
 * 描述了一次Socket动作（接收或者发送），并且记录了动作到结果 要考虑发送接收到数据类型除了String还有byte[]，先实现String类型
 * 发送接收的数据还不足，需要重构，TODO:
 * @author huangyang
 * 
 */
public class SocketAction
{
	private String _DataToSend;

	public String getDataToSend()
	{
		return _DataToSend;
	}

	public void setDataToSend(final String dataToSend)
	{
		_DataToSend = dataToSend;
	}
	
	private StringBuffer _DataReceived;
	public StringBuffer getDataReceived()
	{
		return _DataReceived;
	}
	
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

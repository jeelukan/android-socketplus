package com.jeelu.android.crazysocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.jeelu.android.Enums.SocketCommunicationDirection;
import com.jeelu.android.Exceptions.NullParamException;
import com.jeelu.android.Exceptions.SocketUnconnectedException;
import com.jeelu.android.Exceptions.SocketUninitializedException;

/**
 * 用于描述一次socket应用过程
 * 
 * @author huangyang
 * 
 */
public class SocketBusiness
{
	private SocketBusinessParam _SocketBusinessParam = null;
	private EasySocketClient _Client = null;

	public void setBusiness(final SocketBusinessParam param)
	{
		_SocketBusinessParam = param;
	}

	/**
	 * 执行
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws SocketUnconnectedException
	 * @throws SocketUninitializedException
	 */
	public void execute() throws NullParamException, UnknownHostException, IOException, SocketUninitializedException, SocketUnconnectedException
	{
		if (_SocketBusinessParam == null)
		{
			throw new NullParamException();
		}
		// 建立连接
		buildConnection(_SocketBusinessParam.getHost(), _SocketBusinessParam.getPort());
		ArrayList<SocketAction> actionList = _SocketBusinessParam.getActionList();
		int count = actionList.size();
		for (int i = 0; i < count; i++)
		{
			executeSocketAction(actionList.get(i));
		}
	}

	private void executeSocketAction(final SocketAction socketAction) throws UnknownHostException, IOException, SocketUninitializedException, SocketUnconnectedException
	{
		// 发送动作
		if (socketAction.getDirection() == SocketCommunicationDirection.Send)
		{
			processSocketSend(socketAction);
		}
		// 接收动作
		if (socketAction.getDirection() == SocketCommunicationDirection.Receive)
		{
			processSocketReceive(socketAction);
		}
	}

	private void buildConnection(final String host, final int port) throws UnknownHostException, IOException
	{
		_Client = new EasySocketClient(host, port);
		_Client.BuildSocket();
	}

	/**
	 * 处理发送动作
	 * 
	 * @param socketAction
	 * @throws IOException
	 * @throws SocketUnconnectedException
	 * @throws UnsupportedEncodingException
	 * @throws SocketUninitializedException
	 */
	private void processSocketSend(final SocketAction socketAction) throws SocketUninitializedException, UnsupportedEncodingException, SocketUnconnectedException, IOException
	{
		boolean breakAfterSend = socketAction.getDisconnectAfterAction();
		// if (socketAction.getDataToSend() instanceof String)
		// {
		_Client.Send(socketAction.getDataToSend(), breakAfterSend);
		// }
	}

	/**
	 * 处理接收动作
	 * 
	 * @param socketAction
	 * @throws SocketUninitializedException
	 * @throws SocketUnconnectedException
	 * @throws IOException
	 */
	private void processSocketReceive(final SocketAction socketAction) throws SocketUninitializedException, SocketUnconnectedException, IOException
	{
		boolean breakAfterSend = socketAction.getDisconnectAfterAction();
		_Client.Receive(socketAction.getDataReceived(), breakAfterSend);
	}
}

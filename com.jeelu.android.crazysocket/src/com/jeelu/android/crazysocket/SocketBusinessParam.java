package com.jeelu.android.crazysocket;

import java.util.ArrayList;

@SocketBusinessParamDescription(description = "只发送，不接收，发送后断开连接")
public class SocketBusinessParam
{
	private String _Host;

	public void setHost(final String host)
	{
		_Host = host;
	}

	public String getHost()
	{
		return _Host;
	}

	private int _Port;

	public void setPort(final int Port)
	{
		_Port = Port;
	}

	public int getPort()
	{
		return _Port;
	}

	/**
	 * 连接超时
	 */
	private int _ConnectTimeout;

	public void setConnectTimeout(final int timeout)
	{
		_ConnectTimeout = timeout;
	}

	public int getConnectTimeout()
	{
		return _ConnectTimeout;
	}

	/**
	 * 发送超时
	 */
	private int _SendTimeout;

	public void setSendTimeout(final int timeout)
	{
		_SendTimeout = timeout;
	}

	public int getSendTimeout()
	{
		return _SendTimeout;
	}

	/**
	 * 接收超时
	 */
	private int _ReceiveTimeout;

	public void setReceiveTimeout(final int timeout)
	{
		_ReceiveTimeout = timeout;
	}

	public int getReceiveTimeout()
	{
		return _ReceiveTimeout;
	}

	/**
	 * Socket动作列表
	 */
	private ArrayList<SocketAction> _ActionList;

	public ArrayList<SocketAction> getActionList()
	{
		return _ActionList;
	}

	public void setActionList(final ArrayList<SocketAction> _ActionList)
	{
		this._ActionList = _ActionList;
	}
}

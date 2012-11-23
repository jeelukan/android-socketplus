package com.jeelu.android.Enums;

public enum SocketCommunicationDirection
{
	Send("发送"), Receive("接收");
	private final String display;

	private SocketCommunicationDirection(final String display)
	{
		this.display = display;
	}

	public String getDisplayName()
	{
		return display;
	}
}

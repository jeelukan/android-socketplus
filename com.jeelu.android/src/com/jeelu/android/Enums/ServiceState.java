package com.jeelu.android.Enums;

public enum ServiceState
{
	Stopped("已停止"), Started("已开始"), Paused("已暂停");

	private final String display;

	private ServiceState(String display)
	{
		this.display = display;
	}

	public String getDisplayName()
	{
		return display;
	}
}

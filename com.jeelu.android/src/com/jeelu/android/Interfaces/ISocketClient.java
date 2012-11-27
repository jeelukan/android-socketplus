package com.jeelu.android.Interfaces;

public interface ISocketClient
{
	public boolean buildSocket(String ipAddress, int port);

	public String sendMessage(String message);

	public boolean beginSendMessage(String message);

	public String beginAysnReplay();

	public boolean close();
}

package com.jeelu.android.Net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtility
{
	/**
	 * UDP����
	 * 
	 * @param strMessage
	 * @param strRemoteIP
	 * @param RemotePort
	 * @return
	 */
	public boolean UDPSend(String strMessage, String strRemoteIP, int RemotePort)
	{
		try
		{
			byte[] btMessageSource = strMessage.getBytes("Unicode"); //
			byte[] btMessage = new byte[btMessageSource.length - 2];
			for (int i = 0; i < btMessage.length; i++)
				//
				btMessage[i] = btMessageSource[i + 2];
			InetAddress RemoteIP = InetAddress.getByName(strRemoteIP);
			DatagramPacket packet = new DatagramPacket(btMessage, btMessage.length, RemoteIP, RemotePort);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);

			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * �ж�wifi����״̬
	 * 
	 * @param context
	 * @return
	 */
	public boolean IsWiFiOpen(Context context)
	{
		ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
			{
				for (int i = 0; i < info.length; i++)
				{
					if (info[i].getTypeName().equals("WIFI") && info[i].isConnected())
						return true;
				}
			}
		}

		return false;
	}
}

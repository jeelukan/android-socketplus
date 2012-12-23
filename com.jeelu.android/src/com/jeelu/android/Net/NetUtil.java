package com.jeelu.android.Net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil
{
	/**
	 * 向指定的IP地址以UDP方式发送消息
	 * 
	 * @param strMessage
	 * @param strRemoteIP
	 * @param RemotePort
	 * @return
	 */
	public static boolean UDPSend(final String strMessage, final String strRemoteIP, final int RemotePort)
	{
		try
		{
			byte[] btMessageSource = strMessage.getBytes("Unicode"); //
			byte[] btMessage = new byte[btMessageSource.length - 2];
			for (int i = 0; i < btMessage.length; i++)
			{
				btMessage[i] = btMessageSource[i + 2];
			}
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
	 * 测试WIFI是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean IsWiFiOpen(final Context context)
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
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 获取本机的IP地址
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalIpAddress() throws SocketException
	{
		String ipaddress = "";

		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
		{
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
			{
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress())
				{
					ipaddress = inetAddress.getHostAddress();
				}
			}
		}
		return ipaddress;
	}

}

package com.jeelu.android.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Util
{
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

package com.jeelu.android.crazysocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;

import android.util.Log;

import com.jeelu.android.Net.Common;

public class DNSService extends Thread
{
	public DatagramSocket socket;
	public Boolean isRuning = false;
	public Hashtable<String, String> dnsCache = new Hashtable<String, String>();

	public DNSService(final DatagramSocket skt)
	{
		socket = skt;
		isRuning = true;
	}

	@Override
	public void run()
	{
		final byte[] byteBuffer = new byte[1024];
		while (isRuning)
			try
			{
				Arrays.fill(byteBuffer, (byte) 0);
				final DatagramPacket dataPacket = new DatagramPacket(byteBuffer, 1024);
				socket.receive(dataPacket);
				Log.i(Common.TAG, "dns packet recv");
				final int len = dataPacket.getLength();
				final byte[] data = dataPacket.getData();
				if (len < 13)
					continue;
				final String strDomain = getRequestDomain(data);
				Log.d(Common.TAG, "Query Domain: " + strDomain);
				String strIPString = "";
				strIPString = QueryDomainName(strDomain);
				if (strIPString.length() <= 0)
					continue;
				final byte[] responseBuffer = BuildDNSResponsePacket(data, len, strIPString);
				final int replen = responseBuffer.length;
				final DatagramPacket resp = new DatagramPacket(responseBuffer, 0, replen);
				resp.setPort(dataPacket.getPort());
				resp.setAddress(InetAddress.getLocalHost());
				socket.send(resp);
				Log.i(Common.TAG, "response dns request success" + resp.getAddress().toString() + ":" + resp.getPort());
				SaveToHosts(strDomain, strIPString);
			}
			catch (final IOException e)
			{
				Log.e(Common.TAG, "DNS Recv IO Exception", e);
			}
			catch (final Exception e)
			{
				Log.e(Common.TAG, "DNS Recv Exception", e);
			}
	}

	private synchronized String getRequestDomain(final byte[] request)
	{
		String requestDomain = "";
		final int reqLength = request.length;
		if (reqLength > 13)
		{
			final byte[] question = new byte[reqLength - 12];
			System.arraycopy(request, 12, question, 0, reqLength - 12);
			requestDomain = getPartialDomain(question);
			requestDomain = requestDomain.substring(0, requestDomain.length() - 1);
		}
		return requestDomain;
	}

	private String getPartialDomain(final byte[] request)
	{
		String result = "";
		final int length = request.length;
		final int partLength = request[0];
		if (partLength == 0)
			return result;
		try
		{
			final byte[] left = new byte[length - partLength - 1];
			System.arraycopy(request, partLength + 1, left, 0, length - partLength - 1);
			result = new String(request, 1, partLength) + ".";
			result += getPartialDomain(left);
		}
		catch (final Exception e)
		{
			Log.e(Common.TAG, "getPartialDomin error", e);
		}
		return result;
	}

	public synchronized String QueryDomainName(final String domainString)
	{
		String result = "";
		if (dnsCache.containsKey(domainString))
		{
			result = dnsCache.get(domainString);
			Log.d(Common.TAG, "(cache)DNS Success: " + domainString + "---->" + result);
			return result;
		}
		result = QueryDomainOnNetwork(domainString);
		return result;
	}

	public String QueryDomainOnNetwork(final String domainString)
	{
		String result = "";
		try
		{
			final Socket skt = new Socket(Common.proxyHost, Common.proxyPort);
			skt.setSoTimeout(30000);
			final BufferedReader din = new BufferedReader(new InputStreamReader(skt.getInputStream()));
			final BufferedWriter dout = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));
			final String strRequest = "GET http://wap.ip138.com/ip.asp?ip=" + domainString + " HTTP/1.1\r\n" + "Host: wap.ip138.com\r\n" + "Accept: */*\r\n" + "User-Agent: " + Common.strUserAgent + "\r\n" + "Connection: Keep-Alive\r\n" + "\r\n";
			dout.write(strRequest);
			dout.flush();
			String line = "";
			final String httpStatusString = din.readLine();
			if (!httpStatusString.contains("200"))
			{
				skt.close();
				return result;
			}
			while ((line = din.readLine()) != null)
			{
				if (line.contains(domainString))
				{
					String startTagString = "&gt;&gt; ";
					final String endTagString = "<br/>";
					int start = line.indexOf(startTagString);
					if (start <= 0)
					{
						startTagString = ">> ";
						start = line.indexOf(">> ");
					}
					final int end = line.indexOf(endTagString, start);
					result = line.substring(start + startTagString.length(), end);
					if (result.contains("."))
					{
						Log.d(Common.TAG, "DNS Success: " + domainString + "---->" + result);
						dnsCache.put(domainString, result);
					}
					else
					{
						Log.d(Common.TAG, "DNS Faild: " + domainString + "---->" + result);
						result = "";
					}
					break;
				}
				if (line.contains("</body>"))
					break;
				if (line.contains("</wml>"))
					break;
			}
			skt.close();
		}
		catch (final IOException e)
		{
			Log.e(Common.TAG, "Dns on network io excpetion", e);
		}
		catch (final Exception e)
		{
			Log.e(Common.TAG, "Dns on network excpetion", e);
		}
		return result;
	}

	public synchronized byte[] BuildDNSResponsePacket(final byte[] request, final int reqLen, final String ipAddress)
	{
		final byte[] repPacket = new byte[1024];
		byte[] realResponsePacket = null;
		int length = 0;
		try
		{
			final byte[] byteID = new byte[2];
			System.arraycopy(request, 0, byteID, 0, 2);
			System.arraycopy(byteID, 0, repPacket, length, 2);
			length += 2;
			final byte[] other = new byte[] { (byte) 0x81, (byte) 0x80, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			System.arraycopy(other, 0, repPacket, length, 10);
			length += 10;
			System.arraycopy(request, 12, repPacket, length, reqLen - 12);
			length += (reqLen - 12);
			final byte[] answer = new byte[] { (byte) 0xc0, (byte) 0x0c, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0xbf, (byte) 0x00, (byte) 0x04 };
			System.arraycopy(answer, 0, repPacket, length, 12);
			length += 12;
			final byte[] addr = conver_inet_addr(ipAddress);
			System.arraycopy(addr, 0, repPacket, length, 4);
			length += 4;
			realResponsePacket = new byte[length];
			System.arraycopy(repPacket, 0, realResponsePacket, 0, length);
		}
		catch (final Exception e)
		{
			Log.e(Common.TAG, "build dns packet error", e);
		}
		return realResponsePacket;
	}

	public byte[] conver_inet_addr(String ipaddr)
	{
		final byte[] addr = new byte[4];
		String strTempString = ipaddr;
		int iTemp = 0;
		try
		{
			for (int i = 0; i < 4; i++)
			{
				int pos = ipaddr.indexOf(".");
				if (pos == -1)
					pos = ipaddr.length();
				strTempString = ipaddr.substring(0, pos);
				iTemp = Integer.valueOf(strTempString);
				addr[i] = (byte) (iTemp & 0xff);
				if (i != 3)
					ipaddr = ipaddr.substring(pos + 1);
			}
		}
		catch (final Exception e)
		{
			Log.e(Common.TAG, "conver_inet_addr error", e);
		}
		return addr;
	}

	public void CloseAll()
	{
		isRuning = false;
		if (!socket.isClosed())
			socket.close();
	}

	public void SaveToHosts(final String domain, final String ip)
	{
		try
		{
			String dnsHosts = ip + "\t\t\t" + domain;
			final File fe = new File("/system/etc/hosts");
			if (!fe.exists())
				fe.createNewFile();
			final FileReader fr = new FileReader(fe);
			final FileOutputStream fos = new FileOutputStream(fe, true);
			final BufferedReader bReader = new BufferedReader(fr);
			String line = "";
			while ((line = bReader.readLine()) != null)
			{
				Log.d("hosts", line);
				if (line.contains(domain))
				{
					bReader.close();
					fos.close();
					return;
				}
			}
			fr.close();
			dnsHosts = "\n" + dnsHosts;
			fos.write(dnsHosts.getBytes());
			fos.close();
			Log.d(Common.TAG, "Save To Hosts" + ip + "-->" + domain);
		}
		catch (final Exception e)
		{
			Log.e(Common.TAG, "save Dns error", e);
		}
	}
}

package com.jeelu.android.crazysocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/** SOCKS aware echo client */

public class SocksSocketClient implements Runnable
{

	// private final int port;
	// private InetAddress hostIP;

	private final Socket ss;
	private final InputStream in;
	private final OutputStream out;

	// private static final int BUF_SIZE = 1024;
	static final int defaultProxyPort = 1080; // Default Port
	static final String defaultProxyHost = "www-proxy"; // Default proxy

	public SocksSocketClient(String host, int port) throws IOException, UnknownHostException
	{
		// this.port = port;
		ss = new Socket(host, port);
		out = ss.getOutputStream();
		in = ss.getInputStream();
		System.out.println("Connected...");
		System.out.println("TO: " + host + ":" + port);
		System.out.println("ViaProxy: " + ss.getLocalAddress().getHostAddress() + ":" + ss.getLocalPort());
	}

	public void close() throws IOException
	{
		ss.close();
	}

	public void send(String s) throws IOException
	{
		out.write(s.getBytes());
	}

	public void run()
	{
		byte[] buf = new byte[1024];
		int bytes_read;
		try
		{
			while ((bytes_read = in.read(buf)) > 0)
			{
				System.out.write(buf, 0, bytes_read);
			}
		}
		catch (IOException io_ex)
		{
			io_ex.printStackTrace();
		}
	}

	public static void usage()
	{
		System.err.print("Usage: java SocksTest host port [socksHost socksPort]\n");
	}

	public static boolean TestSend(String host, int port, String msg)
	{
		// int proxyPort;
		// String proxyHost;

		try
		{
			// proxyPort = defaultProxyPort;
			// proxyHost = defaultProxyHost;
			// CProxy.setDefaultProxy(proxyHost,proxyPort,"KOUKY001");
			// Proxy.setDefaultProxy(proxyHost,proxyPort);

			// rsimac: commented out below line to make the source compile on
			// java 1.6
			// I believe below line was -only- disabling the proxy for
			// localhost.
			// TBD understand and fix properly
			// CProxy.getDefaultProxy().setDirect(InetAddress.getByName("localhost"));

			SocksSocketClient st = new SocksSocketClient(host, port);
			Thread thread = new Thread(st);
			thread.start();

			st.send(msg + "\r\n");
			st.close();
			return true;

			// }catch(SocksException s_ex){
			// System.err.println("SocksException:"+s_ex);
			// s_ex.printStackTrace();
			// return false;
		}
		catch (IOException io_ex)
		{
			io_ex.printStackTrace();
			return false;
		}
		catch (NumberFormatException num_ex)
		{
			usage();
			num_ex.printStackTrace();
			return false;
		}

	}

}// End of class


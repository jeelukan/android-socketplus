package com.jeelu.android.Net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
// import android.database.Cursor;
// import android.net.Uri;

public class Common
{

	// cmwap�Ĵ��������
	public static String proxyHost = "10.0.0.172";
	public static int proxyPort = 80;

	// User-Agent
	public static String strUserAgent = "Mozilla/5.0 (Linux; U; Android 1.0; en-us; generic) AppleWebKit/525.10+ (KHTML, like Gecko) Version/3.0.4 Mobile Safari/523.12.2";

	// ���Ա�־
	public static String TAG = "fuckCM";

	// ��������״̬
	public static String ServiceStatus = "SERVICESTATUS";
	public static int SERVICE_STOPPED = 0; // ����ֹͣ
	public static int SERVICE_RUNING = 1; // ��������

	public static String actionString = "com.xec.fuckcm.recv";

	// ��������
	public static int SERVICE_PORT = 48966; // ����˹����˿�
	public static int SERVICE_DNSPORT = 48953; // DNS�����˿�

	// ��������
	public static String remountFileSystem = "busybox mount -o rw,remount /system";

	// IPtables����
	public static String enableIPForward = "echo 1 > /proc/sys/net/ipv4/ip_forward";
	public static String disableIPForward = "echo 0 > /proc/sys/net/ipv4/ip_forward";

	public static String ipTable_command[] = { "iptables -t nat -A OUTPUT -o rmnet0 -p tcp --dport 80 --destination ! 10.0.0.0/8 -j DNAT --to-destination " + proxyHost + ":" + proxyPort, "iptables -t nat -A OUTPUT -o rmnet0 -p udp --dport 53  -j DNAT --to-destination 127.0.0.1:" + SERVICE_DNSPORT, "iptables -t nat -A OUTPUT -o rmnet0 -p tcp -m multiport --destination ! 10.0.0.0/8 --destination-port ! 80," + SERVICE_PORT + "," + SERVICE_DNSPORT + " -j LOG --log-level info --log-prefix \"fuckCM \"",
			"iptables -t nat -A OUTPUT -o rmnet0 -p tcp -m multiport --destination ! 10.0.0.0/8 --destination-port ! 80," + SERVICE_PORT + "," + SERVICE_DNSPORT + " -j DNAT --to-destination 127.0.0.1:" + SERVICE_PORT };

	public static String cleanIPTables = "iptables -F -t nat";

	/*
	 * ���ú���
	 */
	public static synchronized int rootCMD(String cmd)
	{
		int result = -1;
		ArrayList<String> strReader = new ArrayList<String>();
		ArrayList<String> strError = new ArrayList<String>();
		try
		{

			Log.d(TAG, "Root Command:" + cmd);

			Process process = Runtime.getRuntime().exec("su");
			OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());

			writer.write(cmd + "\n");
			writer.write("exit\n");
			writer.flush();

			result = doWaitFor(process, strReader, strError, "");
			if (result == 0)
				Log.d(TAG, cmd + " exec success");
			else
			{
				Log.d(TAG, cmd + " exec with result " + result);
				for (int i = 0; i < strError.size(); i++)
				{
					Log.w(TAG, strError.get(i));
				}
			}

			process.destroy();

		}
		catch (Exception e)
		{
			Log.e(Common.TAG, "root command error", e);
		}

		return result;
	}

	public static synchronized int doWaitFor(Process p, ArrayList<String> strReader, ArrayList<String> strError, String strFilter) throws Exception
	{
		int exitValue = -1; // returned to caller when p is finished

		InputStream in = p.getInputStream();
		InputStream err = p.getErrorStream();
		boolean finished = false; // Set to true when p is finished

		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		BufferedReader berror = new BufferedReader(new InputStreamReader(err));

		while (!finished)
		{
			try
			{
				while (bin.ready())
				{
					String strMessage = bin.readLine();
					if (strFilter.length() > 0)
					{
						if (strMessage.contains(strFilter))
							strReader.add(strMessage);
					}
					else
					{
						strReader.add(strMessage);
					}
					Log.d("logs", strMessage);
				}

				while (berror.ready())
				{
					String strMessage = berror.readLine();
					Log.e(TAG, strMessage);
					strError.add(strMessage);
				}
				exitValue = p.exitValue();
				finished = true;
			}
			catch (IllegalThreadStateException e)
			{
				testSleep(500);
			}
		}

		bin.close();
		berror.close();
		in.close();
		err.close();
		return exitValue;
	}// end of doWaitFor method

	/**
	 * ��ʱ����
	 */
	public static void testSleep(long time)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(time);
		}
		catch (InterruptedException e)
		{
		}
	}

	/**
	 * ��ȡapn������
	 */
	public static String getAPNName(Context context)
	{

		String apnName = "";

		// �ж��Ƿ�Ϊwifi
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo == null)
		{

			apnName = "none";
			return apnName;
		}

		if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
		{
			apnName = "wifi";
			return apnName;
		}

		Log.i(TAG, "current network:" + networkInfo.getExtraInfo());

		if (networkInfo.getExtraInfo().length() > 0)
		{

			apnName = networkInfo.getExtraInfo();
			return apnName;
		}

		// Cursor mCursor = context.getContentResolver().query(
		// Uri.parse("content://telephony/carriers"),
		// new String[] { "apn" }, "current=1", null, null);
		// if (mCursor != null) {
		// try {
		// if (mCursor.moveToFirst()) {
		// apnName = mCursor.getString(0);
		// return apnName;
		// }
		// } catch (Exception e) {
		// Log.e(TAG, "Can not get Network info", e);
		// } finally {
		// mCursor.close();
		// }
		// }

		return apnName;

	}
}

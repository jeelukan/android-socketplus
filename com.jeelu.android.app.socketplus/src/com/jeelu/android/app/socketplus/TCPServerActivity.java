package com.jeelu.android.app.socketplus;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TCPServerActivity extends Activity
{
	TextView _LogView;
	LinearLayout _ClientByListenListView;
	TextView _ServerIpTextView;
	Thread _MockThread;
	boolean _IsMock = false;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcpserver);

		_LogView = (TextView) findViewById(R.id.textview_tcpserver_logpanel);
		_ClientByListenListView = (LinearLayout) findViewById(R.id.linear_clientByListen_iplist);
		_ServerIpTextView = (TextView) findViewById(R.id.textview_serverinfo_ipaddress);

		try
		{
			_ServerIpTextView.setText(getLocalIpAddress());
		}
		catch (Exception e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		for (int i = 0; i < 3; i++)
		{
			CheckBox checkBox = new CheckBox(this);
			checkBox.setText("192.168.255." + i);
			_ClientByListenListView.addView(checkBox);
		}

		final Handler handler = new Handler()
		{
			@Override
			public void handleMessage(final Message msg)
			{
				_LogView.setText((String) msg.obj);
			}
		};

		Runnable mock = new Runnable()// 更新UI的模拟数据线程
		{
			public void run()
			{
				_IsMock = true;
				while (_IsMock)
				{
					String c = UUID.randomUUID().toString();
					String msg = new StringBuffer().append(c).append(_LogView.getText()).toString();
					Message message = Message.obtain();
					message.obj = msg;
					// 通过Handler发布传送消息，handler
					handler.sendMessage(message);
					try
					{
						Thread.sleep(350);
					}
					catch (InterruptedException e)
					{
						_IsMock = false;
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		_MockThread = new Thread(mock);
		_MockThread.start();
	}

	public String getLocalIpAddress()
	{
		String ipaddress = "";

		try
		{
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
		}
		catch (SocketException ex)
		{
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipaddress;
	}

	private static final String[] GENRES = new String[] { "Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama", "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller" };

	@Override
	protected void onStop()
	{
		if (_MockThread != null)
		{
			try
			{
				_IsMock = false;
			}
			catch (Exception e)
			{
				Log.w("myapp", e.getMessage());
			}
		}
		super.onStop();
	}
}

package com.jeelu.android.app.socketplus;

import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeelu.android.Net.NetUtil;

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
			_ServerIpTextView.setText(NetUtil.getLocalIpAddress());
		}
		catch (Exception e)
		{
			Log.w("myapp", e);
			_ServerIpTextView.setText("127.0.0.1");
		}

		for (int i = 0; i < 3; i++)
		{
			CheckBox checkBox = new CheckBox(this);
			checkBox.setTextSize(11);
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

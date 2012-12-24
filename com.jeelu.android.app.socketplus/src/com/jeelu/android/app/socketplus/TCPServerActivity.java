package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeelu.android.Net.AblySocketServer;
import com.jeelu.android.Net.NetUtil;

public class TCPServerActivity extends Activity
{
	AblySocketServer _SocketServer;

	TextView _LogView;
	LinearLayout _ClientByListenListView;
	TextView _ServerIpTextView;

	Thread _Thread;

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

		final Handler handler = new Handler()
		{
			@Override
			public void handleMessage(final Message msg)
			{
				_LogView.setText((String) msg.obj);
			}
		};
		creatSocketServer(handler);
	}

	private void creatSocketServer(final Handler handler)
	{
		Runnable runnable = new Runnable()
		{
			public void run()
			{
				_SocketServer = new AblySocketServer();
				try
				{
					_SocketServer.startServer(handler);
				}
				catch (Exception e)
				{
					Log.e("Socket", e.getMessage());
				}
			}
		};
		_Thread = new Thread(runnable);
		_Thread.setName("AblySocketServer");
		_Thread.start();
	}

	@Override
	protected void onStop()
	{
		_SocketServer.stopServer();
		_Thread.stop();
		super.onStop();
	}
}

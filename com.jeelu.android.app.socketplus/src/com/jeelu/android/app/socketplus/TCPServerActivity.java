package com.jeelu.android.app.socketplus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xsocket.connection.Server;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeelu.android.common.Util;
import com.jeelu.android.common.ui.SlipSwitch;
import com.jeelu.android.common.ui.SlipSwitch.OnSwitchListener;

public class TCPServerActivity extends Activity
{
	Server _SocketServer;

	TextView _LogPanel;
	LinearLayout _ClientByListenListView;
	TextView _ServerIpTextView;

	Thread _Thread;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcpserver);

		_LogPanel = (TextView) findViewById(R.id.textview_tcpserver_logpanel);
		_ClientByListenListView = (LinearLayout) findViewById(R.id.linear_clientByListen_iplist);
		_ServerIpTextView = (TextView) findViewById(R.id.textview_serverinfo_ipaddress);

		try
		{
			_ServerIpTextView.setText(Util.getLocalIpAddress());
		}
		catch (Exception e)
		{
			Log.w("app", e);
			_ServerIpTextView.setText("127.0.0.1");
		}

		switchbutton();

		creatSocketServer(new TcpServerUIHandler());
	}

	SlipSwitch _SlipSwitch;

	private void switchbutton()
	{
		_SlipSwitch = (SlipSwitch) findViewById(R.id.slipswitch_pause_server);
		_SlipSwitch.setImageResource(R.drawable.slipswitch_on, R.drawable.slipswitch_off, R.drawable.slipswitch_button);
		_SlipSwitch.setSwitchState(true);
		_SlipSwitch.setOnSwitchListener(new OnSwitchListener()
		{
			public void onSwitched(final boolean isSwitchOn)
			{
				if (isSwitchOn)
				{
					Toast.makeText(TCPServerActivity.this, "开关已经开启", 300).show();
				}
				else
				{
					Toast.makeText(TCPServerActivity.this, "开关已经关闭", 300).show();
				}
			}
		});
	}

	private void creatSocketServer(final Handler uiHandler)
	{
		Runnable runnable = new Runnable()
		{
			public void run()
			{
				try
				{
					SocketDataHandler processor = new SocketDataHandler();
					processor.setUIHandler(uiHandler);
					_SocketServer = new Server(9009, processor);
					_SocketServer.run();
				}
				catch (Exception ex)
				{
					Log.e("app", ex.getMessage());
				}
			}
		};

		_Thread = new Thread(runnable);
		_Thread.start();
	}

	@Override
	protected void onStop()
	{
		_SocketServer.close();
		_Thread.stop();
		super.onStop();
	}

	@SuppressLint("HandlerLeak")
	class TcpServerUIHandler extends Handler
	{
		@Override
		public void handleMessage(final Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
				case 0:// 数据
					Date now = new Date();
					SimpleDateFormat format = (SimpleDateFormat) DateFormat.getTimeInstance();
					format.applyPattern("mm:ss:SSS");
					StringBuffer buffer = new StringBuffer(_LogPanel.getText());
					buffer.insert(0, "\n");
					buffer.insert(0, msg.obj);
					buffer.insert(0, "\n");
					buffer.insert(0, format.format(now));
					_LogPanel.setText(buffer.toString());
					break;
				case 1:
				case 2:
				case -1:
				default:
					break;
			}
		}
	}
}

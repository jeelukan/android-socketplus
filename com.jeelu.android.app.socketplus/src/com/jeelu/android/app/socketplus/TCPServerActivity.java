package com.jeelu.android.app.socketplus;

import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class TCPServerActivity extends Activity
{
	TextView _LogView;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcpserver);
		_LogView = (TextView) findViewById(R.id.textview_tcpserver_logpanel);

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
				for (int j = 0; j < 10000; j++)
				{
					String c = UUID.randomUUID().toString();
					String msg = new StringBuffer().append(c).append(_LogView.getText()).toString();
					Message message = Message.obtain();
					message.obj = msg;
					// 通过Handler发布传送消息，handler
					handler.sendMessage(message);
					try
					{
						Thread.sleep(500);
					}
					catch (InterruptedException e)
					{
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		new Thread(mock).start();
	}
}

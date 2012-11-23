package com.jeelu.android.socketplus;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;
import com.jeelu.android.Interfaces.ISocketClient;

public class SocketClientActivity extends Activity
{
	@InjectView(R.id.editTextIp)
	private EditText _IpAddressEditText;
	private EditText port, msg, log;
	private Button connect, send;
	private String logContent = "";
	private ISocketClient _SocketClient;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socketclient);
		_IpAddressEditText = (EditText) findViewById(R.id.editTextIp);
		port = (EditText) findViewById(R.id.editTextPort);
		msg = (EditText) findViewById(R.id.editTextSendContent);
		log = (EditText) findViewById(R.id.editTextLog);
		connect = (Button) findViewById(R.id.buttonConnect);
		connect.setOnClickListener(new SocketBuilderClick());
		send = (Button) findViewById(R.id.buttonSend);
		send.setOnClickListener(new SocketSenderClick());
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_socketclient, menu);
		return true;
	}

	/**
	 * 将接收的内容显示在日志文本框内
	 * 
	 * @param msg
	 */
	private void appendMessage(final String msg)
	{
		logContent = msg + "\r\n" + logContent;
		log.setText(logContent);
	}

	/**
	 * 通过接口的函数注入一个SocketClient
	 * 
	 * @param socketClient
	 *            指定的被注入的SocketClient
	 */
	@Inject
	public void setService(final ISocketClient socketClient)
	{
		_SocketClient = socketClient;
	}

	private final class SocketSenderClick implements OnClickListener
	{
		@Override
		public void onClick(final View v)
		{
			if (_SocketClient == null)
				return;
			final String message = msg.getText().toString();
			final String replayMsg = _SocketClient.sendMessage(message);
			appendMessage(replayMsg);
		}
	}

	private final class SocketBuilderClick implements OnClickListener
	{
		@Override
		public void onClick(final View v)
		{
			if (_SocketClient == null)
				return;
			final String host = _IpAddressEditText.getText().toString();
			final String portStr = port.getText().toString();
			_SocketClient.buildSocket(host, Integer.parseInt(portStr));
		}
	}
}

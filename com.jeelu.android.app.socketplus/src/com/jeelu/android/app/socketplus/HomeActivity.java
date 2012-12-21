package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity// RoboActivity
{
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button button_start_tcpserver = (Button) findViewById(R.id.button_start_tcpserver);
		Button button_start_tcpclient = (Button) findViewById(R.id.button_start_tcpclient);
		Button button_start_udpserver = (Button) findViewById(R.id.button_start_udpserver);
		Button button_start_udpclient = (Button) findViewById(R.id.button_start_udpclient);
		Button button_manager_protocol = (Button) findViewById(R.id.button_manager_protocol);
		Button button_lefthandtoolbox = (Button) findViewById(R.id.button_lefthandtoolbox);

		button_start_tcpserver.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View view)
			{
				Intent i = new Intent(getApplicationContext(), TCPServerActivity.class);
				startActivity(i);
			}
		});

		button_start_tcpclient.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View view)
			{
				Intent i = new Intent(getApplicationContext(), TCPClientActivity.class);
				startActivity(i);
			}
		});

		button_start_udpserver.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View view)
			{
				Intent i = new Intent(getApplicationContext(), UDPServerActivity.class);
				startActivity(i);
			}
		});

		button_start_udpclient.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View view)
			{
				Intent i = new Intent(getApplicationContext(), UDPClientActivity.class);
				startActivity(i);
			}
		});

		button_manager_protocol.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View view)
			{
				Intent i = new Intent(getApplicationContext(), ProtocolManagerActivity.class);
				startActivity(i);
			}
		});

		button_lefthandtoolbox.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View view)
			{
				Intent i = new Intent(getApplicationContext(), LeftHandToolboxActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_home, menu);
		// MenuItem menuItem = null;
		// 为菜单项注册监听器
		// menuItem.setOnMenuItemClickListener(new MyMenuItemClickListener());
		return true;
	}

	/*
	 * 使用onCreateDialog(int)来管理对话框的状态, 那么每次对话框被解除时, 该对话框对象的状态会被Activity保存.调用 removeDialog(int)将所有该对象的内部引用移除 如本程序那样,如果不加removeDialog，那么显示的是第一次的内容
	 */
	@Override
	protected Dialog onCreateDialog(final int id)
	{
		Dialog dialog = null;
		switch (id)
		{
		// case R.id.buildServerButton:
		// case R.id.buildClientButton:
		// SocketCreatorDialog.Builder builder = new SocketCreatorDialog.Builder(this);
		// builder.setTitle(R.string.buildServerDialogTitle);
		// builder.setMessage(R.string.demoText);
		// DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
		// {
		// public void onClick(final DialogInterface dialog, final int which)
		// {
		// // 将此处移除后，无论怎么修改EditText内容
		// // 每次点击显示普通对话框，总是第一次的内容
		// // 如果想更新EditText内容就得加removeDialog
		// // removeDialog(SERVER_BUILDER_DIALOG);
		// }
		// };
		// builder.setPositiveButton(R.string.okTextButton, listener);
		// dialog = builder.create();
		// break;
			default:
				break;
		}
		Log.e("onCreateDialog", "onCreateDialog");
		return dialog;
	}

	// 第一步：创建监听器类
	class MyMenuItemClickListener implements OnMenuItemClickListener
	{
		public boolean onMenuItemClick(final MenuItem item)
		{
			// do something here...
			return true; // finish handling
		}
	}

}

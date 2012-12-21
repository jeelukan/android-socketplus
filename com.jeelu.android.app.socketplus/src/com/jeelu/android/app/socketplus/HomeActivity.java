package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class HomeActivity extends Activity// RoboActivity
{
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);
		// setContentView(R.layout.activity_home);
		//
		// final Resources resources = getResources();
		// final Window window = getWindow();
		// final Drawable drawable = resources.getDrawable(R.drawable.mainColor);
		// // 设置背景色为本应用的主色调
		// window.setBackgroundDrawable(drawable);
		//
		// _ServerBuilderButton = (Button) findViewById(R.id.buildServerButton);
		// // 当点击“创建Server”时
		// _ServerBuilderButton.setOnClickListener(new OnClickListener()
		// {
		// public void onClick(final View v)
		// {
		// if (v.getId() == R.id.buildServerButton)
		// showDialog(R.id.buildServerButton);
		// }
		// });
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

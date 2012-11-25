package com.jeelu.android.app.socketplus.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.jeelu.android.app.socketplus.R;

/**
 * SocketServer创建器。当创建Server时需要弹出一个Dialog来填写相关参数。 如：监听的端口，协议的匹配等等。
 */
public class ServerBuilderDialog extends Dialog
{
	TextView _TitleTextView;

	public ServerBuilderDialog(final Context context)
	{
		super(context);
		// 取消自定义dialog的标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_build_server);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setTitle(final CharSequence title)
	{
		_TitleTextView = (TextView) findViewById(R.id.buildServerTitleTextview);
		if (_TitleTextView != null)
			_TitleTextView.setText(title);
	}
}

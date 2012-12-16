package com.jeelu.android.app.socketplus.dialogs;

import roboguice.inject.InjectView;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.jeelu.android.app.socketplus.R;

/**
 * SocketServer创建器。当创建Server时需要弹出一个Dialog来填写相关参数。 如：监听的端口，协议的匹配等等。
 */
public class ServerBuilderDialog extends Dialog
{
	@InjectView(R.id.buildServerDialogConfirmButton)
	Button _ConfirmButton;
	@InjectView(R.id.buildServerDialogCancelButton)
	Button _CancelButton;

	public ServerBuilderDialog(final Context context)
	{
		super(context);
		// 取消自定义dialog的标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_build_server);
		// setNegativeButton("不喜欢", ocl);
		// setNeutralButton("一般般", ocl);
		// setPositiveButton("很喜欢", ocl);
	}

	// 对话框按钮点击事件监听器
	OnClickListener ocl = new OnClickListener()
	{
		public void onClick(final DialogInterface dialog, final int which)
		{
			switch (which)
			{
				case DialogInterface.BUTTON_NEGATIVE:
					Toast.makeText((Context) dialog, "我不喜欢他的电影。", Toast.LENGTH_LONG).show();
					break;
				case DialogInterface.BUTTON_NEUTRAL:
					Toast.makeText((Context) dialog, "说不上喜欢不喜欢。", Toast.LENGTH_LONG).show();
					break;
				case DialogInterface.BUTTON_POSITIVE:
					Toast.makeText((Context) dialog, "我很喜欢他的电影。", Toast.LENGTH_LONG).show();
					break;
			}
		}
	};

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		_CancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(final View v)
			{

			}
		});
	}
}

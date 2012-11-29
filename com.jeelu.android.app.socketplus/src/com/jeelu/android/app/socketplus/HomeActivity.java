package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.jeelu.android.app.socketplus.dialogs.ServerBuilderDialog;

public class HomeActivity extends Activity
{
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		final Resources resources = getResources();
		final Window window = getWindow();
		final Drawable drawable = resources.getDrawable(R.drawable.mainColor);
		// 设置背景色为本应用的主色调
		window.setBackgroundDrawable(drawable);

		final Button btn = (Button) findViewById(R.id.buildServerButton);
		btn.setOnClickListener(new BuildServierListener());
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	/**
	 * 当点击“创建Server”时
	 * 
	 * @author jeelukan@gmail.com
	 * 
	 */
	class BuildServierListener implements OnClickListener
	{
		public void onClick(final View v)
		{
			if (v.getId() == R.id.buildServerButton)
			{
				Dialog dialog = new ServerBuilderDialog(HomeActivity.this);
				dialog.setOnDismissListener(new CloseListener());
				dialog.show();
			}
		}
	}

	class CloseListener implements OnDismissListener
	{
		public void onDismiss(final DialogInterface dialog)
		{
			startActivity(new Intent(getApplication(), SocketClientActivity.class));
			finish();
		}
	}
}

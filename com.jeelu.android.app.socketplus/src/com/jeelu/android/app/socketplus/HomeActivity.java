package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

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
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
}

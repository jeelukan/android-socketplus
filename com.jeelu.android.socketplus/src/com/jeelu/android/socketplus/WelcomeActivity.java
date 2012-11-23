package com.jeelu.android.socketplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends Activity
{
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		final Handler x = new Handler();
		x.postDelayed(new Splashhandler(), 4000);
	}

	class Splashhandler implements Runnable
	{
		@Override
		public void run()
		{
			startActivity(new Intent(getApplication(), SocketClientActivity.class));
			finish();
		}
	}
}
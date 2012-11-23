package com.jeelu.android.app.socketplus;

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
		// 欢迎页。设置为全屏显示。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// 停留4秒钟，以保证相关的视觉信息的传递。4秒钟后跳转到Home页。
		final Handler x = new Handler();
		x.postDelayed(new Splashhandler(), 4000);
	}

	/**
	 * 欢迎页的动作处理
	 * 
	 * @author jeelukan@gmail.com
	 */
	class Splashhandler implements Runnable
	{
		public void run()
		{
			startActivity(new Intent(getApplication(), HomeActivity.class));
			finish();
		}
	}
}
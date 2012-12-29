package com.jeelu.android.socketplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.jeelu.android.common.ui.DashboardLayout;

public class WelcomeActivity extends Activity
{
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		System.out.println(DashboardLayout.class.getName());
		// 欢迎页。设置为全屏显示。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// 定义一个4秒钟的渐变动画,显示4秒钟后自动消失
		ImageView img = (ImageView) findViewById(R.id.imageView_welcome_background);
		AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
		animation.setDuration(1200);
		img.startAnimation(animation);// 开始播放动画
		animation.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(final Animation animation)
			{
				Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
				WelcomeActivity.this.startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(final Animation animation)
			{
			}

			@Override
			public void onAnimationStart(final Animation animation)
			{
			}

		});
	}
}
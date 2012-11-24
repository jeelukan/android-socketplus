package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

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

	/**
	 * 创建一个包含自定义view的PopupWindow
	 * 
	 * @param cx
	 * @return
	 */
	private PopupWindow makePopupWindow(final Context cx)
	{
		final Resources res = cx.getResources();
		final PopupWindow window = new PopupWindow(cx);
		window.setBackgroundDrawable(new ColorDrawable(res.getColor(R.drawable.whiteColor)));

		final TextView textView = new TextView(cx);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(R.drawable.whiteColor);
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		textView.setText("创建Server么？");
		// 设置PopupWindow显示和隐藏时的动画
		window.setAnimationStyle(R.style.AnimationFade);
		// 设置PopupWindow的大小（宽度和高度）
		window.setWidth(res.getDimensionPixelSize(R.dimen.buildServerPopwinWidth));
		window.setHeight(res.getDimensionPixelSize(R.dimen.buildServerPopwinHeight));
		// 设置PopupWindow的内容view
		window.setContentView(textView);
		// 设置PopupWindow外部区域是否可触摸
		window.setOutsideTouchable(true);
		return window;
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
			Context cx = HomeActivity.this;
			if (v.getId() == R.id.buildServerButton)
			{
				PopupWindow popupWin = makePopupWindow(cx);
				popupWin.showAtLocation(v, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
			}
		}
	}
}

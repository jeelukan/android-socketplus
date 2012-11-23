package com.jeelu.android.app.socketplus;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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

		final Button btn = (Button) findViewById(R.id.startServerButton);
		btn.setOnClickListener(new StartServierListener());
	}

	class StartServierListener implements OnClickListener
	{

		public void onClick(final View v)
		{
			final Context cx = HomeActivity.this;
			if (v.getId() == R.id.startServerButton)
			{
				PopupWindow popupWin = makePopupWindow(cx);
				popupWin.showAtLocation(v, Gravity.RIGHT | Gravity.BOTTOM, 10, 10);
			}
		}
	}

	/**
	 * 创建一个包含自定义view的PopupWindow
	 * 
	 * @param cx
	 * @return
	 */
	private PopupWindow makePopupWindow(final Context cx)
	{
		final PopupWindow window = new PopupWindow(cx);
		final TextView textView = new TextView(cx);
		textView.setGravity(Gravity.CENTER);
		final Resources res = cx.getResources();
		// contentView.setBackgroundColor(R.color.page_window_bgcolor);
		// window.setBackgroundDrawable(new
		// ColorDrawable(res.getColor(R.color.page_window_bgcolor)));
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// 设置PopupWindow显示和隐藏时的动画
		window.setAnimationStyle(R.style.AnimationFade);
		// 设置PopupWindow的大小（宽度和高度）
		window.setWidth(res.getDimensionPixelSize(R.dimen.page_window_width));
		window.setHeight(res.getDimensionPixelSize(R.dimen.page_window_height));
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
}

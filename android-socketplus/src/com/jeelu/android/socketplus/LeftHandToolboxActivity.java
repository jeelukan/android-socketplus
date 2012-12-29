package com.jeelu.android.socketplus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class LeftHandToolboxActivity extends Activity
{

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_left_hand_toolbox);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_left_hand_toolbox, menu);
		return true;
	}

}

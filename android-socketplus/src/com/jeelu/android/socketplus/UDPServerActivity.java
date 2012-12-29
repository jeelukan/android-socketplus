package com.jeelu.android.socketplus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class UDPServerActivity extends Activity
{

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_udpserver);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_udpserver, menu);
		return true;
	}

}

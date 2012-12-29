package com.jeelu.android.socketplus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ProtocolManagerActivity extends Activity
{

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protocol_manager);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_protocol_manager, menu);
		return true;
	}

}

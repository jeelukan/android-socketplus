package com.jeelu.android.app.socketplus.common;

import java.util.List;

import android.app.Application;

import com.google.inject.Module;

public class GuiceApplication extends Application
{
	protected void addApplicationModules(final List<Module> modules)
	{
		modules.add(new GuiceModule());
	}
}

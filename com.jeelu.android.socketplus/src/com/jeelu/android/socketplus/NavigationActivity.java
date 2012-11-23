package com.jeelu.android.socketplus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NavigationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_navigation, menu);
        return true;
    }
}

package com.jeelu.android.app.socketplus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SocketClientActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_socket_client, menu);
        return true;
    }
}

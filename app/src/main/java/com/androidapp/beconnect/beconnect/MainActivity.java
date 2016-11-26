package com.androidapp.beconnect.beconnect;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.onebeacon.api.OneBeacon;
import io.onebeacon.api.ScanStrategy;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    Button bLogin;
    private MonitorService mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(LoginIntent);
            }
        });

        if (!bindService(new Intent(this, MonitorService.class), (ServiceConnection) this, BIND_AUTO_CREATE)) {
            setTitle("Bind failed! Manifest?");
        }

    }


    @Override
    protected void onDestroy() {
        // Activity is gone, set scan mode to use lowest possible power usage
        OneBeacon.setScanStrategy(ScanStrategy.LOW_POWER);
        if (null != mService) {
            // optionally stop the service if running in background is not desired
//            stopService(new Intent(this, MonitorService.class));
            unbindService(this);
            mService = null;
        }
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = ((MonitorService.LocalServiceBinder) service).getService();
        setTitle("Service connected");

        // make the service to stick around by actually starting it
        startService(new Intent(this, MonitorService.class));

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
        setTitle("Service disconnected");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Activity is visible, scan with most reliable results
        OneBeacon.setScanStrategy(ScanStrategy.LOW_LATENCY);
    }

    @Override
    protected void onPause() {
        // Activity is not in foreground, make a trade-off between battery usage and scan latency
        OneBeacon.setScanStrategy(ScanStrategy.BALANCED);
        super.onPause();
    }
}

package com.androidapp.beconnect.beconnect;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class News extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;

    Button bDetails1;
    Button bDetails2;
    Button bDetails3;
    Button bDetails4;
    Button bDetails5;
    Button bDetails6;
    Button bDetails7;
    Button bDetails8;
    Button bDetails9;
    Button bDetails10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        bDetails1 = (Button) findViewById(R.id.bDetails1);

        bDetails1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails2 = (Button) findViewById(R.id.bDetails2);

        bDetails2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails3 = (Button) findViewById(R.id.bDetails3);

        bDetails3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails4 = (Button) findViewById(R.id.bDetails4);

        bDetails4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails5 = (Button) findViewById(R.id.bDetails5);

        bDetails5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails6 = (Button) findViewById(R.id.bDetails6);

        bDetails6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails7 = (Button) findViewById(R.id.bDetails7);

        bDetails7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails8 = (Button) findViewById(R.id.bDetails8);

        bDetails8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails9 = (Button) findViewById(R.id.bDetails9);

        bDetails9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        bDetails10 = (Button) findViewById(R.id.bDetails10);

        bDetails10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsDetailsIntent = new Intent(News.this, NewsDetails.class);
                News.this.startActivity(NewsDetailsIntent);
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 檢查是否有可用的藍牙裝置
        if (mBluetoothAdapter == null) {
            // 若無可用裝置時執行
            Toast.makeText(this, "Bluetooth not supported on this Device", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 如果藍牙目前不可用，請求使用者開啟藍芽功能。
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    // 使用onActivityResult 接收其他 Activity回傳的資料
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 接收請求開啟藍芽功能的結果
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 使用者授權藍牙後執行
            Toast.makeText(this, "使用者已授權藍牙使用", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            // 使用者拒絕授權藍牙後執行
            Toast.makeText(this, "使用者拒絕授權藍牙使用", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
}

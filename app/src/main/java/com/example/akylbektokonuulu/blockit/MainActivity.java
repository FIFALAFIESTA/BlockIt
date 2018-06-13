package com.example.akylbektokonuulu.blockit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    final String ChangeWifi = Manifest.permission.CHANGE_WIFI_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (checkSelfPermission(ChangeWifi) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);

        if (checkSelfPermission(ChangeWifi) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View v){

        if(v.getId() == R.id.notif_list){
            Intent intent = new Intent(this, Notifications.class);
            startActivity(intent);
        }
    }
}

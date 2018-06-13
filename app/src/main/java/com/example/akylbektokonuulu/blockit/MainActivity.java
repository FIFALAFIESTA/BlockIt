package com.example.akylbektokonuulu.blockit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.akylbektokonuulu.blockit.history.history;
import com.example.akylbektokonuulu.blockit.history.history_entry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {
    //history History;
    history History = new history();

    final String ChangeWifi = Manifest.permission.CHANGE_WIFI_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (checkSelfPermission(ChangeWifi) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);

        if (checkSelfPermission(ChangeWifi) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        /* Get the history */
        try {
            History.get_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* Update the history */
        try {
            History.set_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buttonClicked(View v) {

        if (v.getId() == R.id.notif_list) {
            Intent intent = new Intent(this, Notifications.class);
            startActivity(intent);
        }
    }
}
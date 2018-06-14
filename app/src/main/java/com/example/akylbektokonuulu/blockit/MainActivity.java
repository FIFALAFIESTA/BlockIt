package com.example.akylbektokonuulu.blockit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.akylbektokonuulu.blockit.history.history;
import com.example.akylbektokonuulu.blockit.history.history_entry;

import java.io.IOException;

public class MainActivity extends Activity {
    history History;
    TextView ruleList;

    final String Write = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    final String Read = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Write) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);

        if (checkSelfPermission(Read) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        History = new history();
        /* Get the history */
        /*try {
            History.get_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*ruleList = findViewById(R.id.rule_list);
        ruleList.setText("");
        for (int i=0; i<History.data.size(); i++) {
            history_entry tmp = History.data.get(i);
            String temp = tmp.appName +" "+ tmp.time +" "+ tmp.keyword +" "+ tmp.category +" "+ tmp.isClicked +" "+ tmp.appRate +" "+ "\n" + ruleList.getText();
            ruleList.setText(temp);
        }
*/
        //ruleList.setText("alooooooha!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*try {
            History.get_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*ruleList.setText("");
        for (int i=0; i<History.data.size(); i++) {
            history_entry tmp = History.data.get(i);
            String temp = tmp.appName +" "+ tmp.time +" "+ tmp.keyword +" "+ tmp.category +" "+ tmp.isClicked +" "+ tmp.appRate +" "+ "\n" + ruleList.getText();
            ruleList.setText(temp);
        }*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /*try {
            History.get_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*ruleList.setText("");
        for (int i=0; i<History.data.size(); i++) {
            history_entry tmp = History.data.get(i);
            String temp = tmp.appName +" "+ tmp.time +" "+ tmp.keyword +" "+ tmp.category +" "+ tmp.isClicked +" "+ tmp.appRate +" "+ "\n" + ruleList.getText();
            ruleList.setText(temp);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void buttonClicked(View v) {

        if (v.getId() == R.id.notif_list) {
            Intent intent = new Intent(this, Notifications.class);
            startActivity(intent);
        }
    }
}
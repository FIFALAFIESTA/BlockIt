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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {
    history History;
    TextView ruleList;
    File MYFILE;
    String name;

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

        name = "hist.txt";
        MYFILE = new File(getExternalFilesDir("BLOCKIT"), name);
        History = new history();
        /* Get the history */
        /*try {
            get_history();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ruleList = findViewById(R.id.rule_list);
        ruleList.setText("");
        for (int i=0; i<History.data.size(); i++) {
            history_entry tmp = History.data.get(i);
            String temp = tmp.appName +" "+ tmp.time +" "+ tmp.keyword +" "+ tmp.category +" "+ tmp.isClicked +" "+ tmp.appRate +" "+ "\n" + ruleList.getText();
            ruleList.setText(temp);
        }*/

        //ruleList.setText("alooooooha!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*try {
            get_history();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ruleList.setText("");
        for (int i=0; i<History.data.size(); i++) {
            history_entry tmp = History.data.get(i);
            String temp = tmp.appName +" "+ tmp.time +" "+ tmp.keyword +" "+ tmp.category +" "+ tmp.isClicked +" "+ tmp.appRate +" "+ "\n" + ruleList.getText();
            ruleList.setText(temp);
        }*/
    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
        try {
            get_history();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ruleList.setText("");
        for (int i=0; i<History.data.size(); i++) {
            history_entry tmp = History.data.get(i);
            String temp = tmp.appName +" "+ tmp.time +" "+ tmp.keyword +" "+ tmp.category +" "+ tmp.isClicked +" "+ tmp.appRate +" "+ "\n" + ruleList.getText();
            ruleList.setText(temp);
        }
    }
*/
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

    public history_entry parse(String str) {

        ArrayList<String> features = new ArrayList<String>(0);

        String s = "";
        int index = 0;
        str = str + " ";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                s = s + str.charAt(i);
            } else {
                if (s.equals("")) continue;
                features.add(s);
                s = "";
            }
        }
        //  Log.v("MainActivity", "MAC : " + MAC);
        //  Log.v("MainActivity", "RSSI : " + String.valueOf(RSSI));
        history_entry ans = new history_entry(features.get(0), features.get(1), features.get(2), features.get(3), features.get(4), features.get(5),features.get(6));

        return ans;
    }

    public void get_history() throws IOException {

        MYFILE.createNewFile();
        FileInputStream is = new FileInputStream(MYFILE);
        DataInputStream in = new DataInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //data.clear();
        //ArrayList<history_entry> data = new ArrayList<>();

        String mydata = "";
        while ((strLine = br.readLine()) != null) {
            history_entry temp = parse(strLine);
            boolean ok = true;
            int index = 0;
            History.data.add(temp);
        }
        is.close();
        in.close();
        //  if(!History.data.isEmpty())
        //    Log.v("this", "get_history: " + String.valueOf(History.data.size()));
        //else Toast.makeText(this,"fu",Toast.LENGTH_SHORT).show();
    }
}
package com.example.akylbektokonuulu.blockit;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.akylbektokonuulu.blockit.history.history;
import com.example.akylbektokonuulu.blockit.history.history_entry;

import java.io.IOException;
import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    history History;
    private TextView txtView;
    private NotificationReceiver nReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        txtView = (TextView) findViewById(R.id.textView);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver,filter);

        History = new history();
        try {
            History.get_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            History.set_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        unregisterReceiver(nReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            History.set_history(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void buttonClicked(View v){

        if(v.getId() == R.id.btnCreateNotify){
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder ncomp = new NotificationCompat.Builder(this);
            ncomp.setContentTitle("My Notification");
            ncomp.setContentText("Notification Listener Service Example");
            ncomp.setTicker("Notification Listener Service Example");
            ncomp.setSmallIcon(R.mipmap.ic_launcher);
            ncomp.setAutoCancel(true);
            nManager.notify((int)System.currentTimeMillis(),ncomp.build());
        }
        else if(v.getId() == R.id.btnClearNotify){
            Intent i = new Intent("NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            i.putExtra("command","clearall");
            sendBroadcast(i);
        }
        else if(v.getId() == R.id.btnListNotify){
            Intent i = new Intent("NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            i.putExtra("command","list");
            sendBroadcast(i);
        }
    }


    Double NB_List_Class (history_entry features, String condition){
        Double answer = 1.0;
        int how_many_features = 6;


        for(int i=0; i<how_many_features; i++) {
            Double count1 = 0.0, count2 = 0.0;

            for(int j=0; j<History.data.size(); j++) {
                if(features.get(i).equals(History.data.get(j)) && condition.equals(History.data.get(6))) {
                    if (!features.get(i).equals("null") && !condition.equals("null"))
                        count1++;
                }

                if(condition.equals(History.data.get(6))) {
                    if (!condition.equals("null"))
                        count2++;
                }
            }

            answer = answer * count1 * 1.0 / count2*1.0;

        }



        return answer;

    }
    double NB_List_finalClass (history_entry entry, String give_condition){
        Double answer ;
        ArrayList<String> classes = new ArrayList<String>();
        classes.add("accept"); classes.add("postpone"); classes.add("decline");

        Double numerator = NB_List_Class(entry, give_condition) * 0.33333333333;
        Double denumenator = 0.0;

        for(int i=0; i<3; i++) {
            Double cur = NB_List_Class(entry, classes.get(i))* 0.33333333333;
            denumenator += cur;
        }


        return (numerator+1.0) * 1.0 / (denumenator+2.0) * 1.0;
    }

    public ArrayList<Double> NB_ALL(history_entry entry){

        ArrayList<Double> probability_of_action = new ArrayList<Double>();

        ArrayList<String> classes = new ArrayList<String>();
        classes.add("accept"); classes.add("postpone"); classes.add("decline");

        for(int i=0; i<3; i++) {
            Double cur = NB_List_finalClass(entry, classes.get(i));
            probability_of_action.add(cur);
        }

        return probability_of_action;
    }
}

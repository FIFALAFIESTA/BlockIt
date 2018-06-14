package com.example.akylbektokonuulu.blockit;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akylbektokonuulu.blockit.history.history;
import com.example.akylbektokonuulu.blockit.history.history_entry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import weka.gui.Main;

public class Notifications extends AppCompatActivity {
    history History;
    String name;
    File MYFILE;
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

        name = "hist.txt";
        MYFILE = new File(getExternalFilesDir("BLOCKIT"), name);

        History = new history();

        try {
            get_history();
            //if(!History.data.isEmpty())
             //   Log.v("this", String.valueOf(History.data.size()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            //History.get_history(this);
            //if(!History.data.isEmpty())
            //Log.v("this", String.valueOf(History.data.size()));
            set_history();
        } catch (IOException e) {
            e.printStackTrace();
        }
        unregisterReceiver(nReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            //History.get_history(this);
            //if(!History.data.isEmpty())
            //Log.v("this", String.valueOf(History.data.size()));
            set_history();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //History.data.add(new history_entry("t", "y", "n", "t", "y", "k"));
            String stringExtra = intent.getStringExtra("notification_event");
            history_entry tmp = parse(stringExtra);
            ArrayList<Double> action = new ArrayList<Double>(3);
            action =        NB_ALL(tmp);

            Log.v("this",String.valueOf(action.get(0)) + " ---- " + String.valueOf(action.get(1))  + " ---- " +  String.valueOf(action.get(2)) );
//            Toast.makeText(this, String.valueOf(1.1),Toast.LENGTH_SHORT).show();
            // String.valueOf(action.get(0)) + "\n" + String.valueOf(action.get(1))  + "\n" +  String.valueOf(action.get(2))


            History.data.add(tmp);

            String temp = intent.getStringExtra("notification_event") + "\n" + txtView.getText();
            txtView.setText(temp);
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
        Double answer = 0.9999999999;
        int how_many_features = 7;


        for(int i=0; i<how_many_features; i++) {
            Double count1 = 0.0, count2 = 0.0;

            for(int j=0; j<History.data.size(); j++) {
                if(features.get(i).equals(History.data.get(j).get(i)) && condition.equals(History.data.get(j).get(6))) {
                    //if (!features.get(j).equals("null") && !condition.equals("null"))
                        count1++;
                }

                if(condition.equals(History.data.get(j).get(6))) {
                    //if (!condition.equals("null"))
                        count2++;
                }
            }
            if( count2 != 0)
                answer = answer * (count1  * 1.0) / (count2 )*1.0;
            Log.v("this",String.valueOf(count1) + " ///// " + String.valueOf(count2)  + " //// " +  String.valueOf(condition) );
        }

        if(answer == 1.0) answer = 0.0;

        return answer;

    }
    double NB_List_finalClass (history_entry entry, String give_condition){
        Double answer ;
        ArrayList<String> classes = new ArrayList<String>();
        classes.add("accept"); classes.add("postpone"); classes.add("decline");

        Double numerator = NB_List_Class(entry, give_condition) * 0.33333333333;
        Log.v("this",  String.valueOf(numerator) + "  " + give_condition );
        Double denumenator = 0.0;

        for(int i=0; i<3; i++) {
            Double cur = NB_List_Class(entry, classes.get(i))* 0.33333333333;

            denumenator += cur;
        }
        Log.v("this",String.valueOf(numerator) + " :::: " + String.valueOf(denumenator) );
        if(denumenator == 0.000) denumenator = 1.0;
        return (numerator) * 1.0 / (denumenator) * 1.0;
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


    public void set_history() throws IOException {
        FileOutputStream os = new FileOutputStream(MYFILE);
        //MYFILE.createNewFile();
        //if(!data.isEmpty())
        //   Log.v("this", String.valueOf(data.size()));
        //Toast.makeText(Notifications.this, String.valueOf(data.size()),Toast.LENGTH_SHORT).show();
        try {
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("newhistory.txt", Context.MODE_PRIVATE));
            if (!History.data.isEmpty())
                for (int j = 0; j < History.data.size(); j++) {
                    history_entry i = History.data.get(j);
                    //outputStreamWriter.write(i.appName + " " + i.time + " " + i.keyword + " " +
                    //        i.category + " " + i.isClicked + " " + i.appRate + "\n");
                    os.write((i.appName + " " + i.time + " " + i.keyword + " " +
                            i.category + " " + i.isClicked + " " + i.appRate + " " + i.action_token+"\n").getBytes());
                }
            //outputStreamWriter.close();
            os.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

package com.example.akylbektokonuulu.blockit.history;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class history {

    public ArrayList<history_entry> data;

    public history () {
        data = new ArrayList<history_entry>();
    }

    public history_entry parse(String str) {

        ArrayList<String> features = new ArrayList<>(0);

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
        history_entry ans = new history_entry(features.get(0), features.get(1), features.get(2), features.get(3), features.get(4), features.get(5));

        return ans;
    }

    public void get_history(Context context) throws IOException {

        String name = "EXPERIMENT_HISTORY.txt";
        File MYFILE = new File(context.getExternalFilesDir("BLOCKIT"), name);
        MYFILE.createNewFile();
        FileInputStream is = new FileInputStream(MYFILE);
        DataInputStream in = new DataInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        //ArrayList<history_entry> data = new ArrayList<>();

        String mydata = "";
        while ((strLine = br.readLine()) != null) {
            history_entry temp = parse(strLine);
            //String mmac = temp.mac;
            //int rrssi = temp.rssi;
            // Log.v("MainActivity", strLine);
            //   Log.v("MainActivity", mmac + "  " + String.valueOf(rrssi));
            //                            Toast.makeText(MainActivity.this, strLine, Toast.LENGTH_SHORT).show();
            //                        my
            boolean ok = true;
            int index = 0;
            //for(Data i: datas) {
            //if(temp.mac == i.mac) //{datas.set(index, new Data(i.mac, i.rssi + temp.rssi, i.count + temp.count)); ok = false; break;}

            // index++;
            //                        }
            //                      if(ok)

            data.add(temp);
        }
        in.close();
        //return data;
    }


    public void set_history(Context context) throws IOException {
        String name =  "EXPERIMENT_HISTORY.txt";
        File MYFILE = new File(context.getExternalFilesDir("PIDARAZ"), name);
        FileOutputStream os = new FileOutputStream(MYFILE);

        int sum = 0;
        int error = 0, KOL = 0;
        if(!data.isEmpty())
        for (int j=0; j<6; j++) {
            history_entry i = data.get(j);
            os.write((i.appName + " " + i.time+ " " + i.keyword + " " +
                    i.category + " " + i.isClicked + " "  + i.appRate).getBytes()
            );
        }

    }

}

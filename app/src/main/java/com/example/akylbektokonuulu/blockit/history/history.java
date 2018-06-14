package com.example.akylbektokonuulu.blockit.history;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        history_entry ans = new history_entry(features.get(0), features.get(1), features.get(2), features.get(3), features.get(4), features.get(5),features.get(6));

        return ans;
    }

    public void get_history(Context context) throws IOException {

        //String name = "EXPERIMENT_HISTORY.txt";
        //File MYFILE = new File(context.getExternalFilesDir("BLOCKIT"), name);
        //MYFILE.createNewFile();
        //FileInputStream is = new FileInputStream(MYFILE);
        //DataInputStream in = new DataInputStream(is);
        //BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        data.clear();
        //ArrayList<history_entry> data = new ArrayList<>();

        //String mydata = "";
        /*while ((strLine = br.readLine()) != null) {
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
        in.close();*/

        try {
            InputStream inputStream = context.openFileInput("history.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String mydata = "";
            while ((strLine = br.readLine()) != null) {
                history_entry temp = parse(strLine);
                boolean ok = true;
                int index = 0;
                data.add(temp);
            }
                inputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }


    public void set_history(Context context) throws IOException {
        //String name =  "EXPERIMENT_HISTORY.txt";
        //File MYFILE = new File(context.getExternalFilesDir("PIDARAZ"), name);
        //FileOutputStream os = new FileOutputStream(MYFILE);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("history.txt", Context.MODE_PRIVATE));
            if (!data.isEmpty())
                for (int j = 0; j < data.size(); j++) {
                    history_entry i = data.get(j);
                    outputStreamWriter.write(i.appName + " " + i.time + " " + i.keyword + " " +
                            i.category + " " + i.isClicked + " " + i.appRate + "\n");
                }
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

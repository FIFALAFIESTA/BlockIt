package com.example.akylbektokonuulu.blockit.history;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.akylbektokonuulu.blockit.MainActivity;
import com.example.akylbektokonuulu.blockit.Notifications;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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


}

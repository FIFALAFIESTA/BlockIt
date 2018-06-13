package com.example.akylbektokonuulu.blockit.history;

public class history_entry {
    String appName;
    String time;
    String keyword;
    String category;
    String isClicked;
    String appRate;

    history_entry () {
        appName = keyword = time = category = isClicked = appRate = "null";
    }
    history_entry (String a, String t, String k, String c, String i, String r) {
        appName = a;
        time = t;
        keyword = k;
        category = c;
        isClicked = i;
        appRate = r;
    }
}

package com.example.akylbektokonuulu.blockit.history;

public class history_entry {
    public String appName;
    public String time;
    public String keyword;
    public String category;
    public String isClicked;
    public String appRate;

    public history_entry () {
        appName = keyword = time = category = isClicked = appRate = "null";
    }
    public history_entry(String a, String t, String k, String c, String i, String r) {
        appName = a;
        time = t;
        keyword = k;
        category = c;
        isClicked = i;
        appRate = r;
    }
}

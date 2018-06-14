package com.example.akylbektokonuulu.blockit.history;

public class history_entry {
    public String appName;
    public String time;
    public String keyword;
    public String category;
    public String isClicked;
    public String appRate;
    public String action_token;

    public history_entry () {
        appName = keyword = time = category = isClicked = appRate = action_token = "null";
    }
    public history_entry(String a, String t, String k, String c, String i, String r, String pp) {
        appName = a;
        time = t;
        keyword = k;
        category = c;
        isClicked = i;
        appRate = r;
        action_token = pp;
    }
    public String get(int i) {
        if(i == 0) return appName;
        else if(i == 1) return time;
        else if(i == 2) return keyword;
        else if(i == 3) return category;
        else if(i == 4) return isClicked;
        else if(i == 5) return appRate;
        else return action_token;

    }
}

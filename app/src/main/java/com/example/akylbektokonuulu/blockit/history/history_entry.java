package com.example.akylbektokonuulu.blockit.history;

public class history_entry {
    public String appName;
    public String time;
    public String foreground;
    public String category;
    public String background;
    public String appRate;
    public String action_token;

    public history_entry () {
        appName = foreground = time = category = background = appRate = action_token = "null";
    }
    public history_entry(String a, String t, String k, String c, String i, String r, String pp) {
        appName = a;
        time = t;
        foreground = k;
        category = c;
        background = i;
        appRate = r;
        action_token = pp;
    }
    public String get(int i) {
        if(i == 0) return appName;
        else if(i == 1) return time;
        else if(i == 2) return foreground;
        else if(i == 3) return category;
        else if(i == 4) return background;
        else if(i == 5) return appRate;
        else return action_token;

    }
}

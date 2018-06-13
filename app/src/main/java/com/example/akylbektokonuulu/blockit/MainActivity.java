package com.example.akylbektokonuulu.blockit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View v){

        if(v.getId() == R.id.notif_list){
            Intent intent = new Intent(this, Notifications.class);
            startActivity(intent);
        }
    }
}

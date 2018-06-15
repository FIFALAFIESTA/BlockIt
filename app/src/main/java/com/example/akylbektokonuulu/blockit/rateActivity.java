package com.example.akylbektokonuulu.blockit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class rateActivity extends AppCompatActivity {
    Button enter;
    EditText rate;
    TextView textView;
    String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        textView = findViewById(R.id.textView3);
        enter = findViewById(R.id.button);
        rate = findViewById(R.id.rate);

        appName = getIntent().getStringExtra("app");
        rate.setInputType(InputType.TYPE_NULL);
        rate.setInputType(InputType.TYPE_CLASS_TEXT);
        rate.requestFocus();
        InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr2.showSoftInput(rate, InputMethodManager.SHOW_FORCED);

        enter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(rateActivity.this, "Rating is set to: " + rate.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

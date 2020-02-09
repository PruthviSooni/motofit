package com.test.motofit_temp.test_1;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
        // Using handler with postDelayed called runnable run method
            @Override
            public void run() {
                Intent i = new Intent(splash.this, login.class);
                startActivity(i);
                // close this activity
                splash.this.finish();
            }
        }, 1000);
    }
}


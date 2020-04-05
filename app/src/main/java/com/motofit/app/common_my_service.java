package com.motofit.app;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class common_my_service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comman_layout_service);
        CollapsingToolbarLayout t1 = findViewById(R.id.collapsing_toolbar);
        TextView location = findViewById(R.id.usr_location);
        TextView time = findViewById(R.id.time);
        TextView model = findViewById(R.id.bike_model);
//        ///Toolbar For Going Back
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
        String data = getIntent().getStringExtra("Service Name");
        String data2 = getIntent().getStringExtra("Service Location");
        String data3 = getIntent().getStringExtra("Service Time");
        String data4 = getIntent().getStringExtra("Meter");

        t1.setTitle(data);
        location.setText(data2);
        time.setText(data3);
        model.setText(data4);
    }
}

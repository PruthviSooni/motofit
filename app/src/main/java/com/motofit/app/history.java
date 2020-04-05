package com.motofit.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.motofit.app.Adapters.TabAdapter;
import com.motofit.app.Fragment.HistoryFragment_Tab1;
import com.motofit.app.Fragment.ServiceFragment_Tab2;

@SuppressLint("Registered")
public class history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Assign Id to Components
        going_back();
        final ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ServiceFragment_Tab2(), "Service History");
        adapter.addFragment(new HistoryFragment_Tab1(), "Breakdown History");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void going_back() {
        ///Toolbar For Going Back
        Toolbar toolbar = findViewById(R.id.tb);
        toolbar.setTitle("My History");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}

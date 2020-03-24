package com.motofit.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motofit.app.Firebase_Classes.Breakdown;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class history extends AppCompatActivity {
    private ProgressBar p1;
    private String userId;
    List<Breakdown> breakdownList;
    ListView listView_breakdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Assign Id to Components
        p1 = findViewById(R.id.progressBar);
        listView_breakdown = findViewById(R.id.dropdown_history_list);
        breakdownList = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        fetch_data();
        going_back();
    }

    private void fetch_data() {

        //FireBase Variables
        DatabaseReference mFirebaseDB = FirebaseDatabase.getInstance().getReference("BreakDown_Service");
        p1.setVisibility(View.VISIBLE);
        //Access Logged In User Name
        mFirebaseDB.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                breakdownList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Breakdown breakdown = ds.getValue(Breakdown.class);
                    breakdownList.add(breakdown);
                }
                Adapters breakdown_adapter = new Adapters(history.this,breakdownList);
                listView_breakdown.setAdapter(breakdown_adapter);
                p1.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void going_back() {
        ///Toolbar For Going Back
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Breakdown History");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }
}

package com.test.motofit_temp.test_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.motofit_temp.test_1.Firebase_Classes.Users;

import static android.support.constraint.Constraints.TAG;

public class user_profile extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    Button b1;

    TextView t1,t2,t3;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Id of Activity Components
        b1 =  findViewById(R.id.signout);
        t1=findViewById(R.id.t2);
        t2=findViewById(R.id.t3);
        t3=findViewById(R.id.t5);
        pb =findViewById(R.id.progressBar_1);
        ///Firebase DB Variables
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase.keepSynced(true);
        ///Toolbar For Going Back
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        // add it only if it is not saved to database
        if (user != null) {
            userId = user.getUid();
        }
        //Calling Function to Fatch Data From Firebase
        addUserChangeListener();
        //Action Taken On Button on This Activity
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.signout:
                        //Fire base Auth Instance
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(),login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("loginStatus",false);
                        editor.apply();
                        break;
                    default:
                }
            }
        });
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {

        // User data change listener
        if (mFirebaseDatabase != null) {
            pb.setVisibility(View.VISIBLE);
            mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users user = dataSnapshot.getValue(Users.class);

                    // Check for null
                    if (user == null) {
                        Log.e(TAG, "User data is null!");
                        Toast.makeText(getApplicationContext(),"User Data is null",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);

                    // Display newly updated name and email
                    t1.setText("User Name : "+user.name);
                    t2.setText("Mobile Number : "+user.mobnum);
                    t3.setText("Email : "+user.email);
                    pb.setVisibility(View.GONE);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", error.toException());
                }
            });
        }
    }
}

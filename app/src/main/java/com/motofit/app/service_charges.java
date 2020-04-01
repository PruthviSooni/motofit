package com.motofit.app;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motofit.app.Firebase_Classes.ServiceParts;
import com.motofit.app.Firebase_Classes.Users;

import static android.support.constraint.Constraints.TAG;

@SuppressLint("Registered")
public class service_charges extends AppCompatActivity {
    Button select;
    CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, uName;

    private int price;
    private String UserId;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_charges);
        toolbar();
        get_user_data();
        AssignID();

        select.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                price = 0;
                String str, msg = "";
                if (c1.isChecked()) {
                    str = t1.getText().toString();
                    msg = msg + " | " + c1.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c2.isChecked()) {
                    str = t2.getText().toString();
                    msg = msg + " | " + c2.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c3.isChecked()) {
                    str = t3.getText().toString();
                    msg = msg + " | " + c3.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c4.isChecked()) {
                    str = t4.getText().toString();
                    msg = msg + " | " + c4.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c5.isChecked()) {
                    str = t5.getText().toString();
                    msg = msg + " | " + c5.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c6.isChecked()) {
                    str = t6.getText().toString();
                    msg = msg + " | " + c6.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c7.isChecked()) {
                    str = t7.getText().toString();
                    msg = msg + " | " + c7.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c8.isChecked()) {
                    str = t8.getText().toString();
                    msg = msg + " | " + c8.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c9.isChecked()) {
                    str = t9.getText().toString();
                    msg = msg + " | " + c9.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c10.isChecked()) {
                    str = t10.getText().toString();
                    msg = msg + " | " + c10.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c11.isChecked()) {
                    str = t11.getText().toString();
                    msg = msg + " | " + c11.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c12.isChecked()) {
                    str = t12.getText().toString();
                    msg = msg + " | " + c12.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c13.isChecked()) {
                    str = t13.getText().toString();
                    msg = msg + " | " + c13.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c14.isChecked()) {
                    str = t14.getText().toString();
                    msg = msg + " | " + c14.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c15.isChecked()) {
                    str = t15.getText().toString();
                    msg = msg + " | " + c15.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c16.isChecked()) {
                    str = t16.getText().toString();
                    msg = msg + " | " + c16.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c17.isChecked()) {
                    str = t17.getText().toString();
                    msg = msg + " | " + c17.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c18.isChecked()) {
                    str = t18.getText().toString();
                    msg = msg + " | " + c18.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c19.isChecked()) {
                    str = t19.getText().toString();
                    msg = msg + " | " + c19.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                if (c20.isChecked()) {
                    str = t20.getText().toString();
                    msg = msg + " | " + c20.getText().toString();
                    price = price + Integer.parseInt(str);
                }
                String uName = service_charges.this.uName.getText().toString().trim();
                String parts = msg;
                String Price = "₹ " + (price);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                UserId = user.getUid();
                DatabaseReference mDereference = FirebaseDatabase.getInstance().getReference("Service Parts");
                ServiceParts serviceParts = new ServiceParts(uName, parts, Price);
                mDereference.child(UserId).setValue(serviceParts);
                onBackPressed();
            }
        });
    }

    private void toolbar() {
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
    }

    private void get_user_data() {
        //FireBase Variables
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDB = mFirebaseInstance.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserId = user.getUid();
        }

        //Access Logged In User Name
        mFirebaseDB.child(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
                uName.setText(user.name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @SuppressLint("CutPasteId")
    private void AssignID() {
        select = findViewById(R.id.select);
        c1 = findViewById(R.id.checkbox1);
        c2 = findViewById(R.id.checkbox2);
        c3 = findViewById(R.id.checkbox3);
        c4 = findViewById(R.id.checkbox4);
        c5 = findViewById(R.id.checkbox5);
        c6 = findViewById(R.id.checkbox6);
        c7 = findViewById(R.id.checkbox7);
        c8 = findViewById(R.id.checkbox8);
        c9 = findViewById(R.id.checkbox9);
        c10 = findViewById(R.id.checkbox10);
        c11 = findViewById(R.id.checkbox11);
        c12 = findViewById(R.id.checkbox12);
        c13 = findViewById(R.id.checkbox13);
        c14 = findViewById(R.id.checkbox14);
        c15 = findViewById(R.id.checkbox15);
        c16 = findViewById(R.id.checkbox16);
        c17 = findViewById(R.id.checkbox17);
        c18 = findViewById(R.id.checkbox18);
        c19 = findViewById(R.id.checkbox19);
        c20 = findViewById(R.id.checkbox20);


        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        t3 = findViewById(R.id.text3);
        t4 = findViewById(R.id.text4);
        t5 = findViewById(R.id.text5);
        t6 = findViewById(R.id.text6);
        t7 = findViewById(R.id.text7);
        t8 = findViewById(R.id.text8);
        t9 = findViewById(R.id.text9);
        t10 = findViewById(R.id.text10);
        t11 = findViewById(R.id.text11);
        t12 = findViewById(R.id.text12);
        t13 = findViewById(R.id.text13);
        t14 = findViewById(R.id.text14);
        t15 = findViewById(R.id.text15);
        t16 = findViewById(R.id.text16);
        t17 = findViewById(R.id.text17);
        t18 = findViewById(R.id.text18);
        t19 = findViewById(R.id.text19);
        t20 = findViewById(R.id.text20);

        uName = findViewById(R.id.username);
    }
}

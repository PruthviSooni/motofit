package com.test.motofit_temp.test_1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.support.constraint.Constraints.TAG;

@SuppressLint("ValidFragment")
public class User_infoFragment extends Fragment implements View.OnClickListener {
    Button b1;
    View v;
    TextView t1,t2,t3;
    ProgressBar pb;


    public User_infoFragment() {
    }


    @Nullable
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_userinfo,container,false);
        b1 =  v.findViewById(R.id.signout);
        t1=v.findViewById(R.id.t2);
        t2=v.findViewById(R.id.t3);
        t3=v.findViewById(R.id.t5);
        pb = v.findViewById(R.id.progressBar_1);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase.keepSynced(true);

        // add it only if it is not saved to database
        if (user != null) {
            userId = user.getUid();
        }

        addUserChangeListener();
        b1.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signout:
                //Fire base Auth Instance
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("loginStatus",false);
                editor.apply();
                break;
            default:
        }
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
                        Toast.makeText(getContext(),"User Data is null",Toast.LENGTH_LONG).show();
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


package com.test.motofit_temp.test_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("ValidFragment")
public class User_infoFragment extends Fragment implements View.OnClickListener {
     Button b1;
     View v;
    TextView t1,t2,t3;
    @SuppressLint("ValidFragment")
    public User_infoFragment() {



    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_userinfo,container,false);
        b1 = (Button) v.findViewById(R.id.signout);
        t1=(TextView)v.findViewById(R.id.t2);
        t2=(TextView)v.findViewById(R.id.t3);
        t3=(TextView)v.findViewById(R.id.t5);
        String name=getArguments().getString("usrname");
        String number=getArguments().getString("mob");
        String email=getArguments().getString("email");
        t1.setText(name);
        t2.setText(number);
        t3.setText(email);
        b1.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),login.class));
                break;
            default:
        }
    }
}

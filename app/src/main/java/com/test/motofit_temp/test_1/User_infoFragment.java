package com.test.motofit_temp.test_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class User_infoFragment extends Fragment implements View.OnClickListener {
     Button b1;
     View v;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_userinfo,container,false);
        b1 = (Button) v.findViewById(R.id.signout);
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

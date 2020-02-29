package com.test.motofit_temp.test_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class more_infoFragment extends Fragment  {
    View v;
    ConstraintLayout c1,c2;
    public more_infoFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_moreinfo,container,false);
       c1 = v.findViewById(R.id.account_activity);
       c2 = v.findViewById(R.id.about_us);

       c1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(getActivity(),user_profile.class);
               startActivity(i);
           }
       });
//       c2.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Intent i = new Intent(getActivity(),user_profile.class);
//               startActivity(i);
//           }
//       });

       return v;

    }

}


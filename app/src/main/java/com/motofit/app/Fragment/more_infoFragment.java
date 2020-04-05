package com.motofit.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.motofit.app.R;
import com.motofit.app.about_us;
import com.motofit.app.history;
import com.motofit.app.user_profile;

@SuppressLint("ValidFragment")
public class more_infoFragment extends Fragment {
    View v;
    LinearLayout l1, l2, l3;

    public more_infoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_moreinfo, container, false);
        l1 = v.findViewById(R.id.account_activity);
        l2 = v.findViewById(R.id.about_us);
        l3 = v.findViewById(R.id.history_activity);
        getActivity().setTitle("More Info");
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), user_profile.class);
                startActivity(i);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), about_us.class);
                startActivity(i);
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), history.class);
                startActivity(i);
            }
        });
        return v;

    }

}


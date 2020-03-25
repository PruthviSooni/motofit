package com.motofit.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motofit.app.Firebase_Classes.Breakdown;
import com.motofit.app.R;
import com.motofit.app.breakdown_adapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment_Tab1 extends Fragment {
    List<Breakdown> breakdownList;
    ListView listView_breakdown;
    private ProgressBar p1;
    private String userId;
    private ImageView service_notFound_Img;
    private TextView service_notFound_txt;

    public HistoryFragment_Tab1() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_breakdown_tab1, container, false);
        //Assign Id to Components
        p1 = v.findViewById(R.id.h1_progressBar);
        service_notFound_Img = v.findViewById(R.id.service_notFound_Img);
        service_notFound_txt = v.findViewById(R.id.service_notFound_Txt);
        listView_breakdown = v.findViewById(R.id.dropdown_history_list);
        breakdownList = new ArrayList<>();
        if (breakdownList.isEmpty()) {
            Log.d("TAB_2 List Debug", "breakdown_list is Empty");
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        fetch_data();

        return v;
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
                if (breakdownList.isEmpty()) {
                    service_notFound_Img.setVisibility(View.VISIBLE);
                    service_notFound_txt.setVisibility(View.VISIBLE);
                    listView_breakdown.setVisibility(View.GONE);
                } else {
                    service_notFound_Img.setVisibility(View.GONE);
                    service_notFound_txt.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        breakdown_adapter breakdown_adapter = new breakdown_adapter(getActivity(), breakdownList);
                        listView_breakdown.setAdapter(breakdown_adapter);
                    }
                }
                p1.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}

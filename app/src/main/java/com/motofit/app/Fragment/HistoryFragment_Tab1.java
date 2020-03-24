package com.motofit.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motofit.app.breakdown_adapter;
import com.motofit.app.Firebase_Classes.Breakdown;
import com.motofit.app.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment_Tab1 extends Fragment {
    public HistoryFragment_Tab1() {
        // Required empty public constructor
    }
    private ProgressBar p1;
    private String userId;
    List<Breakdown> breakdownList;
    ListView listView_breakdown;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_history, container, false);
        //Assign Id to Components
        p1 = v.findViewById(R.id.h1_progressBar);
        listView_breakdown = v.findViewById(R.id.dropdown_history_list);
        breakdownList = new ArrayList<>();
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
                breakdown_adapter breakdown_adapter = new breakdown_adapter(getActivity(),breakdownList);
                listView_breakdown.setAdapter(breakdown_adapter);
                p1.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}

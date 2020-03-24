package com.motofit.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.motofit.app.Firebase_Classes.Services;
import com.motofit.app.R;
import com.motofit.app.service_adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment_Tab2 extends Fragment {

    public ServiceFragment_Tab2() {
        // Required empty public constructor
    }
    private ProgressBar p1;
    private String userId;
    List<Services> servicesList;
    ListView listView_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_service__tab2, container, false);
        //Assign Id to Components
        p1 = v.findViewById(R.id.h2_progressBar);
        listView_service = v.findViewById(R.id.service_history_list);
        servicesList = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        fetch_data();
        return v;

    }
    private void fetch_data() {

        //FireBase Variables
        DatabaseReference mFirebaseDB = FirebaseDatabase.getInstance().getReference("Services");
        p1.setVisibility(View.VISIBLE);
        //Access Logged In User Name
        mFirebaseDB.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Services service = ds.getValue(Services.class);
                    servicesList.add(service);
                }
                service_adapter service_adapter = new service_adapter(getActivity(),servicesList);
                listView_service.setAdapter(service_adapter);
                p1.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}

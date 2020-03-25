package com.motofit.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.motofit.app.Firebase_Classes.Services;
import com.motofit.app.R;
import com.motofit.app.service_adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment_Tab2 extends Fragment {

    List<Services> servicesList;
    ListView listView_service;
    private ProgressBar p1;
    private String userId;
    private ImageView service_notFound_Img;
    private TextView service_notFound_txt;

    public ServiceFragment_Tab2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_service__tab2, container, false);
        //Assign Id to Components
        p1 = v.findViewById(R.id.h2_progressBar);
        service_notFound_Img = v.findViewById(R.id.service_notFound_Img);
        service_notFound_txt = v.findViewById(R.id.service_notFound_Txt);
        listView_service = v.findViewById(R.id.service_history_list);
        servicesList = new ArrayList<>();
        if (servicesList.isEmpty()) {
            Log.d("TAB_1 List Debug", "service_list is Empty");
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        return v;

    }


    @Override
    public void onStart() {
        super.onStart();
        //FireBase Variables
        DatabaseReference mFirebaseDB = FirebaseDatabase.getInstance().getReference("Services");
        p1.setVisibility(View.VISIBLE);
        //Access Logged In User Name
        mFirebaseDB.child(userId).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Services service = ds.getValue(Services.class);
                    servicesList.add(service);
                }
                if (servicesList.isEmpty()) {
                    service_notFound_Img.setVisibility(View.VISIBLE);
                    service_notFound_txt.setVisibility(View.VISIBLE);
                    listView_service.setVisibility(View.GONE);
                } else {
                    service_notFound_Img.setVisibility(View.GONE);
                    service_notFound_txt.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        service_adapter service_adapter = new service_adapter(getActivity(), servicesList);
                        listView_service.setAdapter(service_adapter);
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


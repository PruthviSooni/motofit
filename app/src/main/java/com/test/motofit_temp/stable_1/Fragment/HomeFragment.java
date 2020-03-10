package com.test.motofit_temp.stable_1.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.motofit_temp.test_1.R;

public class HomeFragment extends Fragment {
    public HomeFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ConstraintLayout const_1 = v.findViewById(R.id.constraintLayout_1);
        ConstraintLayout const_2 = v.findViewById(R.id.constraintLayout_2);
        final AppCompatActivity activity = (AppCompatActivity) v.getContext();
        //Jump to ServiceFragment
        const_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFragment = new ServicesFragment();
                //Select Icon in Bottom Navigation Area
                BottomNavigationView navigation = v.getRootView().findViewById(R.id.bottom_navigation);
                navigation.setSelectedItemId(R.id.nav_services);
                //
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        //Jump To DoorstepFragment
        const_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFragment = new MotorcycleFragment();
                //Select Icon in Bottom Navigation Area
                BottomNavigationView navigation = v.getRootView().findViewById(R.id.bottom_navigation);
                navigation.setSelectedItemId(R.id.nav_motorcycle);
                //
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return v;
    }

}


package com.motofit.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.motofit.app.R;
import com.motofit.app.history;

import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public HomeFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ConstraintLayout const_1 = v.findViewById(R.id.constraintLayout_1);
        ConstraintLayout const_2 = v.findViewById(R.id.constraintLayout_2);
        ConstraintLayout const_4 = v.findViewById(R.id.constraintLayout_4);

        Objects.requireNonNull(getActivity()).setTitle("How It's Work?");
        const_1.setOnClickListener(this);
        const_2.setOnClickListener(this);
        const_4.setOnClickListener(this);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        final AppCompatActivity activity = (AppCompatActivity) v.getContext();
        switch (v.getId()) {
            case R.id.constraintLayout_1:
                Fragment myFragment_1 = new ServicesFragment();
                //Select Icon in Bottom Navigation Area
                BottomNavigationView navigation_1 = v.getRootView().findViewById(R.id.bottom_navigation);
                navigation_1.setSelectedItemId(R.id.nav_services);
                //
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, myFragment_1)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.constraintLayout_2:
                Fragment myFragment_2 = new MotorcycleFragment();
                //Select Icon in Bottom Navigation Area
                BottomNavigationView navigation_2 = v.getRootView().findViewById(R.id.bottom_navigation);
                navigation_2.setSelectedItemId(R.id.nav_motorcycle);
                //
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frame_container, myFragment_2)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.constraintLayout_4:
                Intent i = new Intent(getContext(), history.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}


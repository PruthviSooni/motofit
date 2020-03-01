package com.test.motofit_temp.test_1.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.motofit_temp.test_1.R;

public class MotorcycleFragment extends Fragment{
    Button doorstep;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_motorcycle, container, false);
        doorstep=(Button)v.findViewById(R.id.doorstep);

        doorstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(getContext(),Doorstep.class);
//                startActivity(i);

            }
        });
        return v;

    }


}

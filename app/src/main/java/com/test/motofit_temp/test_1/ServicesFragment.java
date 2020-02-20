package com.test.motofit_temp.test_1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ServicesFragment  extends Fragment {
    private  View v;
    EditText et_date,et_time,e1;
    Button reg_btn;
    TextView t1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mreference;
    private String userId;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_services,container,false);
        et_date=v.findViewById(R.id.et_date);
        et_time=v.findViewById(R.id.et_time);
        e1=v.findViewById(R.id.editText3);
        t1=v.findViewById(R.id.t1);
        reg_btn=v.findViewById(R.id.register);
        //Default Date
        String myFormat = "dd-MMM-yyyy";
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String sdf1 = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        et_date.setText(sdf1);
        ///Register Button Logic

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = et_date.getText().toString().trim();
                String time0 = et_time.getText().toString().trim();
                mreference = mFirebaseDatabase.getReference("Services");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userId = user.getUid();
                Services services = new Services(date,time0);
                mreference.child(userId).setValue(services);
                Toast.makeText(getContext(),"Data Sent to Realtime Database",Toast.LENGTH_LONG).show();

            }
        });












        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),service_types.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
        });
           //Date Picker Logic
           et_date.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final Calendar myCalendar = Calendar.getInstance();
                   DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           // TODO Auto-generated method stub
                           myCalendar.set(Calendar.YEAR, year);
                           myCalendar.set(Calendar.MONTH, monthOfYear);
                           myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                           String myFormat = "dd-MMM-yyyy"; // your format
                           SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                           et_date.setText(sdf.format(myCalendar.getTime()));
                       }
                   };
                   new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
               }
           });
           ///Time Picker Logic
           et_time.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Calendar mcurrentTime = Calendar.getInstance();
                   int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                   int minute = mcurrentTime.get(Calendar.MINUTE);

                   TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                       @Override
                       public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            et_time.setText( selectedHour + ":" + selectedMinute);
                       }
                   }, hour, minute, false);
                   mTimePicker.setTitle("Select Time");
                   mTimePicker.show();
               }
           });
        return v;

    }

}




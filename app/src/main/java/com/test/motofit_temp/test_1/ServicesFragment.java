package com.test.motofit_temp.test_1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ServicesFragment  extends Fragment  {
    private  View v;
    EditText et_date,et_time,odometer,e4,notes;
    Button reg_btn;
    Spinner s1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mreference;
    private String userId;
    CoordinatorLayout coordinatorLayout;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_services,container,false);
        et_date=v.findViewById(R.id.et_date);
        et_time=v.findViewById(R.id.et_time);
        s1 = v.findViewById(R.id.spinner);
        e4=v.findViewById(R.id.location);
        reg_btn=v.findViewById(R.id.register);
        notes =v.findViewById(R.id.notes);
        odometer = v.findViewById(R.id.kilometer);
        mAuth = FirebaseAuth.getInstance();
        coordinatorLayout = v.findViewById(R.id.coordinatorLayout);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final int title = R.string.title_1;

        ArrayAdapter<String> service = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Services));
        service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(service);


        ///Register Button Logic
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = et_date.getText().toString().trim();
                String odomtr = odometer.getText().toString().trim();
                String time = et_time.getText().toString().trim();
                String locate = e4.getText().toString().trim();
                String service = s1.getSelectedItem().toString().trim();
                String note = notes.getText().toString().trim();
                if (date.isEmpty()) {
                    et_date.setError("");
                    et_date.requestFocus();
                    return;
                }
                if (time.isEmpty()) {
                    et_time.setError("");
                    et_time.requestFocus();
                    return;
                }
                if (odomtr.isEmpty()) {
                    odometer.setError("Enter Total Kilometer");
                    odometer.requestFocus();
                    return;
                }
                if (locate.isEmpty()) {
                    e4.setError("Enter Location");
                    e4.requestFocus();
                    return;
                }
                mreference = mFirebaseDatabase.getReference("Services");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userId = user.getUid();
                Services services = new Services(date,time,service,odomtr,note);
                mreference.child(userId).push().setValue(services);
                mreference.keepSynced(true);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Data Sent to Realtime Database", Snackbar.LENGTH_LONG);
                snackbar.show();


            }
        });

           //Date Picker Logic
           et_date.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final Calendar myCalendar = Calendar.getInstance();
                   final Calendar system = Calendar.getInstance();
                   DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           // TODO Auto-generated method stub
                           myCalendar.set(Calendar.YEAR, year);
                           myCalendar.set(Calendar.MONTH, monthOfYear);
                           myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                           String myFormat = "dd-MMM-yyyy"; // your format
                           SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                           if (system.getTimeInMillis() > myCalendar.getTimeInMillis()){
                               Toast.makeText(getActivity(), "Please Enter Another Date",Toast.LENGTH_LONG).show();
                           }
                           else{
                                et_date.setText(sdf.format(myCalendar.getTime()));
                           }
                       }
                   };
                   new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
               }
           });

           ///Time Picker Logic
           et_time.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   final Calendar mcurrentTime = Calendar.getInstance();
                   int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                   int minute = mcurrentTime.get(Calendar.MINUTE);
                   TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                       @SuppressLint({"SetTextI18n", "DefaultLocale"})
                       @Override
                       public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                               int hour = selectedHour % 12;
                               et_time.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                       selectedMinute, selectedHour < 12 ? "AM" : "PM"));
                           }
                   }, hour, minute, false);
                   mTimePicker.setTitle("Select Time");
                   mTimePicker.show();
               }
           });
           return  v;

    }

}





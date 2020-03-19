package com.motofit.beta.r1.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motofit.beta.r1.Firebase_Classes.Services;
import com.motofit.beta.r1.Firebase_Classes.Users;
import com.motofit.beta.r1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class ServicesFragment  extends Fragment {
    public ServicesFragment(){}

    private EditText et_date,odometer,e4,notes;
    private Button reg_btn;
    private Spinner s1,sp_time;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mreference;
    private String userId;
    private CoordinatorLayout coordinatorLayout;
    private TextView username;


    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        //Assign id to Variable's
        et_date = v.findViewById(R.id.et_date);
        sp_time = v.findViewById(R.id.spinner_time);
        s1 = v.findViewById(R.id.spinner);
        e4 = v.findViewById(R.id.location);
        reg_btn = v.findViewById(R.id.register);
        notes = v.findViewById(R.id.notes);
        odometer = v.findViewById(R.id.kilometer);
        username = v.findViewById(R.id.username);
        coordinatorLayout = v.findViewById(R.id.coordinatorLayout);
        ImageButton info_btn = v.findViewById(R.id.info_btn);

        //FireBase Variables
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDB = mFirebaseInstance.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        if (user != null) {
            userId = user.getUid();
        }

        //Access Logged In User Name
        mFirebaseDB.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    Toast.makeText(getContext(), "User Data is null", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);

                username.setText(user.name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Service's DropDown
        ArrayAdapter<String> service = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Services));
        service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setPrompt("Select Service");
        s1.setAdapter(service);

        //Time Dropdown
        ArrayAdapter<String> service_time = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Services_time));
        service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_time.setPrompt("Select Time");
        sp_time.setAdapter(service_time);

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), com.motofit.beta.r1.service_info.class);
                startActivity(i);
            }
        });

        ///Register Button Logic
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg_btn.setClickable(true);
                reg_btn.isEnabled();
                String Km = " Km";
                String date = et_date.getText().toString().trim();
                String odomtr = odometer.getText().toString().trim() + Km;
                String time = sp_time.getSelectedItem().toString().trim();
                String locate = e4.getText().toString().trim();
                String service = s1.getSelectedItem().toString().trim();
                String note = notes.getText().toString().trim();
                String name = username.getText().toString().trim();
                if (date.isEmpty()) {
                    et_date.setError("Select Data!!");
                    et_date.requestFocus();
                    return;
                }
                if (odomtr.isEmpty()) {
                    odometer.setError("Enter Total Kilometer!!");
                    odometer.requestFocus();
                    return;
                }
                if (locate.isEmpty()) {
                    e4.setError("Locate Your Self!!");
                    e4.requestFocus();
                    return;
                }
                mreference = mFirebaseDatabase.getReference("Services");
                Services services = new Services(name, date, time, service, odomtr, note, locate);
                mreference.child(userId).push().setValue(services);
                mreference.keepSynced(true);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Service Registered.", Snackbar.LENGTH_LONG);
                snackbar.show();
                reg_btn.setClickable(false);
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
                        if (system.getTimeInMillis() > myCalendar.getTimeInMillis()) {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Enter Another Date", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            et_date.setText(sdf.format(myCalendar.getTime()));
                        }
                    }
                };
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return v;
    }
}










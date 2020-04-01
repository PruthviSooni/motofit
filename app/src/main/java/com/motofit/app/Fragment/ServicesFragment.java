package com.motofit.app.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motofit.app.Firebase_Classes.ServiceParts;
import com.motofit.app.Firebase_Classes.Services;
import com.motofit.app.Firebase_Classes.Users;
import com.motofit.app.R;
import com.motofit.app.service_charges;
import com.motofit.app.service_info;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

public class ServicesFragment extends Fragment {
    public TextView username, charges, total;
    public TableRow tableRow;
    private EditText et_date, odometer, e4, notes;
    private Button reg_btn, select_parts;
    private Spinner s1, sp_time;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private String userId;
    private CoordinatorLayout coordinatorLayout;
    private String parts_price;

    public ServicesFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services, container, false);
        getActivity().setTitle("Book Service");
        //Assign id to Variable's

        et_date = v.findViewById(R.id.et_date);
        sp_time = v.findViewById(R.id.spinner_time);
        s1 = v.findViewById(R.id.spinner);
        e4 = v.findViewById(R.id.usr_location);
        reg_btn = v.findViewById(R.id.register);
        notes = v.findViewById(R.id.notes);
        odometer = v.findViewById(R.id.kilometer);
        username = v.findViewById(R.id.username);
        coordinatorLayout = v.findViewById(R.id.coordinatorLayout);
        ImageButton info_btn = v.findViewById(R.id.info_btn);
        select_parts = v.findViewById(R.id.select_parts);
        tableRow = v.findViewById(R.id.tableRow);
        charges = v.findViewById(R.id.service_charge);
        total = v.findViewById(R.id.total);
        //Getting user Name
        get_user_data();
        ////Date Picker
        get_time();
        //Send Data To Firebase
        Firebase_RealTimeDB();
        //Services Selection
        Select_service();
        //Time Dropdown
        ArrayAdapter<String> service_time = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Services_time));
        service_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_time.setPrompt("Select Time");
        sp_time.setAdapter(service_time);
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), service_info.class);
                startActivity(i);
            }
        });
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Select_service() {
        //Service's DropDown
        final ArrayAdapter<String> service = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Services));
        service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setPrompt("Select Service");
        s1.setAdapter(service);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableRow.setVisibility(View.GONE);
                if (s1.getSelectedItem().equals("Spare Parts Installation")) {
                    tableRow.setVisibility(View.VISIBLE);
                    charges.setVisibility(View.GONE);
                    select_parts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getContext(), service_charges.class);
                            startActivity(i);
                        }
                    });

                } else {
                    tableRow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                charges.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
            }
        });
    }

    private void get_user_data() {
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
        //FireBase Variables
        DatabaseReference mFirebaseDB_2 = mFirebaseInstance.getReference("Service Parts");
        //Access Logged In Parts Price
        mFirebaseDB_2.child(userId);
        mFirebaseDB_2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ServiceParts serviceParts = ds.getValue(ServiceParts.class);
                    assert serviceParts != null;
                    charges.setText(serviceParts.price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void get_time() {
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
    }

    private void Firebase_RealTimeDB() {
        ///Register Button Logic
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg_btn.setClickable(true);
                reg_btn.isEnabled();
                String Km = " Km";
                String date = et_date.getText().toString().trim();
                String odometer = ServicesFragment.this.odometer.getText().toString().trim() + Km;
                String time = sp_time.getSelectedItem().toString().trim();
                String locate = e4.getText().toString().trim();
                String service = s1.getSelectedItem().toString().trim();
                String note = notes.getText().toString().trim();
                String name = username.getText().toString().trim();
                if (s1.getSelectedItem().equals("Select Service")) {
                    ((TextView) s1.getSelectedView()).setError("Select Service");
                    s1.requestFocus();
                    return;
                }
                if (date.isEmpty()) {
                    et_date.setError("Select Data!!");
                    et_date.requestFocus();
                    return;
                }
                if (odometer.isEmpty()) {
                    ServicesFragment.this.odometer.setError("Enter Total Kilometer!!");
                    ServicesFragment.this.odometer.requestFocus();
                    return;
                }
                if (locate.isEmpty()) {
                    e4.setError("Locate Your Self!!");
                    e4.requestFocus();
                    return;
                }
                mReference = mFirebaseDatabase.getReference("Services");
                Services services = new Services(name, date, time, service, odometer, note, locate);
                mReference.child(userId).push().setValue(services);
                mReference.keepSynced(true);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Service Registered.", Snackbar.LENGTH_LONG);
                snackbar.show();
                reg_btn.setClickable(false);
            }
        });

    }


}
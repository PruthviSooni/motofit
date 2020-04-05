package com.motofit.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.motofit.app.Firebase_Classes.Breakdown;
import com.motofit.app.Firebase_Classes.Users;
import com.motofit.app.LoadingDialog;
import com.motofit.app.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MotorcycleFragment extends Fragment {
    private static final int REQUEST_LOCATION = 1;
    View v;
    private Spinner brand, model, service_drop;
    private LocationManager locationManager;
    private CoordinatorLayout coordinatorLayout;
    private EditText current_location;
    private DatabaseReference m_reference;
    private String userID;
    private String usrId;
    private TextView userName, userNumber;
    private Button register;
    private ImageButton location_btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_motorcycle, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Book BreakDown");

        //TextView for getting user data
        userName = v.findViewById(R.id.usr_name);
        userNumber = v.findViewById(R.id.usr_number);

        //Spinners
        brand = v.findViewById(R.id.brand);
        service_drop = v.findViewById(R.id.service_drop);
        model = v.findViewById(R.id.model);

        //Button
        location_btn = v.findViewById(R.id.imageButton);
        current_location = v.findViewById(R.id.e1);
        register = v.findViewById(R.id.register);

        //SnapBar layout object
        coordinatorLayout = v.findViewById(R.id.doorstep_coordinator);

        //Getting Current userID from FireBase DB
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userID = user.getUid();
        }

        //Calling method of getting user name and number
        user_name();

        //DropDown For Selecting Model And Brand
        Drop_down();

        //Getting Current Location
        GettingLocation();

        //Registering Service
        RegisteredBreakDown();

        return v;
    }

    private void GettingLocation() {
        ///////code for getting location
        location_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        //enable to gps on your devices.
                        OnGPS();
                    } else {
                        //gps is already on ..
                        getLocation();
                    }
                }
            }
        });
    }

    private void Drop_down() {
        //Brand name... DropDown
        ArrayAdapter<String> Company = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.company));
        Company.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand.setAdapter(Company);

        //Model name... DropDown
        ArrayAdapter<String> Doorstep = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.doorstep_service));
        Doorstep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        service_drop.setAdapter(Doorstep);

        //Model name fetch from spinner of brand
        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (brand.getSelectedItem().equals("Hero Motors")) {
                    ArrayAdapter<String> service1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hero));
                    service1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service1);
                } else if (brand.getSelectedItem().equals("Honda Motors")) {
                    ArrayAdapter<String> service2 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.honda));
                    service2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service2);
                } else if (brand.getSelectedItem().equals("TVS Motors")) {
                    ArrayAdapter<String> service3 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tvs));
                    service3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service3);
                } else if (brand.getSelectedItem().equals("Bajaj Motors")) {
                    ArrayAdapter<String> service4 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bajaj));
                    service4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service4);
                } else if (brand.getSelectedItem().equals("Yamaha Motors")) {
                    ArrayAdapter<String> service5 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yamaha));
                    service5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service5);
                } else if (brand.getSelectedItem().equals("Royal Enfield")) {
                    ArrayAdapter<String> service6 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.royal_enfield));
                    service6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service6);
                } else if (brand.getSelectedItem().equals("Mahindra Motors")) {
                    ArrayAdapter<String> service7 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mahindra));
                    service7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service7);
                } else if (brand.getSelectedItem().equals("KTM")) {
                    ArrayAdapter<String> service8 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ktm));
                    service8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service8);
                } else if (brand.getSelectedItem().equals("Piaggio")) {
                    ArrayAdapter<String> service9 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.piaggio));
                    service9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service9);
                } else if (brand.getSelectedItem().equals("BMW")) {
                    ArrayAdapter<String> service10 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bmw));
                    service10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service10);
                } else {
                    ArrayAdapter<String> service12 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.select_model));
                    service12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(service12);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void RegisteredBreakDown() {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        // Push Data To FireBase By Clicking Button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validating
                String Brand = brand.getSelectedItem().toString().trim();
                String Model = model.getSelectedItem().toString().trim();
                String Service_drop = service_drop.getSelectedItem().toString().trim();
                String Location = current_location.getText().toString().trim();
                Date currentTime = Calendar.getInstance().getTime();
                String Date_and_Time = currentTime.toString().trim();
                String Name = userName.getText().toString().trim();
                String Number = userNumber.getText().toString().trim();
                if (brand.getSelectedItem().equals("SELECT BRAND")) {
                    ((TextView) brand.getSelectedView()).setError("Select Brand");
                    brand.requestFocus();
                    return;
                }
                if (service_drop.getSelectedItem().equals("Select Service")) {
                    ((TextView) service_drop.getSelectedView()).setError("Select Service");
                    service_drop.requestFocus();
                    return;
                }
                if (Location.isEmpty()) {
                    current_location.setError("Locate Your Self!!");
                    current_location.requestFocus();
                    return;
                }
                //-------------------------------------------------------------------------------//
                loadingDialog.startsLoading();
                m_reference = FirebaseDatabase.getInstance().getReference("BreakDown_Service");
                Breakdown breakdown = new Breakdown(Name, Number, Model, Brand, Service_drop, Location, Date_and_Time);
                m_reference.child(userID).push().setValue(breakdown);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissLoading();
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Booked Breakdown.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }, 2000);
                register.setClickable(false);
            }
        });
    }

    ////getting location method from onclick
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double log = LocationGps.getLongitude();
                location(lat, log);
            }
            if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double log = LocationNetwork.getLongitude();
                location(lat, log);
            }
            if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double log = LocationPassive.getLongitude();
                location(lat, log);
            } else {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Can't Get Your Location", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }

    }

    private void location(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            String city = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalcode = addresses.get(0).getPostalCode();
            String fulladdress = address + ", " + area + ", " + city + ", " + country + ", " + postalcode;
            current_location.setText(fulladdress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void user_name() {
        //FireBase Variables
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDB = mFirebaseInstance.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            usrId = user.getUid();
        }
        //Access Logged In User Name
        mFirebaseDB.child(usrId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
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
                userName.setText(user.name);
                userNumber.setText(user.mobnum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
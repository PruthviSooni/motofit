package com.motofit.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.motofit.app.Firebase_Classes.Users;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class signup extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "Activity";

    EditText mail, pass, mophone, usrname;
    Button sup;
    ProgressBar pb;
    TextView lin;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mreference;
    private CoordinatorLayout coordinatorLayout;
    private String userId;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        //Get Firebase Instance
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.pwd);
        sup = findViewById(R.id.signup);
        lin = findViewById(R.id.tv2);
        pb = findViewById(R.id.progressBar);
        usrname = findViewById(R.id.usrname);
        mophone = findViewById(R.id.mobphone);
        //Sign up Button
        sup.setOnClickListener(this);
        //Goto to Login activity
        lin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                final String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                final String usr_name = usrname.getText().toString().trim();
                final String mob_num = mophone.getText().toString().trim();
                if (email.isEmpty()) {
                    mail.setError("Email Required!");
                    mail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pass.setError("Password Required!");
                    pass.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mail.setError("Please Enter Valid Email!");
                    mail.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    pass.setError("Minimum Length of Password be 6");
                    pass.requestFocus();
                    return;
                }
                pb.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
//                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "User Registered", Snackbar.LENGTH_LONG);
//                            snackbar.show();
                            Intent it = new Intent(signup.this, login.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            // Firebase Database
                            mreference = mFirebaseDatabase.getReference("Users");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                userId = user.getUid();
                            }
                            if (user != null) {
                                Email = user.getEmail();
                            }
                            Users myUser = new Users(usr_name, email, mob_num);
                            mreference.child(userId).setValue(myUser);

                            startActivity(it);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, "You Are Already Registered", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, Objects
                                        .requireNonNull(Objects.requireNonNull(task.getException()).getMessage()), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    }
                });
                //Sending FCM Token To Localhost
                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
                Log.e(TAG, "Token: " + token);
                StringRequest stringRequest = new StringRequest
                        (Request.Method.POST, URL.app_server_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(signup.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("fcm_number", mob_num);
                        params.put("fcm_token", token);
                        return params;
                    }
                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
                MySingleton.getInstance(signup.this).addToRequestque(stringRequest);
                break;
            case R.id.tv2:
                Intent intent = new Intent(signup.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}

package com.motofit.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class login extends AppCompatActivity implements OnClickListener {
    Button Login;
    TextView sign_up;
    private EditText pwd, mail;
    private ProgressBar pb;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Shared Preference For Saving User Login state
        sharedPreferences = this.getSharedPreferences("Login", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("loginStatus", false)) {
            startActivity(new Intent(this, home.class));
        }
        //For Check Connection
        if (!isConnected(login.this)) buildDialog(login.this).show();
        else {
            setContentView(R.layout.login);
        }
        //Get Firebase Instance
        mAuth = FirebaseAuth.getInstance();
        //Assigning Component's Id to variables
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        Login = findViewById(R.id.but1);
        mail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        sign_up = findViewById(R.id.tv1);
        pb = findViewById(R.id.progressBar);
        //Login Button
        Login.setOnClickListener(this);
        //Goto SignUp activity
        sign_up.setOnClickListener(this);
    }

    //Function For Check Internet Connection
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = null;
        if (cm != null) {
            netinfo = cm.getActiveNetworkInfo();
        }
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    //Alert Dialog for Exiting App
    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;
    }

    //For Close the App
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        login.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Button Click Events.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but1:
                String email = mail.getText().toString().trim();
                String password = pwd.getText().toString().trim();
                if (email.isEmpty()) {
                    mail.setError("Email Required!");
                    mail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pwd.setError("Password Required!");
                    pwd.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mail.setError("Please Enter Valid Email!");
                    mail.requestFocus();
                    return;
                }
                if (pwd.length() < 6) {
                    pwd.setError("Minimum Length of Password be 6");
                    pwd.requestFocus();
                    return;
                }
                //Progress Bar Vertical
                pb.setVisibility(View.VISIBLE);
                //FireBase Authentication Check Method
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            sharedPreferences = login.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("loginStatus", true);
                            editor.apply();
                            Intent it = new Intent(login.this, home.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(it);
                        } else {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, Objects
                                    .requireNonNull(Objects.requireNonNull(task.getException()).getMessage()), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
                break;
            case R.id.tv1:
                //Goto To Signup Activity
                Intent it = new Intent(login.this, signup.class);
                startActivity(it);
                break;
        }
    }
}
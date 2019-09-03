package com.test.motofit_temp.test_1;

import android.content.Intent;
import android.media.tv.TvContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity
{
    EditText pwd,mail;
    Button loginup;
    TextView signup;
    ProgressBar pb;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        loginup = (Button) findViewById(R.id.but1);
        mail= (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.tv1);
        pb = (ProgressBar)  findViewById(R.id.progressBar);
        loginup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
                pb.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Intent it = new Intent(login.this, home.class);
                            it.addFlags(it.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(it);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
















        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(login.this,signup.class);
                startActivity(it);
            }
        });
    }
}

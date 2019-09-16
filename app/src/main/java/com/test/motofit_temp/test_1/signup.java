package com.test.motofit_temp.test_1;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity {
    EditText mail, pass,mophone,usrname;
    Button sup;
    ProgressBar pb;
    TextView lin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pwd);
        sup = (Button) findViewById(R.id.signup);
        lin = (TextView) findViewById(R.id.tv2);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        usrname = (EditText) findViewById(R.id.usrname);
        mophone = (EditText) findViewById(R.id.mobphone);
        //
        sup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
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
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_LONG).show();
                            Intent it = new Intent(signup.this,login.class);
                            it.putExtra("username",usr_name);
                            it.putExtra("mobphone",mob_num);
                            startActivity(it);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"You Are Already Registered",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }

        });
        lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });

    }
}

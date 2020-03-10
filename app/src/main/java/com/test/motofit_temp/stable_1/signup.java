package com.test.motofit_temp.stable_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.motofit_temp.stable_1.Firebase_Classes.Users;
import com.test.motofit_temp.test_1.R;

public class signup extends AppCompatActivity {
    EditText mail, pass,mophone,usrname;
    Button sup;
    ProgressBar pb;
    TextView lin;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mreference;
    private CoordinatorLayout coordinatorLayout;

    private String userId;
    private  String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        //Get Firebase Instance
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pwd);
        sup = (Button) findViewById(R.id.signup);
        lin = (TextView) findViewById(R.id.tv2);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        usrname = (EditText) findViewById(R.id.usrname);
        mophone = (EditText) findViewById(R.id.mobphone);

        sup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Snackbar snackbar = Snackbar.make(coordinatorLayout,"User Registered", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Intent it = new Intent(signup.this,login.class);
                            it.addFlags(it.FLAG_ACTIVITY_CLEAR_TOP | it.FLAG_ACTIVITY_CLEAR_TASK);

                            // Firebase Database
                            mreference = mFirebaseDatabase.getReference("Users");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            userId = user.getUid();
                            Email = user.getEmail();
                            Users myUser = new Users(usr_name,email,mob_num);
                            mreference.child(userId).setValue(myUser);

                            startActivity(it);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Snackbar snackbar = Snackbar.make(coordinatorLayout,"You Are Already Registered", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            }else{
                                Snackbar snackbar = Snackbar.make(coordinatorLayout,task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
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
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}

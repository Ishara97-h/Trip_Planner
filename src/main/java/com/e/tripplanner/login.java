package com.e.tripplanner;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    Button b3;
    EditText louser,lopw;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        louser= (EditText) findViewById(R.id.louser);
        lopw= (EditText) findViewById(R.id.lopw);
        b3= (Button) findViewById(R.id.b3);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            finish();
            startActivity(new Intent(login.this,home.class));
        }

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(louser.getText().toString(), lopw.getText().toString());
            }
        });

    }
    private void validate(String userName, String userPassword){


        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                    //startActivity(new Intent(login.this,home.class));
                }else{
                    Toast.makeText(login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if (emailflag){
            finish();
            startActivity(new Intent(login.this,home.class));
        }else{
            Toast.makeText(login.this,"Verify your Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}

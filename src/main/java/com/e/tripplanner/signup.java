package com.e.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class signup extends AppCompatActivity {

    private EditText reuser,repw,email,phone;
    private Button sign;
    FirebaseAuth firebaseAuth;
    private String name,password,useremail,userphone,suggestiontest;
    private ArrayList<String> sutest = new ArrayList<>();
    private String[] sutest2=new String[7];
    private String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupUIViews();


        listItems = getResources().getStringArray(R.array.preferenceItems);
        checkedItems = new boolean[listItems.length];

        firebaseAuth = FirebaseAuth.getInstance();

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup.this);
                mBuilder.setTitle("Please select your preferences");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                                suggestiontest = item;
                                //sutest.add(i,item);
                            }
                        }
                        //mItemSelected.setText(item);

                        Toast.makeText(signup.this,sutest2[0]+","+sutest2[1] ,Toast.LENGTH_SHORT).show();
                        newFunc();

                    }
                });

                mBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();




            }
        });
    }

    private void newFunc(){
        if (validate()){
            //upload data to database
            String user_email = email.getText().toString().trim();
            String user_password = repw.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        //Toast.makeText(signup.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                        // startActivity(new Intent(signup.this,login.class));
                        sendEmailVerifivation();
                    }else{
                        Toast.makeText(signup.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void setupUIViews(){
        reuser = (EditText) findViewById(R.id.reuser);
        repw = (EditText) findViewById(R.id.repw);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        sign = (Button) findViewById(R.id.sign);
    }

    private Boolean validate(){
        Boolean result = false;

        name= reuser.getText().toString();
        password = repw.getText().toString();
        useremail = email.getText().toString();
        userphone = phone.getText().toString();

        if (name.isEmpty() || password.isEmpty() || useremail.isEmpty() || userphone.isEmpty()){
            Toast.makeText(this,"Please enter all details",Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name,useremail,userphone, suggestiontest);
        myRef.setValue(userProfile);

    }
    private void sendEmailVerifivation(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(signup.this,"Successfully Registered, Verification mail sent!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(signup.this,login.class));
                    }else{
                        Toast.makeText(signup.this,"Verification mail hasn't been sent!",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}

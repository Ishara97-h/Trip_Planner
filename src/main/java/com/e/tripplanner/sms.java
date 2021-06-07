package com.e.tripplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sms extends AppCompatActivity {
    Button smsbut;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mref;
    String a,user_id,b;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        smsbut = findViewById(R.id.smsbut);
        mAuth = FirebaseAuth.getInstance();

        smsbut.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)){
            smsbut.setEnabled(true);
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }






    }

    public void onSend(View v){
        user_id = mAuth.getCurrentUser().getUid();
        mref = FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("userName");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mref = FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("userPhone");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                b = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String pno = "0778564311";
        String mes = a+","+b;

        if (pno == null || pno.length() == 0 ||
        mes == null || mes.length() == 0){
            return;
        }
        if (checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(pno,null,mes,null,null);
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}

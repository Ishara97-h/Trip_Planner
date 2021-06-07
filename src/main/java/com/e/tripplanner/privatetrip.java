package com.e.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


import androidx.appcompat.app.AppCompatActivity;

public class privatetrip extends AppCompatActivity {

    private Button search;
    FirebaseAuth firebaseAuth;
    public  EditText datepicker,datepicker2;
    private EditText area,visitor;
    public static String b;
    DatePickerDialog.OnDateSetListener setListener;
    private String area2,visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privatetrip);

        area = findViewById(R.id.area);
        area2 = area.getText().toString();
        visitor = findViewById(R.id.visitors);
        visit = visitor.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();

        search = findViewById(R.id.search);
        datepicker = findViewById(R.id.datepicker);
        datepicker2 = findViewById(R.id.datepicker2);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int year2 = calendar.get(Calendar.YEAR);
        final int month2 = calendar.get(Calendar.MONTH);
        final int day2 = calendar.get(Calendar.DAY_OF_MONTH);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = area.getText().toString();
                Intent intent = new Intent(privatetrip.this, PostListActivity.class);
                startActivity(intent);
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(privatetrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        datepicker.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        datepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(privatetrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        datepicker2.setText(date);
                    }
                },year2,month2,day2);
                datePickerDialog.show();

            }
        });


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference().child("Users2").child(firebaseAuth.getUid());
        UserDetails userDetails = new UserDetails(area2,visit);
        myRef.setValue(userDetails);
    }
}

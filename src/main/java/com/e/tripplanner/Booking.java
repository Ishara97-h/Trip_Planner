package com.e.tripplanner;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView textView_arrivalDate, textView_departureDate;
    private int year, month, day;
    private DatePickerDialog datePickerDialog;
    private int arr_year, arr_month, arr_day;
    private boolean isDate = false;
    private ProgressDialog progressDialog;
    String arrivalDate, departureDate;
    Button subbtn;
    String mail,text;
    EditText sub_name,sub_address,sub_mobile,adults,children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        subbtn = findViewById(R.id.subbtn);
        mail= "1234rexexe@gmail.com";

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        sub_name = findViewById(R.id.sub_name);
        sub_address = findViewById(R.id.sub_address);
        sub_mobile = findViewById(R.id.sub_mobile);
        adults = findViewById(R.id.adults);
        children = findViewById(R.id.children);

        progressDialog = new ProgressDialog(Booking.this);

        Spinner spinner_room_book_room = findViewById(R.id.spinner_room_book_room);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_room_book_room.setAdapter(adapter);
        spinner_room_book_room.setOnItemSelectedListener(this);

        textView_arrivalDate = findViewById(R.id.textView_arrivalDate_booking);
        textView_departureDate = findViewById(R.id.textView_departureDate_booking);

        textView_arrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog = new DatePickerDialog(Booking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        arr_year = year;
                        arr_month = month;
                        arr_day = dayOfMonth;

                        isDate = true;

                        arrivalDate = selectDate(dayOfMonth, month, year);
                        textView_arrivalDate.setText(arrivalDate);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        textView_departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(arr_year, arr_month, arr_day);
                long startTime = calendar.getTimeInMillis();

                if (isDate) {
                    datePickerDialog = new DatePickerDialog(Booking.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            departureDate = selectDate(dayOfMonth, month, year);
                            textView_departureDate.setText(departureDate);
                        }
                    }, arr_year, arr_month, arr_day + 1);
                    datePickerDialog.getDatePicker().setMinDate(startTime - 1000);
                    datePickerDialog.show();
                } else {
                    Toast.makeText(Booking.this, getResources().getString(R.string.please_select_first_arrivalDate), Toast.LENGTH_SHORT).show();
                }
            }
        });



        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a=sub_name.getText().toString();
                String b=sub_address.getText().toString();
                String c=sub_mobile.getText().toString();
                String d=adults.getText().toString();
                String e=children.getText().toString();
                String f=textView_arrivalDate.getText().toString();
                String g=textView_departureDate.getText().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:"+mail));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Room Booking");
                //intent.putExtra(Intent.EXTRA_TEXT,sub_name.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,"Name : "+a+"\n"+"Address : "+b+"\n"+"Mobile : "+c+"\n"+"Room : "+text+"\n"+"Adults : "+d+"\n"+"Children : "+e+"\n"+"Arrival Date : "+f+"\n"+"Departure Date : "+g);
                startActivity(intent);
            }
        });



    }

    public String selectDate(int day, int month, int year) {

        String monthYear;
        String dayMonth;

        if (month + 1 < 10) {
            monthYear = "0" + String.valueOf(month + 1);
        } else {
            monthYear = String.valueOf(month + 1);
        }
        if (day < 10) {
            dayMonth = "0" + String.valueOf(day);
        } else {
            dayMonth = String.valueOf(day);
        }

        return dayMonth + "/" + monthYear + "/" + String.valueOf(year);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

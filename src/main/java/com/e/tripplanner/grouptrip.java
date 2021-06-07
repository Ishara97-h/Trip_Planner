package com.e.tripplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class grouptrip extends AppCompatActivity {

    CardView v1,v2,v3;
    TextView url1,url2,url3,url4;
    ImageView img1,img2,img3,img4;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private DatabaseReference childrefrence = reference.child("Grouptrip").child("01").child("Image");
    private DatabaseReference childrefrence2 = reference.child("Grouptrip").child("02").child("Image");
    private DatabaseReference childrefrence3 = reference.child("Grouptrip").child("03").child("Image");
    private DatabaseReference childrefrence4 = reference.child("Grouptrip").child("04").child("Image");

    private DatabaseReference textref1 = reference.child("Grouptrip").child("01").child("Name");
    private DatabaseReference textref2 = reference.child("Grouptrip").child("02").child("Name");
    private DatabaseReference textref3 = reference.child("Grouptrip").child("03").child("Name");
    private DatabaseReference textref4 = reference.child("Grouptrip").child("04").child("Name");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouptrip);

        url1 = findViewById(R.id.text1);
        img1 = findViewById(R.id.image1);

        url2 = findViewById(R.id.text2);
        img2 = findViewById(R.id.image2);

        url3 = findViewById(R.id.text3);
        img3 = findViewById(R.id.image3);

        url4 = findViewById(R.id.text4);
        img4 = findViewById(R.id.image4);

        v1 = (CardView)findViewById(R.id.v1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(grouptrip.this, gt1.class);
                startActivity(intent);
            }
        });

        v2= (CardView)findViewById(R.id.v2);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(grouptrip.this, pdfgene.class);
                startActivity(intent);
            }
        });

        v3= (CardView)findViewById(R.id.v3);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(grouptrip.this, sms.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        childrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
               // url1.setText(message);
                Picasso.get()
                        .load(message)
                        .into(img1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        childrefrence2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                // url1.setText(message);
                Picasso.get()
                        .load(message)
                        .into(img2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        childrefrence3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                // url1.setText(message);
                Picasso.get()
                        .load(message)
                        .into(img3);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        childrefrence4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                // url1.setText(message);
                Picasso.get()
                        .load(message)
                        .into(img4);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                url1.setText(message);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                url2.setText(message);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                url3.setText(message);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                url4.setText(message);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.e.tripplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    CardView groupbutton,privatebutton;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        groupbutton= (CardView) findViewById(R.id.groupbutton);
        groupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Map();
                GroupTrip();


            }
        });

        privatebutton= (CardView) findViewById(R.id.privatebutton);
        privatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, NewMapsActivity.class);
                startActivity(intent);

                //Map();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        //logout = (Button)findViewById(R.id.logout);

        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Logout();

            }
        });*/

    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(home.this,login.class));
    }

    public void Map(){
        Intent intent = new Intent(this, map.class);
        startActivity(intent);
    }

    public void GroupTrip(){
        Intent intent = new Intent(this, grouptrip.class);
        startActivity(intent);
    }

}


package com.e.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
//import android.widget.SearchView;
import android.view.View;
import android.widget.Toast;
//import android.support.v7.widget.SearchView;
import androidx.appcompat.widget.SearchView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class hotels extends AppCompatActivity implements AdapterClass.OnItemClickListener{

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_NAME ="Name";
    public static final String EXTRA_DISC = "Disc";

    DatabaseReference ref;
    private ArrayList<Deal> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);


        ref = FirebaseDatabase.getInstance().getReference().child("Hotels");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            list.add(ds.getValue(Deal.class));
                        }
                        final AdapterClass adapterClass = new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);
                    }

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(hotels.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

    }
    private void search(String str){
        ArrayList<Deal> myList = new ArrayList<>();
        for (Deal object : list){
            if (object.getHdisc().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        AdapterClass adapterClass = new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);
        adapterClass.setOnItemClickListener(hotels.this);

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,DetailActivity.class);
        Deal clickedItem = list.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getHimage());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getHname());
        detailIntent.putExtra(EXTRA_DISC, clickedItem.getHdisc());

        startActivity(detailIntent);
    }
}

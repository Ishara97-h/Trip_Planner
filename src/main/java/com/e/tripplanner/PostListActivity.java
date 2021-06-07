//package com.e.tripplanner;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.MenuItemCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.widget.SearchView;
//
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//import java.io.ByteArrayOutputStream;
//import static com.e.tripplanner.privatetrip.b;
//
//public class PostListActivity extends AppCompatActivity {
//
//    RecyclerView mRecyclerView;
//    FirebaseDatabase mFirebaseDatabase;
//    DatabaseReference mRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_post_list);
//
//        ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setTitle("Hotels List");
//
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mRef = mFirebaseDatabase.getReference("Hotels");
//
//
//
//    }
//
//    public void firebaseSearch(String searchText){
//        searchText = b.toLowerCase();
//        Query firebaseSearchQuery = mRef.orderByChild("harea".toLowerCase()).startAt(searchText).endAt(searchText + "\uf8ff");
//
//        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(
//                Model.class,
//                R.layout.row,
//                ViewHolder.class,
//                firebaseSearchQuery
//        ) {
//            @Override
//            protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
//
//                viewHolder.setDetails(getApplicationContext(),model.getHdisc(),model.getHimage(),model.getHname());
//
//            }
//
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
//                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        String mTitle = getItem(position).getHname();
//                        String mDesc = getItem(position).getHdisc();
//                        String mImage = getItem(position).getHimage();
//
//                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
//                        intent.putExtra("hname",mTitle);
//                        intent.putExtra("hdisc",mDesc);
//                        intent.putExtra("himage",mImage);
//                        startActivity(intent);
//
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
//
//                    }
//                });
//
//                return viewHolder;
//            }
//
//
//        };
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }
//
//   @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseSearch("");
//        /*FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(
//                Model.class,
//                R.layout.row,
//                ViewHolder.class,
//                mRef
//        ) {
//            @Override
//            protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
//                viewHolder.setDetails(getApplicationContext(),model.getHdisc(),model.getHimage(),model.getHname());
//            }
//
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
//                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                        String mTitle = getItem(position).getHname();
//                        String mDesc = getItem(position).getHdisc();
//                        String mImage = getItem(position).getHimage();
//
//                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
//                        intent.putExtra("hname",mTitle);
//                        intent.putExtra("hdisc",mDesc);
//                        intent.putExtra("himage",mImage);
//                        startActivity(intent);
//
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
//
//                    }
//                });
//
//                return viewHolder;
//            }
//        };
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);*/
//    }
//
//   /* @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu2,menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                firebaseSearch(query.toLowerCase());
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                firebaseSearch(newText.toLowerCase());
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id ==R.id.action_settings){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }*/
//}

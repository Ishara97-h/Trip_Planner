package com.e.tripplanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.e.tripplanner.Common.Common;
import com.e.tripplanner.Model.PlaceDetail;
import com.e.tripplanner.Remote.IGoogleAPIService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewViewPlaceActivity extends AppCompatActivity {

    ImageView photo;
    RatingBar ratingBar;
   // FButton btnViewOnMap,btnViewDirection;
    TextView place_name,place_address,opening_hours;
    Button bookbtn;

    IGoogleAPIService mService;

    PlaceDetail mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_place);

        photo = findViewById(R.id.photo);
        bookbtn = findViewById(R.id.bookbtn);

        ratingBar = findViewById(R.id.ratingBar);

//        btnViewOnMap = findViewById(R.id.btnShowMap);
//        btnViewDirection = findViewById(R.id.btnViewDirection);

        place_address = findViewById(R.id.places_address);
        place_name = findViewById(R.id.places_name);
        opening_hours = findViewById(R.id.places_open_hour);

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewViewPlaceActivity.this,Booking.class);
                startActivity(intent);
            }
        });

        mService = Common.getGoogleAPIService();

        place_name.setText("");
        place_address.setText("");
        opening_hours.setText("");

//        btnViewDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(NewViewPlaceActivity.this,ViewDirection.class));
//            }
//        });
//
//        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPlace.getResult().getUrl()));
//                startActivity(mapIntent);
//            }
//        });

        if (Common.currentResults.getPhotos() != null && Common.currentResults.getPhotos().length > 0)  {

            Picasso.get()
                    .load(getPhotoOfPlaces(Common.currentResults.getPhotos()[0].getPhoto_reference(),1000))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(photo);
        }

        if (Common.currentResults.getRating() != null && !TextUtils.isEmpty(Common.currentResults.getRating()))  {
            ratingBar.setRating(Float.parseFloat(Common.currentResults.getRating()));
        }
        else    {
            ratingBar.setVisibility(View.GONE);
        }

        if (Common.currentResults.getOpening_hours() != null)  {
            opening_hours.setText("Open Now : "+Common.currentResults.getOpening_hours().getOpen_now());
        }
        else    {
            opening_hours.setVisibility(View.GONE);
        }

        mService.getDetailPlaces(getPlaceDetailUrl(Common.currentResults.getPlace_id()))
                .enqueue(new Callback<PlaceDetail>() {
                    @Override
                    public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {

                        mPlace = response.body();
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+mPlace);
                        if (place_address!=null) {
                        try{
                            place_address.setText(mPlace.getResult().getFormatted_address());}
                         catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        }else{
                            place_address.setText(" 0 ");
                        }

                        if (place_name!=null) {

                            try{
                                place_name.setText(mPlace.getResult().getName());}
                            catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }else{
                            place_name.setText(" 0 ");
                        }

                    }

                    @Override
                    public void onFailure(Call<PlaceDetail> call, Throwable t) {

                    }
                });
    }

    private String getPlaceDetailUrl(String place_id) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid="+place_id);
        url.append("&key="+getResources().getString(R.string.browser_key));

        return url.toString();
    }

    private String getPhotoOfPlaces(String photo_reference,int maxWidth) {

        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth="+maxWidth);
        url.append("&photoreference="+photo_reference);
        url.append("&key="+getResources().getString(R.string.browser_key));

        return url.toString();
    }
}

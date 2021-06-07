//package com.e.tripplanner;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.e.tripplanner.Model2.PlaceData;
//import com.e.tripplanner.Remote.IGoogleAPIService;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class ViewPlace extends AppCompatActivity {
//
//    ImageView photo;
//    IGoogleAPIService mService;
//    RatingBar ratingBar;
//    PlaceData mPlace;
//    TextView place_address,place_name;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_place);
//
//        mService = Common.getGoogleAPIService();
//
//        place_address = findViewById(R.id.place_address);
//        place_name = findViewById(R.id.place_name);
//        photo = findViewById(R.id.photo);
//        ratingBar = findViewById(R.id.ratingBar);
//
//        place_name.setText("");
//        place_address.setText("");
//
////        if (Common.currentResults.getPhotos() != null && Common.currentResults.getPhotos().length > 0)  {
////
////            Picasso.get()
////                    .load(getPhotoOfPlaces(Common.currentResults.getPhotos()[0].getPhoto_reference(),1000))
////                    .placeholder(R.drawable.ic_image_black_24dp)
////                    .error(R.drawable.ic_clear_black_24dp)
////                    .into(photo);
////        }
////        if (Common.currentResult.getPhotos() != null && Common.currentResult.getPhotos().length > 0) {
////            Picasso.get()
////                    .load(getPhotoOfPlace(Common.currentResult.getPhotos()[0].getPhoto_reference(),1000))
////                    .into(photo);
////        }
////        mService.getDeyailPlace(getPlaceDetailUrl(Common.currentResult.getPlace_id()))
////                .enqueue(new Callback<PlaceData>() {
////                    @Override
////                    public void onResponse(Call<PlaceData> call, Response<PlaceData> response) {
////
////                        mPlace = response.body();
////                        place_address.setText(mPlace.getResult().getName());
////
////
////                    }
////
////                    @Override
////                    public void onFailure(Call<PlaceData> call, Throwable t) {
////
////                    }
////                });
//
//        System.out.println(" current result is in the ViewPlace====== " + Common.currentResults.getPlace_id());
//
//        mService.getDeyailPlace(getPlaceDetailUrl(Common.currentResults.getPlace_id()))
//                .enqueue(new Callback<PlaceData>() {
//                    @Override
//                    public void onResponse(Call<PlaceData> call, Response<PlaceData> response) {
//
//
//                        mPlace = response.body();
//
//                        //HotelMap.currentPlace2 = mPlace;
//
//
//                        System.out.println("bbbbbbbbbbb"+mPlace);
//
//                       // place_address.setText(mPlace.getResult().getFormatted_address());
//                        place_name.setText(mPlace.getResult().getName());
//                    }
//
//                    @Override
//                    public void onFailure(Call<PlaceData> call, Throwable t) {
//
//                    }
//                });
//
//
//
//
//
//    }
//
//    private String getPlaceDetailUrl(String place_id) {
//        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
//        url.append("?placeid="+place_id);
//        url.append("&key"+getResources().getString(R.string.hey));
//        return url.toString();
//    }
//
//    private String getPhotoOfPlaces(String photo_reference,int maxWidth) {
//
//        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
//        url.append("?maxwidth="+maxWidth);
//        url.append("&photoreference="+photo_reference);
//        url.append("&key="+getResources().getString(R.string.browser_key));
//
//        return url.toString();
//    }
//}

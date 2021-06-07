package com.e.tripplanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.e.tripplanner.Common.Common;
import com.e.tripplanner.Model.MyPlaces;
import com.e.tripplanner.Model.Results;
import com.e.tripplanner.Remote.IGoogleAPIService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSION_CODE = 1000;
    private GoogleMap mMap;

    private double latitude,longitude;
    private Location mLastLocation;
    private Marker mMarker;
    private LocationRequest mLocationRequest;

    IGoogleAPIService mService;

    MyPlaces currentPlaces;
    SearchView searchView;
    private Marker marker1;

    private LatLng loclat;
    private Address sLocation;

    private Address address;
    public static String locA;
    public String location;

    private double slat,slong;
    Button bottom_navigation,bottom_navigation2;


    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mService = Common.getGoogleAPIService();
        searchView = findViewById(R.id.sv_location);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkLocationPermission();
        }
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation2 = findViewById(R.id.bottom_navigation2);

        bottom_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearByPlaces("hotels");

            }
        });




//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId())   {
//
//                    case R.id.action_hospital:
//                        nearByPlaces("hotels");
//                        break;
//
//                    case R.id.action_market:
//                        nearByPlaces("market");
//                        break;
//
//                    case R.id.action_restaurant:
//                        nearByPlaces("restaurant");
//                        break;
//
//                    case R.id.action_school:
//                        nearByPlaces("school");
//                        break;
//                }
//
//                return true;
//            }
//        });true

        buildLocationRequest();
        buildLocationCallback();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,locationCallback, Looper.myLooper());

        bottom_navigation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nearByPlaces2("tourist_attraction");

            }
        });
    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback()   {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);




                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        location = searchView.getQuery().toString();
                        locA = location;
                        List<Address> addressList = null;
                        if (location != null || !location.equals("")){
                            Geocoder geocoder = new Geocoder(NewMapsActivity.this);
                            try {
                                addressList = geocoder.getFromLocationName(location,1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            address = addressList.get(0);

                            sLocation=address;
                            loclat = new LatLng(address.getLatitude(),address.getLongitude());



                            if (mMarker!=null) {


                                mMarker.remove();
                            }
                            latitude= address.getLatitude();
                            longitude=address.getLongitude();
                            LatLng latLng = new LatLng(latitude,longitude);

                            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                    .title("Your Location")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                            mMarker = mMap.addMarker(markerOptions);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                        }


                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });







//                mLastLocation = locationResult.getLastLocation();
//
//                if (mMarker != null)    {
//                    mMarker.remove();
//                }
//
//                latitude = mLastLocation.getLatitude();
//                longitude = mLastLocation.getLongitude();
//
//                LatLng latLng = new LatLng(latitude,longitude);
//
//                MarkerOptions markerOptions = new MarkerOptions().position(latLng)
//                        .title("Your Location")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//                mMarker = mMap.addMarker(markerOptions);
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        };
    }

    @SuppressLint("RestrictedApi")
    private void buildLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(10f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void nearByPlaces(final String placeType) {
        mMap.clear();
        String url = getUrl(latitude,longitude,placeType);

        mService.getNearByPlaces(url)
                .enqueue(new Callback<MyPlaces>() {
                    @Override
                    public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {

                        currentPlaces = response.body();

                        if (response.isSuccessful())    {
                            for (int i = 0; i < response.body().getResults().length; i++)   {

                                MarkerOptions markerOptions = new MarkerOptions();
                                Results googlePlaces = response.body().getResults()[i];

                                double lat = Double.parseDouble(googlePlaces.getGeometry().getLocation().getLat());
                                double lng = Double.parseDouble(googlePlaces.getGeometry().getLocation().getLng());

                                String placeName = googlePlaces.getName();
                                String vicinity = googlePlaces.getVicinity();
                                LatLng latLng = new LatLng(lat,lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName);

                                if (placeType.equals("hotels")) {
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel2));
                                }
//                                else if (placeType.equals("market")) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cart));
//                                }
//                                else if (placeType.equals("restaurant")) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant));
//                                }
//                                else if (placeType.equals("school")) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_school));
//                                }
//                                else    {
//                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                                }

                                markerOptions.snippet(String.valueOf(i));

                                mMap.addMarker(markerOptions);

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlaces> call, Throwable t) {

                    }
                });
    }

    private void nearByPlaces2(final String placeType) {
        mMap.clear();
        String url = getUrl(latitude,longitude,placeType);

        mService.getNearByPlaces(url)
                .enqueue(new Callback<MyPlaces>() {
                    @Override
                    public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {

                        currentPlaces = response.body();

                        if (response.isSuccessful())    {
                            for (int i = 0; i < response.body().getResults().length; i++)   {

                                MarkerOptions markerOptions = new MarkerOptions();
                                Results googlePlaces = response.body().getResults()[i];

                                double lat = Double.parseDouble(googlePlaces.getGeometry().getLocation().getLat());
                                double lng = Double.parseDouble(googlePlaces.getGeometry().getLocation().getLng());

                                String placeName = googlePlaces.getName();
                                String vicinity = googlePlaces.getVicinity();
                                LatLng latLng = new LatLng(lat,lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName);

                                if (placeType.equals("tourist_attraction")) {
                                    markerOptions.title("sss");
                                }
//                                else if (placeType.equals("market")) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cart));
//                                }
//                                else if (placeType.equals("restaurant")) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant));
//                                }
//                                else if (placeType.equals("school")) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_school));
//                                }
//                                else    {
//                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                                }

                                markerOptions.snippet(String.valueOf(i));

                                mMap.addMarker(markerOptions);

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlaces> call, Throwable t) {

                    }
                });
    }



    private String getUrl(double latitude, double longitude, String placeType) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location="+latitude+","+longitude);
        googlePlacesUrl.append("&radius="+10000);
        googlePlacesUrl.append("&type="+placeType);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key="+getResources().getString(R.string.browser_key));

        Log.d("getUrl",googlePlacesUrl.toString());

        return googlePlacesUrl.toString();
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION

                },MY_PERMISSION_CODE);
            }
            else    {
                ActivityCompat.requestPermissions(this,new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION

                },MY_PERMISSION_CODE);
            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)    {

            case MY_PERMISSION_CODE :   {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)    {

                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)  {

                        mMap.setMyLocationEnabled(true);
                        buildLocationRequest();
                        buildLocationCallback();

                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

                        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,locationCallback, Looper.myLooper());
                    }
                }
            }
            break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
        else    {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getSnippet() != null) {
                    System.out.println(currentPlaces+"aaaaaccccc");
                    Common.currentResults = currentPlaces.getResults()[Integer.parseInt(marker.getSnippet())];

                    startActivity(new Intent(NewMapsActivity.this, NewViewPlaceActivity.class));
                }
                return true;
            }
        });
    }
}

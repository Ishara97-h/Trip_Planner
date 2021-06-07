//package com.e.tripplanner;
//
//import android.Manifest;
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Looper;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.SearchView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.FragmentActivity;
//
//import com.e.tripplanner.Interface.IOnLoadLocationListener;
//import com.firebase.geofire.GeoFire;
//import com.firebase.geofire.GeoLocation;
//import com.firebase.geofire.GeoQueryEventListener;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener, IOnLoadLocationListener {
//
//    private GoogleMap mMap;
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private Marker currentUser,user;
//    private DatabaseReference myLocationRef,placeName;
//    private GeoFire geoFire;
//    private List<LatLng> dangerousArea;
//    private IOnLoadLocationListener listener;
//    String message;
//    private List<String> test;
//    private Button mapButton;
//    private EditText mSearchText;
//    private static final String TAG = "MyActivity";
//    private static final float DEFAULT_ZOOM = 15f;
//    SupportMapFragment mapFragment;
//    SearchView searchView;
//    private Marker marker;
//
//    private LatLng loclat;
//    private Address sLocation;
//
//
//    //new
//
//    private EditText searchbarf;
//    GoogleApiClient googleApiClient;
//    public GoogleMap fmap;
//    private SupportMapFragment supportMapFragmentf;
//    private FusedLocationProviderClient mfusedLocationProviderClient;
//
//    Location lastLocationf;
//    Location lastLocationclnew;
//    LocationRequest locationRequestf;
//    LatLng temp;
//    private Button fuelbtn;
//    private FirebaseAuth firebaseAuth;
//
//    private double latitude,longtitude;
//    private String hospital="hospital";
//    private String fuelstation="gas_station";
//
//
//    private int proximityRadius=10000;
//
//    private Button selectbtn;
//
//    private boolean isSelectOpen=false;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps3);
//        searchView = findViewById(R.id.sv_location);
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String location = searchView.getQuery().toString();
//                List<Address> addressList = null;
//                if (location != null || !location.equals("")){
//                    Geocoder geocoder = new Geocoder(MapsActivity.this);
//                    try {
//                        addressList = geocoder.getFromLocationName(location,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//
//                    sLocation=address;
//                     loclat = new LatLng(address.getLatitude(),address.getLongitude());
//
//
//                    if (marker!=null) {
//
//
//                        marker.remove();
//                    }
//
//                   marker= mMap.addMarker(new MarkerOptions().position(loclat).title(location));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loclat,10));
//
//                    searchNearbyFuel();
//                }
//
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        mapFragment.getMapAsync(this);
//
//
//
//        //mSearchText = findViewById(R.id.input_search);
//
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//
//                        mapButton = findViewById(R.id.mapButton);
//                        mapButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(MapsActivity.this,pdfgene.class);
//                                startActivity(intent);
//                            }
//                        });
//                        buildLocationRequest();
//                        buildLocationCallback();
//                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
//                        placeName = FirebaseDatabase.getInstance().getReference("DangerousArea").child("MyCity").child("1").child("name");
//
//                        placeName.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                message = dataSnapshot.getValue(String.class);
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//                        initArea();
//                        settingGeoFire();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        Toast.makeText(MapsActivity.this, "You must enable permission", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                    }
//                }).check();
//
//
//    }
//
//   /* private void init(){
//        Log.d(TAG, "init: initializing");
//        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if(actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
//
//                    geoLocate();
//                }
//
//                return false;
//            }
//        });
//
//    }*/
//
//   /* private void geoLocate(){
//        Log.d(TAG, "geoLocate: geolocating");
//
//        String searchString = mSearchText.getText().toString();
//
//        Geocoder geocoder = new Geocoder(MapsActivity.this);
//        List<Address> list = new ArrayList<>();
//        try{
//            list = geocoder.getFromLocationName(searchString, 1);
//        }catch (IOException e){
//            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
//        }
//
//        if(list.size() > 0){
//            Address address = list.get(0);
//
//            Log.d(TAG, "geoLocate: found a location: " + address.toString());
//            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
//
//            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
//                    address.getAddressLine(0));
//        }
//    }*/
//
//    private void initArea() {
//
//        listener = this;
//
//        //Load from Firebase
//        FirebaseDatabase.getInstance()
//                .getReference("DangerousArea")
//                .child("MyCity")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        List<MyLatLng> latLngList = new ArrayList<>();
//                        for(DataSnapshot locationSnapShot: dataSnapshot.getChildren())
//                        {
//                            MyLatLng latLng = locationSnapShot.getValue(MyLatLng.class);
//                            latLngList.add(latLng);
//                        }
//                        listener.onLoadLocationSuccess(latLngList);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        listener.onLoadLocationFailed(databaseError.getMessage());
//                    }
//                });
//
//       /* dangerousArea = new ArrayList<>();
//        dangerousArea.add(new LatLng(6.346602,80.225914));*/
//
//        /*FirebaseDatabase.getInstance()
//                .getReference("DangerousArea")
//                .child("MyCity")
//                .setValue(dangerousArea)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(MapsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MapsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });*/
//
//    }
//
//    private void settingGeoFire() {
//        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
//        geoFire = new GeoFire(myLocationRef);
//    }
//
//    private void buildLocationCallback() {
//        locationCallback = new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if(mMap != null){
//                    if(currentUser != null)currentUser.remove();
//                    currentUser = mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(locationResult.getLastLocation().getLatitude(),
//                            locationResult.getLastLocation().getLongitude()))
//                    .title("You"));
//
//                    /*user = mMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(6.346602,80.225914))
//                            .title("user"));*/
//
//                    mMap.animateCamera(CameraUpdateFactory
//                    .newLatLngZoom(currentUser.getPosition(),12.0f));
//                }
//            }
//        };
//    }
//
//    private void buildLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setSmallestDisplacement(10f);
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        if  (fusedLocationProviderClient != null)
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());
//
//      /*  for(LatLng latLng : dangerousArea){
//            mMap.addMarker(new MarkerOptions().position(latLng)
//            .title("Place")
//                    .snippet(message)
//            );
//
//            GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude,latLng.longitude),0.5f);//500m
//            geoQuery.addGeoQueryEventListener(MapsActivity.this);
//        }
//        init();*/
//    }
//
//    @Override
//    protected void onStop() {
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//        super.onStop();
//    }
//
//    @Override
//    public void onKeyEntered(String key, GeoLocation location) {
//        sendNotification("TripPlanner",String.format("%s entered the dangerous area",key));
//    }
//
//    @Override
//    public void onKeyExited(String key) {
//        sendNotification("TripPlanner",String.format("%s leave the dangerous area",key));
//
//    }
//
//    private void sendNotification(String title, String content) {
//    }
//
//    @Override
//    public void onKeyMoved(String key, GeoLocation location) {
//        sendNotification("TripPlanner",String.format("%s move within the dangerous area",key));
//
//    }
//
//    @Override
//    public void onGeoQueryReady() {
//
//    }
//
//    @Override
//    public void onGeoQueryError(DatabaseError error) {
//        Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onLoadLocationSuccess(List<MyLatLng> latLngs) {
//        dangerousArea = new ArrayList<>();
//        for(MyLatLng myLatLng : latLngs)
//        {
//            LatLng convert = new LatLng(myLatLng.getLatitude(),myLatLng.getLongitude());
//            dangerousArea.add(convert);
//
//        }
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(MapsActivity.this);
//    }
//
//    @Override
//    public void onLoadLocationFailed(String message) {
//        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
//    }
//
//
//
//
//
//    public void searchNearbyFuel() {
//
//        final Object transferData[] = new Object[2];
//        final GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
//
//
//        latitude = sLocation.getLatitude();
//        longtitude = sLocation.getLongitude();
//
//        String url = getUrl(latitude, longtitude, fuelstation);
//
//        transferData[0] = fmap;
//        transferData[1] = url;
//
//        getNearbyPlaces.execute(transferData);
//        Toast.makeText(MapsActivity.this, "Searching For Nearby Fuel Stations", Toast.LENGTH_LONG).show();
//        Toast.makeText(MapsActivity.this, "Showing Nearby Fuel Stations", Toast.LENGTH_LONG).show();
//
//    }
//
//
//    private String getUrl(double latitude, double longtitude, String nearbyPlace){
//
//
//
//        StringBuilder googleURl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googleURl.append("location="+latitude+","+longtitude);
//        googleURl.append("&radius="+proximityRadius);
//
//        googleURl.append("&type="+ nearbyPlace);
//        googleURl.append("&sensor=true");
//        googleURl.append("&key="+"AIzaSyA1f5S7UZa959RviWY4ioK5ZETUSuHMwLA");
//
//
//        System.out.println("URLLLLLLL"+googleURl.toString());
//
//        return googleURl.toString();
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//    private void init(){
//
//        System.out.println("Initializing");
//        searchbarf.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//
//                if (actionId== EditorInfo.IME_ACTION_SEARCH || actionId==EditorInfo.IME_ACTION_DONE
//                        ||keyEvent.getAction()==KeyEvent.ACTION_DOWN||keyEvent.getAction()==KeyEvent.KEYCODE_ENTER){
//
//                    geoLocate();
//
//                }
//                return false;
//            }
//        });
//    }
//
//
//
//    private void geoLocate(){
//
//        System.out.println("GeoLocating.......");
//
//        String searchString =searchbarf.getText().toString();
//
//        Geocoder geocoder=new Geocoder(MapsActivity.this);
//
//        List<Address> list=new ArrayList<>();
//
//        try{
//
//            list=geocoder.getFromLocationName(searchString,1);
//        }
//
//        catch (IOException e){
//
//            System.out.println("Exception thrown"+e);
//        }
//
//        if (list.size()>0){
//
//            Address address=list.get(0);
//
//            System.out.println("SearchBar task successful "+address.toString());
//
//            // moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),18,address.getAddressLine(0));
//            LatLng ln=new LatLng(address.getLatitude(),address.getLongitude());
//            fmap.moveCamera(CameraUpdateFactory.newLatLngZoom(ln,18));
//
//            temp=ln;
//
//            if(marker!=null){
//                marker.remove();
//            }
//
//
//
//            MarkerOptions markerOptionscl=new MarkerOptions()
//                    .position(temp)
//                    .title("Searched Location").draggable(true);
//
//            marker=fmap.addMarker(markerOptionscl);
//
//            //saveconfirmed();
//
//            fmap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//                @Override
//                public void onMarkerDragStart(Marker marker) {
//
//                }
//
//                @Override
//                public void onMarkerDrag(Marker marker) {
//
//                }
//
//                @Override
//                public void onMarkerDragEnd(Marker marker) {
//
//                    temp=marker.getPosition();
//
//                }
//            });
//
//
//
//        }
//    }
//
//
//}

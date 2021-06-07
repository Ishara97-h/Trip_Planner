//package com.e.tripplanner;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.os.Bundle;
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
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.e.tripplanner.Model2.MyPlaces;
//import com.e.tripplanner.Remote.IGoogleAPIService;
//import com.google.android.gms.common.ConnectionResult;
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
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class HotelMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
//
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
//    private Button fuelbtn,nextbtn;
//    Marker marker;
//    private FirebaseAuth firebaseAuth;
//
//    private double latitude,longtitude;
//   private String hospital="hospital";
//   // private String hotels="tourist_attraction";
//    private String hotels="hotels";
//    public String location;
//
//
//
//    private int proximityRadius=10000;
//
//    private Button selectbtn;
//
//    private boolean isSelectOpen=false;
//
//   // SupportMapFragment mapFragment;
//    SearchView searchView;
//    private Marker marker1;
//
//    private LatLng loclat;
//    private Address sLocation;
//
//    private Address address;
//    public static String locA;
//
//
//    private double slat,slong;
//
//   public static MyPlaces currentPlace;
//   // public static PlaceData currentPlace2;
//
//
//    IGoogleAPIService mService;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hotel_map);
//        searchView = findViewById(R.id.sv_location);
//       // mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//
//
//        //searchbarf=(EditText)findViewById(R.id.inputSearch_f);
//        fuelbtn=(Button)findViewById(R.id.fuelbtn);
//        nextbtn=(Button)findViewById(R.id.nextbtn);
//
//        firebaseAuth= FirebaseAuth.getInstance();
//
//
//
//        supportMapFragmentf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_f);
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(HotelMap.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQ_CODE );
//        }
//        else {
//            supportMapFragmentf.getMapAsync(this);
//        }
//
//        mService = Common.getGoogleAPIService();
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                location = searchView.getQuery().toString();
//                locA = location;
//                List<Address> addressList = null;
//                if (location != null || !location.equals("")){
//                    Geocoder geocoder = new Geocoder(HotelMap.this);
//                    try {
//                        addressList = geocoder.getFromLocationName(location,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                     address = addressList.get(0);
//
//                    sLocation=address;
//                    loclat = new LatLng(address.getLatitude(),address.getLongitude());
//
//
////                    if (marker1!=null) {
//
//
//                        marker1.remove();
//                    }
//
//                    marker1= fmap.addMarker(new MarkerOptions().position(loclat).title(location));
//                    fmap.animateCamera(CameraUpdateFactory.newLatLngZoom(loclat,10));
//
//                    searchNearbyFuel();
//                   // nearByPlaces("hotels");
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
//        nextbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HotelMap.this, AttractionMap.class);
//                startActivity(intent);
//            }
//        });
//
//        //mapFragment.getMapAsync(this);
//
//        //searchNearbyFuel();
//
//
//
//
//
//    }
//
//
//    public void searchNearbyFuel(){
//
//final Object transferData[]=new Object[2];
//final GetNearbyPlaces getNearbyPlaces=new GetNearbyPlaces();
//
//        fuelbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                slat=address.getLatitude();
//                slong=address.getLongitude();
//                String url=getUrl(slat,slong,hotels);
//
//                transferData[0]=fmap;
//                transferData[1]=url;
//
//                getNearbyPlaces.execute(transferData);
//                Toast.makeText(HotelMap.this,"Searching For Nearby Hotels",Toast.LENGTH_LONG).show();
//                Toast.makeText(HotelMap.this,"Showing Nearby Hotels",Toast.LENGTH_LONG).show();
//
//
//                mService.getNearByPlaces(url)
//                        .enqueue(new Callback<MyPlaces>() {
//                            @Override
//                            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
//
//                                currentPlace = response.body();
//
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<MyPlaces> call, Throwable t) {
//
//                            }
//                        });
//
//
//
//            }
//        });
//
//
//
//
//
//
//    }
//
//
//    private String getUrl(double latitude, double longtitude, String nearbyPlace){
//
//StringBuilder googleURl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//googleURl.append("location="+latitude+","+longtitude);
//googleURl.append("&radius="+proximityRadius);
//
//googleURl.append("&type="+ nearbyPlace);
//googleURl.append("&sensor=true");
//googleURl.append("&key="+"AIzaSyBNZ9VYGOv6_dFQdn-dOY4XFKErRYOoTFk");
//
//
//        System.out.println("URLLLLLLL"+googleURl.toString());
//
//return googleURl.toString();
//    }
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        if (getApplicationContext() != null) {
//
//
//            latitude=location.getLatitude();
//            longtitude=location.getLongitude();
//            lastLocationclnew = location;
//
//            LatLng latLng=new LatLng(lastLocationclnew.getLatitude(),lastLocationclnew.getLongitude());}
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//        mfusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
//
//        locationRequestf = new LocationRequest();
//        locationRequestf.setInterval(1000);
//        locationRequestf.setFastestInterval(1000);
//        locationRequestf.setPriority(LocationRequest.PRIORITY_LOW_POWER);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(HotelMap.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQ_CODE );
//        }
//
//
//        mfusedLocationProviderClient.requestLocationUpdates(locationRequestf,new LocationCallback(){
//
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                if (locationResult.getLastLocation()!=null){
//
//
//                    //   Toast.makeText(ChooseLocation.this,"Lati: "+locationResult.getLastLocation().getLatitude()+"Long: "+locationResult.getLastLocation().getLongitude(),Toast.LENGTH_LONG).show();
//                    lastLocationclnew=locationResult.getLastLocation();
//                }
//
//                onLocationChanged(locationResult.getLastLocation());
//
//            }
//        },getMainLooper() );
//
//
//        mfusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//
//                if (task.isSuccessful()){
//
//                    lastLocationf=(Location)task.getResult();
//
//                    LatLng lt=new LatLng(lastLocationf.getLatitude(),lastLocationf.getLongitude());
//
//
//                    fmap.moveCamera(CameraUpdateFactory.newLatLng(lt));
//                    fmap.animateCamera(CameraUpdateFactory.zoomTo(18));
//                }
//
//                else {
//                    Toast.makeText(HotelMap.this,"task failed",Toast.LENGTH_LONG).show();}
//            }
//        });
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//
//
//
//    @Override
//    public void onMapReady(final GoogleMap googleMap) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        buildGoogleApiClient();
//       fmap=googleMap;
//        fmap.setMyLocationEnabled(true);
//
//        /*fmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//              //  Common.currentResult = currentPlace.getResults()[Integer.parseInt(marker.getSnippet())];
//
//
//                Common.currentResults = currentPlace.getResults()[Integer.parseInt(marker.getSnippet())];
//                Intent intent =  new Intent(HotelMap.this,ViewPlace.class);
//                //startActivity(new Intent(HotelMap.this,ViewPlace.class));
//                Bundle extras = new Bundle();
//
//                //   extras.putInt("int-key", int_value);
//
//                extras.putString("string-key", String.valueOf(marker1.getTitle()));
//                System.out.println(marker1.getTitle()+"    ghghghgghggggggggggggggggggggggg");
//                // extras.putFloat("float-key", key);
//                intent.putExtras(extras);
//                startActivity(intent);
//                return false;
//            }
//        });*/
//
//
//        fmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                if (marker.getSnippet() != null) {
//                    System.out.println(marker.getSnippet()+"  aaaaaaaaaaaaaaaaacccccccccccc");
//                    System.out.println("ggggg " + currentPlace);
//
//
//                    Common.currentResults = currentPlace.getResults()[Integer.parseInt(marker.getSnippet())];
//
//                    System.out.println(" current result is ====== " + Common.currentResults);
//
//                    startActivity(new Intent(HotelMap.this, ViewPlace.class));
//                }
//                return true;
//            }
//        });
//
//
//       // init();
//       // confirmLocation();
//
//    }
//
//    protected synchronized void buildGoogleApiClient(){
//
//        googleApiClient=new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        googleApiClient.connect();
//    }
//    final int LOCATION_REQ_CODE=1;
//
//
//    //
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//
//
//            //new
//            case LOCATION_REQ_CODE:{
//
//                if(grantResults.length>0  &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//
//                    supportMapFragmentf.getMapAsync(this);
//                }
//
//                else{
//
//                    Toast.makeText(getApplicationContext(), "Please give permission", Toast.LENGTH_SHORT).show();
//                }
//
//                break;}
//            //
//        }
//    }
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
//        Geocoder geocoder=new Geocoder(HotelMap.this);
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
//                fmap.clear();
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
//    private String getPlaceDetailUrl(String place_id) {
//        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
//        url.append("?placeid="+place_id);
//        url.append("&key"+getResources().getString(R.string.hey));
//        return url.toString();
//    }
//
//}

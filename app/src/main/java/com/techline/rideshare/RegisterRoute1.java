package com.techline.rideshare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.techline.rideshare.util.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class RegisterRoute1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private double long_val, lat_val;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap m_map;
    private Context context = this;
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "REG_ROUTE_1";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status,
            globalPickupLocationSearchResult, globalwhereToSearchResult;
    SharedPreferences SP;
    EditText etPickupLocation, etWhereTo;
    TextView btnContinue;
    private String pickUpPlaceId, pickUpGeometry, pickUpLocation_type, pickUpLocation,
            pickUpLat, pickUpLng, whereToPlaceId, whereToGeometry, whereToLocation_type,
            whereToLat, whereToLocation, whereToLng, distanceOfRoute, pickUpDesc, whereToDesc;
    private MarkerOptions myPLocationMarker, myWLocationMarker;
    private LatLng pickUp, whereTo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_route1);
        myPLocationMarker = new MarkerOptions()
                .position(new LatLng(lat_val, long_val))
                .title("Here");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadDataFromSharedPrefs();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etPickupLocation = findViewById(R.id.etPickupLocation);
        etWhereTo = findViewById(R.id.etWhereTo);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpDesc = etPickupLocation.getText().toString();
                whereToDesc = etWhereTo.getText().toString();
                if (pickUpDesc.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Pickup Location is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (whereToDesc.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Where To is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "first PickupLocation >>" + pickUpDesc);
                Log.d(TAG, "first WhereTo >>" + whereToDesc);
                pickUpDesc = clean(pickUpDesc);
                whereToDesc = clean(whereToDesc);
                Log.d(TAG, "second PickupLocation >>" + pickUpDesc);
                Log.d(TAG, "second WhereTo >>" + whereToDesc);
                Log.d(TAG, "before makePickupLocationGeoCodingQuery");
                makePickupLocationGeoCodingQuery(pickUpDesc);
                Log.d(TAG, "after makePickupLocationGeoCodingQuery");

                Log.d(TAG, "before makeWhereToGeoCodingQuery");
                makeWhereToGeoCodingQuery(whereToDesc);
                Log.d(TAG, "after makeWhereToGeoCodingQuery");

                btnContinue.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void finalProcessing() {

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(RegisterRoute1.this)
                .addOnConnectionFailedListener(RegisterRoute1.this)
                .addApi(LocationServices.API)
                .build();


        Log.d(TAG, "inside finalProcessing >> ");
        Log.d(TAG, "pickUpLat >> " + pickUpLat);
        Log.d(TAG, "pickUpLng >> " + pickUpLng);
        Log.d(TAG, "whereToLat >> " + whereToLat);
        Log.d(TAG, "whereToLng >> " + whereToLng);

        myPLocationMarker = new MarkerOptions()
                .position(new LatLng(lat_val, long_val))
                .title("Here");

        myWLocationMarker = new MarkerOptions()
                .position(new LatLng(Double.parseDouble(whereToLat), Double.parseDouble(whereToLng)))
                .title("WhereTo");
        whereTo = new LatLng(Double.parseDouble(whereToLat), Double.parseDouble(whereToLng));
        pickUp = new LatLng(Double.parseDouble(pickUpLat), Double.parseDouble(pickUpLng));
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        boundsBuilder.include(myPLocationMarker.getPosition());
        boundsBuilder.include(myWLocationMarker.getPosition());

        LatLngBounds bounds = boundsBuilder.build();

        m_map.setLatLngBoundsForCameraTarget(bounds);
//        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        m_map.addPolyline(new PolylineOptions().geodesic(true)
                .add(pickUp)
                .add(whereTo));
        onMapReady(m_map);
        float[] result = new float[1];
        Location.distanceBetween(Double.parseDouble(pickUpLat),
                Double.parseDouble(pickUpLng), Double.parseDouble(whereToLat),
                Double.parseDouble(whereToLng), result);

        distanceOfRoute = String.valueOf(result[0]);
        Log.d(TAG, "Distance >> " + distanceOfRoute);
        Log.d(TAG, "Distance double >> " + (double) result[0]);
        Log.d(TAG, "before makeSaveDataQuery");
        //--------------------------------
        //save route data
        // --------------------------------
        makeRideSharemakeSaveDataQuery(pickUpPlaceId, pickUpGeometry, pickUpLocation_type, pickUpLocation,
                pickUpLat, pickUpLng, whereToPlaceId, whereToGeometry, whereToLocation_type,
                whereToLat, whereToLocation, whereToLng, accountNumber, distanceOfRoute, pickUpDesc, whereToDesc);

        Log.d(TAG, "after makeSaveDataQuery");
        Toast.makeText(this, "Route Registered", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(RegisterRoute1.this, StartRide.class);
        startActivity(it);
    }

    private void makeRideSharemakeSaveDataQuery(String pickUpPlaceIdValue, String pickUpGeometryValue, String pickUpLocation_typeValue,
                                                String pickUpLocationValue, String pickUpLatValue, String pickUpLngValue,
                                                String whereToPlaceIdValue, String whereToGeometryValue, String whereToLocation_typeValue,
                                                String whereToLatValue, String whereToLocationValue, String whereToLngValue,
                                                String accountNo, String distanceValue, String pickUpDescValue, String whereToDescValue) {
        URL RideShareSelectUserURl = NetworkUtils.buildInsertRouteUrl(pickUpPlaceIdValue, pickUpGeometryValue, pickUpLocation_typeValue,
                pickUpLocationValue, pickUpLatValue, pickUpLngValue, whereToPlaceIdValue, whereToGeometryValue, whereToLocation_typeValue,
                whereToLatValue, whereToLocationValue, whereToLngValue, accountNo, distanceValue, pickUpDescValue, whereToDescValue);
        Log.d(TAG, "RideShare insert Rout Url is: " + RideShareSelectUserURl.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new RegisterRoute1.RideShareInsertRouteTask().execute(RideShareSelectUserURl);

    }

    private void continuePickupPutExtra() {
        Log.d(TAG, "inside continuePickupPutExtra");
        SP = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();

        editor.putString("pickUpPlaceId", pickUpPlaceId);
        Log.d(TAG, "pickUpPlaceId put into Extra>> " + pickUpPlaceId);

        editor.putString("pickUpGeometry", pickUpGeometry);
        Log.d(TAG, "pickUpGeometry put into Extra>> " + pickUpGeometry);

        editor.putString("pickUpLocation_type", pickUpLocation_type);
        Log.d(TAG, "pickUpLocation_type put into Extra>> " + pickUpLocation_type);

        editor.putString("pickUpLocation", pickUpLocation);
        Log.d(TAG, "pickUpLocation put into Extra>> " + pickUpLocation);

        editor.putString("pickUpLat", pickUpLat);
        Log.d(TAG, "pickUpLat put into Extra>> " + pickUpLat);

        editor.putString("pickUpLng", pickUpLng);
        Log.d(TAG, "pickUpLng put into Extra>> " + pickUpLng);

        editor.apply();
    }


    private void makePickupLocationGeoCodingQuery(String pickupLocation) {
        URL builtPickupLocationURL = NetworkUtils.buildGeoCodeUrl(pickupLocation);
        Log.d(TAG, "builtPickupLocationURL is: " + builtPickupLocationURL.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new PickupLocationGeoCodingQueryTask().execute(builtPickupLocationURL);
    }

    private void makeWhereToGeoCodingQuery(String whereTo) {
        URL builtWhereTURLo = NetworkUtils.buildGeoCodeUrl(whereTo);
        Log.d(TAG, "builtWhereTURLo is: " + builtWhereTURLo.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new whereToGeoCodingQueryTask().execute(builtWhereTURLo);
    }

    public class PickupLocationGeoCodingQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RideShareSearchResults = null;
            try {
                RideShareSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return RideShareSearchResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String RideShareSearchResults) {
            if (RideShareSearchResults != null && !RideShareSearchResults.equals("")) {
                Log.d(TAG, "RideShareSearchResults is :" + RideShareSearchResults);
                globalPickupLocationSearchResult = RideShareSearchResults;
                loadPickupLocationResultInView();
            }
        }
    }

    private void loadPickupLocationResultInView() {
        try {
            Log.d(TAG, "inside loadResultInView");
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(globalPickupLocationSearchResultMethod());
            String results_data = obj.getString("results");
            Log.d(TAG, "results_data is: " + results_data);

            String results_data2 = results_data.substring(1, results_data.length() - 1);

            JSONObject obj2 = new JSONObject(results_data2);
            Log.d(TAG, "obj2 is: " + obj2);
            pickUpPlaceId = obj2.getString("place_id");
            Log.d(TAG, "pickUpPlaceId is: " + pickUpPlaceId);

            pickUpGeometry = obj2.getString("geometry");
            Log.d(TAG, "pickUpGeometry is: " + pickUpGeometry);

            JSONObject obj3 = new JSONObject(pickUpGeometry);
            Log.d(TAG, "obj3 is: " + obj3);

            pickUpLocation_type = obj3.getString("location_type");
            Log.d(TAG, "pickUpLocation_type is: " + pickUpLocation_type);

            pickUpLocation = obj3.getString("location");
            Log.d(TAG, "pickUpLocation is: " + pickUpLocation);

            JSONObject obj4 = new JSONObject(pickUpLocation);
            Log.d(TAG, "obj4 is: " + obj4);

            pickUpLat = obj4.getString("lat");
            Log.d(TAG, "pickUpLat is: " + pickUpLat);
            pickUpLng = obj4.getString("lng");
            Log.d(TAG, "pickUpLng is: " + pickUpLng);
            continuePickupPutExtra();

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private String globalPickupLocationSearchResultMethod() {
        return globalPickupLocationSearchResult;
    }

    //---------------------------
    public class whereToGeoCodingQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RideShareSearchResults = null;
            try {
                RideShareSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return RideShareSearchResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String RideShareSearchResults) {
            if (RideShareSearchResults != null && !RideShareSearchResults.equals("")) {
                Log.d(TAG, "whereToSearchResults is :" + RideShareSearchResults);
                globalwhereToSearchResult = RideShareSearchResults;
                loadwhereToResultInView();

            }
        }
    }

    private void loadwhereToResultInView() {
        try {
            Log.d(TAG, "inside loadwhereToResultInView");
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(globalwhereToSearchResultMethod());
            // fetch JSONString named success
            String results_data = obj.getString("results");
            Log.d(TAG, "results_data is: " + results_data);
            String results_data2 = results_data.substring(1, results_data.length() - 1);

            JSONObject obj2 = new JSONObject(results_data2);
            Log.d(TAG, "obj2 is: " + obj2);
            whereToPlaceId = obj2.getString("place_id");
            Log.d(TAG, "whereToPlaceId is: " + whereToPlaceId);

            whereToGeometry = obj2.getString("geometry");
            Log.d(TAG, "whereToGeometry is: " + whereToGeometry);

            JSONObject obj3 = new JSONObject(whereToGeometry);
            Log.d(TAG, "obj3 is: " + obj3);

            whereToLocation_type = obj3.getString("location_type");
            Log.d(TAG, "whereToLocation_type is: " + whereToLocation_type);

            whereToLocation = obj3.getString("location");
            Log.d(TAG, "whereToLocation is: " + whereToLocation);

            JSONObject obj4 = new JSONObject(whereToLocation);
            Log.d(TAG, "obj4 is: " + obj4);

            whereToLat = obj4.getString("lat");
            Log.d(TAG, "whereToLat is: " + whereToLat);
            whereToLng = obj4.getString("lng");
            Log.d(TAG, "whereToLng is: " + whereToLng);
            continueWhereToPutExtra();
            finalProcessing();
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void continueWhereToPutExtra() {
        Log.d(TAG, "inside continueWhereToPutExtra");
        SP = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();

        editor.putString("whereToPlaceId", whereToPlaceId);
        Log.d(TAG, "whereToPlaceId put into Extra>> " + whereToPlaceId);

        editor.putString("whereToGeometry", whereToGeometry);
        Log.d(TAG, "whereToGeometry put into Extra>> " + whereToGeometry);

        editor.putString("whereToLocation_type", whereToLocation_type);
        Log.d(TAG, "whereToLocation_type put into Extra>> " + whereToLocation_type);

        editor.putString("whereToLat", whereToLat);
        Log.d(TAG, "whereToLat put into Extra>> " + whereToLat);

        editor.putString("whereToLocation", whereToLocation);
        Log.d(TAG, "whereToLocation put into Extra>> " + whereToLocation);

        editor.putString("whereToLng", whereToLng);
        Log.d(TAG, "whereToLng put into Extra>> " + whereToLng);
        editor.apply();
    }

    private String globalwhereToSearchResultMethod() {
        return globalwhereToSearchResult;
    }


    private String clean(String input) {
        String output = "";
        output = input.replace("&", " AND ");
        output = input.replace("%", " PERCENT ");
        output = input.replace("#", " NO. ");

        return output;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //connect api client
        mGoogleApiClient.connect();
        Log.d(TAG, "Google Api Client STARTED >> ");

    }

    @Override
    protected void onStop() {
        //disconnect+
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            Log.d(TAG, "Google Api Client is being stopped >> ");
        }

        super.onStop();
    }

    private void loadDataFromSharedPrefs() {
        Log.d(TAG, "Inside loadDataFromSharedPrefs");
        SP = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String username = SP.getString("strUser", null);
        String password = SP.getString("strPass", null);
        if (username != null && password != null) {

            strUser = SP.getString("strUser", null);
            Log.d(TAG, "strUser >> " + strUser);

            strPass = SP.getString("strPass", null);
            Log.d(TAG, "strPass >> " + strPass);

            strFName = SP.getString("strFName", null);
            Log.d(TAG, "firstName >> " + strFName);

            strLName = SP.getString("strFName", null);
            Log.d(TAG, "lastName >> " + strLName);

            strPhone = SP.getString("strPhone", null);
            Log.d(TAG, "phoneNumber >> " + strPhone);

            accountNumber = SP.getString("accountNumber", null);
            Log.d(TAG, "accountNumber >> " + accountNumber);

            strEmail = SP.getString("strEmail", null);
            Log.d(TAG, "email >> " + strEmail);

            status = SP.getString("status", null);
            Log.d(TAG, "status >> " + status);

            strFullName = SP.getString("strFullName", null);
            Log.d(TAG, "strFullName >> " + strFullName);

            strBalance = SP.getString("strBalance", null);
            Log.d(TAG, "strBalance >> " + strBalance);

            strUserType = SP.getString("strUserType", null);
            Log.d(TAG, "strUserType >> " + strUserType);

            strCurrentCity = SP.getString("strCurrentCity", null);
            Log.d(TAG, "strCurrentCity >> " + strCurrentCity);

        } else {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "after loadDataFromSharedPrefs");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_route1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            if (!strUserType.isEmpty()) {
                Intent it;
                if (strUserType.equalsIgnoreCase("PASSENGER")) {
                    it = new Intent(RegisterRoute1.this, DashboardPass.class);
                    startActivity(it);
                } else {
                    it = new Intent(RegisterRoute1.this, DashboardDriver.class);
                    startActivity(it);
                }
            }
        } else if (id == R.id.nav_add_funds) {
            Intent it = new Intent(RegisterRoute1.this, AddFunds.class);
            startActivity(it);
        } else if (id == R.id.nav_register_route1) {
            Intent it = new Intent(RegisterRoute1.this, RegisterRoute1.class);
            startActivity(it);
        } else if (id == R.id.nav_register_route2) {
            Intent it = new Intent(RegisterRoute1.this, RegisterRoute2.class);
            startActivity(it);
        } else if (id == R.id.nav_history) {
            Intent it = new Intent(RegisterRoute1.this, TransactionHistory.class);
            startActivity(it);
        } else if (id == R.id.nav_help) {
            Intent it = new Intent(RegisterRoute1.this, HelpSupport.class);
            startActivity(it);
        } else if (id == R.id.nav_start_ride) {
            Intent it = new Intent(RegisterRoute1.this, StartRide.class);
            startActivity(it);
        } else if (id == R.id.logout) {
            logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.clear().commit();
        editor.apply();
        Intent itLogout = new Intent(RegisterRoute1.this, signin.class);
        startActivity(itLogout);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "inside OnConnected>>");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);// update location info every  second
        //       mLocationRequest.setInterval(360000);// update location info every  1 hour

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "inside if (ActivityCompat.checkSelfPermission >>");

            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            Log.d(TAG, "inside onConnected else >> ");

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d(TAG, "inside mFusedLocationClient onSuccess >>");

                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Log.d(TAG, "longitude >> " + location.getLongitude());
                                Log.d(TAG, "Latitude >> " + location.getLatitude());
                                long_val = location.getLongitude();
                                lat_val = location.getLatitude();
                                String toastStr = "longitude >> " + location.getLatitude() + " Latitude >> " + location.getLatitude();
                                Toast.makeText(getApplicationContext(), toastStr, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "last location >> " + location.toString());
                                onMapReady(m_map);
                            }
                        }
                    });

        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Api Client location suspended >> ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Google Api Client location FAILED >> ");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "location changed to >> " + location.toString());
        long_val = location.getLongitude();
        lat_val = location.getLatitude();
        Log.d(TAG, "long_val changed to >> " + long_val);
        Log.d(TAG, "lat_val changed to >> " + lat_val);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "inside onRequestPermissionsResult >> ");

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "permission GRANTED >> ");
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        Log.d(TAG, "inside SECOND mFusedLocationClient onSuccess >>");
                                        Log.d(TAG, "inside mFusedLocationClient onSuccess >>");

                                        // Got last known location. In some rare situations, this can be null.
                                        if (location != null) {
                                            // Logic to handle location object
                                            Log.d(TAG, "longitude >> " + location.getLatitude());
                                            Log.d(TAG, "Latitude >> " + location.getLatitude());
                                            Log.d(TAG, "last location >> " + location.toString());
                                        }
                                    }
                                });

                    }

                } else {

                    Log.d(TAG, "permission denied >> ");

                }
                return;
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "inside onMapReady >> ");
        Log.d(TAG, "longitude >> " + long_val);
        Log.d(TAG, "Latitude >> " + lat_val);

        m_map = googleMap;
        LatLng myCurrentLocation = new LatLng(lat_val, long_val);
        CameraPosition target = CameraPosition.builder().target(myCurrentLocation).zoom(14).build();
        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        m_map.addMarker(myPLocationMarker);
        if (myWLocationMarker == null) {
            return;
        } else {
            m_map.addMarker(myWLocationMarker);
        }
    }

    public class RideShareInsertRouteTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RideShareInsertRouteResults = null;
            try {
                RideShareInsertRouteResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return RideShareInsertRouteResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String RideShareInsertRouteResults) {
            if (RideShareInsertRouteResults != null && !RideShareInsertRouteResults.equals("")) {
                Log.d(TAG, "RideShareInsertRouteResults is :" + RideShareInsertRouteResults);
                // put valeus in intent and fire intent
            }
        }
    }
}

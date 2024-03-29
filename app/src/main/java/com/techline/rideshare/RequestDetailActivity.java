package com.techline.rideshare;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class RequestDetailActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "REQUEST_DETAILS";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status, ride_id;
    SharedPreferences SP;
    Bundle extras;
    TextView btnStartRide, btnStopRide;
    TextView tvPickUp, tvWhereTo, tvPassenger_name, tvRequested_time, tvRequest_id, tvmDate;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadDataFromSharedPrefs();

        tvPickUp = findViewById(R.id.etPickupLocation);
        tvWhereTo = findViewById(R.id.etWhereTo);
        tvRequested_time = findViewById(R.id.etRequested_time);
        tvPassenger_name = findViewById(R.id.etPassenger_name);
        tvRequest_id = findViewById(R.id.etRequest_id);
        btnStartRide = findViewById(R.id.btnStartRide);
        btnStopRide = findViewById(R.id.btnStopRide);
        imgBack = findViewById(R.id.imgBack);


        btnStopRide.setVisibility(View.INVISIBLE);
        btnStartRide.setVisibility(View.VISIBLE);

        extras = getIntent().getExtras();
        if (extras != null) {
            String pickUp = extras.getString("pickUp");
            Log.d(TAG, "pickUp is :" + pickUp);

            String whereTo = extras.getString("whereTo");
            Log.d(TAG, "whereTo is :" + whereTo);

            String passenger_name = extras.getString("passenger_name");
            Log.d(TAG, "passenger_name is :" + passenger_name);

            String request_id = extras.getString("request_id");
            ride_id = request_id;
            Log.d(TAG, "request_id is :" + request_id);

            String requested_time = extras.getString("requested_time");
            Log.d(TAG, "requested_time is :" + requested_time);

            tvPickUp.setText(pickUp);
            tvWhereTo.setText(whereTo);
            tvPassenger_name.setText(passenger_name);
            tvRequest_id.setText(request_id);
            tvRequested_time.setText(requested_time);
        } else {
            return;
        }

        btnStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "starting Ride >> ");
                startRide(accountNumber, strFullName, ride_id);
                btnStartRide.setVisibility(View.INVISIBLE);
                btnStopRide.setVisibility(View.VISIBLE);
            }
        });
        btnStopRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "stoping Ride >> ");
                stopRide(ride_id);
                showCustomCompletedRideDialog();
            /*    Intent it = new Intent(RequestDetailActivity.this, StartRideDriver.class);
                startActivity(it);*/
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RequestDetailActivity.this, StartRideDriver.class);
                startActivity(it);
            }
        });
    }

    private void showCustomCompletedRideDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.driver_ride_completed_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void startRide(String driverId, String driverName, String rideIdValue) {
        URL startRideURl = NetworkUtils.buildStartRideUrl(driverId, driverName, rideIdValue);
        Log.d(TAG, "startRideURl Url is: " + startRideURl.toString());
        new RequestDetailActivity.RideShareStartRideTask().execute(startRideURl);
    }

    public class RideShareStartRideTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RideShareStartRideResults = null;
            try {
                RideShareStartRideResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return RideShareStartRideResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String RideShareStartRideResults) {
            if (RideShareStartRideResults != null && !RideShareStartRideResults.equals("")) {
                Log.d(TAG, "RideShareStartRideResults is :" + RideShareStartRideResults);
                // put valeus in intent and fire intent
            }
        }
    }


    private void stopRide(String rideIdValue) {
        URL stopRideURl = NetworkUtils.buildStopRideUrl(rideIdValue);
        Log.d(TAG, "stopRideURl Url is: " + stopRideURl.toString());
        new RequestDetailActivity.RideShareStopRideTask().execute(stopRideURl);
    }

    public class RideShareStopRideTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RideShareStopRideResults = null;
            try {
                RideShareStopRideResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return RideShareStopRideResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String RideShareStopRideResults) {
            if (RideShareStopRideResults != null && !RideShareStopRideResults.equals("")) {
                Log.d(TAG, "RideShareStopRideResults is :" + RideShareStopRideResults);
                // put valeus in intent and fire intent
            }
        }
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

}

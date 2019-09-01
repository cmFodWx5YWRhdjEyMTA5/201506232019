package com.techline.rideshare;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class RequestDetailActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "REQUEST_DETAILS";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;
    Bundle extras;
    TextView tvPickUp;
    TextView tvWhereTo, tvPassenger_name, tvRequested_time, tvRequest_id, tvmDate;
    Image imgBack;

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
        extras = getIntent().getExtras();
        if (extras != null) {
            String pickUp = extras.getString("pickUp");
            Log.d(TAG, "pickUp is :" + pickUp);

            String whereTo = extras.getString("whereTo");
            Log.d(TAG, "whereTo is :" + whereTo);

            String passenger_name = extras.getString("passenger_name");
            Log.d(TAG, "passenger_name is :" + passenger_name);

            String request_id = extras.getString("request_id");
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

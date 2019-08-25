package com.techline.rideshare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class RequestDetailActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "REQUEST_DETAILS";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;
    Bundle extras;
    TextView tvPickUp;
    TextView tvWhereTo, tvPassenger_name, tvRequested_time, tvRequest_id, tvmDate;
    String linkString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        tvPickUp = findViewById(R.id.pickUp);
        tvWhereTo = findViewById(R.id.whereTo);
        tvRequested_time = findViewById(R.id.requested_time);
        tvPassenger_name = findViewById(R.id.passenger_name);
        tvRequest_id = findViewById(R.id.request_id);
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

            String mDate = extras.getString("mDate");
            Log.d(TAG, "mDate is :" + mDate);

            tvPickUp.setText(pickUp);
            tvWhereTo.setText(whereTo);
            tvPassenger_name.setText(passenger_name);
            tvRequest_id.setText(request_id);
            tvRequested_time.setText(mDate);
        } else {
            return;
        }

    }
}

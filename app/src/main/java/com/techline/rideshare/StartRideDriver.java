package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.techline.rideshare.custom.CustomMsgAdaptor;
import com.techline.rideshare.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class StartRideDriver extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "START_RIDE_DRIVER";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;
    Button logout_button;
    Bundle extras;
    ImageView imgBack;

    ArrayList<String> passenger_name = new ArrayList<>();
    ArrayList<String> requested_time = new ArrayList<>();
    ArrayList<String> pickUpDesc = new ArrayList<>();
    ArrayList<String> whereToDesc = new ArrayList<>();
    ArrayList<String> ride_id = new ArrayList<>();

    private String globalinitializeCardResultsMethodString;
    private String globalVerifyCardResultsMethodString, globalSaveCardURLResult;
    private String authorization_url, access_code, reference, data_id, domain, data_status, amount_in_kobo, data_whereTo,
            gateway_response, paid_at, created_at, channel, currency, ip_address, data_metadata, log, fees, fees_split,
            authorization, customer_data, customer_id, order_id, customer_first_name, customer_last_name, customer_email,
            customer_code, customer_phone, customer_metadata, customer_risk_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ride_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadDataFromSharedPrefs();

        Log.d(TAG, "before makeLoadAllRideRequestsQuery");

        makeLoadAllRideRequestsQuery();

        Log.d(TAG, "after makeLoadAllRideRequestsQuery");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(StartRideDriver.this, DashboardDriver.class);
                startActivity(it);
            }
        });
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

    private void makeLoadAllRideRequestsQuery() {
        URL allChatMsgSearchUrl = NetworkUtils.buildSelectRideRequestsUrl(strUser);
        Log.d(TAG, "allChatMsgSearchUrl is: " + allChatMsgSearchUrl.toString());
        // COMPLETED (4) Create a new allChatMsgQueryTask and call its execute method, passing in the url to query
        new allChatMsgQueryTask().execute(allChatMsgSearchUrl);
    }

    private void loadResultView() {
        Log.d(TAG, "inside loadResultView>> ");
        RecyclerView recyclerView = findViewById(R.id.list);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {

            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(globalSearchResultMethod());
            Log.d(TAG, "obj >> " + obj);
            JSONArray usersArray = obj.getJSONArray("users");
            Log.d(TAG, "usersArray >> " + usersArray.toString());

            for (int i = 0; i < usersArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject srDetail = usersArray.getJSONObject(i);
                // fetch email and name and store it in arraylist

                pickUpDesc.add(srDetail.getString("pickUpDesc"));
                Log.d(TAG, "pickUp is: " + pickUpDesc);
                whereToDesc.add(srDetail.getString("whereToDesc"));
                Log.d(TAG, "whereTo is: " + whereToDesc);
                ride_id.add(srDetail.getString("ride_id"));
                Log.d(TAG, "request_id is: " + ride_id);
                passenger_name.add(srDetail.getString("passenger_name"));
                Log.d(TAG, "passenger_name is: " + passenger_name);
                requested_time.add(srDetail.getString("requested_time"));
                Log.d(TAG, "requested_time is: " + requested_time);

            }
            /*extras = getIntent().getExtras();
            if (extras != null) {
                myOwnDisplayName = extras.getString("MY_USER_EMAIL");
//                myOwnDisplayName = extras.getString("pickUp");
                Log.d(TAG, "my personal user DisplayName is :" + myOwnDisplayName);
                extras.putString("pickUp", myOwnDisplayName);
            }
            my_own_pickUp.add(myOwnDisplayName);
            Log.d(TAG, "my_own_pickUp is: " + myOwnDisplayName);
*/


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomMsgAdaptor customAdapter = new CustomMsgAdaptor(StartRideDriver.this, pickUpDesc,
                whereToDesc,
                passenger_name,
                ride_id,
                requested_time
        );
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

    }

    private String globalSearchResultMethod() {
        return globalSearchResult;
    }

    public class allChatMsgQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String allChatMsgSearchResults = null;
            try {
                allChatMsgSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.d(TAG, "allChatMsgSearchResults is : " + allChatMsgSearchResults.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return allChatMsgSearchResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String allChatMsgSearchResults) {
            if (allChatMsgSearchResults != null && !allChatMsgSearchResults.equals("")) {
                Log.d(TAG, "allChatMsgSearchResults is :" + allChatMsgSearchResults);
                globalSearchResult = allChatMsgSearchResults;
                loadResultView();
            }
        }
    }



    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */

}

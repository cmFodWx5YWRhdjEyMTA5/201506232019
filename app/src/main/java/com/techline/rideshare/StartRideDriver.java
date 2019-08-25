package com.techline.rideshare;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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
    TextView btnAddCard;
    Bundle extras;
    ArrayList<String> pickUp = new ArrayList<>();
    ArrayList<String> whereTo = new ArrayList<>();
    ArrayList<String> request_id = new ArrayList<>();
    ArrayList<String> passenger_name = new ArrayList<>();
    ArrayList<String> requested_time = new ArrayList<>();
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

        Log.d(TAG, "before makeLoadAllRideRequestsQuery");

        makeLoadAllRideRequestsQuery();

        Log.d(TAG, "after makeLoadAllRideRequestsQuery");
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

                pickUp.add(srDetail.getString("pickUp"));
                Log.d(TAG, "pickUp is: " + pickUp);
                whereTo.add(srDetail.getString("whereTo"));
                Log.d(TAG, "whereTo is: " + whereTo);
                request_id.add(srDetail.getString("request_id"));
                Log.d(TAG, "request_id is: " + request_id);
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
        CustomMsgAdaptor customAdapter = new CustomMsgAdaptor(StartRideDriver.this, pickUp,
                whereTo,
                passenger_name,
                request_id,
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

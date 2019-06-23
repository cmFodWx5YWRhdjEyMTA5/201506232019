package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class signin extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "SIGN_IN";
    //declare controls
    EditText txtUsername, txtPssword;
    TextView tvSin;
    ImageView sback;
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //assign ontrols
        sback = (ImageView) findViewById(R.id.sinb);
        txtUsername = findViewById(R.id.usrusr);
        txtPssword = findViewById(R.id.pswrd);
        tvSin = findViewById(R.id.sin);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signin.this, MainActivity.class);
                startActivity(it);
            }
        });

        //get values
        tvSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "username is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtUsername.getText().toString().trim().length() < 5) {
                    Toast.makeText(getApplicationContext(), "username is invalid.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtPssword.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "password is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtPssword.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), "password is invalid.", Toast.LENGTH_SHORT).show();
                    return;
                }
                strUser = txtUsername.getText().toString();
                strPass = txtPssword.getText().toString();

                //pass values
                Log.d(TAG, "before fetching from open Users Table");
                makeRideShareSelectUserQuery(strUser, strPass);
                Log.d(TAG, "after fetching from open Users Table");

            }
        });

    }

    private void makeRideShareSelectUserQuery(String strUser, String strPass) {
        URL RideShareSelectUserURl = NetworkUtils.buildSelectUserUrl(strUser, strPass);
        Log.d(TAG, "RideShareSearchUrl is: " + RideShareSelectUserURl.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new RideShareQueryTask().execute(RideShareSelectUserURl);

    }

    private void loadResultInView() {
        try {
            Log.d(TAG, "inside loadResultInView");
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(globalSearchResultMethod());
            // fetch JSONString named success
            String user_data = obj.getString("users");

            Log.d(TAG, "user_data is: " + user_data);
            if (user_data != null && user_data.length() != 0 && !user_data.equals("")) {

                // fetch JSONArray named user
                JSONArray userArray = obj.getJSONArray("users");
                // implement for loop for getting Countrys list data
                for (int i = 0; i < userArray.length(); i++) {
                    // create a JSONObject for fetching single Country data
                    JSONObject srDetail = userArray.getJSONObject(i);
                    // fetch email and name and store it in arraylist

                    strFName = srDetail.getString("firstname");
                    Log.d(TAG, "strFName is: " + strFName);

                    strLName = srDetail.getString("lastname");
                    Log.d(TAG, "strLName is: " + strLName);

                    strFullName = srDetail.getString("fullname");
                    Log.d(TAG, "strFullName is: " + strFullName);

                    strEmail = srDetail.getString("email");
                    Log.d(TAG, "strEmail is: " + strEmail);

                    strUser = srDetail.getString("username");
                    Log.d(TAG, "strUser is: " + strUser);

                    strPhone = srDetail.getString("phone");
                    Log.d(TAG, "strPhone is: " + strPhone);

                    strPass = srDetail.getString("password");
                    Log.d(TAG, "strPass is: " + strPass);

                    strBalance = srDetail.getString("balance");
                    Log.d(TAG, "strBalance is: " + strBalance);

                    strUserType = srDetail.getString("user_type");
                    Log.d(TAG, "strUserType is: " + strUserType);

                    strCurrentCity = srDetail.getString("current_city");
                    Log.d(TAG, "strcurrent_city is: " + strCurrentCity);

                    accountNumber = srDetail.getString("accountNumber");
                    Log.d(TAG, "accountNumber is: " + accountNumber);

                    status = srDetail.getString("status");
                    Log.d(TAG, "status is: " + status);
                    getSharedPrefDataFromDb();
                }
                Intent it;
                if (strUserType.equalsIgnoreCase("PASSENGER")) {
                    it = new Intent(signin.this, DashboardPass.class);
                } else {
                    it = new Intent(signin.this, DashboardDriver.class);

                }

                it.putExtra("strFName", strFName);
                it.putExtra("strLName", strLName);
                it.putExtra("strFullName", strFullName);
                it.putExtra("strEmail", strEmail);
                it.putExtra("strPhone", strPhone);
                it.putExtra("strUser", strUser);
                it.putExtra("strPass", strPass);
                it.putExtra("strBalance", strBalance);
                it.putExtra("strCurrentCity", strCurrentCity);

                Log.d(TAG, "before saving in open shared Preferences");
                getSharedPrefDataFromDb();
                Log.d(TAG, "after saving in open shared Preferences");

                Log.d(TAG, "after saving object");
                signin.this.startActivity(it);
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void getSharedPrefDataFromDb() {
        SP = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();

        editor.putString("strUser", strUser);
        editor.putString("strPass", strPass);
        editor.putString("strFName", strFName);
        editor.putString("strLName", strLName);
        editor.putString("strPhone", strPhone);
        editor.putString("strFullName", strFullName);
        editor.putString("strEmail", strEmail);
        editor.putString("strUserType", strUserType);
        editor.putString("status", status);
        editor.putString("accountNumber", accountNumber);
        editor.commit(); // commit changes

    }

    private String globalSearchResultMethod() {
        return globalSearchResult;

    }

    // COMPLETED (1) Create a class called RideShareQueryTask that extends AsyncTask<URL, Void, String>
    public class RideShareQueryTask extends AsyncTask<URL, Void, String> {

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
                globalSearchResult = RideShareSearchResults;
                loadResultInView();
            }
        }
    }


}

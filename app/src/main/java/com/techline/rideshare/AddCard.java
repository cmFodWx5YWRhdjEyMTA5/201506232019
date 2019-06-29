package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class AddCard extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "ADD_CARD";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status,
            globalPickupLocationSearchResult, globalwhereToSearchResult;
    SharedPreferences SP;
    EditText edit_card_number, edit_cvc, edit_expiry_month, edit_expiry_year, edit_four_digit_pin;
    TextView button_perform_transaction;
    String nameOnCard, fullNumberOnCard, tokenisedCard, pinOnCard, otpOnCard, tokenisationVerifiedOnCard,
            authorizationCodeOnCard, bankOnCard, typeOnCard, last4DigitsOnCard, emilOnCard, authObjOnCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        edit_card_number = findViewById(R.id.edit_card_number);
        edit_cvc = findViewById(R.id.edit_cvc);
        edit_expiry_month = findViewById(R.id.edit_expiry_month);
        edit_expiry_year = findViewById(R.id.edit_expiry_year);
        edit_four_digit_pin = findViewById(R.id.edit_pin);
        button_perform_transaction = findViewById(R.id.button_perform_transaction);

        loadDataFromSharedPrefs();
        button_perform_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before adding card");
                //--------------------------------
                //adding card data
                // --------------------------------
                makeRideSharemakeSaveCardQuery("", fullNumberOnCard, tokenisedCard, pinOnCard, "", "",
                        "", "", "", last4DigitsOnCard, emilOnCard, "",
                        accountNumber, "@string/PAY_STACK_SECRET_KEY");

                Log.d(TAG, "after adding card");

                Intent it = new Intent(AddCard.this, AddCard.class);
                startActivity(it);
            }
        });
    }

    private void makeRideSharemakeSaveCardQuery(String nameOnCardValue, String fullNumberOnCardValue,
                                                String tokenisedCardValue, String pinOnCardValue, String otpOnCardValue,
                                                String tokenisationVerifiedOnCardValue, String authorizationCodeOnCardValue,
                                                String bankOnCardValue, String typeOnCardValue, String last4DigitsOnCardValue,
                                                String emilOnCardValue, String authObjOnCardValue, String accountNumberValue,
                                                String token) {

        URL RideShareSelectUserURl = NetworkUtils.buildInsertCardUrl(nameOnCardValue, fullNumberOnCardValue, tokenisedCardValue,
                pinOnCardValue, otpOnCardValue, tokenisationVerifiedOnCardValue, authorizationCodeOnCardValue, bankOnCardValue,
                typeOnCardValue, last4DigitsOnCardValue, emilOnCardValue, authObjOnCardValue, accountNumberValue,token);
        Log.d(TAG, "RideShare insert Rout Url is: " + RideShareSelectUserURl.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new AddCard.RideShareInsertCardTask().execute(RideShareSelectUserURl);
    }

    public class RideShareInsertCardTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RideShareSearchResults = null;
            try {
                RideShareSearchResults = NetworkUtils.getResponseFromPaystackHttpUrl(searchUrl);
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
                //loadafterCardResultInView();
            }
        }
    }

    private void logoutUser() {
        SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.clear().commit();
        editor.apply();
        Intent itLogout = new Intent(AddCard.this, signin.class);
        startActivity(itLogout);
        finish();
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

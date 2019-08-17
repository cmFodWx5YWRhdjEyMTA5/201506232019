package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.util.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class AddFunds extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "ADD_FUNDS";
    public static final String CHARGE_AMOUNT_IN_KOBO = "1000"; //amount in KOBO
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;
    TextView btnAddCard;
    private String globalinitializeCardResultsMethodString;
    private String globalVerifyCardResultsMethodString;
    private String authorization_url, access_code, reference, data_id, domain, data_status, amount_in_kobo, data_message,
            gateway_response, paid_at, created_at, channel, currency, ip_address, data_metadata, log, fees, fees_split,
            authorization, customer_data, customer_id, order_id, customer_first_name, customer_last_name, customer_email,
            customer_code, customer_phone, customer_metadata, customer_risk_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "started Add Funds >> ");
        btnAddCard = findViewById(R.id.btnAddCard);

        loadDataFromSharedPrefs();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Log.d(TAG, "started Add Funds >>5 ");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d(TAG, "started Add Funds >> 6");
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "strEmail >> " + strEmail);
                makeIniializeCardURL(strEmail, CHARGE_AMOUNT_IN_KOBO);
                /*Intent it = new Intent(AddFunds.this, AddCard.class);
                startActivity(it);*/
            }
        });
    }

    private void makeIniializeCardURL(String chrgeEmail, String chrgeAmount) {
        URL RideSharemakeIniializeCardURL = NetworkUtils.buildInsertCardUrl(chrgeEmail, chrgeAmount,
                "@string/PAY_STACK_SECRET_KEY");
        Log.d(TAG, "RideShare insert Rout Url is: " + RideSharemakeIniializeCardURL.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new initializeCardQueryTask().execute(RideSharemakeIniializeCardURL);
    }

    public class initializeCardQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String initializeCardResults = null;
            try {
                initializeCardResults = NetworkUtils.getResponseFromInitializePaystackHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return initializeCardResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String initializeCardResults) {
            if (initializeCardResults != null && !initializeCardResults.equals("")) {
                Log.d(TAG, "initializeCardResults is :" + initializeCardResults);
                globalinitializeCardResultsMethodString = initializeCardResults;
                try {
                    loadinitializeCardResultsInView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loadinitializeCardResultsInView() throws JSONException {
        Log.d(TAG, "inside loadinitializeCardResultsInView");
        // get JSONObject from JSON file
        JSONObject obj = new JSONObject(globalinitializeCardResultsMethod());
        String status_str = obj.getString("status");
        Log.d(TAG, "status_str is: " + status_str);
        if (status_str.equalsIgnoreCase("true")) {
            String data_str = obj.getString("data");
            Log.d(TAG, "data_str is: " + data_str);
            JSONObject dta_obj = new JSONObject(data_str);
            authorization_url = dta_obj.getString("authorization_url");
            Log.d(TAG, "authorization_url is: " + authorization_url);
            access_code = dta_obj.getString("access_code");
            Log.d(TAG, "access_code is: " + access_code);
            reference = dta_obj.getString("reference");
            Log.d(TAG, "reference is: " + reference);

            makeVerifyCardURL(reference);
        } else {
            Toast.makeText(getApplicationContext(), "Card Initialization Failed.", Toast.LENGTH_SHORT).show();

        }

    }

    private String globalinitializeCardResultsMethod() {
        return globalinitializeCardResultsMethodString;
    }


    private void makeVerifyCardURL(String reference) {
        Log.d(TAG, "inside makeVerifyCardURL ");
        URL RideSharemakeIVerifyCardURL = NetworkUtils.buildVerifyCardUrl(reference,"@string/PAY_STACK_SECRET_KEY");
        Log.d(TAG, "makeVerifyCardURL Url is: " + RideSharemakeIVerifyCardURL.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new verifyCardQueryTask().execute(RideSharemakeIVerifyCardURL);

    }

    public class verifyCardQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String verifyCardResults = null;
            try {
                verifyCardResults = NetworkUtils.getResponseFromVeriyPaystackHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return verifyCardResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String verifyCardResults) {
            if (verifyCardResults != null && !verifyCardResults.equals("")) {
                Log.d(TAG, "verifyCardResults is :" + verifyCardResults);
                globalVerifyCardResultsMethodString = verifyCardResults;
                try {
                    loadVerifyCardResultsInView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loadVerifyCardResultsInView() throws JSONException {
        Log.d(TAG, "inside loadVerifyCardResultsInView");
        // get JSONObject from JSON file
        JSONObject obj = new JSONObject(globalVerifyCardResultsMethod());
        String status_str = obj.getString("status");
        Log.d(TAG, "status_str is: " + status_str);
        if (status_str.equalsIgnoreCase("true")) {
            String message_str = obj.getString("message");
            Log.d(TAG, "message_str is: " + message_str);
            String data_str = obj.getString("data");
            Log.d(TAG, "data_str is: " + data_str);
            JSONObject dta_obj = new JSONObject(data_str);

            data_id = dta_obj.getString("id");
            Log.d(TAG, "data_id is: " + data_id);
            domain = dta_obj.getString("domain");
            Log.d(TAG, "domain is: " + domain);
            data_status = dta_obj.getString("status");
            Log.d(TAG, "data_status is: " + data_status);
            amount_in_kobo = dta_obj.getString("amount");
            Log.d(TAG, "amount_in_kobo is: " + amount_in_kobo);
            data_message = dta_obj.getString("message");
            Log.d(TAG, "data_message is: " + data_message);
            gateway_response = dta_obj.getString("gateway_response");
            Log.d(TAG, "gateway_response is: " + gateway_response);
            paid_at = dta_obj.getString("paid_at");
            Log.d(TAG, "paid_at is: " + paid_at);
            created_at = dta_obj.getString("created_at");
            Log.d(TAG, "created_at is: " + created_at);
            channel = dta_obj.getString("channel");
            Log.d(TAG, "channel is: " + channel);
            currency = dta_obj.getString("currency");
            Log.d(TAG, "currency is: " + currency);
            ip_address = dta_obj.getString("ip_address");
            Log.d(TAG, "ip_address is: " + ip_address);
            data_metadata = dta_obj.getString("metadata");
            Log.d(TAG, "data_metadata is: " + data_metadata);
            log = dta_obj.getString("log");
            Log.d(TAG, "log is: " + log);
            fees = dta_obj.getString("fees");
            Log.d(TAG, "fees is: " + fees);
            fees_split = dta_obj.getString("fees_split");
            Log.d(TAG, "fees_split is: " + fees_split);
            authorization = dta_obj.getString("authorization");
            Log.d(TAG, "authorization is: " + authorization);
            customer_data = dta_obj.getString("customer");
            Log.d(TAG, "customer_data is: " + customer_data);

            order_id = dta_obj.getString("order_id");
            Log.d(TAG, "order_id is: " + order_id);

            JSONObject customer_data_obj = new JSONObject(customer_data);

            customer_id = customer_data_obj.getString("id");
            Log.d(TAG, "customer_id is: " + customer_id);

            customer_first_name = customer_data_obj.getString("first_name");
            Log.d(TAG, "customer_first_name is: " + customer_first_name);

            customer_last_name = customer_data_obj.getString("last_name");
            Log.d(TAG, "customer_last_name is: " + customer_last_name);

            customer_email = customer_data_obj.getString("email");
            Log.d(TAG, "customer_email is: " + customer_email);

            customer_code = customer_data_obj.getString("customer_code");
            Log.d(TAG, "customer_code is: " + customer_code);

            customer_phone = customer_data_obj.getString("phone");
            Log.d(TAG, "customer_phone is: " + customer_phone);

            customer_metadata = customer_data_obj.getString("metadata");
            Log.d(TAG, "customer_metadata is: " + customer_metadata);

            customer_risk_action = customer_data_obj.getString("risk_action");
            Log.d(TAG, "customer_risk_action is: " + customer_risk_action);

            makeInsertPstkDataUri(authorization_url, access_code, reference, data_id, domain, data_status, amount_in_kobo, data_message,
                    gateway_response, paid_at, created_at, channel, currency, ip_address, data_metadata, log, fees, fees_split,
                    authorization, customer_data, customer_id, order_id, customer_first_name, customer_last_name, customer_email,
                    customer_code, customer_phone, customer_metadata, customer_risk_action);

        } else {
            Toast.makeText(getApplicationContext(), "Card Verification Failed.", Toast.LENGTH_SHORT).show();

        }

    }

    private void makeInsertPstkDataUri(String authorization_urlValue, String access_codeValue, String referenceValue, String data_idValue,
                                       String domainValue, String data_statusValue, String amount_in_koboValue, String data_messageValue,
                                       String gateway_responseValue, String paid_atValue, String created_atValue, String channelValue,
                                       String currencyValue, String ip_addressValue, String data_metadataValue, String logValue, String feesValue,
                                       String fees_splitValue, String authorizationValue, String customer_dataValue, String customer_idValue,
                                       String order_idValue, String customer_first_nameValue, String customer_last_nameValue,
                                       String customer_emailValue, String customer_codeValue, String customer_phoneValue,
                                       String customer_metadataValue, String customer_risk_action) {


    }

    private String globalVerifyCardResultsMethod() {
        return globalVerifyCardResultsMethodString;
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
        getMenuInflater().inflate(R.menu.add_funds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
  /*      if (id == R.id.action_settings) {
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
                    it = new Intent(AddFunds.this, DashboardPass.class);
                    startActivity(it);
                } else {
                    it = new Intent(AddFunds.this, DashboardDriver.class);
                    startActivity(it);
                }
            }
        } else if (id == R.id.nav_add_funds) {
            Intent it = new Intent(AddFunds.this, AddFunds.class);
            startActivity(it);
        } else if (id == R.id.nav_register_route1) {
            Intent it = new Intent(AddFunds.this, RegisterRoute1.class);
            startActivity(it);
        } else if (id == R.id.nav_register_route2) {
            Intent it = new Intent(AddFunds.this, RegisterRoute2.class);
            startActivity(it);
        } else if (id == R.id.nav_history) {
            Intent it = new Intent(AddFunds.this, TransactionHistory.class);
            startActivity(it);
        } else if (id == R.id.nav_help) {
            Intent it = new Intent(AddFunds.this, HelpSupport.class);
            startActivity(it);
        } else if (id == R.id.nav_start_ride) {
            Intent it = new Intent(AddFunds.this, StartRide.class);
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
        Intent itLogout = new Intent(AddFunds.this, signin.class);
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

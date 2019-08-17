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

import java.io.IOException;
import java.net.URL;

public class AddFunds extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "ADD_FUNDS";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;
    TextView btnAddCard;
    private String globalinitializeCardResults;

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
                makeIniializeCardURL(strEmail, "5000");
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
                initializeCardResults = NetworkUtils.getResponseFromPaystackHttpUrl(searchUrl);
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
                globalinitializeCardResults = initializeCardResults;
                loadinitializeCardResultsInView();
            }
        }

    }

    private void loadinitializeCardResultsInView() {
        verifyTransactiononCard();
    }

    private void verifyTransactiononCard() {

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

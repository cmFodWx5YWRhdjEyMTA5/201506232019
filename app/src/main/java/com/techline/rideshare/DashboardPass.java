package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardPass extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "DASHBOARD_PASS";
    ImageView imgStartRide, imgAddFunds, imgTransactionHistory, imageRoute1,
            imageRoute2, imgHelpSupport;
    TextView tvStartRide, tvAddFunds, tvTransactionHistory, tvRegRoute1,
            tvRegRoute2, tvHelpSupport;
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        imgStartRide = findViewById(R.id.imgStartRide);
        imgAddFunds = findViewById(R.id.imgAddFunds);
        imgTransactionHistory = findViewById(R.id.imgTransactionHistory);
        imageRoute1 = findViewById(R.id.imageRoute1);
        imageRoute2 = findViewById(R.id.imageRoute2);
        imgHelpSupport = findViewById(R.id.imgHelpSupport);

        tvStartRide = findViewById(R.id.tvStartRide);
        tvAddFunds = findViewById(R.id.tvAddFunds);
        tvTransactionHistory = findViewById(R.id.tvTransactionHistory);
        tvRegRoute1 = findViewById(R.id.tvRegRoute1);
        tvRegRoute2 = findViewById(R.id.tvRegRoute2);
        tvHelpSupport = findViewById(R.id.tvHelpSupport);

        loadDataFromSharedPrefs();
        imgStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, StartRide.class);
                startActivity(it);
            }
        });

        imgAddFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, AddFunds.class);
                startActivity(it);
            }
        });

        imageRoute1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, RegisterRoute1.class);
                startActivity(it);
            }
        });


        imageRoute2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, RegisterRoute2.class);
                startActivity(it);
            }
        });

        imgHelpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, HelpSupport.class);
                startActivity(it);
            }
        });

        imgTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, TransactionHistory.class);
                startActivity(it);
            }
        });
        tvStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, StartRide.class);
                startActivity(it);
            }
        });

        tvAddFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, AddFunds.class);
                startActivity(it);
            }
        });

        tvRegRoute1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, RegisterRoute1.class);
                startActivity(it);
            }
        });


        tvRegRoute2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, RegisterRoute2.class);
                startActivity(it);
            }
        });

        tvHelpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, HelpSupport.class);
                startActivity(it);
            }
        });

        tvTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardPass.this, TransactionHistory.class);
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
        getMenuInflater().inflate(R.menu.dashboard_pass, menu);
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
                    it = new Intent(DashboardPass.this, DashboardPass.class);
                    startActivity(it);
                } else {
                    it = new Intent(DashboardPass.this, DashboardDriver.class);
                    startActivity(it);
                }
            }
        } else if (id == R.id.nav_add_funds) {
            Intent it = new Intent(DashboardPass.this, AddFunds.class);
            startActivity(it);
        } else if (id == R.id.nav_register_route1) {
            Intent it = new Intent(DashboardPass.this, RegisterRoute1.class);
            startActivity(it);
        } else if (id == R.id.nav_register_route2) {
            Intent it = new Intent(DashboardPass.this, RegisterRoute2.class);
            startActivity(it);
        } else if (id == R.id.nav_history) {
            Intent it = new Intent(DashboardPass.this, TransactionHistory.class);
            startActivity(it);
        } else if (id == R.id.nav_help) {
            Intent it = new Intent(DashboardPass.this, HelpSupport.class);
            startActivity(it);
        } else if (id == R.id.nav_start_ride) {
            Intent it = new Intent(DashboardPass.this, StartRide.class);
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
        Intent itLogout = new Intent(DashboardPass.this, signin.class);
        startActivity(itLogout);
        finish();
    }

}

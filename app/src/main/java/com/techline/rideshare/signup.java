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

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class signup extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "PASSENGER_SIGNUP";
    //declare controls
    EditText txtUsername, txtPssword, txtFullname, txtPhone, txtEmail, txtFname, txtLname;
    TextView tvSin;
    ImageView sback;
    String strUser, strPass, strFullName, strEmail, strPhone, strFName, strLName,
            strCurrentCity, accountNumber, strUserType;
    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //assign ontrols
        txtUsername = findViewById(R.id.usrusr);
        txtPssword = findViewById(R.id.pswrd);
        txtFname = findViewById(R.id.fname);
        txtLname = findViewById(R.id.lname);
        txtPhone = findViewById(R.id.phone);
        txtEmail = findViewById(R.id.mail);

        tvSin = findViewById(R.id.sin);

        sback = (ImageView) findViewById(R.id.sback);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signup.this, MainActivity.class);
                startActivity(it);

            }
        });
        //get values on click
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
                strFName = txtFname.getText().toString();
                strLName = txtLname.getText().toString();
                strPhone = txtPhone.getText().toString();
                strEmail = txtEmail.getText().toString();

                //pass values
                strFullName = strFName + " " + strLName;
                Log.d(TAG, "before saving in open Users Table");
                makeRideShareInsertUserQuery(strFName, strLName, strFullName, strEmail, strUser, strPhone,
                        "SURULERE", strPass, "LAGOS", "NIGERIA", "PASSENGER");
                Log.d(TAG, "after saving in open Users Table");
                Log.d(TAG, "before saving in open shared Preferences");
                accountNumber = generatedAccountNumber();
                populatePreferences();
                Log.d(TAG, "after saving in open shared Preferences");
            }
        });

    }

    private String generatedAccountNumber() {
        String myPrefix = "P";
        strUserType = "PASSENGER";
        if (strUserType.equalsIgnoreCase("PASSENGER")) {
            myPrefix = "P";
        } else {
            myPrefix = "D";
        }
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String strDateInFormat = dateFormat.format(cal.getTime());
        System.out.println("strDateInFormat is: " + strDateInFormat);
        strDateInFormat = strDateInFormat.replace("-", "");
        strDateInFormat = strDateInFormat.replace(" ", "");
        strDateInFormat = strDateInFormat.replace(":", "");
        strDateInFormat = myPrefix + strDateInFormat;
        System.out.println("strDateInFormat is: " + strDateInFormat);
        return strDateInFormat;
    }

    private void populatePreferences() {
        SP = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.putString("strUser", strUser);
        editor.putString("strPass", strPass);
        editor.putString("strFName", strFName);
        editor.putString("strLName", strLName);
        editor.putString("strPhone", strPhone);
        editor.putString("strFullName", strFullName);
        editor.putString("strEmail", strEmail);
        editor.putString("strBalance", "0.00");
        editor.putString("strUserType", "PASSENGER");
        editor.putString("strCurrentCity", strCurrentCity);
        editor.putString("accountNumber", accountNumber);
        editor.putString("status", "ACTIVE");
        editor.commit(); // commit changes

    }

    private void makeRideShareInsertUserQuery(String strFirstName, String strLastName, String strFullName, String strEmail,
                                              String strUser, String strPhone, String curreCityValue,
                                              String strPass, String stateValue, String CountryValue,
                                              String type) {
        URL RideShareSelectUserURl = NetworkUtils.buildInsertUserUrl(strFirstName, strLastName, strFullName, strEmail, strUser,
                strPhone, strPass, "LAGOS", "NIGERIA", "NA", "PASSENGER", "0.00",
                accountNumber, "ACTIVE");
        Log.d(TAG, "RideShareSearchUrl is: " + RideShareSelectUserURl.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new signup.RideShareInsertQueryTask().execute(RideShareSelectUserURl);
    }

    // COMPLETED (1) Create a class calld RideShareQueryTask that extends AsyncTask<URL, Void, String>
    public class RideShareInsertQueryTask extends AsyncTask<URL, Void, String> {

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
                // put valeus in intent and fire intent
                Intent it = new Intent(signup.this, EditProfilePass.class);
                it.putExtra("strFName", strFName);
                it.putExtra("strLName", strLName);
                it.putExtra("strFullName", strFullName);
                it.putExtra("strEmail", strEmail);
                it.putExtra("strPhone", strPhone);
                it.putExtra("strUser", strUser);
                it.putExtra("strPass", strPass);

                startActivity(it);
            }
        }
    }
}
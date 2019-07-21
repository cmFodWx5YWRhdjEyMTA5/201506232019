package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EditProfileDriver extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "EDIT_PROFILE_DRIVER";
    //declare controls
    Spinner spinnerCurrency;
    Switch active_switch;
    EditText eTxtFullName, eTxtEmail, eTxtPhone, eTxtPassword, eTxtFirstName, eTxtLastName, eTxtCity;
    String strUser, strPass, strFullName, strEmail, strPhone, strFName, strLName, strCity, strBalance,
            strUserType, strCurrentCity, accountNumber, status, strCurrency;
    TextView txtBalValue, btnContinue;
    SharedPreferences SP;
    private Bundle extras;
    private ArrayList<String> currencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_driver);
        //assign ontrols
        eTxtFirstName = findViewById(R.id.eTxtFirstName);
        eTxtLastName = findViewById(R.id.eTxtLastName);
        eTxtEmail = findViewById(R.id.eTxtEmail);
        eTxtPassword = findViewById(R.id.eTxtPassword);
        eTxtPhone = findViewById(R.id.eTxtPhone);
        eTxtCity = findViewById(R.id.eTxtCity);
        txtBalValue = findViewById(R.id.txtBalValue);
        btnContinue = findViewById(R.id.btnContinue);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);


        extras = getIntent().getExtras();
        if (extras != null) {
            Log.d(TAG, "<< EXTRAS NOT NULL >> ");

            strUser = extras.getString("strUser");
            Log.d(TAG, "strUser >> " + strUser);

            strPass = extras.getString("strPass");
            Log.d(TAG, "strPass >> " + strPass);

            strFullName = extras.getString("strFullName");
            Log.d(TAG, "strFullName >> " + strFullName);

            strFName = extras.getString("strFName");
            Log.d(TAG, "strFName >> " + strFName);

            strLName = extras.getString("strLName");
            Log.d(TAG, "strLName >> " + strLName);

            strEmail = extras.getString("strEmail");
            Log.d(TAG, "strEmail >> " + strEmail);

            strPhone = extras.getString("strPhone");
            Log.d(TAG, "strPhone >> " + strPhone);

            strCity = extras.getString("strCity");
            Log.d(TAG, "strCity >> " + strCity);

            strBalance = extras.getString("strBalance");
            Log.d(TAG, "strBalance >> " + strBalance);


        } else {
            Log.d(TAG, "<< EXTRAS IS NULLLLLLLLLLLLLLLLLL >> ");

        }
        loadCurrencySelectMenu();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EditProfileDriver.this, DashboardDriver.class);
                startActivity(it);
            }
        });

        loadDataFromSharedPrefs();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eTxtFirstName.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "First Name is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eTxtLastName.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Last Name is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eTxtEmail.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Email is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eTxtPhone.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Phone number is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eTxtPassword.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eTxtCity.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "City is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                active_switch = findViewById(R.id.active_switch);
                spinnerCurrency = findViewById(R.id.spinnerCurrency);

                strCurrency = spinnerCurrency.getSelectedItem().toString();
                Log.d(TAG, "strCurrency >> " + strCurrency);

                strFName = eTxtFirstName.getText().toString();
                strLName = eTxtLastName.getText().toString();
                strEmail = eTxtEmail.getText().toString();
                strPhone = eTxtPhone.getText().toString();
                strPass = eTxtPassword.getText().toString();
                strCity = eTxtCity.getText().toString();
                strFullName = strFName + " " + strLName;
                if (active_switch.isChecked()) {
                    status = "ACTIVE";
                } else {
                    status = "INACTIVE";
                }

                //pass values
                Log.d(TAG, "before fetching from open Users Table");
                makeRideShareEditDriverProfileQuery(strFName, strLName, strUser, strPhone,
                        strPass, strCity, strFullName, status, strCurrency);
                Log.d(TAG, "after makeRideShareEditDriverProfileQuery");

                populatePreferences();

            }
        });
    }

    private void populatePreferences() {
        SP = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.putString("strPass", strPass);
        editor.putString("strFName", strFName);
        editor.putString("strLName", strLName);
        editor.putString("strPhone", strPhone);
        editor.putString("strFullName", strFullName);
        editor.putString("strCurrency", strCurrency);
        editor.putString("status", status);
        editor.putString("strCity", strCity);
        editor.commit(); // commit changes
    }

    private void makeRideShareEditDriverProfileQuery(String strFNameValue, String strLNameValue, String strUserValue,
                                                     String strPhoneValue, String strPassValue, String strCityValue,
                                                     String strFullNameValue, String statusValue, String strCurrencyValue) {
        URL RideShareSelectUserURl = NetworkUtils.buildEditDriverUrl(strFNameValue, strLNameValue, strFullNameValue,
                strUserValue, strPhoneValue, strPassValue, strCityValue, statusValue, strCurrencyValue);
        Log.d(TAG, "RideShareSearchUrl is: " + RideShareSelectUserURl.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new EditProfileDriver.RideShareEditDriverQueryTask().execute(RideShareSelectUserURl);
    }

    private void loadCurrencySelectMenu() {
        spinnerCurrency = (Spinner) findViewById(R.id.spinnerCurrency);
        //category_Spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        currencyList = new ArrayList<String>();
        currencyList.add("NGN");
        currencyList.add("EUR");
        currencyList.add("GBP");
        currencyList.add("USD");
        currencyList.add("AED");
        currencyList.add("AFN");
        currencyList.add("ALL");
        currencyList.add("AMD");
        currencyList.add("AOA");
        currencyList.add("ARS");
        currencyList.add("AUD");
        currencyList.add("AWG");
        currencyList.add("AZN");
        currencyList.add("BAM");
        currencyList.add("BBD");
        currencyList.add("BDT");
        currencyList.add("BGN");
        currencyList.add("BHD");
        currencyList.add("BIF");
        currencyList.add("BMD");
        currencyList.add("BND");
        currencyList.add("BOB");
        currencyList.add("BRL");
        currencyList.add("BSD");
        currencyList.add("BTN");
        currencyList.add("BWP");
        currencyList.add("BYR");
        currencyList.add("BZD");
        currencyList.add("CAD");
        currencyList.add("CDF");
        currencyList.add("CHF");
        currencyList.add("CLP");
        currencyList.add("CNY");
        currencyList.add("COP");
        currencyList.add("CRC");
        currencyList.add("CUP");
        currencyList.add("CVE");
        currencyList.add("CZK");
        currencyList.add("DJF");
        currencyList.add("DKK");
        currencyList.add("DOP");
        currencyList.add("DZD");
        currencyList.add("EGP");
        currencyList.add("ERN");
        currencyList.add("ETB");
        currencyList.add("EUR");
        currencyList.add("FJD");
        currencyList.add("FKP");
        currencyList.add("GBP");
        currencyList.add("GEL");
        currencyList.add("GHS");
        currencyList.add("GIP");
        currencyList.add("GMD");
        currencyList.add("GNF");
        currencyList.add("GTQ");
        currencyList.add("GYD");
        currencyList.add("HKD");
        currencyList.add("HNL");
        currencyList.add("HRK");
        currencyList.add("HTG");
        currencyList.add("HUF");
        currencyList.add("IDR");
        currencyList.add("ILS");
        currencyList.add("INR");
        currencyList.add("IQD");
        currencyList.add("IRR");
        currencyList.add("ISK");
        currencyList.add("JMD");
        currencyList.add("JOD");
        currencyList.add("JPY");
        currencyList.add("KES");
        currencyList.add("KGS");
        currencyList.add("KHR");
        currencyList.add("KPW");
        currencyList.add("KRW");
        currencyList.add("KWD");
        currencyList.add("KYD");
        currencyList.add("KZT");
        currencyList.add("LAK");
        currencyList.add("LBP");
        currencyList.add("LKR");
        currencyList.add("LRD");
        currencyList.add("LSL");
        currencyList.add("LYD");
        currencyList.add("MAD");
        currencyList.add("MDL");
        currencyList.add("MGA");
        currencyList.add("MKD");
        currencyList.add("MMK");
        currencyList.add("MNT");
        currencyList.add("MOP");
        currencyList.add("MRO");
        currencyList.add("MUR");
        currencyList.add("MVR");
        currencyList.add("MWK");
        currencyList.add("MXN");
        currencyList.add("MYR");
        currencyList.add("MZN");
        currencyList.add("NAD");
        currencyList.add("NGN");
        currencyList.add("NIO");
        currencyList.add("NOK");
        currencyList.add("NPR");
        currencyList.add("NZD");
        currencyList.add("OMR");
        currencyList.add("PAB");
        currencyList.add("PEN");
        currencyList.add("PGK");
        currencyList.add("PHP");
        currencyList.add("PKR");
        currencyList.add("PLN");
        currencyList.add("PYG");
        currencyList.add("QAR");
        currencyList.add("RON");
        currencyList.add("RSD");
        currencyList.add("RUB");
        currencyList.add("RWF");
        currencyList.add("SAR");
        currencyList.add("SBD");
        currencyList.add("SCR");
        currencyList.add("SDG");
        currencyList.add("SEK");
        currencyList.add("SGD");
        currencyList.add("SHP");
        currencyList.add("SLL");
        currencyList.add("SOS");
        currencyList.add("SRD");
        currencyList.add("STD");
        currencyList.add("SYP");
        currencyList.add("SZL");
        currencyList.add("THB");
        currencyList.add("TJS");
        currencyList.add("TMT");
        currencyList.add("TND");
        currencyList.add("TOP");
        currencyList.add("TRY");
        currencyList.add("TTD");
        currencyList.add("TWD");
        currencyList.add("TZS");
        currencyList.add("UAH");
        currencyList.add("UGX");
        currencyList.add("USD");
        currencyList.add("UYU");
        currencyList.add("UZS");
        currencyList.add("VEF");
        currencyList.add("VND");
        currencyList.add("VUV");
        currencyList.add("WST");
        currencyList.add("XAF");
        currencyList.add("XCD");
        currencyList.add("XPF");
        currencyList.add("YER");
        currencyList.add("ZAR");
        currencyList.add("ZMW");
        currencyList.add("ZWL");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);

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

            strLName = SP.getString("strLName", null);
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

            strCity = SP.getString("strCity", null);
            Log.d(TAG, "strCity >> " + strCity);

            strCurrency = SP.getString("strCurrency", null);
            Log.d(TAG, "strCurrency >> " + strCurrency);

        } else {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
        eTxtFirstName.setText(strFName);
        eTxtLastName.setText(strLName);
        eTxtEmail.setText(strEmail);
        eTxtPhone.setText(strPhone);
        eTxtPassword.setText(strPass);
        eTxtCity.setText(strCity);
        txtBalValue.setText(strBalance);
        if (strCurrency != null) {
            spinnerCurrency.setSelection(currencyList.indexOf(strCurrency));
        } else {
            Log.d(TAG, "strCurrency is null");
        }
        if (status != null && status == "ACTIVE") {
            active_switch.setChecked(true);
        } else if (status != null && status == "INACTIVE") {
            active_switch.setChecked(false);
        } else if (status == null) {
            Log.d(TAG, "status == null ");
        }else{
            Log.d(TAG, "doing nothing ");
        }

        Log.d(TAG, "after loadDataFromSharedPrefs");
    }


    private void logoutUser() {
        SP = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        editor.clear().commit();
        editor.apply();
        Intent itLogout = new Intent(EditProfileDriver.this, signin.class);
        startActivity(itLogout);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    public class RideShareEditDriverQueryTask extends AsyncTask<URL, Void, String> {

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
                Intent it = new Intent(EditProfileDriver.this, DashboardDriver.class);
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

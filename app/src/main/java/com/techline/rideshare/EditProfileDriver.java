package com.techline.rideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileDriver extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "EDIT_PROFILE_DRIVER";
    //declare controls
    EditText eTxtFullName, eTxtEmail, eTxtPhone, eTxtPassword, eTxtFirstName, eTxtLastName, eTxtCity;
    String strUser, strPass, strFullName, strEmail, strPhone, strFName, strLName, strCity, strBalance, strUserType, strCurrentCity, accountNumber, status;
    TextView txtBalValue, btnContinue;
    SharedPreferences SP;
    private Bundle extras;

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

        extras = getIntent().getExtras();
        if (extras != null) {
            strUser = extras.getString("strUser");
            strPass = extras.getString("strPass");
            strFullName = extras.getString("strFullName");
            strFName = extras.getString("strFName");
            strLName = extras.getString("strLName");
            strEmail = extras.getString("strEmail");
            strPhone = extras.getString("strPhone");
            strCity = extras.getString("strCity");
            strBalance = extras.getString("strBalance");

            eTxtFirstName.setText(strFName);
            eTxtLastName.setText(strLName);
            eTxtEmail.setText(strEmail);
            eTxtPhone.setText(strPhone);
            eTxtPassword.setText(strPass);
            eTxtCity.setText(strCity);
            txtBalValue.setText(strBalance);

        } else {

        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EditProfileDriver.this, DashboardDriver.class);
                startActivity(it);
            }
        });

        loadDataFromSharedPrefs();
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
}

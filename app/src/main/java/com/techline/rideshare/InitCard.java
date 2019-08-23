package com.techline.rideshare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.util.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class InitCard extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String CHARGE_AMOUNT_IN_KOBO = "1000"; //amount in KOBO
    public static final String PAY_STACK_SECRET_KEY = "sk_live_72cd3be08f1025a6312867f75964fdf16793ead9";
    private static final String TAG = "INIT_CARD";
    String strUser, strPass, globalSearchResult, strFullName, strEmail, strPhone, strFName,
            strLName, strBalance, strUserType, strCurrentCity, accountNumber, status;
    SharedPreferences SP;
    private WebView paystackWebView;
    private TextView btnDone;
    private String globalVerifyCardResultsMethodString, globalSaveCardURLResult;
    private String authorization_url, access_code, reference, data_id, domain, data_status, amount_in_kobo, data_message,
            gateway_response, paid_at, created_at, channel, currency, ip_address, data_metadata, log, fees, fees_split,
            authorization, customer_data, customer_id, order_id, customer_first_name, customer_last_name, customer_email,
            customer_code, customer_phone, customer_metadata, customer_risk_action;
    private Bundle extras;
    private TextView myTextView;
    final Handler myHandler = new Handler();

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_card);
        loadDataFromSharedPrefs();

        paystackWebView = findViewById(R.id.paystackWebView);
        btnDone = findViewById(R.id.btnDone);


        extras = getIntent().getExtras();
        if (extras != null) {
            Log.d(TAG, "<< EXTRAS NOT NULL >> ");

            authorization_url = extras.getString("authorization_url");
            Log.d(TAG, "authorization_url >> " + authorization_url);

            reference = extras.getString("reference");
            Log.d(TAG, "reference >> " + reference);

            access_code = extras.getString("access_code");
            Log.d(TAG, "access_code >> " + access_code);

        } else {
            Log.d(TAG, "<< EXTRAS IS NULLLLLLLLLLLLLLLLLL >> ");
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "About to verify");
                makeVerifyCardURL(reference);
            }
        });
        myTextView = (TextView) findViewById(R.id.textView1);
        final JavaScriptInterface myJavaScriptInterface
                = new JavaScriptInterface(this);

        paystackWebView.getSettings().setLightTouchEnabled(true);
        paystackWebView.getSettings().setJavaScriptEnabled(true);
        paystackWebView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        paystackWebView.getSettings().setJavaScriptEnabled(true);
        paystackWebView.getSettings().setSupportMultipleWindows(true);
        paystackWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        paystackWebView.getSettings().setAllowFileAccess(true);
        paystackWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        paystackWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        paystackWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                viewx.loadUrl(urlx);
                return true;
            }
        });
        paystackWebView.loadUrl(authorization_url);


    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        public void showToast(String webMessage) {
            final String msgeToast = webMessage;
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    // This gets executed on the UI thread so it can safely modify Views
                    myTextView.setText(msgeToast);
                }
            });

            Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
        }
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

    private void makeVerifyCardURL(String reference) {
        Log.d(TAG, "inside makeVerifyCardURL ");
        URL RideSharemakeIVerifyCardURL = NetworkUtils.buildVerifyCardUrl(reference, PAY_STACK_SECRET_KEY);
        Log.d(TAG, "makeVerifyCardURL Url is: " + RideSharemakeIVerifyCardURL.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new InitCard.verifyCardQueryTask().execute(RideSharemakeIVerifyCardURL);

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
                    customer_code, customer_phone, customer_metadata, customer_risk_action, accountNumber);

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
                                       String customer_metadataValue, String customer_risk_actionValue, String accountNumberValue) {

        URL saveCardURL = NetworkUtils.buildSaveCardURL(authorization_urlValue, access_codeValue, referenceValue, data_idValue,
                domainValue, data_statusValue, amount_in_koboValue, data_messageValue, gateway_responseValue, paid_atValue, created_atValue, channelValue,
                currencyValue, ip_addressValue, data_metadataValue, logValue, feesValue, fees_splitValue, authorizationValue, customer_dataValue, customer_idValue,
                order_idValue, customer_first_nameValue, customer_last_nameValue, customer_emailValue, customer_codeValue, customer_phoneValue,
                customer_metadataValue, customer_risk_actionValue, accountNumberValue);
        Log.d(TAG, "buildSaveCardURL is: " + saveCardURL.toString());
        // COMPLETED (4) Create a new RideShareQueryTask and call its execute method, passing in the url to query
        new InitCard.saveCardURLQueryTask().execute(saveCardURL);

    }

    private String globalVerifyCardResultsMethod() {
        return globalVerifyCardResultsMethodString;
    }

    private void loadSaveCardURLResultInView() throws JSONException {
        Log.d(TAG, "inside loadSaveCardURLResultInView");
        // get JSONObject from JSON file
        JSONObject obj = new JSONObject(globalSaveCardURLResultMethod());
        Log.d(TAG, "obj is: " + obj);
    }

    private String globalSaveCardURLResultMethod() {
        return globalSaveCardURLResult;
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

    public class saveCardURLQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL saveCardURL = params[0];
            String saveCardURLResults = null;
            try {
                saveCardURLResults = NetworkUtils.getResponseFromHttpUrl(saveCardURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return saveCardURLResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String saveCardURLResults) {
            if (saveCardURLResults != null && !saveCardURLResults.equals("")) {
                Log.d(TAG, "saveCardURLResults is :" + saveCardURLResults);
                globalSaveCardURLResult = saveCardURLResults;
                try {
                    loadSaveCardURLResultInView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

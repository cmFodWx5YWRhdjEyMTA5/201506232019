/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.techline.rideshare.util;

import android.net.Uri;

import com.techline.rideshare.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    private static final String TAG = "NETWORK";

    public static final String BASE_URL = "http://rideshare.com.ng/";
    public static final String BASE_GEODODE_URL = "https://maps.googleapis.com/maps";
    final static String BASE_INSERT_USER_URL = BASE_URL + "android_api/v1/add_user.php";
    final static String BASE_SELECT_USER_URL = BASE_URL + "android_api/v1/select_user.php";
    final static String BASE_USER_LIST_URL = BASE_URL + "android_api/v1/userlist.php";
    final static String BASE_USER_LIST_ONE_URL = BASE_URL + "android_api/v1/userlist_one.php";
    final static String BASE_INSERT_MSG_URL = BASE_URL + "android_api/v1/add_msg.php";

    final static String BASE_INSERT_ENTRY_ID_URL = BASE_URL + "android_api/v1/generate_entry_id.php";
    //final static String BASE_INSERT_PAY_CODE_URL = BASE_URL + "android_api/v1/pay_success.php";

    final static String BASE_INSERT_CONTACT_US_URL = BASE_URL + "android_api/v1/contact_us.php";
    final static String BASE_CHANGE_PASS_URL = BASE_URL + "android_api/v1/change_pass.php";
    final static String BASE_CHECK_PAY_CODE_URL = BASE_URL + "android_api/v1/chek_pay_code.php";
    final static String BASE_GEO_CODE_ONE_URL = BASE_GEODODE_URL+ "/api/geocode/json";

    final static String PARAM_QUERY = "q";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_FULLNAME = "fullname";
    final static String PARAM_EMAIL = "email";
    final static String PARAM_USERNAME = "username";
    final static String PARAM_PHONE = "phone";
    final static String PARAM_PASSWORD = "password";
    final static String PARAM_STATE = "state";
    final static String PARAM_COUNTRY = "country";

    final static String sortBy = "stars";
    final static String PARAM_SORT = "random";
    final static String PARAM_DETAIL = "detail";
    final static String PARAM_SUBJECT = "subject";
    final static String PARAM_PAY_CODE = "pay_code";
    final static String PARAM_NEW_PASS = "new_pass";
    final static String COLUMN_TEXT = "message";
    final static String COLUMN_SENDER = "sender";
    final static String PARAM_MESSAGE = "message";
    final static String PARAM_SENDER_ID = "sender_id";
    final static String PARAM_MDATE = "mdate";
    final static String PARAM_SENDER_NAME = "sender_name";
    //for get entry id
    final static String PARAM_USER = "user_name";
    final static String PARAM_DURATION = "duration";
    final static String PARAM_AMOUNT = "amount";
    final static String PARAM_ACCT_NO = "accountNumber";
    private static final String PARAM_RECIPIENT = "recipient";
    private static final String PARAM_ISSUE_CATEGORY = "issue_category";
    private static final String PARAM_CURRENT_CITY = "current_city";
    private static final String PARAM_USER_TYPE = "user_type";
    private static final String PARAM_FIRST_NAME = "firstname";
    private static final String PARAM_LAST_NAME = "lastname";
    private static final String PARAM_BALANCE = "balance";
    // Message Constants
    // used Write a message to the database
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static String PARAM_CONV_ID = "conv_id";
    private static String PARAM_STATUS = "status";

    private static String PARAM_GEOCODE_ADDRESS = "address";
    private static String PARAM_GEOCODE_KEY = "key";

    /**
     * Builds the URL used to query Database.
     *
     * @params Keyword that will be queried for.
     */
    public static URL buildInsertUserUrl(String firstNameValue, String lastNameValue, String fullnameValue,
                                         String emailValue, String usernameValue,
                                         String phoneValue, String passwordValue, String stateValue,
                                         String countryValue, String CurrentCityValue, String UserTypeValue,
                                         String BalanceValue, String ActNoValue,
                                         String StatusValue) {
        Uri builtUri = Uri.parse(BASE_INSERT_USER_URL).buildUpon()
                .appendQueryParameter(PARAM_FIRST_NAME, firstNameValue)
                .appendQueryParameter(PARAM_LAST_NAME, lastNameValue)
                .appendQueryParameter(PARAM_FULLNAME, fullnameValue)
                .appendQueryParameter(PARAM_EMAIL, emailValue)
                .appendQueryParameter(PARAM_USERNAME, String.valueOf(usernameValue))
                .appendQueryParameter(PARAM_PHONE, phoneValue)
                .appendQueryParameter(PARAM_PASSWORD, passwordValue)
                .appendQueryParameter(PARAM_STATE, stateValue)
                .appendQueryParameter(PARAM_COUNTRY, countryValue)
                .appendQueryParameter(PARAM_CURRENT_CITY, CurrentCityValue)
                .appendQueryParameter(PARAM_USER_TYPE, UserTypeValue)
                .appendQueryParameter(PARAM_BALANCE, BalanceValue)
                .appendQueryParameter(PARAM_ACCT_NO, ActNoValue)
                .appendQueryParameter(PARAM_STATUS, StatusValue)

//                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildSelectUserUrl(String usernameValue, String passwordValue) {
        Uri builtUri = Uri.parse(BASE_SELECT_USER_URL).buildUpon()
                .appendQueryParameter(PARAM_USERNAME, usernameValue)
                .appendQueryParameter(PARAM_PASSWORD, passwordValue)

//                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildUserListUrl() {
        Uri builtUri = Uri.parse(BASE_USER_LIST_URL).buildUpon()

//                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildGeoCodeUrl(String input) {
        Uri builtUri = Uri.parse(BASE_GEO_CODE_ONE_URL).buildUpon()

                .appendQueryParameter(PARAM_GEOCODE_ADDRESS, input)
                .appendQueryParameter(PARAM_GEOCODE_KEY, "AIzaSyBBENhEE_gGgOxGoz6Z7NgLmhXWoBy-owI")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildInsertMessageUrl(String recipientValue, String message,
                                            String sender, String stateValue, String countryValue, String issue_category) {
        String nowTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Uri builtUri = Uri.parse(BASE_INSERT_MSG_URL).buildUpon()
                .appendQueryParameter(PARAM_SENDER_NAME, sender)
                .appendQueryParameter(PARAM_STATE, stateValue)
                .appendQueryParameter(PARAM_MESSAGE, message)
                .appendQueryParameter(PARAM_COUNTRY, countryValue)
                .appendQueryParameter(PARAM_RECIPIENT, recipientValue)
                .appendQueryParameter(PARAM_CONV_ID, nowTime)
                .appendQueryParameter(PARAM_ISSUE_CATEGORY, issue_category)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildInsertEntryCodeUrl(String user_nameValue, String user_amount, String user_duration) {

        Uri builtUri = Uri.parse(BASE_INSERT_ENTRY_ID_URL).buildUpon()
                .appendQueryParameter(PARAM_USER, user_nameValue)
                .appendQueryParameter(PARAM_DURATION, user_duration)
                .appendQueryParameter(PARAM_AMOUNT, user_amount)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildContactEmailSenderUrl(String user_detail, String user_email, String user_subject) {

        Uri builtUri = Uri.parse(BASE_INSERT_CONTACT_US_URL).buildUpon()
                .appendQueryParameter(PARAM_DETAIL, user_detail)
                .appendQueryParameter(PARAM_EMAIL, user_email)
                .appendQueryParameter(PARAM_SUBJECT, user_subject)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildChangePassUrl(String fullname, String user_new_pass) {

        Uri builtUri = Uri.parse(BASE_CHANGE_PASS_URL).buildUpon()
                .appendQueryParameter(PARAM_NEW_PASS, user_new_pass)
                .appendQueryParameter(PARAM_FULLNAME, fullname)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildCheckPayCodeUrl(String pay_code) {

        Uri builtUri = Uri.parse(BASE_CHECK_PAY_CODE_URL).buildUpon()
                .appendQueryParameter(PARAM_PAY_CODE, pay_code)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

}
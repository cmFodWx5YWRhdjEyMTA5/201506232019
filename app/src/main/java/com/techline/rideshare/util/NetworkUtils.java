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
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    private static final String TAG = "NETWORK";

    static final String BASE_URL = "http://rideshare.com.ng/";
    static final String BASE_GEODODE_URL = "https://maps.googleapis.com/maps";
    static final String BASE_TOMTOM_URL = "https://api.tomtom.com/routing/1/calculateRoute/<<LATLONGDATA>>/json/";
    final static String BASE_PASTCK_URL = "https://api.paystack.co/";
    final static String BASE_INITIALIZE_NEW_CARD_URL = BASE_PASTCK_URL + "transaction/initialize";
    final static String BASE_VERIFY_CARD_URL = BASE_PASTCK_URL + "transaction/verify/";
    final static String BASE_INSERT_USER_URL = BASE_URL + "android_api/v1/add_user.php";
    final static String BASE_INSERT_USER_CARD_DATA = BASE_URL + "android_api/v1/add_card.php";
    final static String BASE_SELECT_USER_URL = BASE_URL + "android_api/v1/select_user.php";
    final static String BASE_SELECT_ALL_REQUEST_URL = BASE_URL + "android_api/v1/select_all_requests.php";
    final static String BASE_USER_LIST_URL = BASE_URL + "android_api/v1/userlist.php";
    final static String BASE_USER_LIST_ONE_URL = BASE_URL + "android_api/v1/userlist_one.php";
    final static String BASE_INSERT_MSG_URL = BASE_URL + "android_api/v1/add_msg.php";

    final static String BASE_INSERT_ENTRY_ID_URL = BASE_URL + "android_api/v1/generate_entry_id.php";
    //final static String BASE_INSERT_PAY_CODE_URL = BASE_URL + "android_api/v1/pay_success.php";
    private static final String BASE_START_RIDE_URL = BASE_URL + "android_api/v1/startRide.php";
    private static final String BASE_STOP_RIDE_URL = BASE_URL + "android_api/v1/stopRide.php";

    final static String BASE_INSERT_CONTACT_US_URL = BASE_URL + "android_api/v1/contact_us.php";
    final static String BASE_CHANGE_PASS_URL = BASE_URL + "android_api/v1/change_pass.php";
    final static String BASE_CHECK_PAY_CODE_URL = BASE_URL + "android_api/v1/chek_pay_code.php";
    final static String BASE_ADD_ROUTE_URL = BASE_URL + "android_api/v1/add_route.php";
    final static String BASE_EDIT_PROFILE_PASS_URL = BASE_URL + "android_api/v1/edit_profile_pass.php";
    final static String BASE_EDIT_PROFILE_DRIVER_URL = BASE_URL + "android_api/v1/edit_profile_driver.php";
    final static String BASE_RIDE_REQUEST_URL = BASE_URL + "android_api/v1/ride_request.php";


    final static String BASE_GEO_CODE_ONE_URL = BASE_GEODODE_URL + "/api/geocode/json";

    final static String PARAM_QUERY = "q";

    //pstck


    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_FULLNAME = "fullname";
    final static String PARAM_RIDE_ID = "ride_id";
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
    private static final String PARAM_PICKUP_PLACE_ID = "pickUpPlaceId";
    private static final String PARAM_PICKUP_GEOMETRY = "pickUpGeometry";
    private static final String PARAM_PICKUP_LOCATION_TYPE = "pickUpLocation_type";
    private static final String PARAM_PICKUP_LOCATION = "pickUpLocation";
    private static final String PARAM_PICKUP_LAT = "pickUpLat";
    private static final String PARAM_PICKUP_LNG = "pickUpLng";
    private static final String PARAM_WHERETO_PLACE_ID = "whereToPlaceId";
    private static final String PARAM_WHERETO_GEOMETRY = "whereToGeometry";
    private static final String PARAM_WHERETO_LOCATION_TYPE = "whereToLocation_type";
    private static final String PARAM_WHERETO_LAT = "whereToLat";
    private static final String PARAM_WHERETO_LOCATION = "whereToLocation";
    private static final String PARAM_WHERETO_LNG = "whereToLng";
    private static final String PARAM_DISTANCE = "distance";
    private static final String PARAM_PICKUP_DESC = "pickUpDesc";
    private static final String PARAM_WHERETO_DESC = "whereToDesc";
    private static final String PARAM_NAME_ON_CARD = "nameOnCard";
    private static final String PARAM_FULL_NUMBER_ON_CARD = "fullNumberOnCard";
    private static final String PARAM_TOKENISED_CARD = "tokenisedCard";
    private static final String PARAM_PIN_ON_CARD = "pinOnCard";
    private static final String PARAM_OTP_ON_CARD = "otpOnCard";
    private static final String PARAM_TOKENISATION_VERIFIEDONCARD = "tokenisationVerifiedOnCard";
    private static final String PARAM_AUTHORIZATION_CODE_ON_CARD = "authorizationCodeOnCard";
    private static final String PARAM_BANK_ON_CARD = "bankOnCard";
    private static final String PARAM_TYPE_ON_CARD = "typeOnCard";
    private static final String PARAM_LAST_4DIGITS_ON_CARD = "last4DigitsOnCard";
    private static final String PARAM_EMIL_ON_CARD = "emilOnCard";
    private static final String PARAM_AUTHOBJ_ON_CARD = "authObjOnCard";
    private static final String PARAM_ACCOUNT_NUMBER = "accountNumber";
    private static final String PARAM_CURRENCY = "currency";

    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_VEHICLEHEADING = "vehicleHeading";
    private static final String PARAM_SECTIONTYPE = "sectionType";
    private static final String PARAM_REPORT = "report";
    private static final String PARAM_ROUTETYPE = "routeType";
    private static final String PARAM_TRAFFIC = "traffic";
    private static final String PARAM_AVOID = "avoid";
    private static final String PARAM_TRAVELMODE = "travelMode";
    private static final String PARAM_VEHICLEMAXSPEED = "vehicleMaxSpeed";
    private static final String PARAM_VEHICLECOMMERCIAL = "vehicleCommercial";
    private static final String PARAM_VEHICLEENGINETYPE = "vehicleEngineType";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_TRAVEL_TIME = "travelTime";
    private static final String PARAM_TRAVEL_TIME_INSECONDS = "travelTimeInSeconds";
    private static final String PARAM_ARRIVAL_TIMEEST = "arrivalTimeEst";
    private static final String PARAM_FARE_ESTIMATE = "fareEstimate";
    private static final String PARAM_AUTHORIZATION_URL = "authorization_url";
    private static final String PARAM_ACCESS_CODE = "access_code";
    private static final String PARAM_REFERENCE = "reference";
    private static final String PARAM_DATA_ID = "data_id";
    private static final String PARAM_DOMAIN = "domain";
    private static final String PARAM_DATA_STATUS = "data_status";
    private static final String PARAM_AMOUNT_IN_KOBO = "amount_in_kobo";
    private static final String PARAM_DATA_MESSAGE = "data_message";
    private static final String PARAM_GATEWAY_RESPONSE = "gateway_response";
    private static final String PARAM_PAID_AT = "paid_at";
    private static final String PARAM_CREATED_AT = "created_at";
    private static final String PARAM_CHANNEL = "channel";
    private static final String PARAM_IP_ADDRESS = "ip_address";
    private static final String PARAM_DATA_METADATA = "data_metadata";
    private static final String PARAM_LOG = "log";
    private static final String PARAM_FEES = "fees";
    private static final String PARAM_FEES_SPLIT = "fees_split";
    private static final String PARAM_AUTHORIZATION = "authorization";
    private static final String PARAM_CUSTOMER_DATA = "customer_data";
    private static final String PARAM_CUSTOMER_ID = "customer_id";
    private static final String PARAM_ORDER_ID = "order_id";
    private static final String PARAM_CUSTOMER_FIRST_NAME = "customer_first_name";
    private static final String PARAM_CUSTOMER_LAST_NAME = "customer_last_name";
    private static final String PARAM_CUSTOMER_EMAIL = "customer_email";
    private static final String PARAM_CUSTOMER_CODE = "customer_code";
    private static final String PARAM_CUSTOMER_PHONE = "customer_phone";
    private static final String PARAM_CUSTOMER_METADATA = "customer_metadata";
    private static final String PARAM_CUSTOMER_RISK_ACTION = "customer_risk_action";
    private static final String PARAM_TIME = "time";
    private static final String PARAM_SPLIT_INTO = "splitInto";
    private static final String PARAM_SPLIT_THIS_RIDE = "splitThisRide";
    private static final String PARAM_MEETUP_AT = "meetupAt";

    // Message Constants
    // used Write a message to the database
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static String PARAM_CONV_ID = "conv_id";
    private static String PARAM_STATUS = "status";

    private static String PARAM_GEOCODE_ADDRESS = "address";
    private static String PARAM_GEOCODE_KEY = "key";
    private static String chrgeAmountValueStore, chrgeEmailValueStore, tokenValueStore, cardReferenceValuetore;

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

    public static URL buildInsertRouteUrl(String pickUpPlaceIdValue, String pickUpGeometryValue, String pickUpLocation_typeValue,
                                          String pickUpLocationValue, String pickUpLatValue, String pickUpLngValue,
                                          String whereToPlaceIdValue, String whereToGeometryValue,
                                          String whereToLocation_typeValue, String whereToLatValue,
                                          String whereToLocationValue, String whereToLngValue, String accountNoValue,
                                          String distanceValue, String pickUpDescValue, String whereToDescValue,
                                          String travelTimeValue, String travelTimeInSecondsValue, String arrivalTimeEstValue,
                                          String fareEstimateValue
    ) {

        Uri builtUri = Uri.parse(BASE_ADD_ROUTE_URL).buildUpon()
                .appendQueryParameter(PARAM_PICKUP_PLACE_ID, pickUpPlaceIdValue)
                .appendQueryParameter(PARAM_PICKUP_GEOMETRY, pickUpGeometryValue)
                .appendQueryParameter(PARAM_PICKUP_LOCATION_TYPE, pickUpLocation_typeValue)
                .appendQueryParameter(PARAM_PICKUP_LOCATION, pickUpLocationValue)
                .appendQueryParameter(PARAM_PICKUP_LAT, pickUpLatValue)
                .appendQueryParameter(PARAM_PICKUP_LNG, pickUpLngValue)
                .appendQueryParameter(PARAM_WHERETO_PLACE_ID, whereToPlaceIdValue)
                .appendQueryParameter(PARAM_WHERETO_GEOMETRY, whereToGeometryValue)
                .appendQueryParameter(PARAM_WHERETO_LOCATION_TYPE, whereToLocation_typeValue)
                .appendQueryParameter(PARAM_WHERETO_LAT, whereToLatValue)
                .appendQueryParameter(PARAM_WHERETO_LOCATION, whereToLocationValue)
                .appendQueryParameter(PARAM_WHERETO_LNG, whereToLngValue)
                .appendQueryParameter(PARAM_ACCT_NO, accountNoValue)
                .appendQueryParameter(PARAM_DISTANCE, distanceValue)
                .appendQueryParameter(PARAM_PICKUP_DESC, pickUpDescValue)
                .appendQueryParameter(PARAM_WHERETO_DESC, whereToDescValue)

                .appendQueryParameter(PARAM_TRAVEL_TIME, travelTimeValue)
                .appendQueryParameter(PARAM_TRAVEL_TIME_INSECONDS, travelTimeInSecondsValue)
                .appendQueryParameter(PARAM_ARRIVAL_TIMEEST, arrivalTimeEstValue)
                .appendQueryParameter(PARAM_FARE_ESTIMATE, fareEstimateValue)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static URL buildInsertCardUrl(String chrgeEmailValue, String chrgeAmountValue,
                                         String tokenValue) {
        chrgeEmailValueStore = chrgeEmailValue;
        chrgeAmountValueStore = chrgeAmountValue;
        tokenValueStore = tokenValue;
        Uri builtUri = Uri.parse(BASE_INITIALIZE_NEW_CARD_URL).buildUpon()

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromInitializePaystackHttpUrl(URL url) throws IOException, JSONException {
        StringBuffer response = new StringBuffer();
        JSONObject POST_PARAMS = new JSONObject();

        POST_PARAMS.put("amount", chrgeAmountValueStore);
        POST_PARAMS.put("email", chrgeEmailValueStore);
        Log.d(TAG, "tokenValueStore :  " + tokenValueStore);

        String authorize_header = "Bearer " + tokenValueStore;
        Log.d(TAG, "authorize_header :  " + authorize_header);
        Log.d(TAG, "tokenValueStore :  " + tokenValueStore);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", authorize_header);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        //conn.setDoInput(true);


        try {
            OutputStream os = conn.getOutputStream();
            os.write(POST_PARAMS.toString().getBytes());
            os.flush();
            os.close();
            int responseCode = conn.getResponseCode();
            Log.d(TAG, "POST Response Code :  " + responseCode);
            Log.d(TAG, "POST Response Message : " + conn.getResponseMessage());

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                Log.d(TAG, response.toString());

                return response.toString();

            } else {
                return "ERROR";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "e.printStackTrace() >>" + e.toString());
            Log.d(TAG, "POST DIDN'T WORK");

        } finally {
            return response.toString();

        }

    }


    public static URL buildVerifyCardUrl(String cardReferenceValue, String tokenValue) {
        cardReferenceValuetore = cardReferenceValue;
        tokenValueStore = tokenValue;
        Uri builtUri = Uri.parse(BASE_VERIFY_CARD_URL + cardReferenceValuetore).buildUpon()

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromVeriyPaystackHttpUrl(URL url) throws IOException, JSONException {
        StringBuffer response = new StringBuffer();
        JSONObject POST_PARAMS = new JSONObject();

        POST_PARAMS.put("reference", cardReferenceValuetore);
        Log.d(TAG, "tokenValueStore :  " + tokenValueStore);

        String authorize_header = "Bearer " + tokenValueStore;
        Log.d(TAG, "authorize_header :  " + authorize_header);
        Log.d(TAG, "tokenValueStore :  " + tokenValueStore);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", authorize_header);
        conn.setDoOutput(false);
        conn.setUseCaches(false);
        conn.setReadTimeout(15 * 1000);
        conn.setDoInput(true);
        conn.connect();
        try {

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "Response Code :  " + responseCode);
            Log.d(TAG, "Response Message : " + conn.getResponseMessage());

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                Log.d(TAG, response.toString());

                return response.toString();

            } else {
                return "ERROR";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "e.printStackTrace() >>" + e.toString());
            Log.d(TAG, "POST DIDN'T WORK");

        } finally {
            return response.toString();

        }


    }

    public static URL buildEditPassUrl(String strFNameVal, String strLNameVal, String strFullNameVal,
                                       String strUserVal, String strPhoneVal, String strPassVal,
                                       String strStatusVal, String strCurrencyVal) {
        Uri builtUri = Uri.parse(BASE_EDIT_PROFILE_PASS_URL).buildUpon()
                .appendQueryParameter(PARAM_FIRST_NAME, strFNameVal)
                .appendQueryParameter(PARAM_LAST_NAME, strLNameVal)
                .appendQueryParameter(PARAM_FULLNAME, strFullNameVal)
                .appendQueryParameter(PARAM_USERNAME, strUserVal)
                .appendQueryParameter(PARAM_PHONE, strPhoneVal)
                .appendQueryParameter(PARAM_PASSWORD, strPassVal)
                .appendQueryParameter(PARAM_STATUS, strStatusVal)
                .appendQueryParameter(PARAM_CURRENCY, strCurrencyVal)

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

    public static URL buildEditDriverUrl(String strFNameValue, String strLNameValue, String strFullNameValue,
                                         String strUserValue, String strPhoneValue, String strPassValue,
                                         String strCityValue, String statusValue, String strCurrencyValue) {
        Uri builtUri = Uri.parse(BASE_EDIT_PROFILE_DRIVER_URL).buildUpon()
                .appendQueryParameter(PARAM_FIRST_NAME, strFNameValue)
                .appendQueryParameter(PARAM_LAST_NAME, strLNameValue)
                .appendQueryParameter(PARAM_FULLNAME, strFullNameValue)
                .appendQueryParameter(PARAM_USERNAME, strUserValue)
                .appendQueryParameter(PARAM_PHONE, strPhoneValue)
                .appendQueryParameter(PARAM_PASSWORD, strPassValue)
                .appendQueryParameter(PARAM_CURRENT_CITY, strCityValue)
                .appendQueryParameter(PARAM_STATUS, statusValue)
                .appendQueryParameter(PARAM_CURRENCY, strCurrencyValue)

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

    public static URL buildTomTomRouteTimeUrl(String language, String vehicleHeading, String sectionType,
                                              String report, String routeType, String traffic,
                                              String avoid, String travelMode, String vehicleMaxSpeed,
                                              String vehicleCommercial, String vehicleEngineType, String key, String routeString) {
        String dynamicTomTomUrl = BASE_TOMTOM_URL.replace("<<LATLONGDATA>>", routeString);

        Uri builtUri = Uri.parse(dynamicTomTomUrl).buildUpon()
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .appendQueryParameter(PARAM_VEHICLEHEADING, vehicleHeading)
                .appendQueryParameter(PARAM_SECTIONTYPE, sectionType)
                .appendQueryParameter(PARAM_REPORT, report)
                .appendQueryParameter(PARAM_ROUTETYPE, routeType)
                .appendQueryParameter(PARAM_TRAFFIC, traffic)
                .appendQueryParameter(PARAM_AVOID, avoid)
                .appendQueryParameter(PARAM_TRAVELMODE, travelMode)
                .appendQueryParameter(PARAM_VEHICLEMAXSPEED, vehicleMaxSpeed)
                .appendQueryParameter(PARAM_VEHICLECOMMERCIAL, vehicleCommercial)
                .appendQueryParameter(PARAM_VEHICLEENGINETYPE, vehicleEngineType)
                .appendQueryParameter(PARAM_KEY, key)

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

    public static URL buildSaveCardURL(String authorization_urlValue, String access_codeValue, String referenceValue,
                                       String data_idValue, String domainValue, String data_statusValue, String amount_in_koboValue,
                                       String data_messageValue, String gateway_responseValue, String paid_atValue,
                                       String created_atValue, String channelValue, String currencyValue, String ip_addressValue,
                                       String data_metadataValue, String logValue, String feesValue, String fees_splitValue,
                                       String authorizationValue, String customer_dataValue, String customer_idValue,
                                       String order_idValue, String customer_first_nameValue, String customer_last_nameValue,
                                       String customer_emailValue, String customer_codeValue, String customer_phoneValue,
                                       String customer_metadataValue, String customer_risk_actionValue, String accountNumberValue) {

        Uri builtUri = Uri.parse(BASE_INSERT_USER_CARD_DATA).buildUpon()

                .appendQueryParameter(PARAM_AUTHORIZATION_URL, authorization_urlValue)
                .appendQueryParameter(PARAM_ACCESS_CODE, access_codeValue)
                .appendQueryParameter(PARAM_REFERENCE, referenceValue)
                .appendQueryParameter(PARAM_DATA_ID, data_idValue)
                .appendQueryParameter(PARAM_DOMAIN, domainValue)
                .appendQueryParameter(PARAM_DATA_STATUS, data_statusValue)
                .appendQueryParameter(PARAM_AMOUNT_IN_KOBO, amount_in_koboValue)
                .appendQueryParameter(PARAM_DATA_MESSAGE, data_messageValue)
                .appendQueryParameter(PARAM_GATEWAY_RESPONSE, gateway_responseValue)
                .appendQueryParameter(PARAM_PAID_AT, paid_atValue)
                .appendQueryParameter(PARAM_CREATED_AT, created_atValue)
                .appendQueryParameter(PARAM_CHANNEL, channelValue)
                .appendQueryParameter(PARAM_CURRENCY, currencyValue)
                .appendQueryParameter(PARAM_IP_ADDRESS, ip_addressValue)
                .appendQueryParameter(PARAM_DATA_METADATA, data_metadataValue)
                .appendQueryParameter(PARAM_LOG, logValue)
                .appendQueryParameter(PARAM_FEES, feesValue)
                .appendQueryParameter(PARAM_FEES_SPLIT, fees_splitValue)
                .appendQueryParameter(PARAM_AUTHORIZATION, authorizationValue)
                .appendQueryParameter(PARAM_CUSTOMER_DATA, customer_dataValue)
                .appendQueryParameter(PARAM_CUSTOMER_ID, customer_idValue)
                .appendQueryParameter(PARAM_ORDER_ID, order_idValue)
                .appendQueryParameter(PARAM_CUSTOMER_FIRST_NAME, customer_first_nameValue)
                .appendQueryParameter(PARAM_CUSTOMER_LAST_NAME, customer_last_nameValue)
                .appendQueryParameter(PARAM_CUSTOMER_EMAIL, customer_emailValue)
                .appendQueryParameter(PARAM_CUSTOMER_CODE, customer_codeValue)
                .appendQueryParameter(PARAM_CUSTOMER_PHONE, customer_phoneValue)
                .appendQueryParameter(PARAM_CUSTOMER_METADATA, customer_metadataValue)
                .appendQueryParameter(PARAM_CUSTOMER_RISK_ACTION, customer_risk_actionValue)
                .appendQueryParameter(PARAM_ACCT_NO, accountNumberValue)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static URL buildInsertoldRouteUrl(String pickUpPlaceIdValue, String pickUpGeometryValue, String pickUpLocation_typeValue,
                                             String pickUpLocationValue, String pickUpLatValue, String pickUpLngValue, String whereToPlaceIdValue,
                                             String whereToGeometryValue, String whereToLocation_typeValue, String whereToLatValue,
                                             String whereToLocationValue, String whereToLngValue, String accountNo, String distanceValue,
                                             String pickUpDescValue, String whereToDescValue) {
        Uri builtUri = Uri.parse(BASE_ADD_ROUTE_URL).buildUpon()
                .appendQueryParameter(PARAM_PICKUP_PLACE_ID, pickUpPlaceIdValue)
                .appendQueryParameter(PARAM_PICKUP_GEOMETRY, pickUpGeometryValue)
                .appendQueryParameter(PARAM_PICKUP_LOCATION_TYPE, pickUpLocation_typeValue)
                .appendQueryParameter(PARAM_PICKUP_LOCATION, pickUpLocationValue)
                .appendQueryParameter(PARAM_PICKUP_LAT, pickUpLatValue)
                .appendQueryParameter(PARAM_PICKUP_LNG, pickUpLngValue)
                .appendQueryParameter(PARAM_WHERETO_PLACE_ID, whereToPlaceIdValue)
                .appendQueryParameter(PARAM_WHERETO_GEOMETRY, whereToGeometryValue)
                .appendQueryParameter(PARAM_WHERETO_LOCATION_TYPE, whereToLocation_typeValue)
                .appendQueryParameter(PARAM_WHERETO_LAT, whereToLatValue)
                .appendQueryParameter(PARAM_WHERETO_LOCATION, whereToLocationValue)
                .appendQueryParameter(PARAM_WHERETO_LNG, whereToLngValue)
                .appendQueryParameter(PARAM_ACCT_NO, accountNo)
                .appendQueryParameter(PARAM_DISTANCE, distanceValue)
                .appendQueryParameter(PARAM_PICKUP_DESC, pickUpDescValue)
                .appendQueryParameter(PARAM_WHERETO_DESC, whereToDescValue)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildRideRequestUrl(String pickUpPlaceIdValue, String pickUpGeometryValue, String pickUpLocation_typeValue,
                                          String pickUpLocationValue, String pickUpLatValue, String pickUpLngValue, String whereToPlaceIdValue,
                                          String whereToGeometryValue, String whereToLocation_typeValue, String whereToLatValue,
                                          String whereToLocationValue, String whereToLngValue, String accountNumberValue, String distanceOfRouteValue,
                                          String pickUpDescValue, String whereToDescValue, String travelTimeValue, String travelTimeInSecondsValue,
                                          String arrivalTimeValue, String strFareValue, String strFullNameValue, String dateTimeStValue, String splitIntoValue,
                                          String splitThisRideValue, String meetupAtValue
    ) {

        Uri builtUri = Uri.parse(BASE_RIDE_REQUEST_URL).buildUpon()
                .appendQueryParameter(PARAM_PICKUP_PLACE_ID, pickUpPlaceIdValue)
                .appendQueryParameter(PARAM_PICKUP_GEOMETRY, pickUpGeometryValue)
                .appendQueryParameter(PARAM_PICKUP_LOCATION_TYPE, pickUpLocation_typeValue)
                .appendQueryParameter(PARAM_PICKUP_LOCATION, pickUpLocationValue)
                .appendQueryParameter(PARAM_PICKUP_LAT, pickUpLatValue)
                .appendQueryParameter(PARAM_PICKUP_LNG, pickUpLngValue)
                .appendQueryParameter(PARAM_WHERETO_PLACE_ID, whereToPlaceIdValue)
                .appendQueryParameter(PARAM_WHERETO_GEOMETRY, whereToGeometryValue)
                .appendQueryParameter(PARAM_WHERETO_LOCATION_TYPE, whereToLocation_typeValue)
                .appendQueryParameter(PARAM_WHERETO_LAT, whereToLatValue)
                .appendQueryParameter(PARAM_WHERETO_LOCATION, whereToLocationValue)
                .appendQueryParameter(PARAM_WHERETO_LNG, whereToLngValue)
                .appendQueryParameter(PARAM_ACCT_NO, accountNumberValue)
                .appendQueryParameter(PARAM_DISTANCE, distanceOfRouteValue)
                .appendQueryParameter(PARAM_PICKUP_DESC, pickUpDescValue)
                .appendQueryParameter(PARAM_WHERETO_DESC, whereToDescValue)

                .appendQueryParameter(PARAM_TRAVEL_TIME, travelTimeValue)
                .appendQueryParameter(PARAM_TRAVEL_TIME_INSECONDS, travelTimeInSecondsValue)
                .appendQueryParameter(PARAM_ARRIVAL_TIMEEST, arrivalTimeValue)
                .appendQueryParameter(PARAM_FARE_ESTIMATE, strFareValue)
                .appendQueryParameter(PARAM_FULLNAME, strFullNameValue)
                .appendQueryParameter(PARAM_TIME, dateTimeStValue)

                .appendQueryParameter(PARAM_SPLIT_INTO, splitIntoValue)
                .appendQueryParameter(PARAM_SPLIT_THIS_RIDE, splitThisRideValue)
                .appendQueryParameter(PARAM_MEETUP_AT, meetupAtValue)

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildSelectRideRequestsUrl(String usernameValue) {
        Uri builtUri = Uri.parse(BASE_SELECT_ALL_REQUEST_URL).buildUpon()
                .appendQueryParameter(PARAM_USERNAME, usernameValue)

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

    public static URL buildStartRideUrl(String driverId, String driverName, String rideId) {
        Uri builtUri = Uri.parse(BASE_START_RIDE_URL).buildUpon()
                .appendQueryParameter(PARAM_ACCOUNT_NUMBER, driverId)
                .appendQueryParameter(PARAM_FULLNAME, driverName)
                .appendQueryParameter(PARAM_RIDE_ID, rideId)

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

    public static URL buildStopRideUrl(String rideIdValue) {
        Uri builtUri = Uri.parse(BASE_STOP_RIDE_URL).buildUpon()

                .appendQueryParameter(PARAM_RIDE_ID, rideIdValue)

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
}
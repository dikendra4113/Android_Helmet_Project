package com.example.androidtranning.webservices.user;

import android.util.Log;


import com.example.androidtranning.models.UserDetails;
import com.example.androidtranning.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiEmailLogin extends ApiCaller {

    private static final String TAG = ApiEmailLogin.class.getSimpleName();

    private String email;
    private String password;
    private boolean status;
    private String message;
    private String fcm_token;

    public ApiEmailLogin setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
        return this;
    }

    public ApiEmailLogin setEmail(String email) {
        this.email = email;
        return this;
    }

    public ApiEmailLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getURL() {
        return "user_login/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
//        params.put("password", password);
        params.put("fcm_token", fcm_token);
        return params;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserDetails getUserDetails(String response) {

        UserDetails user = null;
        if (Util.isStringValid(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                jsonObject.optJSONObject("result");
                status = jsonObject.optBoolean("status");
                message = jsonObject.optString("message");
                JSONObject userObj = jsonObject.optJSONObject("result");

                if (status) {
                    user = new UserDetails();
                    if (userObj != null) {
                        user.userId = userObj.optString("_id");
                        user.userEmail = userObj.optString("email");
                        user.fcmToken = userObj.optString("fcm_token");
                        user.userFirstName = userObj.optString("first_name");
                        user.userLastName = userObj.optString("last_name");
                        user.address = userObj.optString("address");
                        user.bloodGroup = userObj.optString("blood_group");
                        user.dateOfBirth = userObj.optString("data_of_birth");
                        user.imageUrl = userObj.optString("image_url");
                        user.city = userObj.optString("city");
                        user.userToken = userObj.optString("user_token");
                        user.mobileNumber = userObj.optString("mobile_number");
                        user.gender = userObj.optString("gender");
                        user.active = userObj.optBoolean("active");
                        user.pincode = userObj.optString("pincode");

                        JSONArray locationArray = userObj.optJSONArray("favorite_locations");
                        if (locationArray != null && locationArray.length() > 0) {
                            JSONObject location = locationArray.optJSONObject(1) ;

                            if(location != null && location.length() > 0){
                                JSONArray location_coord = location.optJSONArray("location_coord");
                                if(location_coord != null && location_coord.length() > 0 ){
                                    user.userLongitude = location_coord.optString(0);
                                user.userLatitude = location_coord.optString(1);

                                }


                            }

                        }
                    }
                } else {
                    Log.e(TAG, "getUserDetails: " + message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}

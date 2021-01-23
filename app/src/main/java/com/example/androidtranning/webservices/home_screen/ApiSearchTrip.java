package com.example.androidtranning.webservices.home_screen;

import android.util.Log;

import com.example.androidtranning.models.SearchTripModel;
import com.example.androidtranning.models.UserAllTripModel;
import com.example.androidtranning.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import utils.ApiCaller;

public class ApiSearchTrip extends ApiCaller {
    public static final String TAG = ApiSearchTrip.class.getName();
    private String userToken;
    private double longitute;
    private double latitude;
    private double[] location = new double[2];




    public ApiSearchTrip setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }
    public ApiSearchTrip setLongitude(double longitude) {
        this.longitute = longitude;
        location[0]= longitude;
        return this;
    }
    public ApiSearchTrip setLatitude(double latitude) {
        this.latitude = latitude;
        location[1] = latitude;
        return this;
    }





    @Override
    public String getURL() {
        return "get_user_favorite_location/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user-token", userToken);
        params.put("location",location);
        return params;
    }

//    public ArrayList<ApiSearchTrip> getPostList(String response) {
//
//        ArrayList<ApiSearchTrip> trips = new ArrayList<>();
//
//        if (Util.isStringValid(response)) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                Log.i(TAG, "getAllPosts: " + jsonObject.optJSONArray("result"));
//                JSONArray jsonArray = jsonObject.optJSONArray("result");
//
//                if (jsonArray != null && jsonArray.length() > 0) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject obj = jsonArray.optJSONObject(i);
//                        SearchTripModel model = SearchTripModel.parseAllTrip(obj);
//                        trips.add(model);
//                    }
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return shares;
//
//    }
}

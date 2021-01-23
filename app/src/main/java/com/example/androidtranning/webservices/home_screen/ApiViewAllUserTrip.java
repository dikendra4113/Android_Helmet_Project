package com.example.androidtranning.webservices.home_screen;

import android.util.Log;

import com.example.androidtranning.models.UserAllTripModel;
import com.example.androidtranning.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import utils.ApiCaller;

public class ApiViewAllUserTrip extends ApiCaller {

    private String userToken;
    private static final String TAG = ApiViewAllUserTrip.class.getSimpleName();



    public ApiViewAllUserTrip setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }



    @Override
    public String getURL() {
        return "view_all_user_trips/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user-token", userToken);
        return params;
    }

    public ArrayList<UserAllTripModel> getPostList(String response) {

        ArrayList<UserAllTripModel> tripModel = new ArrayList<>();

        if (Util.isStringValid(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
               // Log.i(TAG, "getAllPosts: " + jsonObject.optJSONArray("result"));
                JSONArray jsonArray = jsonObject.optJSONArray("result");

                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        UserAllTripModel model = UserAllTripModel.parseAllTrip(obj);
                        tripModel.add(model);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tripModel;

    }
}

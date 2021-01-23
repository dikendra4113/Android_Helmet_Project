package com.example.androidtranning.webservices.home_screen;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiGetUserFavLocation extends ApiCaller {

    private String userToken;



    public ApiGetUserFavLocation setUserToken(String userToken) {
        this.userToken = userToken;
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
        return params;
    }
}
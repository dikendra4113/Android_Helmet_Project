package com.example.androidtranning.webservices.home_screen;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiViewTripsUser extends ApiCaller {


    private String user_token;
    private int starting_after;
    private int limit;

    public ApiViewTripsUser setUserToken(String user_token) {
        this.user_token = user_token;
        return this;
    }

    public ApiViewTripsUser setStarting_after(int starting_after) {
        this.starting_after = starting_after;
        return this;
    }

    public ApiViewTripsUser setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public String getURL() {
        return "view_trips_user/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user-token", user_token);
        params.put("starting_after", starting_after);
        params.put("limit", limit);
        return params;
    }
}

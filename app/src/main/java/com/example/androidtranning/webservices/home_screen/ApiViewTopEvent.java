package com.example.androidtranning.webservices.home_screen;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiViewTopEvent  extends ApiCaller {

    private String userToken;
    private int starting_after;
    private int limit;


    public ApiViewTopEvent setStarting_after(int starting_after) {
        this.starting_after = starting_after;
        return this;
    }

    public ApiViewTopEvent setLimit(int limit) {
        this.limit = limit;
        return this;
    }


    public ApiViewTopEvent setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }



    @Override
    public String getURL() {
        return "view_top_events/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user-token", userToken);
        params.put("starting_after", starting_after);
        params.put("limit", limit);
        return params;
    }
}
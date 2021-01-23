package com.example.androidtranning.webservices.home_screen;

import com.example.androidtranning.webservices.user.ApiCheckUserExist;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiGetPendingFeedback extends ApiCaller {

    private String userToken;



    public ApiGetPendingFeedback setUserToken(String user_token) {
        this.userToken = user_token;
        return this;
    }



    @Override
    public String getURL() {
        return "get_pending_feedback/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user-token", userToken);
        return params;
    }
}
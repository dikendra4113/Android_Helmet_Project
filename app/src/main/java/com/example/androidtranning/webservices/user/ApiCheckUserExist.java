package com.example.androidtranning.webservices.user;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiCheckUserExist extends ApiCaller {

    private String email;
    private String residentID;


    public ApiCheckUserExist setEmail(String email) {
        this.email = email;
        return this;
    }



    @Override
    public String getURL() {
        return "check_user_exist/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        return params;
    }
}


package com.example.androidtranning.webservices.user;

import java.util.HashMap;

import utils.ApiCaller;

public class ApiAddUser extends ApiCaller {

    private String email;
    private String image_url;
    private String first_name;
    private String fcm_token;


    public ApiAddUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public ApiAddUser setImageUrl(String image_url) {
        this.image_url = image_url;
        return this;
    }

    public ApiAddUser setFirstName(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public ApiAddUser setFcmToken(String fcm_token) {
        this.fcm_token = fcm_token;
        return this;
    }



    @Override
    public String getURL() {
        return "add_user/";
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("first_name", first_name);
        params.put("fcm_token", fcm_token);
        params.put("image_url", image_url);
        return params;
    }
}

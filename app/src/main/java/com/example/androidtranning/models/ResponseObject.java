package com.example.androidtranning.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseObject {

    public String message;
    public boolean status;

    public static ResponseObject parseResponseObject(String response) {
        ResponseObject item = new ResponseObject();
        try {
            JSONObject resObj = new JSONObject(response);
            item.status = resObj.optBoolean("status");
            item.message = resObj.optString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }
}

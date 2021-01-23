package com.example.androidtranning.models.responses;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultString {
    public String result;

    public static ResultString parseResult(String response) {
        ResultString item = new ResultString();
        try {
            JSONObject jObj = new JSONObject(response);
            item.result = jObj.optString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }
}

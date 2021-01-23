package com.example.androidtranning.models.responses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultArray {

    public JSONArray result;

    public static ResultArray getResultArray(String response) {
        ResultArray resultArray = new ResultArray();

        try {
            JSONObject responseObj = new JSONObject(response);
            resultArray.result = responseObj.optJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultArray;
    }
}

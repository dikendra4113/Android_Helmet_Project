package com.example.androidtranning.models.responses;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultObj {

    public JSONObject result;


    public static ResultObj getResultObject(String response) {
        ResultObj resultObj = new ResultObj();
        try {
            JSONObject responseObj = new JSONObject(response);
            resultObj.result = responseObj.optJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultObj;
    }
}

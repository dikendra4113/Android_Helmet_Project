package com.example.androidtranning.models;

import android.util.Log;

import com.example.androidtranning.util.Util;

import org.json.JSONObject;

import io.realm.RealmObject;
import utils.DateUtils;

public class UserAllTripModel extends RealmObject {
    public  String tripName;
    public  String locationFrom;
    public  String locationTo;
    public  String distance;
    public  String date;
    public int riderAllow;
    public int participantCount;
    public int tripStatus;
    public int VIEW_TYPE;

    public static UserAllTripModel parseAllTrip(JSONObject obj) {
        UserAllTripModel model = new UserAllTripModel();
        try {
            model.VIEW_TYPE = 1;
            model.tripName = obj.optString("trip_name");
            model.distance = obj.optString("distance");
            String tempDate= obj.optString("start_date");
            model.date = DateUtils.getDDFromDate(tempDate) +" "+DateUtils.getMMMFromDate(tempDate);
            model.tripStatus = obj.optInt("trip_status");
            model.riderAllow = obj.optInt("riders_allowed");
            model.participantCount = obj.optInt("participant_count");
            JSONObject locFrom = obj.getJSONObject("from_location");
            if (locFrom != null) {
                model.locationFrom = locFrom.optString("location_name");
            }
            JSONObject locTo = obj.getJSONObject("to_location");
            if (locTo != null) {
                model.locationTo = locTo.optString("location_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;

    }
}

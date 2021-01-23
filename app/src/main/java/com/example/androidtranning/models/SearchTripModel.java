package com.example.androidtranning.models;

import org.json.JSONObject;

import io.realm.RealmObject;

public class SearchTripModel extends RealmObject {
    public  String tripName;
    public int tripStatus;
    public  String locationFrom;
    public  String locationTo;
    public  String distance;
    public int VIEW_TYPE;

    public static SearchTripModel parseAllTrip(JSONObject obj) {
        SearchTripModel model = new SearchTripModel();
        try {
            model.VIEW_TYPE = 1;
            model.tripName = obj.optString("trip_name");
            model.distance = obj.optString("distance");
            model.tripStatus = obj.optInt("trip_status");
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

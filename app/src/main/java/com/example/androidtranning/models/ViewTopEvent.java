package com.example.androidtranning.models;

import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewTopEvent {
    public String _id;
    public String event_name;
    public String description;
    public String start_date;
    public String end_date;
    public String event_state_name;
    public String location_name;
    public String type;
    public String priority;
    public String priority_start_date;
    public String priority_end_date;
    public String EventLatitude;
    public String EventLongitude;
    public String tickets_available;
    public String registration_link;
    public String pricing;
    public String organiser_id ;
    public String organiser_firstname;
    public String organiser_lastname;
    public String organiser_email;
    public String organiser_image_url;
    public String organiser_mobile_number;
    public String last_registration_date;
    public String images;
    public String facebook_url;
    public String instagram_url;
    public String twitter_url;
    public int total;
    public int totatdata;
    public  boolean approved;


    public ViewTopEvent parseTopEventResponse(String response){
        ViewTopEvent topEvents = new ViewTopEvent();
        ViewTopEvent topEvent = new ViewTopEvent();
         List<SlideModel> remoteImage = new ArrayList<>();
            try {
                JSONObject resObj = new JSONObject(response);
                JSONObject result = resObj.optJSONObject("result");
                boolean status = resObj.getBoolean("status");
                if(status){
                    topEvent.event_name = result.optString("event_name");
                    topEvent.images = result.optString("images");

                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  topEvents;
    }


}

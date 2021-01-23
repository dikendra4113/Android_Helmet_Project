package com.example.androidtranning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.codemybrainsout.ratingdialog.RatingDialog;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.androidtranning.adapter.UserAllTripAdapter;
import com.example.androidtranning.application.BaseActivity;
import com.example.androidtranning.helper.MyButtonClickListener;

import com.example.androidtranning.helper.SwipeHelper;
import com.example.androidtranning.models.SearchTripModel;
import com.example.androidtranning.models.UserAllTripModel;
import com.example.androidtranning.models.UserDetails;
import com.example.androidtranning.models.ViewTopEvent;
import com.example.androidtranning.util.Prevelent;
import com.example.androidtranning.webservices.home_screen.ApiGetPendingFeedback;
import com.example.androidtranning.webservices.home_screen.ApiGetUserFavLocation;
import com.example.androidtranning.webservices.home_screen.ApiSearchTrip;
import com.example.androidtranning.webservices.home_screen.ApiViewAllUserTrip;
import com.example.androidtranning.webservices.home_screen.ApiViewTopEvent;
import com.example.androidtranning.webservices.home_screen.ApiViewTripsUser;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.ObservableSource;
;
import io.realm.Realm;
import utils.ApiTaskDelegate;
import utils.Net;

public class HomeScreenActivity extends BaseActivity {

    private String userToken;
    private  String longtude;
    private String latitude;
    ImageSlider imageSlider;
    public RecyclerView mUCTripRecycler,mOGTripRecycler,mAllTripRecycler;
    public RecyclerView mTripsNearMeRecycler;
    public TextView noTripFound;
    public TextView mUPTitle,mOGTitle,mAllTitle,mTNMTitle;
    HashMap<String, String> headers;
    public UserAllTripAdapter upComingAdapter;
    public UserAllTripAdapter onGoingAdapter;
    public UserAllTripAdapter allTripAdapter;
    public ArrayList<UserAllTripModel> allTripItems;
    public ArrayList<UserAllTripModel> onGoingTripItems=null;
    public ArrayList<UserAllTripModel> upComingTripItems=null;
    public List<SlideModel> remoteImage = new ArrayList<>();
    public RatingDialog ratingDialog;
    public ShimmerFrameLayout shimmerFrameLayout;
    private Toolbar mToolbar;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected int getLayout() {
        return R.layout.activity_home_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_screen);
        Paper.init(this);
        userToken = Paper.book().read( Prevelent.USER_TOKEN);
        longtude =  Paper.book().read(Prevelent.PREF_LONGITUTE);
        latitude =   Paper.book().read(Prevelent.PREF_LATITUDE);


        headers = new HashMap<>();
        headers.put("user-token", userToken);
        Realm.init(this);
        imageSlider = (ImageSlider) findViewById(R.id.image_slider);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final List<SlideModel> remoteImage = new ArrayList<>();
        mUPTitle = findViewById(R.id.upComingTitle);
        mUCTripRecycler = findViewById(R.id.upComingRecycler);
        mUCTripRecycler.setHasFixedSize(true);

        mOGTitle = findViewById(R.id.vOngoingTitle);
        mOGTripRecycler = findViewById(R.id.onGoingRecycler);
        mOGTripRecycler.setHasFixedSize(true);

        mAllTitle = findViewById(R.id.vAllTripTitle);
        mAllTripRecycler = findViewById(R.id.vAllTripRecycler);
        mAllTripRecycler.setHasFixedSize(true);
        mAllTripRecycler.setLayoutManager(new LinearLayoutManager(this));

        mTNMTitle = findViewById(R.id.vTripsNearMeTitle);
        noTripFound = findViewById(R.id.notFoundText);
        mTripsNearMeRecycler = findViewById(R.id.vTripsNearMeRecycler);
        mTripsNearMeRecycler.setLayoutManager(new LinearLayoutManager(this));

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadInitialData();
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

    }

    private void loadInitialData() {


        ApiGetPendingFeedback apiGetPendingFeedback = new ApiGetPendingFeedback().setUserToken(userToken);
        ApiViewAllUserTrip apiViewAllUserTrip = new ApiViewAllUserTrip().setUserToken(userToken);
        ApiGetUserFavLocation getUserFavLocation = new ApiGetUserFavLocation().setUserToken(userToken);
        ApiViewTopEvent apiViewTopEvent = new ApiViewTopEvent().setUserToken(userToken).setLimit(5).setStarting_after(0);
        ApiViewTripsUser apiViewTripsUser = new ApiViewTripsUser().setUserToken(userToken).setLimit(5).setStarting_after(0);
        ApiSearchTrip apiSearchTrip = new ApiSearchTrip().setUserToken(userToken).setLongitude(Double.parseDouble(longtude )).setLatitude(Double.parseDouble(latitude));


        ObservableSource<String> pendingFeedbackObservable = apiGetPendingFeedback.getAPI().getReponse(apiGetPendingFeedback.getURL(), headers,apiGetPendingFeedback.getParams());
        ObservableSource<String> viewAllUserTripObservable = apiViewAllUserTrip.getAPI().getReponse(apiViewAllUserTrip.getURL(), headers, apiViewAllUserTrip.getParams());
        ObservableSource<String> getUserFavObserver = getUserFavLocation.getAPI().getReponse(getUserFavLocation.getURL(), headers, getUserFavLocation.getParams());
        ObservableSource<String> topEventObservable = apiViewTopEvent.getAPI().getReponse(apiViewTopEvent.getURL(), headers, apiViewTopEvent.getParams());
        ObservableSource<String> tripUserObserver = apiViewTripsUser.getAPI().getReponse(apiViewTripsUser.getURL(), headers, apiViewTripsUser.getParams());
        ObservableSource<String> searchObserver = apiSearchTrip.getAPI().getReponse(apiSearchTrip.getURL(), headers, apiSearchTrip.getParams());

        HashMap<String, ObservableSource<String>> requests = new HashMap<>();
        requests.put("GetPendingFeedback", pendingFeedbackObservable);
        requests.put("ViewAllUserTrip", viewAllUserTripObservable);
        requests.put("GetUserFavLoc", getUserFavObserver);
        requests.put("ViewTopEvent", topEventObservable);
        requests.put("ViewTripUser", tripUserObserver);
        requests.put("SearchTrip", searchObserver);

        Net.getInstance(this).setRequestZip(requests).doMakeApiCall(new ApiTaskDelegate() {
            @Override
            public void onTaskCompleted(HashMap<String, String> observables) {
                String pendingFeedbackResponse = observables.get("GetPendingFeedback");
                String viewAllUserResponse = observables.get("ViewAllUserTrip");
                String userFavLocResponse = observables.get("GetUserFavLoc");
                String topEventResponse = observables.get("ViewTopEvent");
                String viewUserTripResponse = observables.get("ViewTripUser");
                String searchTripResponse = observables.get("SearchTrip");
                ViewTopEvent topEvent = new ViewTopEvent();

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                runOnUiThread(() -> {
                            if(viewAllUserResponse != null){

                                upComingTripItems = new ArrayList<>();
                                onGoingTripItems= new ArrayList<>();
                                allTripItems = new ApiViewAllUserTrip().getPostList(viewAllUserResponse);
                                for(int i = 0 ; allTripItems.size() > i;i++){
                                    UserAllTripModel model = allTripItems.get(i);

                                    if(model.tripStatus == 1){
                                        upComingTripItems.add(model);

                                    }
                                    else {
                                        onGoingTripItems.add(model);
                                    }
                                }
                                if(upComingTripItems.size() >0) {
                                    mUPTitle.setVisibility(View.VISIBLE);
                                    upComingAdapter = new UserAllTripAdapter(HomeScreenActivity.this, upComingTripItems);
                                    mUCTripRecycler.setAdapter(upComingAdapter);
                                }else {
                                    mUCTripRecycler.setVisibility(View.GONE);
                                    mUPTitle.setVisibility(View.GONE);

                                }
                                if(onGoingTripItems.size() > 0 ){
                                    mOGTitle.setVisibility(View.VISIBLE);
                                    onGoingAdapter = new UserAllTripAdapter(HomeScreenActivity.this,onGoingTripItems);
                                    mOGTripRecycler.setAdapter(onGoingAdapter);
                                }else {
                                    mOGTripRecycler.setVisibility(View.GONE);
                                    mOGTitle.setVisibility(View.GONE);
                                }
                                if(allTripItems.size() > 0 ){

                                    mAllTitle.setVisibility(View.VISIBLE);
                                    allTripAdapter = new UserAllTripAdapter(HomeScreenActivity.this,allTripItems);
                                    mAllTripRecycler.setAdapter(allTripAdapter);
                                    makeSwipeableView(allTripItems,mAllTripRecycler);
                                }else {
                                    mOGTripRecycler.setVisibility(View.GONE);
                                    mOGTitle.setVisibility(View.GONE);
                                }

                            }

                            if(searchTripResponse != null){

                                try {
                                    JSONObject resObj = new JSONObject(searchTripResponse);
                                    JSONArray results = resObj.getJSONArray("result");

                                    if(results.length()>0){
                                        for (int i = 0; i < results.length(); i++) {
                                            JSONObject result = results.getJSONObject(i);

                                        }
                                    }else{
                                            mTNMTitle.setVisibility(View.VISIBLE);
                                            noTripFound.setVisibility(View.VISIBLE);
                                            mTripsNearMeRecycler.setVisibility(View.INVISIBLE);

                                    }



                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }


                            if (topEventResponse != null) {
                                try {
                                    JSONObject resObj = new JSONObject(topEventResponse);
                                    JSONArray results = resObj.getJSONArray("result");
                                    boolean status = resObj.getBoolean("status");
                                    // Log.i("asssssssa",results.toString());
                                    if (status) {
                                        for (int i = 0; i < results.length(); i++) {
                                            JSONObject result = results.getJSONObject(i);
                                            JSONArray images = result.getJSONArray("images");
                                            remoteImage.add(new SlideModel(images.getString(i), result.optString("event_name"), ScaleTypes.FIT));
                                            imageSlider.setImageList(remoteImage, ScaleTypes.FIT);
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            if(pendingFeedbackResponse != null)
                            {

                                try {
                                    JSONObject resObj = new JSONObject(pendingFeedbackResponse);
                                    JSONArray results = resObj.getJSONArray("result");
                                    if(results.length() > 0){
                                        for(int i =0;results.length() > i;i++){
                                            JSONObject resultobj = results.getJSONObject(i);
                                            String title = resultobj.optString("trip_name");
                                            showRatingForm(title);
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if(userFavLocResponse != null){
                                //write Code after update
                            }


                        }

                        );
                // Stopping swipe refresh
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onErrorOccured(String error) {
                // Stopping swipe refresh
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });




    }

    private void makeSwipeableView(ArrayList<UserAllTripModel> allTripItems, RecyclerView mAllTripRecycler) {
        SwipeHelper swipeHelper = new SwipeHelper(HomeScreenActivity.this,mAllTripRecycler,250) {
                                    @Override
                                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                                        return false;
                                    }

                                    @Override
                                    public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<SwipeHelper.MyButton> buffer) {
                                        int a = viewHolder.getAdapterPosition();
                                        UserAllTripModel model = allTripItems.get(a);
                                        buffer.add(new MyButton(HomeScreenActivity.this,
                                                "Start on "+model.date,30,0,
                                                Color.parseColor("#000000"),
                                                new MyButtonClickListener(){
                                                    @Override
                                                    public void onClick(int pos) {
                                                        Toast.makeText(HomeScreenActivity.this, "Date Click", Toast.LENGTH_SHORT).show();
                                                    }
                                                }));

                                        buffer.add(new MyButton(HomeScreenActivity.this,
                                                "Riders "+model.participantCount+"/"+model.riderAllow,30,0,
                                                Color.parseColor("#FF3C30"),
                                                new MyButtonClickListener(){
                                                    @Override
                                                    public void onClick(int pos) {
                                                        Toast.makeText(HomeScreenActivity.this, "Rider Click", Toast.LENGTH_SHORT).show();
                                                    }
                                                }));
                                    }
                                };
    }

    private void showRatingForm(String title) {
         ratingDialog = new RatingDialog.Builder(this)

//                 .session(7)
                .threshold(5)
                .title(title)
                .titleTextColor(R.color.black)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.white)
                .negativeButtonTextColor(R.color.white)
                .formTitle("Submit Feedback")
                .formHint("Tell us where we can improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .ratingBarColor(R.color.yellow)
                 .negativeButtonBackgroundColor(R.drawable.button_selector_negative)
                 .positiveButtonBackgroundColor(R.drawable.button_selector_positive)
                .playstoreUrl("YOUR_URL")
                .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                    @Override
                    public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                        //do something
                        Toast.makeText(HomeScreenActivity.this, rating+" Star", Toast.LENGTH_SHORT).show();
                        ratingDialog.dismiss();

                    }
                })
                .onThresholdFailed(new RatingDialog.Builder.RatingThresholdFailedListener() {
                    @Override
                    public void onThresholdFailed(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                        //do something
//                           ratingDialog.dismiss();
                    }
                })
                .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                    @Override
                    public void onRatingSelected(float rating, boolean thresholdCleared) {


                    }
                })
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    public void clear() {
//        items.clear();
//        notifyDataSetChanged();
//    }
//
//    // Add a list of items -- change to type used
//    public void addAll(List<Tweet> list) {
//        items.addAll(list);
//        notifyDataSetChanged();
//    }

    @Override
    protected void onStart() {

        super.onStart();
        loadInitialData();

    }

}
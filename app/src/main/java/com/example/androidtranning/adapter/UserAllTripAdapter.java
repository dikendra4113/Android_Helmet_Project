package com.example.androidtranning.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidtranning.R;
import com.example.androidtranning.models.UserAllTripModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class UserAllTripAdapter extends RecyclerView.Adapter<UserAllTripAdapter.AllTripHolder> {
    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
    private static final int VIEW_TYPE_OBJECT_VIEW = 1;
    private Context context;
    private ArrayList<UserAllTripModel> models;
    private String userID;


    public UserAllTripAdapter(Context context, ArrayList<UserAllTripModel> models) {
        this.context = context;
        this.models = models;
        this.userID = userID;
    }



    @NonNull
    @Override
    public AllTripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new AllTripHolder(LayoutInflater.from(context).inflate(R.layout.trip_info, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AllTripHolder holder, int position) {
        UserAllTripModel item = models.get(position);
        if (item != null && item.isValid()) {
            holder.mTripName.setText(item.tripName);
            holder.mLocationFrom.setText(item.locationFrom);
            holder.mLocationTo.setText(item.locationTo);
            holder.mDistance.setText(item.distance +" KM");
            if(item.tripStatus == 2){
                holder.mTitle.setText("OnGoing Trips");
            }
        }
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(models.)
//    }

    static class AllTripHolder extends RecyclerView.ViewHolder {

        private TextView mTripName;
        private TextView mLocationFrom;
        private TextView mDistance;
        private TextView mLocationTo;
        private ImageView mLogo,mBike;
        private TextView mTitle;
        private CardView mLlOptions;


        private AllTripHolder(@NonNull View itemView) {
            super(itemView);
            mTripName = itemView.findViewById(R.id.vTripName);
            mLocationFrom = itemView.findViewById(R.id.vCityFrom);
            mLocationTo = itemView.findViewById(R.id.vCityTo);
            mDistance = itemView.findViewById(R.id.vDistance);
            mTitle = itemView.findViewById(R.id.vTitle);

            mLlOptions = itemView.findViewById(R.id.trip_ll);
        }
    }


}

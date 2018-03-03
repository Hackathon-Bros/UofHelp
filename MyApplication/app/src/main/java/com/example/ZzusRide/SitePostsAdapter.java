package com.example.ZzusRide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by winto on 8/6/2017.
 */

public class SitePostsAdapter extends RecyclerView.Adapter<SitePostsAdapter.SitePostsAdapterViewHolder>
{
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataReference;
    // COMPLETED (23) Create a private string array called mWeatherData
    private String[] mPostsData;

    // COMPLETED (47) Create the default constructor (we will pass in parameters in a later lesson)
    public SitePostsAdapter() {
        mDatabase = FirebaseDatabase.getInstance();
        mDataReference = mDatabase.getReference().child("posts").child("WashingtonStateUniversity");

    }

    // COMPLETED (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // COMPLETED (17) Extend RecyclerView.ViewHolder
    /**
     * Cache of the children views for a forecast list item.
     */
    public class SitePostsAdapterViewHolder extends RecyclerView.ViewHolder {

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        // COMPLETED (18) Create a public final TextView variable called mWeatherTextView
        public final TextView mPostsView;

        // COMPLETED (19) Create a constructor for this class that accepts a View as a parameter
        // COMPLETED (20) Call super(view)
        // COMPLETED (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public SitePostsAdapterViewHolder(View view)
        {
            super(view);
            mPostsView = (TextView) view.findViewById(R.id.tv_post_data);
        }
        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
    }

    @Override
    public SitePostsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.post_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new SitePostsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SitePostsAdapterViewHolder postsAdapterViewHolder, int position) {
        String post = mPostsData[position];
        postsAdapterViewHolder.mPostsView.setText(post);
    }

    @Override
    public int getItemCount() {
        if (null == mPostsData) return 0;
        return mPostsData.length;
    }
}
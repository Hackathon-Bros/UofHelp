package com.example.ZzusRide;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    public static final int WASHINGTON_STATE_UNIVERSITY = 1;
    public static final int BOISE_STATE_UNIVERSITY = 2;

    private String mUsername, firstName, lastName;
    public static final int RC_SIGN_IN = 1;

    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private SitePostsAdapter mPostsAdapter;

    private ProgressBar mLoad;

    private FirebaseUser currentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseRecyclerAdapter<RidePost, PostViewHolder> firebaseRecyclerAdapter;

    public static class PostViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        ImageView postImage;
        TextView postName;
        TextView postDetails;

        public PostViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setPostDetails(String details) {
            postDetails = (TextView) mView.findViewById(R.id.post_details);
            postDetails.setText(details);
        }

        public void setPostName(String name)
        {
            postName = (TextView) mView.findViewById(R.id.tv_post_data);
            postName.setText(name);
        }

        public void setPostImage(Context ctx, String postURL) {
            postImage = (ImageView) mView.findViewById(R.id.personal_image);
            Picasso.with(ctx).load(postURL).into(postImage);
        }

        public void setLatitude(int lat)
        {

        }
        public void setLongitude(int longit)
        {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mDataReference = mDatabase.getReference().child("posts").child("WashingtonStateUniversity");
        mDataReference.keepSynced(true);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoad = (ProgressBar) findViewById(R.id.progressBar);

        mPostsAdapter = new SitePostsAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.site_posts);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mPostsAdapter);

        currentUser = mAuth.getCurrentUser();

        mUsername = currentUser.getDisplayName();

        Query personQuery = mDataReference.limitToLast(50);

        FirebaseRecyclerOptions<RidePost> postOptions = new FirebaseRecyclerOptions.Builder<RidePost>().setQuery(personQuery, RidePost.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RidePost, PostViewHolder>(postOptions)
        {

            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_list_item, parent, false);

                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull RidePost model) {
                holder.setPostName(model.getName());
                holder.setPostDetails(model.getText());
                holder.setPostImage(getApplicationContext(), model.getPhotoUrl());
                holder.setLongitude(model.getLONGITUDE());
                holder.setLatitude(model.getLATITUDE());
            }


            /*
            @Override
            protected void onBindViewHolder(PostViewHolder holder, int position, RidePost model) {
                holder.setPostName(model.getName());
                holder.setPostDetails(model.getText());
                holder.setPostImage(getApplicationContext(), model.getPhotoUrl());
                holder.setLongitude(model.getLONGITUDE());
                holder.setLatitude(model.getLATITUDE());

            }

            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_list_item, parent, false);

                return new PostViewHolder(view);
            }*/
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        showPostsView();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();

    }

    private void showPostsView()
    {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // COMPLETED (44) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // COMPLETED (44) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void startLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view)
    {
        LoginManager.getInstance().logOut();
        startLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                startLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();

            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Signed in cancelled!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void onSignedInInitialize(String username)
    {
        mUsername = username;
    }

    private void onSignedOutCleanup()
    {
        mUsername = "";

    }
}

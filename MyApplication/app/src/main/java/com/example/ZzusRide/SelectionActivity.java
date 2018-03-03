package com.example.ZzusRide;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by winto on 8/5/2017.
 */

public class SelectionActivity extends AppCompatActivity
{
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_selection);

        mDatabase = FirebaseDatabase.getInstance();
        mDataReference = mDatabase.getReference().child("schools_list");


    }
}

package com.example.cis_marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }
    private void populateData(){
        // get the object passed in from home activity & update UI using this information
    }
}
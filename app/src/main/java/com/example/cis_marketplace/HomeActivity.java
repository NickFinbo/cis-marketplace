package com.example.cis_marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cis_marketplace.Fleming.AddItemActivity;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    EditText searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchResult = findViewById(R.id.search);


    }
    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthActivity.class));
    }
    public void imageClicked(View v){
        ImageView image = (ImageView) findViewById(v.getId());
        // also need to find someway to get the object from firebase and intent put extra to show in itemActivity
        startActivity(new Intent(this, ItemActivity.class));
    }
    public void addItemClicked(View v){
        startActivity(new Intent(this, AddItemActivity.class));
    }
}
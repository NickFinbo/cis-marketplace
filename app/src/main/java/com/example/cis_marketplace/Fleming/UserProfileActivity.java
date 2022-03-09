package com.example.cis_marketplace.Fleming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cis_marketplace.AuthActivity;
import com.example.cis_marketplace.Fleming.AddItemActivity;
import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ArrayList<String> categories;

    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        category = findViewById(R.id.category);
        categories = new ArrayList<>();
        categories.add("Available");
        categories.add("Reserved");
        categories.add("Sold");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);
    }

    public void signOut(View v) {
        mAuth.signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    public void addListing(View v) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
        finish();
    }


}

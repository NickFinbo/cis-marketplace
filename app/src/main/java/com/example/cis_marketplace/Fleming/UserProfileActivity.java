package com.example.cis_marketplace.Fleming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cis_marketplace.AuthActivity;
import com.example.cis_marketplace.Fleming.AddItemActivity;
import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToAddItem(View v) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
        finish();
    }


}

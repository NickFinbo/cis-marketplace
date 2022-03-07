package com.example.cis_marketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    EditText searchResult;
    RecyclerView categoryView;
    ArrayList<String> subjects = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchResult = findViewById(R.id.search);

        categoryView = findViewById(R.id.categoryView);
        subjects.add("Mathemajic");
        categoryView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, categoryView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Note position is index
                        System.out.println("SUS!!!!");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Don't Long Press
                        System.out.println("SUS!!!!");
                    }

                })
        );

        categoryView.setLayoutManager(new LinearLayoutManager(this));




    }
    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthActivity.class));
    }
    public void imageClicked(View v){
        ImageView image = (ImageView) findViewById(v.getId());
        // also need to find someway to get the object from firebase and intent put extra to show in itemActivity
        startActivity(new Intent(this, ItemProfileActivity.class));
    }
    public void addItemClicked(View v){
        startActivity(new Intent(this, AddItemActivity.class));
    }

}
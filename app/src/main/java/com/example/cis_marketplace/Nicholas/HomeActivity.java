package com.example.cis_marketplace.Nicholas;

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

import com.example.cis_marketplace.Fleming.UserProfileActivity;
import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.Marco.AuthActivity;
import com.example.cis_marketplace.Fleming.AddItemActivity;
import com.example.cis_marketplace.Lucas.ItemProfileActivity;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


public class HomeActivity extends AppCompatActivity {
    EditText searchResult;
    RecyclerView categoryView;
    RecyclerView suggestedView;
    ArrayList<String> subjects = new ArrayList<>();
    ArrayList<Listing> suggestedItems = new ArrayList<>();
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchResult = findViewById(R.id.search);
        categoryView = findViewById(R.id.categoryView);
        categoryView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, categoryView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Note position is index
                        marketClicked();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Don't Long Press
                        marketClicked();


                    }

                })
        );
        Collections.addAll(subjects, "Math AI", "Math AI", "English L&L", "English Lit", "Economics","Computer Science","Geography","History","Psychology","Physics","Biology","Chemistry","Chinese A L&L","Chinese A Lit","Chinese B");

        SubjectRecyclerAdapter adapter = new SubjectRecyclerAdapter(subjects);
        categoryView.setAdapter(adapter);
        categoryView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));







        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getEmail();

        suggestedView = findViewById(R.id.recommendView);
        suggestedView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, suggestedView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Note position is index
                        //marketClicked(position,suggestedItems);
                        marketClicked();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Don't Long Press
                        marketClicked();

                    }

                })
        );




        db.collection("listings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Listing listing = document.toObject(Listing.class);
                                    suggestedItems.add(listing);

                            }

                            RecommendationRecyclerAdapter adapter = new RecommendationRecyclerAdapter(suggestedItems);
                            suggestedView.setAdapter(adapter);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }


                    }

                });

        suggestedView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));













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
    public void subjectClicked(View v,String itemClickedType){//for example Math AA...
        ImageView image = (ImageView) findViewById(v.getId());
        // also need to find someway to get the object from firebase and intent put extra to show in itemActivity
        startActivity(new Intent(this, ItemProfileActivity.class));
    }
    public void addItemClicked(View v){
        startActivity(new Intent(this, AddItemActivity.class));
    }
    public void marketClicked(){
        startActivity(new Intent(this, MarketActivity.class));
    }



    public void recyclerClickedUI(int position, ArrayList<String> subjectItems){

        //Send All market item data


        //Intent intent = new Intent(this, ItemsVies.class);
        //intent.putExtra("items", items);

        //startActivity(intent);*/
    }

    public void goToProfile(View v) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

}
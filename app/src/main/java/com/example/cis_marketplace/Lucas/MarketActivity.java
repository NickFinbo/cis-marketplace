package com.example.cis_marketplace.Lucas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.Marco.SpacingItemDecorator;
import com.example.cis_marketplace.Nicholas.HomeActivity;
import com.example.cis_marketplace.Nicholas.RecommendationRecyclerAdapter;
import com.example.cis_marketplace.Nicholas.RecyclerItemClickListener;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity {
    RecyclerView marketView;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ArrayList<Listing> marketItems = new ArrayList<>();
    Button constrainButton;
    TextView constrainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        marketView = findViewById(R.id.marketView);
        db = FirebaseFirestore.getInstance();
        constrainButton = findViewById(R.id.constrainButton);
        constrainText = findViewById(R.id.constrainText);
        System.out.println(constrainButton.getText());

        marketView = findViewById(R.id.marketView);
        marketView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, marketView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Note position is index
                        //marketClicked(position,suggestedItems);
                        Intent intent = new Intent(MarketActivity.this, ItemProfileActivity.class);
                        intent.putExtra("Listing", (Serializable) marketItems.get(position));
                        MarketActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //Don't Long Press
                        Intent intent = new Intent(MarketActivity.this, ItemProfileActivity.class);
                        intent.putExtra("Listing", (Serializable) marketItems.get(position));
                        MarketActivity.this.startActivity(intent);
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
                                marketItems.add(listing);

                            }
                            SpacingItemDecorator itemDecorator = new SpacingItemDecorator(50);
                            marketView.addItemDecoration(itemDecorator);
                            RecommendationRecyclerAdapter adapter = new RecommendationRecyclerAdapter(marketItems);
                            marketView.setAdapter(adapter);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }


                    }

                });

        marketView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


    public void constrainClicked(View V) {
        marketItems.clear();
        db.collection("listings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Listing listing = document.toObject(Listing.class);
                                System.out.println(constrainButton.getText());
                                System.out.println(listing.getName());
                                if (listing.getName().contains(constrainText.getText())) {
                                    //Toast.makeText(getApplicationContext(), listing.getName(), Toast.LENGTH_SHORT).show();
                                    marketItems.add(listing);
                                }
                            }
                            SpacingItemDecorator itemDecorator = new SpacingItemDecorator(50);
                            marketView.addItemDecoration(itemDecorator);
                            RecommendationRecyclerAdapter adapter = new RecommendationRecyclerAdapter(marketItems);
                            marketView.setAdapter(adapter);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }


                    }

                });

        marketView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}
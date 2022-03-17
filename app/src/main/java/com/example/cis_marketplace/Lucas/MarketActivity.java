package com.example.cis_marketplace.Lucas;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cis_marketplace.Nicholas.HomeActivity;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity implements MarketAdapter.listingListener{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public FirebaseFirestore firebase;
    RecyclerView marketRec;
    public static Listing listing;
    private MarketAdapter myAdapter;
    private ArrayList<Listing> listingsList;
    private ArrayList<String> namdata;
    private ArrayList<String> catdata;
    private ArrayList<String> yeadata;
    private ArrayList<String> condata;
    private ArrayList<String> pridata;
    private EditText searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        marketRec = findViewById(R.id.marketRec);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        listingsList = new ArrayList<>();
        user = mAuth.getCurrentUser();


        namdata = new ArrayList();
        catdata = new ArrayList();
        yeadata = new ArrayList();
        condata = new ArrayList();
        pridata = new ArrayList();
        searchKey = findViewById(R.id.searchBar);

        myAdapter = new MarketAdapter(namdata, catdata, yeadata, condata, pridata, this);
        marketRec.setAdapter(myAdapter);
        marketRec.setLayoutManager(new LinearLayoutManager(this));
        getAndPopulateData();
    }

    public void getAndPopulateData() {
        if (!searchKey.getText().toString().isEmpty())
        {
            firebase.collection("listings")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                            Listing ls = ds.toObject(Listing.class);
                            listingsList.add(ls);
                        }

                        for (Listing eachListing: listingsList) {
                            String eachItem = eachListing.getName();
                            namdata.add(eachItem);

                            String eachCategory = eachListing.getType();
                            catdata.add(eachCategory);

                            String eachYear = eachListing.getYearLevel().toString();
                            yeadata.add(eachYear);

                            String eachCondition = eachListing.getCondition();
                            condata.add(eachCondition);

                            String eachList = eachListing.getPrice().toString();
                            pridata.add(eachList);
                        }



                    } else {
                        Toast.makeText(getApplicationContext(), "you don't have anythings yet", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    public void getAndPopulateDataAfterSearch(){
        if (searchKey.getText().toString().isEmpty())
        {
            firebase.collection("listings")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                            Listing ls = ds.toObject(Listing.class);
                            if (ls.getName().toLowerCase() == searchKey.getText().toString().toLowerCase() ||
                                    ls.getType().toLowerCase() == searchKey.getText().toString().toLowerCase() ||
                                    ls.getYearLevel().toString() == searchKey.getText().toString().toLowerCase() ||
                                    ls.getCondition().toLowerCase() == searchKey.getText().toString().toLowerCase() ||
                                    ls.getPrice().toString() == searchKey.getText().toString().toLowerCase())
                            {
                                listingsList.add(ls);
                            }
                        }

                        for (Listing eachListing: listingsList) {
                            String eachItem = eachListing.getName();
                            namdata.add(eachItem);

                            String eachCategory = eachListing.getType();
                            catdata.add(eachCategory);

                            String eachYear = eachListing.getYearLevel().toString();
                            yeadata.add(eachYear);

                            String eachCondition = eachListing.getCondition();
                            condata.add(eachCondition);

                            String eachList = eachListing.getPrice().toString();
                            pridata.add(eachList);
                        }



                    } else {
                        Toast.makeText(getApplicationContext(), "you don't have anythings yet", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    public void listingOnClick(int p) {
        listing = listingsList.get(p);
        startActivity(new Intent(this, ItemProfileActivity.class));

    }

    public void search(View v)
    {
        getAndPopulateDataAfterSearch();
    }



    public void goToHome(View v)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }
}

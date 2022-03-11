package com.example.cis_marketplace.Fleming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.Marco.AuthActivity;
import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ArrayList<String> categories;
    private ArrayList<Listing> lists;
    FirebaseFirestore db;
    RecyclerView rec;
    Adapter adap;
    int amount = 0;

    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        category = findViewById(R.id.category);
        rec = findViewById(R.id.rec);
        lists = new ArrayList<>();
        categories = new ArrayList<>();
        categories.add("Available");
        categories.add("Reserved");
        categories.add("Sold");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        db.collection("listings").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
            for (QueryDocumentSnapshot ds : Objects.requireNonNull(task.getResult())) {
                Listing bob = ds.toObject(Listing.class);
                lists.add(bob);
                amount++;

            }
            adap = new Adapter(lists, amount);
            rec.setLayoutManager(new LinearLayoutManager(this));
         //   adap.setClickListener(this);
            rec.setAdapter(adap);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rec.getContext(), LinearLayoutManager.HORIZONTAL);
            rec.addItemDecoration(dividerItemDecoration);


        }
    });

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

package com.example.cis_marketplace.Fleming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cis_marketplace.Lucas.ItemProfileActivity;
import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.Marco.AuthActivity;
import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    private FirebaseAuth mAuth;
    private ArrayList<String> categories;
    private ArrayList<Listing> lists;
    private ArrayList<Listing> available;
    private ArrayList<Listing> sold;
    private ArrayList<Listing> reserved;

    FirebaseFirestore db;
    RecyclerView rec;
    Adapter adap;

    int avail = 0;
    int sol = 0;
    int res = 0;
    TextView username;

    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        category = findViewById(R.id.category);
        username = findViewById(R.id.username);
        username.setText(mAuth.getCurrentUser().getDisplayName());
        rec = findViewById(R.id.rec);

        lists = new ArrayList<>();
        available = new ArrayList<>();
        sold = new ArrayList<>();
        reserved = new ArrayList<>();

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
            }
        }
    });
        for(int i = 0;i<lists.size();i++) {
            if(lists.get(i).getState().equals("Available")) {
                available.add(lists.get(i));
                avail++;
            }
            else if(lists.get(i).getState().equals("Sold")) {
                sold.add(lists.get(i));
                sol++;
            }
            else if(lists.get(i).getState().equals("Reserved")) {
                reserved.add(lists.get(i));
                res++;
            }
        }
        UserProfileActivity c = this;
        rec.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rec.getContext(), LinearLayoutManager.HORIZONTAL);
        rec.addItemDecoration(dividerItemDecoration);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(category.getSelectedItem().toString().equals("Available")) {
                    adap = new Adapter(available, avail);
                    adap.setClickListener(c);
                    rec.setAdapter(adap);
                }
                else if(category.getSelectedItem().toString().equals("Reserved")) {
                    adap = new Adapter(reserved, res);
                    adap.setClickListener(c);
                    rec.setAdapter(adap);
                }
                else if(category.getSelectedItem().toString().equals("Sold")) {
                    adap = new Adapter(sold, sol);
                    adap.setClickListener(c);
                    rec.setAdapter(adap);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

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


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), ItemProfileActivity.class);
        intent.putExtra("listing", (Serializable) lists.get(position));
        view.getContext().startActivity(intent);
}
}

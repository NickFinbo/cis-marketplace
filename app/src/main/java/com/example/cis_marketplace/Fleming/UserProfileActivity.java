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
    int amount = 0;
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
                amount++;

            }
        }
    });
        for(int i = 0;i<lists.size();i++) {
            if(lists.get(i).getState().equals("Available")) {
                available.add(lists.get(i));
            }
            else if(lists.get(i).getState().equals("Sold")) {
                sold.add(lists.get(i));
            }
            else if(lists.get(i).getState().equals("Reserved")) {
                reserved.add(lists.get(i));
            }
        }
        adap = new Adapter(lists, amount);
        rec.setLayoutManager(new LinearLayoutManager(this));
        adap.setClickListener(this);
        rec.setAdapter(adap);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rec.getContext(), LinearLayoutManager.HORIZONTAL);
        rec.addItemDecoration(dividerItemDecoration);

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

package com.example.cis_marketplace.Fleming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cis_marketplace.Lucas.ItemProfileActivity;
import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.Marco.AuthActivity;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    UserProfileActivity c = this;
    private FirebaseStorage st;
    private StorageReference re;
    FirebaseFirestore db;
    RecyclerView rec;
    Adapter adap;
    ImageView im;

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
        im = findViewById(R.id.prof);

        lists = new ArrayList<>();
        available = new ArrayList<>();
        sold = new ArrayList<>();
        reserved = new ArrayList<>();

        categories = new ArrayList<>();
        categories.add("Available");
        categories.add("Reserved");
        categories.add("Sold");

        st = FirebaseStorage.getInstance();
        re = st.getReference();

        StorageReference photoRef = re.child(mAuth.getCurrentUser().getUid());
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
        photoRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                im.setImageBitmap(bm);
                im.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        db.collection("listings").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
            for (QueryDocumentSnapshot ds : Objects.requireNonNull(task.getResult())) {
                Listing bob = ds.toObject(Listing.class);
                if(bob.getOwnerID() !=null && bob.getOwnerID().equals(mAuth.getCurrentUser().getUid())) {
                    lists.add(bob);
                }

            }
            setUp();

        }
    });

}

    public void setUp() {
        for(int i = 0;i<lists.size();i++) {
            if(lists.get(i).getState().equals("available")) {
                available.add(lists.get(i));
                avail++;
                System.out.println(lists.get(i).getState());
            }
            else if(lists.get(i).getState().equals("sold")) {
                sold.add(lists.get(i));
                sol++;
            }
            else if(lists.get(i).getState().equals("reserved")) {
                reserved.add(lists.get(i));
                res++;
            }

        }

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
                adap = new Adapter(available, avail);
                adap.setClickListener(c);
                rec.setAdapter(adap);
            }
        });
        rec.setLayoutManager(new LinearLayoutManager(this));

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
        intent.putExtra("Listing", (Serializable) lists.get(position));
        System.out.println(lists.get(position).getName());
        view.getContext().startActivity(intent);
}
}

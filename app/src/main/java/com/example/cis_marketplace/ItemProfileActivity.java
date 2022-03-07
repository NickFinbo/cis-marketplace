package com.example.cis_marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;

public class ItemProfileActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView des;
    TextView subject;
    ImageView photoOfObject;


    Listing chosen;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);
        name = findViewById(R.id.Name);
        price = findViewById(R.id.Price);
        des = findViewById(R.id.Description);
        subject = findViewById(R.id.Subject);
        photoOfObject = findViewById(R.id.photo);

        mAuth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();

        String nameText = chosen.getName();
        String priceText = chosen.getPrice().toString();
        String desText = chosen.getDescription();
        String subjectText = chosen.getSubject();
        String objectPhoto = chosen.getImagePath();

        name.setText(nameText);
        price.setText(priceText);
        des.setText(desText);
        subject.setText(subjectText);
        photoOfObject.setImageURI(objectPhoto);
    }


}
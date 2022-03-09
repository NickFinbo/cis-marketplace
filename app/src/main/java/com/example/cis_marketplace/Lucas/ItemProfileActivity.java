package com.example.cis_marketplace.Lucas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        String priceText = chosen.getPrice()+"";
        String desText = chosen.getDescription();
        String subjectText = chosen.getSubject();
        //Uri objectPhoto = chosen.getImagePath();

        name.setText(nameText);
        price.setText(priceText);
        des.setText(desText);
        subject.setText(subjectText);
        //photoOfObject.setImageURI(objectPhoto);
    }


}
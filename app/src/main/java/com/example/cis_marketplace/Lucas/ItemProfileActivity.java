package com.example.cis_marketplace.Lucas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class ItemProfileActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView des;
    TextView subject;
    TextView itemName;
    ImageView photoOfObject;


    Listing chosen;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fb;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    Listing listing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.cis_marketplace.R.layout.activity_item_profile);
        name = findViewById(R.id.Name);
        price = findViewById(R.id.Price);
        des = findViewById(R.id.Description);
        subject = findViewById(R.id.Subject);
        photoOfObject = findViewById(R.id.photo);
        itemName = findViewById(R.id.itemNameEditText);

        listing = (Listing) getIntent().getSerializableExtra("Listing");

        mAuth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        String nameText = listing.getName();
        String priceText = listing.getPrice().toString();
        String desText = listing.getDescription();
        String subjectText = listing.getSubject();

        itemName.setText(nameText);
        name.setText(nameText);
        price.setText(priceText);
        des.setText(desText);
        subject.setText(subjectText);
        showImage();

    }
    private void showImage(){
        StorageReference photoRef = storageRef.child(listing.getID());
        final long ONE_MEGABYTE = 1024 * 1024;
        photoRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                photoOfObject.setImageBitmap(bmp);
                photoOfObject.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });
    }


}
package com.example.cis_marketplace.Lucas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button reserveButton;


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
        photoOfObject = findViewById(R.id.prof);
        reserveButton = findViewById(R.id.reserveButton);

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

        name.setText(nameText);
        price.setText("$"+priceText);
        des.setText(desText);
        subject.setText(subjectText);
        showImage();

        

    }
    private void showImage(){
        StorageReference photoRef = storageRef.child(listing.getID());
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
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
    public void reservedClicked(View v){
        if(listing.getState().equals("available")){
            fb.collection("listings").document(listing.getID()).update("state","reserved", "buyerID",mAuth.getUid());
            reserveButton.setBackgroundColor(Color.GRAY);
            Toast.makeText(this,"Successfully Reserved", Toast.LENGTH_LONG).show();
        }
        if(listing.getState().equals("reserved")){
            reserveButton.setBackgroundColor(Color.GRAY);
            reserveButton.setText("Reserved");
            Toast.makeText(this,"This item is reserved by you or someone else", Toast.LENGTH_LONG).show();
        }
    }
}
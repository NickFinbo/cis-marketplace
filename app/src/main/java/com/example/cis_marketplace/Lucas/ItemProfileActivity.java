package com.example.cis_marketplace.Lucas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

public class ItemProfileActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView des;
    TextView subject;
    TextView itemName;
    ImageView photoOfObject;
    private String UUID;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fb;

    Listing chosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.cis_marketplace.R.layout.activity_item_profile);
        name = findViewById(R.id.Name);
        price = findViewById(R.id.Price);
        des = findViewById(R.id.Description);
        subject = findViewById(R.id.Subject);
        photoOfObject = findViewById(R.id.photo);
        itemName = findViewById(R.id.itemName);

        chosen = MarketActivity.listing;

        mAuth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        String nameText = chosen.getName();
        String priceText = chosen.getPrice().toString();
        String desText = chosen.getDescription();
        String subjectText = chosen.getSubject();


        name.setText(nameText);
        itemName.setText(nameText);
        price.setText(priceText);
        des.setText(desText);
        subject.setText(subjectText);
        fb.collection("listings").whereEqualTo("id", chosen.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                        Listing ls = ds.toObject(Listing.class);
                        UUID = ls.getId();
                    }
                }
            }
        });

        StorageReference photoRef = storageRef.child(UUID);
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
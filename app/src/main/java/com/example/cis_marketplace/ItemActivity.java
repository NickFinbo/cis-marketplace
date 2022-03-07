package com.example.cis_marketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ItemActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView des;
    TextView subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        name = findViewById(R.id.Name);
        price = findViewById(R.id.Price);
        des = findViewById(R.id.Description);
        subject = findViewById(R.id.Subject);
    }

}
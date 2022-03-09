package com.example.cis_marketplace.Fleming;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddItemActivity extends AppCompatActivity {

    TextView name;
    TextView condition;
    TextView description;
    TextView price;
    TextView image;
    TextView yearLevel;
    TextView type;


    EditText namee;
    Spinner conditionn;
    EditText descriptionn;
    EditText pricee;
    Spinner yearLevell;
    Spinner typee;

    Spinner vehic;


    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mAuth = FirebaseAuth.getInstance();

        //    name = findViewById(R.id.name);
        condition = findViewById(R.id.condition);
        //    price = findViewById(R.id.price);
        pricee = findViewById(R.id.pricee);
        conditionn = findViewById(R.id.conditionn);
        namee = findViewById(R.id.namee);
        description = findViewById(R.id.description);
        descriptionn = findViewById(R.id.descriptionEditText);
        yearLevel = findViewById(R.id.yearLevel);
        yearLevell = findViewById(R.id.yearLevelSpinner);

        db = FirebaseFirestore.getInstance();


//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modelss);
//        vehic.setAdapter(adapter);

    }

    public void addItem(View v) {

    }

    public void upload(View v) {

    }


    public boolean isValid() {
        return namee != null && pricee != null && descriptionn != null;
    }


}

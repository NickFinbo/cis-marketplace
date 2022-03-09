package com.example.cis_marketplace.Fleming;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cis_marketplace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    private ArrayList<String> conditions;
    private ArrayList<String> yearLevels;
    private ArrayList<String> types;

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

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mAuth = FirebaseAuth.getInstance();

        conditions = new ArrayList<>();
        yearLevels = new ArrayList<>();
        types = new ArrayList<>();

        conditions.add("New");
        conditions.add("Very Good");
        conditions.add("Good");
        conditions.add("Acceptable");

        for(int i = 7;i<=13;i++) {
            yearLevels.add("Y"+i);
        }

        types.add("Textbook");
        types.add("Notes");
        types.add("Subscription");
        types.add("Miscellaneous");

        condition = findViewById(R.id.condition);
        pricee = findViewById(R.id.priceEditText);
        conditionn = findViewById(R.id.conditionSpinner);
        namee = findViewById(R.id.nameEditText);
        description = findViewById(R.id.description);
        descriptionn = findViewById(R.id.descriptionEditText);
        yearLevel = findViewById(R.id.yearLevel);
        yearLevell = findViewById(R.id.yearLevelSpinner);
        type = findViewById(R.id.type);
        typee= findViewById(R.id.typeSpinner);

        db = FirebaseFirestore.getInstance();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, conditions);
        conditionn.setAdapter(adapter);
        ArrayAdapter<String> adapte = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, yearLevels);
        yearLevell.setAdapter(adapte);
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        typee.setAdapter(adapt);

    }

    public void addItem(View v) {

    }

    public void upload(View v) {

    }


    public boolean isValid() {
        return namee != null && pricee != null && descriptionn != null;
    }


}

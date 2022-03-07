package com.example.cis_marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CompleteSignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private EditText phoneNumberField;
    private String yearLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up);
//        mAuth = FirebaseAuth.getInstance();
//        fireStore = FirebaseFirestore.getInstance();
        phoneNumberField = findViewById(R.id.UserPhoneNumber);
        Spinner spinner = findViewById(R.id.YearLevelSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.YearLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        findViewById(R.id.ProfilePictureImageView).setVisibility(View.INVISIBLE);
        findViewById(R.id.FinishSignUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishSignUp();
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        yearLevel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void finishSignUp() {
        startActivity(new Intent(this, HomeActivity.class));
    }

}
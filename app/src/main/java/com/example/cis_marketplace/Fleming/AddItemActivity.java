package com.example.cis_marketplace.Fleming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.Nicholas.HomeActivity;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {

    private ArrayList<String> conditions;
    private ArrayList<String> yearLevels;
    private ArrayList<String> types;
    private ArrayList<String> bob;
    private ArrayList<String> subjects;
    private ArrayList<String> IBsubjects;

    TextView subjectt;

    TextView name;
    TextView condition;
    TextView description;
    TextView price;
    TextView image;
    TextView yearLevel;
    TextView type;
//SUBJECT???
    Spinner subject;
    EditText namee;
    Spinner conditionn;
    EditText descriptionn;
    EditText pricee;
    Spinner yearLevell;
    Spinner typee;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageTask mUploadTask;
    private Button uploadButton;

    String UUIDref = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        conditions = new ArrayList<>();
        yearLevels = new ArrayList<String>();
        types = new ArrayList<>();
        bob = new ArrayList<>();
        subjects = new ArrayList<>();
        IBsubjects = new ArrayList<>();
        bob.add("Please select year level");

        conditions.add("New");
        conditions.add("Very Good");
        conditions.add("Good");
        conditions.add("Acceptable");

        for(int i = 7;i<=13;i++) {
            yearLevels.add(Integer.toString(i));
        }

        types.add("Textbook");
        types.add("Notes");
        types.add("Subscription");
        types.add("Miscellaneous");

        subjects.add("English");
        subjects.add("Chinese");
        subjects.add("InSo");
        subjects.add("Math");
        subjects.add("Science");
        subjects.add("Spanish");
        subjects.add("French");

        IBsubjects.add("Biology");
        IBsubjects.add("Chemistry");
        IBsubjects.add("Chinese A Lit L&L");
        IBsubjects.add("Chinese A Lit");
        IBsubjects.add("Chinese B");
        IBsubjects.add("Computer Science");
        IBsubjects.add("Economics");
        IBsubjects.add("English L&L");
        IBsubjects.add("English Lit");
        IBsubjects.add("Geography");
        IBsubjects.add("History");
        IBsubjects.add("Math AA");
        IBsubjects.add("Math AI");
        IBsubjects.add("Physics");
        IBsubjects.add("Psychology");


        condition = findViewById(R.id.condition);
        subjectt = findViewById(R.id.subjectt);
        subject = findViewById(R.id.subject);
        pricee = findViewById(R.id.priceEditText);
        conditionn = findViewById(R.id.conditionSpinner);
        namee = findViewById(R.id.nameEditText);
        description = findViewById(R.id.description);
        descriptionn = findViewById(R.id.descriptionEditText);
        yearLevel = findViewById(R.id.yearLevel);
        yearLevell = findViewById(R.id.yearLevelSpinner);
        type = findViewById(R.id.type);
        typee= findViewById(R.id.typeSpinner);
        uploadButton = findViewById(R.id.uploadButton);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, conditions);
        conditionn.setAdapter(adapter);
        ArrayAdapter<String> adapte = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, yearLevels);
        yearLevell.setAdapter(adapte);
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        typee.setAdapter(adapt);
        ArrayAdapter<String> ada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, IBsubjects);

        yearLevell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==5 || position==6 ) {
                    subject.setAdapter(ad);
                }
                else {
                    subject.setAdapter(ada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        typee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!typee.getSelectedItem().toString().equals("Textbook") && !typee.getSelectedItem().toString().equals("Notes") ) {
                    subject.setVisibility(View.INVISIBLE);
                    yearLevell.setVisibility(View.INVISIBLE);
                    subjectt.setVisibility(View.INVISIBLE);
                    yearLevel.setVisibility(View.INVISIBLE);


                }
//                else {
//                    subject.setVisibility(View.VISIBLE);
//                    yearLevell.setVisibility(View.VISIBLE);
//                    subjectt.setVisibility(View.VISIBLE);
//                    yearLevel.setVisibility(View.VISIBLE);
//
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

    }

    public void addItem(View v) {

        String conditio = conditionn.getSelectedItem().toString();
        String yearleve = yearLevell.getSelectedItem().toString();
        String typ = typee.getSelectedItem().toString();
        String subjec = subject.getSelectedItem().toString();

        if (!typee.getSelectedItem().toString().equals("Textbook") && !typee.getSelectedItem().toString().equals("Notes") ) {
            subjec = null;
            yearleve = null;
            conditio = null;
        }

        String nam = namee.getText().toString();
        String desc = descriptionn.getText().toString();
        String pric = pricee.getText().toString();

        if(isValid()) {

                Listing listing = new Listing(conditio, desc, UUIDref, nam, mUser.getUid(), Double.parseDouble(pric), "available", subjec, typ, Integer.parseInt(yearleve));
                db.collection("listings").document(listing.getID()).set(listing);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
            }
        else {
            Toast.makeText(getApplicationContext(), "Fields incomplete", Toast.LENGTH_SHORT).show();
        }
    }

    public void upload(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getData()!=null) {
            mImageUri = data.getData();
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        if (mImageUri != null) {
            StorageReference fileReference = storageRef.child(UUIDref); // change this to user UUID later on
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                            uploadButton.setVisibility(View.INVISIBLE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Progress " + (int) progressPercent + " %");
                            if (progressPercent == 100.00) {
                                pd.dismiss();
                            }
                        }
                    });
        }
        TextView imageEditText = findViewById(R.id.addImageEditText);
        imageEditText.setText("Image Uploaded");
    }
    public void homeUI(View v){
        startActivity(new Intent(this, HomeActivity.class));
    }


    public boolean isValid() {
        return namee != null && pricee != null && descriptionn != null && mImageUri != null;
    }


}

package com.example.cis_marketplace.Fleming;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cis_marketplace.Marco.AuthActivity;
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
//SUBJECT???
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
    Long fileName;

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

    }

    public void addItem(View v) {
            //TODO
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
            StorageReference fileReference = storageRef.child("user uuid here"); // change this to user UUID later on
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
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void homeUI(View v){
        startActivity(new Intent(this, HomeActivity.class));
    }


    public boolean isValid() {
        return namee != null && pricee != null && descriptionn != null;
    }


}

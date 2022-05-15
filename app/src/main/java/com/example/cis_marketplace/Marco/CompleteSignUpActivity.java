package com.example.cis_marketplace.Marco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cis_marketplace.Lucas.User;
import com.example.cis_marketplace.Nicholas.HomeActivity;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class CompleteSignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private EditText phoneNumberField;
    private String name;
    private EditText nameEditText;
    private String phoneNumber;
    private Spinner yearLevelSpinner;
    private String yearLevel;
    private ImageView imageView;
    private CheckBox chinese, english, math, inso, biology, chemistry, physics, music, drama, film, visualArts, productDesign, computerScience, TOK;
    private String UUID;
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageTask mUploadTask;
    private Button uploadButton;
    ArrayList<String> subjects = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        phoneNumberField = findViewById(R.id.UserPhoneNumber);
        yearLevelSpinner = findViewById(R.id.YearLevelSpinner);
        uploadButton = findViewById(R.id.ImportImagebutton);
        nameEditText = findViewById(R.id.userName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.YearLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearLevelSpinner.setAdapter(adapter);
        yearLevelSpinner.setOnItemSelectedListener(this);
        imageView = findViewById(R.id.ProfilePictureImageView);
        imageView.setVisibility(View.INVISIBLE);
        findViewById(R.id.FinishSignUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishSignUp();
            }
        });

//      subject checkboxes
        chinese = findViewById(R.id.ChineseCheckBox);
        english = findViewById(R.id.EnglishCheckBox);
        math = findViewById(R.id.MathCheckBox);
        inso = findViewById(R.id.InsoCheckBox);
        biology = findViewById(R.id.BiologyCheckBox);
        chemistry = findViewById(R.id.ChemistryCheckBox);
        physics = findViewById(R.id.PhysicsCheckBox);
        music = findViewById(R.id.MusicCheckBox);
        drama = findViewById(R.id.DramaCheckBox);
        film = findViewById(R.id.FilmCheckBox);
        visualArts = findViewById(R.id.VisualArtsCheckBox);
        productDesign = findViewById(R.id.ProductDesignCheckBox);
        computerScience = findViewById(R.id.ComputerScienceCheckBox);
        TOK = findViewById(R.id.TOKCheckBox);
        UUID = mAuth.getCurrentUser().getUid();
    }

    //  Upload Image
    public void upload(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        if (mImageUri != null) {
            StorageReference fileReference = storageRef.child(UUID);
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                            uploadButton.setVisibility(View.INVISIBLE);
                            findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
                            showImage();
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

    private void showImage() {
        StorageReference photoRef = storageRef.child(UUID);
        final long ONE_MEGABYTE = 1024 * 1024;
        photoRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bmp);
                imageView.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
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

    //  Sign In Success -> to Home
    private void finishSignUp() {
        if (chinese.isChecked())
            subjects.add(chinese.getText().toString());
        if (english.isChecked())
            subjects.add(chinese.getText().toString());
        if (math.isChecked())
            subjects.add(math.getText().toString());
        if (biology.isChecked())
            subjects.add(biology.getText().toString());
        if (chemistry.isChecked())
            subjects.add(chemistry.getText().toString());
        if (physics.isChecked())
            subjects.add(physics.getText().toString());
        if (music.isChecked())
            subjects.add(music.getText().toString());
        if (drama.isChecked())
            subjects.add(drama.getText().toString());
        if (film.isChecked())
            subjects.add(film.getText().toString());
        if (visualArts.isChecked())
            subjects.add(visualArts.getText().toString());
        if (productDesign.isChecked())
            subjects.add(productDesign.getText().toString());
        if (computerScience.isChecked())
            subjects.add(computerScience.getText().toString());
        if (TOK.isChecked())
            subjects.add(TOK.getText().toString());
        phoneNumber = phoneNumberField.getText().toString();
        name = nameEditText.getText().toString();
        User newUser = new User(name, mAuth.getCurrentUser().getEmail(), subjects, phoneNumber, yearLevel, UUID);
        fireStore.collection("users").document(UUID).set(newUser);
        startActivity(new Intent(this, HomeActivity.class));
    }


}
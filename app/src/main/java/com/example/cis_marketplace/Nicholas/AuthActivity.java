package com.example.cis_marketplace.Nicholas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cis_marketplace.CompleteSignUpActivity;
import com.example.cis_marketplace.HomeActivity;
import com.example.cis_marketplace.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private GoogleSignInAccount mGoogleSignInAccount;
    private static int RC_SIGN_IN = 9001;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        emailField = findViewById(R.id.UserEmail);
        passwordField = findViewById(R.id.UserPassword);

//        fireStore = FirebaseFirestore.getInstance();
//
////        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////                .requestIdToken("")
////                .requestEmail()
////                .build();
////
////        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.LogInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
        findViewById(R.id.SignUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }
    private void logIn(){
        startActivity(new Intent(this, HomeActivity.class));
    }
    private void signUp() {
        startActivity(new Intent(this, CompleteSignUpActivity.class));
    }
}


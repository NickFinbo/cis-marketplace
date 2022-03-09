package com.example.cis_marketplace.Marco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cis_marketplace.Nicholas.HomeActivity;
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
    private String TAG = "google sign in";
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        fireStore = FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1020381386559-1mt45vg5sp3r0hm7qhdrfquvsc2o7lir.apps.googleusercontent.com") //1020381386559-1mt45vg5sp3r0hm7qhdrfquvsc2o7lir.apps.googleusercontent.com
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        SignInButton googleSignInButton = findViewById(R.id.GoogleSignInButton);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.GoogleSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
    }
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                updateUI(user);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
        else{
            Toast.makeText(AuthActivity.this, "wrong code", Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    checkUserValidity(task);
                    Toast.makeText(AuthActivity.this, "Google signed up successfully", Toast.LENGTH_LONG).show();

                }else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(AuthActivity.this, "Google sign up failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void checkUserValidity(Task<AuthResult> task){
        if(task.getResult().getAdditionalUserInfo().isNewUser()){
            startActivity(new Intent(AuthActivity.this, CompleteSignUpActivity.class));
        }
        else{
            updateUI(mAuth.getCurrentUser());
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        if(!(currentUser == null)){
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

}


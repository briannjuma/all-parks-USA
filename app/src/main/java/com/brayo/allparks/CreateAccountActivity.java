package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.brayo.allparks.databinding.ActivityCreateAccountBinding;
import com.brayo.allparks.util.UserApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;

    // Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);

        Log.i(TAG, "Create account Activity opened");

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentuser = firebaseAuth.getCurrentUser();    // verify cuurent user

                if (currentuser != null) {
                    //user is already logged in
                } else {
                    // no user
                }
            }
        };

        binding.createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(binding.emailAccount.getText().toString())
                        && (!TextUtils.isEmpty(binding.passwordAccount.getText().toString())
                        && (!TextUtils.isEmpty(binding.usernameAccount.getText().toString())))) {

                    String email = binding.emailAccount.getText().toString().trim();
                    String password = binding.passwordAccount.getText().toString().trim();
                    String username = binding.usernameAccount.getText().toString().trim();

                    createUserEmailAccount(email, password, username);
                } else {
                    Toast.makeText(CreateAccountActivity.this,
                            "Empty Fields not allowed",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void createUserEmailAccount(String email, String password, String username) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {

            binding.createActtProgress.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Launch main activity
                                currentuser = firebaseAuth.getCurrentUser();
                                assert currentuser != null;
                                String currentUserId = currentuser.getUid();

                                // Create user map
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);

                                // Save to firestore
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    binding.createActtProgress.setVisibility(View.INVISIBLE);
                                                                    String name = task.getResult()
                                                                            .getString("username");
                                                                    UserApi userApi = UserApi.getInstance(); // Global User information cache
                                                                    userApi.setUserId(currentUserId);
                                                                    userApi.setUsername(name);


                                                                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                                                    intent.putExtra("username", name);
                                                                    intent.putExtra("userId",currentUserId);
                                                                    startActivity(intent);
                                                                    Log.i(TAG, "Navigating to Log in Activity ...");
//                                                                    Toast.makeText(CreateAccountActivity.this, "Discover All National Parks USA", Toast.LENGTH_SHORT).show();

                                                                } else {
                                                                    binding.createActtProgress.setVisibility(View.INVISIBLE);

                                                                }

                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            } else {
                                // fail
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        } else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentuser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.brayo.allparks.databinding.ActivityMainBinding;
import com.brayo.allparks.fragments.ParksFragment;
import com.brayo.allparks.fragments.ReferenceFragment;
import com.brayo.allparks.models.ParkViewModel;
import com.brayo.allparks.util.UserApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private ActivityMainBinding binding;
    private ParkViewModel parkViewModel;

    private String currentUserId;
    private String currentUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference collectionReference = db.collection("UserProfile");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.i(TAG, "Main Activity opened");

        // User Profile
        firebaseAuth = FirebaseAuth.getInstance();

        // fetch and display username from remote
        if (UserApi.getInstance() != null) {
            currentUserId = UserApi.getInstance().getUserId();
            currentUserName = UserApi.getInstance().getUsername();

            binding.usernameTextView.setText(currentUserName);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                }else{

                }
            }
        };

//        binding.saveUserButton.setOnClickListener(this);
        binding.addImageButton.setOnClickListener(this);





       // Fragment switching
        Fragment referenceFragment = (Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.referenceContainerView);
        referenceFragment.getActivity();

        BottomNavigationView bottomNavigationView = binding.bottomNavigation;
        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            int id = item.getItemId();

            if (id == R.id.user_nav_button) {
                // show reference fragment
                selectedFragment = ReferenceFragment.newInstance();

            } else if (id == R.id.parks_nav_button) {
                // show Park fragment
                selectedFragment = ParksFragment.newInstance();

            } else {
                selectedFragment = ReferenceFragment.newInstance();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.referenceContainerView, selectedFragment)
                    .commit();

            return true;
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.saveUserButton:
//                // save user profile
//                break;
            case R.id.addImageButton:
                // utilize camera
                onLaunchCamera();
                Toast.makeText(MainActivity.this, "Launching Camera",Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }

    private void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
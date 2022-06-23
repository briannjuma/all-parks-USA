package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
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
    private static final String PROFILE_ID = "profile_prefs";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private ActivityMainBinding binding;
    private ParkViewModel parkViewModel;

    private String currentUserId;
    private String currentUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Connection to Firestore
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private final CollectionReference collectionReference = db.collection("UserProfile");


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

                } else {

                }
            }
        };

//        binding.saveUserButton.setOnClickListener(this);
        binding.addImageButton.setOnClickListener(this);


        // Fragment switching
        Fragment referenceFragment = getSupportFragmentManager()
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

                // FILLING IN USER BIO
                String editBio = binding.userBioEditText.getText().toString().trim();

                SharedPreferences sharedPreferences = getSharedPreferences(PROFILE_ID, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("editBio", editBio);
                //  saving to disk
                editor.apply();

            } else {
                selectedFragment = ReferenceFragment.newInstance();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.referenceContainerView, selectedFragment)
                    .commit();

            return true;
        });
        SharedPreferences getShareData = getSharedPreferences(PROFILE_ID, Context.MODE_PRIVATE);
        String value = getShareData.getString("editBio", "User Bio appears here through shared preference");

        binding.showBio.setText(value);
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
                Toast.makeText(MainActivity.this, "Launching Camera", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }

    private void onLaunchCamera() {
//        Uri photoURI = FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName()+".provider",
//                createImageFile());
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//
//        // tell the camera to request write permissions
//        takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//
//        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void onCameraIconClicked(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            onLaunchCamera();
        } else {
            // let's request permission.getContext(),getContext(),
            String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // we have heard back from our request for camera and write external storage.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                onLaunchCamera();
            } else {
                Toast.makeText(this, R.string.cameraPermissionResponse, Toast.LENGTH_LONG).show();
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            binding.userProfileImageView.setImageBitmap(imageBitmap);
            //      encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }
}
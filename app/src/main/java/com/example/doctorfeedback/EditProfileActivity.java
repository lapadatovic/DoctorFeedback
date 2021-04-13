package com.example.doctorfeedback;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.doctorfeedback.dto.LocationDTO;
import com.example.doctorfeedback.dto.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {

    private DatabaseReference mUsersReference;
    private FirebaseUser currentUser;
    private User userProfile;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private EditText inputEditUsername;
    private EditText inputEditDepartment;
    private Button buttonUpdateLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        inputEditUsername = (EditText) findViewById(R.id.editUsername);
        inputEditDepartment = (EditText) findViewById(R.id.editDoctorDepartment);
        buttonUpdateLocation = (Button) findViewById(R.id.buttonDoctorUpdateLocation);
        Button buttonUpdateProfile = (Button) findViewById(R.id.buttonSaveProfileEdit);

        buttonUpdateLocation.setOnClickListener(this);
        buttonUpdateProfile.setOnClickListener(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mUsersReference = FirebaseDatabase.getInstance().getReference("Users");

        if(currentUser == null) {
            Intent loginPage = new Intent(EditProfileActivity.this, LogInActivity.class);
            startActivity(loginPage);
            return;
        }

        mUsersReference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(User.class);
                if(userProfile != null) {
                    inputEditUsername.setText(userProfile.username);
                    if(userProfile.role.toLowerCase().equals("doctor")) {
                        inputEditDepartment.setVisibility(View.VISIBLE);
                        inputEditDepartment.setText(userProfile.department);
                        buttonUpdateLocation.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSaveProfileEdit:
                updateUser();
                break;
            case R.id.buttonDoctorUpdateLocation:
                updateDoctorLocation();
                break;
        }
    }

    private void updateUser() {
        String username = inputEditUsername.getText().toString();
        String department = "";
        if(userProfile.role.toLowerCase().equals("doctor")) {
            department = inputEditDepartment.getText().toString();
        }
        User newUser = new User(username, userProfile.emailAddress, userProfile.role, department, userProfile.rate);
        mUsersReference.child(currentUser.getUid()).setValue(newUser);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
    }

    private void updateDoctorLocation() {
        int isLocationPermissionGranted = ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (isLocationPermissionGranted == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(EditProfileActivity.this)
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null) {
                        Geocoder geocoder = new Geocoder(EditProfileActivity.this, Locale.getDefault());
                        try {

                            // Get locations
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            double latitude = addresses.get(0).getLatitude();
                            double longitude = addresses.get(0).getLongitude();

                            // Update doctor location in the Firebase
                            LocationDTO locationDTO = new LocationDTO(latitude, longitude);
                            mUsersReference.child(currentUser.getUid())
                                    .child("location")
                                    .setValue(locationDTO)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(EditProfileActivity.this, "Doctor location updated", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 44);
            Toast.makeText(EditProfileActivity.this, "No location permission", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.doctorfeedback;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.example.doctorfeedback.dto.DoctorSearchDTO;
import com.example.doctorfeedback.dto.LocationDTO;
import com.example.doctorfeedback.dto.User;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchResultActivity extends BaseActivity {

    private ArrayList<User> doctorsList = new ArrayList<User>();
    private DoctorsSearchArrayAdapter doctorsSearchArrayAdapter;
    private DoctorSearchDTO doctorSearchDTO;
    private LocationDTO currentUserLocation;
    private ListView searchResultDoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchResultDoctorList = (ListView) findViewById(R.id.searchDoctorResultList);
        doctorSearchDTO = (DoctorSearchDTO) getIntent().getExtras().get("doctorSearchDTO");

        getCurrentUserLocation();
    }

    private void renderDoctorsList() {
        addDoctorsEventListener(FirebaseDatabase.getInstance().getReference("Users"));
        doctorsSearchArrayAdapter = new DoctorsSearchArrayAdapter(this, doctorsList);
        searchResultDoctorList.setAdapter(doctorsSearchArrayAdapter);
    }

    private void addDoctorsEventListener(DatabaseReference mUsersReference) {
        mUsersReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(user == null) return;
                user.id = snapshot.getKey();
                if(user.role.toLowerCase().equals("doctor")) {
                    LocationDTO doctorLocation = new LocationDTO(user.latitude, user.longitude);
                    String username = doctorSearchDTO.getUsername();
                    String department = doctorSearchDTO.getDepartment();
                    double distance = doctorSearchDTO.getDistance();
                    boolean isMatch = user.username.contains(username) && user.department.contains(department);

                    if(!isMatch) return;

                    if(currentUserLocation != null && distance != 0) {
                        double twoPointDistance = HaversineDistance.getDistance(currentUserLocation, doctorLocation);
                        if(twoPointDistance > distance) {
                            return;
                        }
                    }

                    doctorsList.add(user);
                    doctorsSearchArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Do something
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Do something
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Do something
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do something
            }
        });
    }

    private void getCurrentUserLocation() {
        int isLocationPermissionGranted = ActivityCompat.checkSelfPermission(SearchResultActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (isLocationPermissionGranted == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(SearchResultActivity.this)
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location != null) {
                                Geocoder geocoder = new Geocoder(SearchResultActivity.this, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    double latitude = addresses.get(0).getLatitude();
                                    double longitude = addresses.get(0).getLongitude();
                                    currentUserLocation = new LocationDTO(latitude, longitude);
                                    renderDoctorsList();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(SearchResultActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 44);
            Toast.makeText(SearchResultActivity.this, "No location permission", Toast.LENGTH_SHORT).show();
        }
    }
}

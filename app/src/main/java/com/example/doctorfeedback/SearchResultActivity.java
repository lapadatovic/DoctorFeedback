package com.example.doctorfeedback;

import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.doctorfeedback.dto.DoctorSearchDTO;
import com.example.doctorfeedback.dto.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity {

    private ArrayList<User> doctorsList = new ArrayList<User>();
    private DoctorsSearchArrayAdapter doctorsSearchArrayAdapter;
    private DoctorSearchDTO doctorSearchDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ListView searchResultDoctorList = (ListView) findViewById(R.id.searchDoctorResultList);
        doctorSearchDTO = (DoctorSearchDTO) getIntent().getExtras().get("doctorSearchDTO");

        // Create doctors list
        addDoctorsEventListener(FirebaseDatabase.getInstance().getReference("Users"));
        doctorsSearchArrayAdapter = new DoctorsSearchArrayAdapter(this, doctorsList);
        searchResultDoctorList.setAdapter(doctorsSearchArrayAdapter);
    }

    private void addDoctorsEventListener(DatabaseReference mUsersReference) {
        mUsersReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                user.id = snapshot.getKey();
                if(user.role.toLowerCase().equals("doctor")) {
                    String username = doctorSearchDTO.getUsername();
                    String department = doctorSearchDTO.getDepartment();
                    boolean isMatch = user.username.contains(username) && user.department.contains(department);
                    if(isMatch) {
                        doctorsList.add(user);
                        doctorsSearchArrayAdapter.notifyDataSetChanged();
                    }
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
}

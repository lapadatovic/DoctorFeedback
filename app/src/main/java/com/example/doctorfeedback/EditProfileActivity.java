package com.example.doctorfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.doctorfeedback.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {

    private DatabaseReference mUsersReference;
    private FirebaseUser currentUser;
    private User userProfile;

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSaveProfileEdit) {
            updateUser();
        }
    }

    public void updateUser() {
        String username = inputEditUsername.getText().toString();
        String department = "";
        if(userProfile.role.toLowerCase().equals("doctor")) {
            department = inputEditDepartment.getText().toString();
        }
        User newUser = new User(username, userProfile.emailAddress, userProfile.role, department);
        mUsersReference.child(currentUser.getUid()).setValue(newUser);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
    }
}

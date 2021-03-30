package com.example.doctorfeedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.doctorfeedback.dto.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText editTextUsername, editTextEmailAddress, editTextPassword;
    private RadioGroup roleRadioGroup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextUsername = (EditText) findViewById(R.id.registerNameInput);
        editTextEmailAddress = (EditText) findViewById((R.id.registerEmailInput));
        editTextPassword = (EditText) findViewById(R.id.registerPasswordInput);
        roleRadioGroup = findViewById(R.id.selectRoleRadioGroup);

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRegister) {
            registerUser();
        }
    }

    private void registerUser() {

        String email = editTextEmailAddress.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();

        int selectedRadioId = roleRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadio = (RadioButton) findViewById(selectedRadioId);
        String role = selectedRadio.getText().toString();

        if(username.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            editTextEmailAddress.setError("Email address is required");
            editTextEmailAddress.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailAddress.setError("Please provide valid email");
            editTextEmailAddress.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                            return;
                        }

                        User user = new User(username, email, role, "");
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this,"Failed to register. Try again!",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Toast.makeText((RegisterActivity.this), "User has been register successfully!",Toast.LENGTH_LONG).show();
                                Intent Login = new Intent(RegisterActivity.this, LogInActivity.class);
                                startActivity(Login);
                            }
                        });
                    }
                });
    }
}
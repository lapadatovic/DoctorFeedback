package com.example.doctorfeedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends Activity implements View.OnClickListener {

    private EditText editTextEmailInput;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        editTextEmailInput = (EditText) findViewById(R.id.emailInput);
        editTextPassword = (EditText) findViewById(R.id.passwordInput);

        mAuth = FirebaseAuth.getInstance();
    }

    @SuppressLint("NonConstantResourceId")

    @Override
    public void onClick(View v) {
        /*
        if (v.getId() == R.id.buttonLogin) {
            Intent pageSearch = new Intent(LogInActivity.this, SearchResultActivity.class);
            startActivity(pageSearch);
        }
        */
        switch (v.getId()){
            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmailInput.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmailInput.setError("Email is required!");
            editTextEmailInput.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailInput.setError("Please enter valid email!");
            editTextEmailInput.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, "Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent findDoctorPage = new Intent(LogInActivity.this, FindDoctorActivity.class);
                        startActivity(findDoctorPage);
                    }
                });
    }
}
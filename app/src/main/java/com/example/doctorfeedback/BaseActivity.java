package com.example.doctorfeedback;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("DoctorFeedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar_menu, menu);
        return true;
    }

    @SuppressLint({"ShowToast", "NonConstantResourceId"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOptionProfile:
                Intent profilePage = new Intent(BaseActivity.this, EditProfileActivity.class);
                startActivity(profilePage);
                return true;
            case R.id.menuOptionLogout:
                FirebaseAuth.getInstance().signOut();
                Intent loginPage = new Intent(BaseActivity.this, LogInActivity.class);
                startActivity(loginPage);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

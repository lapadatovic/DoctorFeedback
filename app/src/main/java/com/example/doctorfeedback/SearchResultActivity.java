package com.example.doctorfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Button buttonGoToProfile = findViewById(R.id.buttonGoToProfile);
        buttonGoToProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGoToProfile) {
            Intent pageDoctorProfile = new Intent(SearchResultActivity.this, DoctorProfileActivity.class);
            startActivity(pageDoctorProfile);
        }
    }
}

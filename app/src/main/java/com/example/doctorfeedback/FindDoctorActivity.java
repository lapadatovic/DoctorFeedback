package com.example.doctorfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.doctorfeedback.dto.DoctorSearchDTO;

public class FindDoctorActivity extends BaseActivity implements View.OnClickListener {

    private EditText inputUsername;
    private EditText inputDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        inputUsername = (EditText) findViewById(R.id.inputSearchDoctorName);
        inputDepartment = (EditText) findViewById(R.id.inputSearchDoctorDepartment);

        Button buttonSearchDoctor = (Button) findViewById(R.id.buttonSearchDoctorButton);
        buttonSearchDoctor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSearchDoctorButton) {
            findDoctors();
        }
    }

    private void findDoctors() {
        DoctorSearchDTO doctorSearchDTO = new DoctorSearchDTO(
            inputUsername.getText().toString(),
            inputDepartment.getText().toString(),
            ""
        );
        Intent searchResultPage = new Intent(FindDoctorActivity.this, SearchResultActivity.class);
        searchResultPage.putExtra("doctorSearchDTO", doctorSearchDTO);
        startActivity(searchResultPage);
    }
}
package com.example.doctorfeedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.doctorfeedback.dto.FeedbackDTO;
import java.util.ArrayList;

public class DoctorProfileActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        Button buttonGoToFeedback = findViewById(R.id.buttonSendFeedback);
        buttonGoToFeedback.setOnClickListener(this);

        FeedbackArrayAdapter feedbackArrayAdapter = new FeedbackArrayAdapter(
            this,
            this.getFeedback()
        );

        ListView feedbackList = findViewById(R.id.doctorFeedbackList);
        feedbackList.setAdapter(feedbackArrayAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSendFeedback) {
            Intent pageFeedback = new Intent(DoctorProfileActivity.this, FeedbackActivity.class);
            startActivity(pageFeedback);
        }
    }

    private ArrayList<FeedbackDTO> getFeedback() {
        ArrayList<FeedbackDTO> feedbackList = new ArrayList<FeedbackDTO>();
        feedbackList.add(new FeedbackDTO("Amazing doctor!", "James", 5.0));
        feedbackList.add(new FeedbackDTO("Good experience", "Maria", 4.0));
        feedbackList.add(new FeedbackDTO("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDTO("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDTO("Super doctor!", "Tim", 4.5));
        return feedbackList;
    }
}

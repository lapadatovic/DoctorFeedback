package com.example.doctorfeedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.doctorfeedback.dto.FeedbackDto;
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

    private ArrayList<FeedbackDto> getFeedback() {
        ArrayList<FeedbackDto> feedbackList = new ArrayList<FeedbackDto>();
        feedbackList.add(new FeedbackDto("Amazing doctor!", "James", 5.0));
        feedbackList.add(new FeedbackDto("Good experience", "Maria", 4.0));
        feedbackList.add(new FeedbackDto("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDto("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDto("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDto("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDto("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDto("Super doctor!", "Tim", 4.5));
        feedbackList.add(new FeedbackDto("Great doctor!", "Tim", 4.5));
        return feedbackList;
    }
}

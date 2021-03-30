package com.example.doctorfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.doctorfeedback.dto.FeedbackDTO;
import com.example.doctorfeedback.dto.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    DatabaseReference feedbackDatabase;
    DatabaseReference userDatabase;
    RadioGroup radioGroup;
    EditText inputFeedbackText;
    String doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        radioGroup = findViewById(R.id.selectFeedbackRateRadioGroup);
        inputFeedbackText = findViewById(R.id.inputFeedbackText);

        Button buttonSendFeedback = findViewById(R.id.buttonSendFeedback);
        buttonSendFeedback.setOnClickListener(this);

        doctorID = getIntent().getExtras().get("doctorUID").toString();
        feedbackDatabase = FirebaseDatabase.getInstance().getReference("Feedback");
        userDatabase = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonSendFeedback) {
            sendFeedback();
        }
    }

    private void sendFeedback() {

        int selectedRadioId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadio = (RadioButton) findViewById(selectedRadioId);
        String rateText = selectedRadio.getText().toString();
        double rate = getRateNumber(rateText);
        String feedbackText = inputFeedbackText.getText().toString();

        userDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    FeedbackDTO feedbackDTO = new FeedbackDTO(doctorID, feedbackText, user.username, rate);
                    String id = feedbackDatabase.push().getKey();
                    feedbackDatabase.child(id).setValue(feedbackDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(FeedbackActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(FeedbackActivity.this, "Successfully sent the feedback", Toast.LENGTH_SHORT).show();
                            Intent doctorProfile = new Intent(FeedbackActivity.this, DoctorProfileActivity.class);
                            doctorProfile.putExtra("doctorUID", doctorID);
                            startActivity(doctorProfile);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private double getRateNumber(String rateText) {
        double rate = 0.0;
        switch (rateText) {
            case "Horrible":
                rate = 1.0;
                break;
            case "Bad":
                rate = 2.0;
                break;
            case "Neutral":
                rate = 3.0;
                break;
            case "Good":
                rate = 4.0;
                break;
            case "Amazing":
                rate = 5.0;
                break;
        }
        return rate;
    }
}
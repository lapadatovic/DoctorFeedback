package com.example.doctorfeedback;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.doctorfeedback.dto.FeedbackDTO;
import com.example.doctorfeedback.dto.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DoctorProfileActivity extends BaseActivity implements View.OnClickListener{

    private String doctorUID;
    private ArrayList<FeedbackDTO> feedbackList = new ArrayList<FeedbackDTO>();
    private FeedbackArrayAdapter feedbackArrayAdapter;
    private LinearLayout doctorCardStarsLinearLayout;
    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Go to feedback page button event listener
        Button buttonGoToFeedback = findViewById(R.id.buttonGoToFeedbackPage);
        buttonGoToFeedback.setOnClickListener(this);

        TextView doctorName = findViewById(R.id.doctorProfileName);
        TextView doctorDepartment = findViewById(R.id.doctorProfileDepartment);
        doctorCardStarsLinearLayout = findViewById(R.id.doctorRateStars);

        // Create feedback list
        addFeedbackEventListener(FirebaseDatabase.getInstance().getReference("Feedback"));
        feedbackArrayAdapter = new FeedbackArrayAdapter(
            this,
            feedbackList
        );

        ListView uiFeedbackList = findViewById(R.id.doctorFeedbackList);
        uiFeedbackList.setAdapter(feedbackArrayAdapter);

        // Populate doctor profile card
        doctorUID = getIntent().getExtras().get("doctorUID").toString();
        userDatabase = FirebaseDatabase.getInstance().getReference("Users");

        userDatabase.child(doctorUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                doctorName.setText(user.username);
                doctorDepartment.setText(user.department);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do something
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGoToFeedbackPage) {
            Intent pageFeedback = new Intent(DoctorProfileActivity.this, FeedbackActivity.class);
            pageFeedback.putExtra("doctorUID", doctorUID);
            startActivity(pageFeedback);
        }
    }

    private void addFeedbackEventListener(DatabaseReference mFeedbackReference) {
        mFeedbackReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FeedbackDTO feedbackDTO = snapshot.getValue(FeedbackDTO.class);
                if(feedbackDTO.doctorID.equals(doctorUID)) {

                    feedbackList.add(feedbackDTO);
                    feedbackArrayAdapter.notifyDataSetChanged();

                    double rateSum = 0;
                    double rate = 0;
                    double fractional = 0;

                    for(FeedbackDTO feedback: feedbackList) {
                        rateSum += feedback.rate;
                    }

                    rate = rateSum / feedbackList.size();
                    fractional = rate - (int) rate;
                    double startsNumber = rate;

                    if(fractional >= 0.5) {
                        startsNumber++;
                    }

                    doctorCardStarsLinearLayout.removeAllViews();

                    for(int i = 0; i < (int) startsNumber; i++) {
                        ImageView starImage = new ImageView(DoctorProfileActivity.this);
                        starImage.setBackgroundResource(R.drawable.star);
                        doctorCardStarsLinearLayout.addView(starImage);
                    }

                    userDatabase.child(feedbackDTO.doctorID).child("rate").setValue(rate);
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

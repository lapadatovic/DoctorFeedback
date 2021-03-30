package com.example.doctorfeedback;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.doctorfeedback.dto.FeedbackDTO;
import java.util.List;

public class FeedbackArrayAdapter extends ArrayAdapter<FeedbackDTO> {

    private Activity context;

    public FeedbackArrayAdapter(Activity context, List<FeedbackDTO> feedbackList) {
        super(context, 0, feedbackList);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FeedbackDTO currentFeedback = getItem(position);
        View listFeedback = convertView;
        if (listFeedback == null) {
            listFeedback = LayoutInflater.from(getContext()).inflate(R.layout.feedback_card, parent, false);
        }

        TextView feedbackHeadline = listFeedback.findViewById(R.id.feedbackHeadline);
        feedbackHeadline.setText(currentFeedback.headline);

        TextView feedbackFrom = listFeedback.findViewById(R.id.feedbackFrom);
        feedbackFrom.setText(currentFeedback.patientName);

        LinearLayout feedbackStarsWrapper = listFeedback.findViewById(R.id.feedbackStarsWrapper);

        double rate = currentFeedback.rate;
        double fractional = rate - (int) rate;

        if(fractional >= 0.5) {
            rate++;
        }

        feedbackStarsWrapper.removeAllViews();

        for(int i = 0; i < (int) rate; i++) {
            ImageView starImage = new ImageView(context);
            starImage.setBackgroundResource(R.drawable.star);
            feedbackStarsWrapper.addView(starImage);
        }

        return listFeedback;
    }
}

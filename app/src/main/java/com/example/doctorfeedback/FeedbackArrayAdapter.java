package com.example.doctorfeedback;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.doctorfeedback.dto.FeedbackDto;
import java.util.List;

public class FeedbackArrayAdapter extends ArrayAdapter<FeedbackDto> {

    public FeedbackArrayAdapter(Activity context, List<FeedbackDto> feedbackList) {
        super(context, 0, feedbackList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FeedbackDto currentFeedback = getItem(position);
        View listFeedback = convertView;
        if (listFeedback == null) {
            listFeedback = LayoutInflater.from(getContext()).inflate(R.layout.feedback_card, parent, false);
        }

        TextView feedbackHeadline = listFeedback.findViewById(R.id.feedbackHeadline);
        feedbackHeadline.setText(currentFeedback.getHeadline());

        TextView feedbackFrom = listFeedback.findViewById(R.id.feedbackFrom);
        feedbackFrom.setText(currentFeedback.getFrom());

        return listFeedback;
    }
}
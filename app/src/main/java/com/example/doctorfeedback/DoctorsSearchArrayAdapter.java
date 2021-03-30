package com.example.doctorfeedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;;
import android.widget.Button;
import android.widget.TextView;
import com.example.doctorfeedback.dto.User;
import java.io.Serializable;
import java.util.List;

public class DoctorsSearchArrayAdapter extends ArrayAdapter<User> {

    private Activity context;

    public DoctorsSearchArrayAdapter(Activity context, List<User> doctorsList) {
        super(context, 0, doctorsList);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        User currentDoctor = getItem(position);
        View listDoctors = convertView;
        if (listDoctors == null) {
            listDoctors = LayoutInflater.from(getContext()).inflate(R.layout.doctor_profile_card, parent, false);
        }

        TextView doctorName = listDoctors.findViewById(R.id.doctorName);
        doctorName.setText(currentDoctor.username);

        TextView doctorDepartment = listDoctors.findViewById(R.id.doctorDepartment);
        doctorDepartment.setText(currentDoctor.department);

        Button buttonViewProfile = listDoctors.findViewById(R.id.buttonViewProfile);

        buttonViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doctorProfile = new Intent(context, DoctorProfileActivity.class);
                doctorProfile.putExtra("doctorUID", currentDoctor.id);
                context.startActivity(doctorProfile);
            }
        });

        return listDoctors;
    }
}

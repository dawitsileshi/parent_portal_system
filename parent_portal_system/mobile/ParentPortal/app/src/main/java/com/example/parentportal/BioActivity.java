package com.example.parentportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.parentportal.Utils.PreferenceUtil;
import com.example.parentportal.account.LoginActivity;
import com.example.parentportal.model.Student;

public class BioActivity extends AppCompatActivity {

    private Student student;
    private TextView tv_activity_bio_name,
            tv_activity_bio_gender,
            tv_activity_bio_age,
            tv_activity_bio_year,
            tv_activity_bio_section,
            tv_activity_bio_inSchool;

    private PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        student = new Student(this);
        preferenceUtil = new PreferenceUtil(this);
        String id = preferenceUtil.retrieveString(PreferenceUtil.PREFERENCE_SELECTED_STUDENTID, null);

        Student studentProfile = student.getStudentById(id);

        tv_activity_bio_name = findViewById(R.id.tv_activity_bio_name);
        tv_activity_bio_gender = findViewById(R.id.tv_activity_bio_gender);
        tv_activity_bio_age = findViewById(R.id.tv_activity_bio_age);
        tv_activity_bio_year = findViewById(R.id.tv_activity_bio_year);
        tv_activity_bio_section = findViewById(R.id.tv_activity_bio_section);
        tv_activity_bio_inSchool = findViewById(R.id.tv_activity_bio_inSchool);

        tv_activity_bio_name.append(studentProfile.getFname() + " " + studentProfile.getLname());
        tv_activity_bio_gender.append(studentProfile.getGender());
        tv_activity_bio_age.append(String.valueOf(studentProfile.getAge()));
        tv_activity_bio_year.append(String.valueOf(studentProfile.getYear()));
        tv_activity_bio_section.append(studentProfile.getSection());
        if(!studentProfile.getInSchool()) {
            tv_activity_bio_inSchool.append("Off School");
        } else {
            tv_activity_bio_inSchool.append("In School");
        }
    }


}

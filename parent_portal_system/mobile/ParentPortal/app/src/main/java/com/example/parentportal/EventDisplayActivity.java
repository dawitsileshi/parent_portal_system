package com.example.parentportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parentportal.model.Event;

public class EventDisplayActivity extends AppCompatActivity {

    private TextView tv_activity_event_when,
            tv_activity_event_createdDate,
            tv_activity_event_title,
            tv_activity_event_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);

        tv_activity_event_createdDate = findViewById(R.id.tv_activity_event_createdDate);
        tv_activity_event_when = findViewById(R.id.tv_activity_event_when);
        tv_activity_event_title = findViewById(R.id.tv_activity_event_title);
        tv_activity_event_description = findViewById(R.id.tv_activity_event_description);

        Intent intent = getIntent();

        if(intent.getExtras() != null) {

            Event event = intent.getParcelableExtra("event");

            tv_activity_event_description.setText(event.getDescription());
            tv_activity_event_title.setText(event.getTitle());
            tv_activity_event_when.setText(event.getWhen().toString());
            tv_activity_event_createdDate.setText(event.getCreatedDate().toString());


        } else {

            Toast.makeText(this, "Nothing sent", Toast.LENGTH_SHORT).show();
            return;

        }
    }
}

package com.example.parentportal;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.parentportal.Utils.PreferenceUtil;
import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.account.LoginActivity;
import com.example.parentportal.model.Attendance;
import com.example.parentportal.model.Discipline;
import com.example.parentportal.model.Schedule;
import com.example.parentportal.model.Schedules;
import com.example.parentportal.model.Student;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements AddParentBottomSheet.SubmitClicked {

    private Intent intent;

    private Retrofit retrofit;
    private RetrofitCalls retrofitCalls;

    private Student student;

    private AddParentBottomSheet addParentBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        student = new Student(this);
        retrofit = RequestController.getRetrofit();
        retrofitCalls = retrofit.create(RetrofitCalls.class);

        final Parcelable[] schedules = intent.getParcelableArrayExtra("schedules");

        Toast.makeText(this, String.valueOf(schedules.length), Toast.LENGTH_SHORT).show();

        findViewById(R.id.cv_activity_home_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] schedulesId = new String[]{};
//                for (int i = 0; i < schedules.length; i++) {
//                    Schedules schedules1 = (Schedules) schedules[i];
//                    Toast.makeText(this, schedules1.getScheduleId(), Toast.LENGTH_SHORT).show();
//                }
                Intent intent1 = new Intent(HomeActivity.this, ScheduleActivity.class);
                intent1.putExtra("schedules", schedules);
                Toast.makeText(HomeActivity.this, String.valueOf(schedules.length), Toast.LENGTH_SHORT).show();
                startActivity(intent1);
//                startActivity(ScheduleActivity.class);
            }
        });


        findViewById(R.id.cv_activity_home_attendance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AttendanceActivity.class);
            }
        });


        findViewById(R.id.cv_activity_home_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EventActivity.class);
            }
        });


        findViewById(R.id.cv_activity_home_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ResultsActivity.class);
            }
        });


        findViewById(R.id.cv_activity_home_discipline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DisciplineActivity.class);
            }
        });
    }

    private void startActivity(Class aClass) {
        intent = new Intent(this, aClass);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_view_profile) {

            startActivity(new Intent(this, BioActivity.class));

        } else if(id == R.id.action_logout) {

            new PreferenceUtil(this).saveBooleanValue(false, PreferenceUtil.PREFERENCE_LOGGEDIN);
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        } else {

            addParentBottomSheet = new AddParentBottomSheet();
            addParentBottomSheet.show(getSupportFragmentManager(), "add_parent");

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clicked(String email, String tel) {

        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        String[] studentIds = student.studentIds();
        Call<String> addParent = retrofitCalls.addParent(email, tel, studentIds);

        addParent.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "The server returned a status code of" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                addParentBottomSheet.dismiss();

                Toast.makeText(HomeActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, "The error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

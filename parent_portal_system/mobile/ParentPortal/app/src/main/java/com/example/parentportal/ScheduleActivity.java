package com.example.parentportal;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.parentportal.Utils.PreferenceUtil;
import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.adapters.RecyclerViewAdapter;
import com.example.parentportal.adapters.ScheduleRecyclerViewAdapter;
import com.example.parentportal.model.Program;
import com.example.parentportal.model.Schedule;
import com.example.parentportal.model.Schedules;
import com.example.parentportal.model.Teacher;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleActivity extends AppCompatActivity implements ScheduleRecyclerViewAdapter.ClickLongClick {

    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    private RecyclerView rv_activity_schedule;
    private ArrayList<Schedule> scheduleArrayList;

    private Retrofit retrofit;
    private RetrofitCalls retrofitCalls;

    private PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        rv_activity_schedule = findViewById(R.id.rv_activity_schedule);

        retrofit = RequestController.getRetrofit();

        retrofitCalls = retrofit.create(RetrofitCalls.class);

        Intent intent = getIntent();

        Parcelable[] parcelables = intent.getParcelableArrayExtra("schedules");

        scheduleArrayList = new ArrayList<>();

        preferenceUtil = new PreferenceUtil(this);

        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(this, scheduleArrayList, this);

        rv_activity_schedule.setLayoutManager(new LinearLayoutManager(this));

        rv_activity_schedule.setAdapter(scheduleRecyclerViewAdapter);

//        String id = preferenceUtil.retrieveString(PreferenceUtil.PREFERENCE_SELECTED_STUDENTID, null);

        for (int i = 0; i < parcelables.length; i++) {
            Schedules schedules = (Schedules) parcelables[i];

            Toast.makeText(this, schedules.getScheduleId(), Toast.LENGTH_SHORT).show();
            Call<Schedule> currentSchedules = retrofitCalls.getCurrentSchedule(schedules.getScheduleId());
            currentSchedules.enqueue(new Callback<Schedule>() {
                @Override
                public void onResponse(@NonNull Call<Schedule> call, @NonNull Response<Schedule> response) {

                    if(!response.isSuccessful()) {
                        Toast.makeText(ScheduleActivity.this, "The server returned a status code of " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    assert response.body() != null;
                    if(response.body() != null) {
                        populateArrayList(response.body());
                    } else {
                        Toast.makeText(ScheduleActivity.this, "It is null", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Schedule> call, Throwable t) {
                    Toast.makeText(ScheduleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void populateArrayList(Schedule body) {

//        Schedule[] schedulesArrayList = body.;

//        for (Schedule aSchedulesArrayList : schedulesArrayList) {

            String day = body.getDay();
            int dayNumber = body.getDayNumber();
            ArrayList<Program> program = body.getProgram();
            Log.i("The day ", program.get(0).getTeacherName());
            Schedule schedule = new Schedule(day, dayNumber, program);
//            schedule.setDay(day);
//            schedule.setDayNumber(dayNumber);

//        final ArrayList<Program> programs = new ArrayList<>();


//        schedule.setProgram(populateTeachers(programs, body));
            scheduleArrayList.add(schedule);
            scheduleRecyclerViewAdapter.notifyDataSetChanged();


        }

    private ArrayList<Program> populateTeachers(final ArrayList<Program> programs, Schedule body) {

        for (int i = 0; i < body.getProgram().size(); i++) {

            final Program program1 = body.getProgram().get(i);
//            for (int i = 0; i < programs.size(); i++) {
            Call<Teacher> teacherCall = retrofitCalls.getTeacher(program1.getTeacherId());

            teacherCall.enqueue(new Callback<Teacher>() {
                @Override
                public void onResponse(@NonNull Call<Teacher> call, @NonNull Response<Teacher> response) {
                    program1.setTeacher(response.body());
                    programs.add(program1);

                }

                @Override
                public void onFailure(@NonNull Call<Teacher> call, @NonNull Throwable t) {
                    Toast.makeText(ScheduleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return programs;

    }
//            addTeacher(schedule.setProgram();)
//            populateTeachers()

//        }

//    }

    @Override
    public void click(Schedule schedule, int position) {
        Intent intent = new Intent(this, ProgramActivity.class);
        ArrayList<Program> programs = schedule.getProgram();
        intent.putExtra("programs", programs);
        startActivity(intent);
        for (int i = 0; i < programs.size(); i++) {
            Log.i("the teacher", programs.get(i).getTeacherName());
        }
//        Toast.makeText(this, programs.get(0).getTeacherName(), Toast.LENGTH_SHORT).show();
    }
}

package com.example.parentportal;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.parentportal.Utils.PreferenceUtil;
import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.adapters.StudentRecyclerViewAdapter;
import com.example.parentportal.model.DB.DataSource;
import com.example.parentportal.model.Data;
import com.example.parentportal.model.Pic;
import com.example.parentportal.model.Schedules;
import com.example.parentportal.model.Student;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KidsActivity extends AppCompatActivity implements StudentRecyclerViewAdapter.ClickLongClick {

    RecyclerView rv_activity_kids;
//    RecyclerViewAdapter recyclerViewAdapter;
    StudentRecyclerViewAdapter studentRecyclerViewAdapter;

    Retrofit retrofit;
    RetrofitCalls retrofitCalls;
//    Call<Student> studentCall;
    Call<ArrayList<Student>> studentCall;

    ArrayList<Student> students;

    private Schedules[] schedules;

    private Student student;
    private DataSource dataSource;
    Cursor allStudents;

    PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);

        rv_activity_kids = findViewById(R.id.rv_activity_kids);

        students = new ArrayList<>();
        student = new Student(this);
        dataSource = new DataSource(this);

        allStudents = dataSource.getAllKids();
        Log.i("mongo", String.valueOf(allStudents.getCount()));

        preferenceUtil = new PreferenceUtil(this);


        Intent intent = getIntent();

        if(allStudents.getCount() == 0) {
            String email = intent.getStringExtra("email");
            retrofit = RequestController.getRetrofit();
            retrofitCalls = retrofit.create(RetrofitCalls.class);
            studentCall = retrofitCalls.getStudentByEmail(email);
            sendRequest(studentCall);

        } else {
            students = student.students();
//            studentRecyclerViewAdapter.notifyDataSetChanged();
            Toast.makeText(this, "From teh database" + String.valueOf(students.size()), Toast.LENGTH_SHORT).show();
        }

        studentRecyclerViewAdapter = new StudentRecyclerViewAdapter(this, students, this);
        rv_activity_kids.setLayoutManager(new LinearLayoutManager(this));
        rv_activity_kids.setAdapter(studentRecyclerViewAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new PreferenceUtil(this).saveBooleanValue(false, PreferenceUtil.PREFERENCE_LOGGEDIN);
    }

    private void sendRequest(final Call<ArrayList<Student>> studentCall) {

        studentCall.enqueue(new Callback<ArrayList<Student>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Student>> call, @NonNull Response<ArrayList<Student>> response) {

                if(!response.isSuccessful()) {
                    Toast.makeText(KidsActivity.this, "The server returned a server code of " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i("mongo size", String.valueOf(response.body().size()));

                Student s = response.body().get(0);
                for (int i = 0; i < s.getPic().getData().getData().length; i++) {
                    Log.i("The single buffer", String.valueOf(s.getPic().getData().getData()[i]));
                }

//                if(response.body().size() > 0) {
                    for (Student student1 : response.body()) {


                        Pic pic = student1.getPic();
                        Data data = pic.getData();

                        Log.i("The buffer", String.valueOf(data.getData().length));

                        schedules = student1.getSchedules();

                        Log.i("The schedules", String.valueOf(schedules.length));

                        Toast.makeText(KidsActivity.this, String.valueOf(schedules.length), Toast.LENGTH_SHORT).show();

//                        long result = student.insertStudent(student1);
//                        Log.i("mongo result", String.valueOf(result));
//                        if(result == -1) {
//                            Toast.makeText(KidsActivity.this, "couldn't sve", Toast.LENGTH_SHORT).show();
//                        }
//                        Student.BinData binData = student1.getBinData();
//                        Student.Pic pic = student1.getPic();
//                        if(binData != null) {
//                            byte[] bytes = binData.getData();
//                            Log.i("The buffer", String.valueOf(bytes.length));
//                        } else {
//                            Toast.makeText(KidsActivity.this, "it is null", Toast.LENGTH_SHORT).show();
//                        }
//                        Toast.makeText(KidsActivity.this, student1.getFname() + " " + student1.getLname(), Toast.LENGTH_SHORT).show();
                        students.add(student1);
                        studentRecyclerViewAdapter.notifyDataSetChanged();

                    }
//
                }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Student>> call, @NonNull Throwable t) {
                Toast.makeText(KidsActivity.this, "Error" +  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void click(Student student, int position) {
        String id = student.getId();

        preferenceUtil.saveString(id, PreferenceUtil.PREFERENCE_SELECTED_STUDENTID);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("schedules", student.getSchedules());
        intent.putExtra("student", student);
        startActivity(intent);
    }

    @Override
    public void longClick(Student student) {

    }
}

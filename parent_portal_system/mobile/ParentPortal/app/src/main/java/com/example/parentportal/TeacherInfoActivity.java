package com.example.parentportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.model.Teacher;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeacherInfoActivity extends AppCompatActivity {

    Retrofit retrofit;
    RetrofitCalls retrofitCalls;

    private TextView tv_activity_teacher_info_name,
            tv_activity_teacher_info_email;

    private Button button_activity_teacher_info_ok,
            button_activity_teacher_info_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

        Intent intent = getIntent();

        if(intent.getExtras() == null) {

            Toast.makeText(this, "Nothing sent", Toast.LENGTH_SHORT).show();
            finish();

        }

        String id = intent.getStringExtra("teacherId");
        retrofit = RequestController.getRetrofit();
        retrofitCalls = retrofit.create(RetrofitCalls.class);

        tv_activity_teacher_info_email = findViewById(R.id.tv_activity_teacher_info_email);
        tv_activity_teacher_info_name = findViewById(R.id.tv_activity_teacher_info_name);

        button_activity_teacher_info_chat = findViewById(R.id.button_activity_teacher_info_chat);
        button_activity_teacher_info_ok = findViewById(R.id.button_activity_teacher_info_ok);

        final Call<Teacher> teacherCall = retrofitCalls.getTeacher(id);

        teacherCall.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(@NonNull Call<Teacher> call, @NonNull Response<Teacher> response) {

                if(!response.isSuccessful()) {

                    Toast.makeText(TeacherInfoActivity.this, "The server returned a status code of " + response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }
                Teacher teacher = response.body();

                String name = teacher.getFname() + " " + teacher.getLname();
                String email = teacher.getEmail();

                tv_activity_teacher_info_name.setText(name);
                tv_activity_teacher_info_email.setText(email);
            }

            @Override
            public void onFailure(@NonNull Call<Teacher> call, @NonNull Throwable t) {
                Toast.makeText(TeacherInfoActivity.this, "The error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}

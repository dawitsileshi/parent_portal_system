package com.example.parentportal.account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parentportal.KidsActivity;
import com.example.parentportal.R;
import com.example.parentportal.RetrofitCalls;
import com.example.parentportal.Utils.PreferenceUtil;
import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.model.Parent;
import com.example.parentportal.model.StudentId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText et_activity_login_email, et_activity_login_password;
    private Button button_activity_login_login,
            button_activity_login_createAccount;

    private Retrofit retrofit;
    private RetrofitCalls retrofitCalls;

    private ArrayList<String> ids;

    PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_activity_login_email = findViewById(R.id.et_activity_login_email);
        et_activity_login_password = findViewById(R.id.et_activity_login_password);

        retrofit = RequestController.getRetrofit();
        retrofitCalls = retrofit.create(RetrofitCalls.class);

        preferenceUtil = new PreferenceUtil(this);

//        if(preferenceUtil.retrieveBooleanValue(PreferenceUtil.PREFERENCE_LOGGEDIN)) {
//
//            startActivity(new Intent(this, KidsActivity.class));
//
//        }

        button_activity_login_login = findViewById(R.id.button_activity_login_login);
        button_activity_login_createAccount = findViewById(R.id.button_activity_login_create_account);

        ids = new ArrayList<>();

        button_activity_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ids.clear();
                String email = et_activity_login_email.getText().toString().trim();
                String password = et_activity_login_password.getText().toString().trim();

                checkDataFilled(email, password);

            }
        });

        button_activity_login_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void checkDataFilled(String email, String password) {

        if(email.length() == 0 || password.length() == 0) {

            Toast.makeText(this, "Fill all the data", Toast.LENGTH_SHORT).show();
            return;

        } else {

            sendRequest(email, password);

        }

    }

    private void sendRequest(String email, String password) {
        Call<Parent> parentLogin = retrofitCalls.parentLogin(email, password);

        parentLogin.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(@NonNull Call<Parent> call, @NonNull Response<Parent> response) {

                if(!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "The server returned a status code of " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.body() != null) {

//                    StudentId[] studentIds = response.body().getStudents();
//                    if(response.body() == 0) {
//                        Toast.makeText(LoginActivity.this, "sorry, couldn't find your account", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
//
//                    }
                    preferenceUtil.saveBooleanValue(true, PreferenceUtil.PREFERENCE_LOGGEDIN);
                    Intent intent = new Intent(LoginActivity.this, KidsActivity.class);
                    Parent parent = response.body();
                    StudentId[] studentIds = parent.getStudents();
                    for (StudentId studentId : studentIds) {
                        ids.add(studentId.getStudentId());
                    }
                    Toast.makeText(LoginActivity.this, "The size of id is " + ids.size(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("ids", ids);
                    intent.putExtra("email", et_activity_login_email.getText().toString());
                    startActivity(intent);

//                    Toast.makeText(LoginActivity.this, "Found the account", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Couldn't find the account", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.parentportal.account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.parentportal.KidsActivity;
import com.example.parentportal.R;
import com.example.parentportal.RetrofitCalls;
import com.example.parentportal.Utils.PreferenceUtil;
import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.model.DB.DataSource;
import com.example.parentportal.model.Parent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_activity_signup_fname,
            et_activity_signup_lname,
            et_activity_signup_email,
            et_activity_signup_tel,
            et_activity_signup_password,
            et_activity_signup_confirmPass,
            et_activity_signup_token;

    RadioGroup rg_activity_signup_relation;
    LinearLayout ll_activity_signup;
    ImageView iv_activity_signup;
    ArrayList<EditText> editTexts;

    private Button button_activity_signup_signup;

    private Retrofit retrofit;
    private RetrofitCalls retrofitCalls;

    private DataSource dataSource;
    ArrayList<String> tokens;

    private PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tokens = new ArrayList<>();
        preferenceUtil = new PreferenceUtil(this);

        et_activity_signup_fname = findViewById(R.id.et_activity_signup_fname);
        et_activity_signup_lname = findViewById(R.id.et_activity_signup_lname);
        et_activity_signup_email = findViewById(R.id.et_activity_signup_email);
        et_activity_signup_tel = findViewById(R.id.et_activity_signup_tel);
        et_activity_signup_password= findViewById(R.id.et_activity_signup_password);
        et_activity_signup_confirmPass = findViewById(R.id.et_activity_signup_confirmPassword);
        et_activity_signup_token = findViewById(R.id.et_activity_signup_token);

        editTexts = new ArrayList<>();
        editTexts.add(et_activity_signup_token);

        button_activity_signup_signup = findViewById(R.id.button_activity_signup_signup);

        rg_activity_signup_relation = findViewById(R.id.rg_activity_signup_relation);

        ll_activity_signup = findViewById(R.id.ll_activity_signup);

        iv_activity_signup = findViewById(R.id.iv_activity_signup_addToken);

        iv_activity_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(SignUpActivity.this);
                editText.setHint(R.string.token_from_sms);
                ll_activity_signup.addView(editText);
                editTexts.add(editText);
            }
        });

        dataSource = new DataSource(this);

        if(dataSource.checkParent().getCount() > 0) {

            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else {
            Toast.makeText(this, "No parent saved", Toast.LENGTH_SHORT).show();
        }
        retrofit = RequestController.getRetrofit();
        retrofitCalls = retrofit.create(RetrofitCalls.class);

        button_activity_signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokens.clear();
                Toast.makeText(SignUpActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                final String fname = et_activity_signup_fname.getText().toString().trim();
                final String lname = et_activity_signup_lname.getText().toString().trim();
                final String email = et_activity_signup_email.getText().toString().trim();
                final String tel = et_activity_signup_tel.getText().toString().trim();
                final String password = et_activity_signup_password.getText().toString().trim();
                final String confirmPass = et_activity_signup_confirmPass.getText().toString().trim();
//                final String token = et_activity_signup_token.getText().toString().trim();

                int checkedRadioButtonId = rg_activity_signup_relation.getCheckedRadioButtonId();

                RadioButton radioButton = findViewById(checkedRadioButtonId);

                if(checkAllTokens(editTexts)) {
//                    Toast.makeText(SignUpActivity.this, "All Tokens inserted", Toast.LENGTH_SHORT).show();
                    register(fname, lname, email, tel, password, confirmPass, radioButton, tokens);
                } else {
                    Toast.makeText(SignUpActivity.this, "Please provide all the tokens", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private boolean checkAllTokens(ArrayList<EditText> editTexts) {
        for (int i = 0; i < editTexts.size(); i++) {
            Toast.makeText(this, String.valueOf(editTexts.size()), Toast.LENGTH_SHORT).show();
            String token = editTexts.get(i).getText().toString().trim();
            if(token.equals("")) {
                return false;
            }
            tokens.add(token);
        }
        return true;
    }

    private void register(String fname, String lname, String email, String tel, String password, String confirmPass, RadioButton radioButton, ArrayList<String> token) {

        if(fname.length() == 0 || lname.length() == 0 || email.length() == 0 || tel.length() == 0 || password.length() == 0 || confirmPass.length() == 0 || radioButton == null) {

            Toast.makeText(this, "Please fill all the data.", Toast.LENGTH_SHORT).show();
            Log.i("value fname", String.valueOf(fname.length()));
            Log.i("value lname", String.valueOf(fname.length()));
            Log.i("value email", String.valueOf(email.length()));
            Log.i("value tel", String.valueOf(tel.length()));
            Log.i("value pass", String.valueOf(password.length()));
            Log.i("value confirm", String.valueOf(confirmPass.length()));

        } else {

            if(passwordsMatch(password, confirmPass)) {

                String relation = radioButton.getText().toString();
                Call<Parent> registerParent = retrofitCalls.registerParent(fname, lname, email, tel, relation, password, token);

                registerParent.enqueue(new Callback<Parent>() {
                    @Override
                    public void onResponse(@NonNull Call<Parent> call, @NonNull Response<Parent> response) {

                        if(!response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        Toast.makeText(SignUpActivity.this, String.valueOf(response.raw().message()), Toast.LENGTH_SHORT).show();
                        Parent parent = response.body();
                        new Parent(SignUpActivity.this).insertParent(parent);

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.putExtra("" +
                                "parent", parent);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(@NonNull Call<Parent> call, @NonNull Throwable t) {

                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



//                Toast.makeText(this, "Password matched", Toast.LENGTH_SHORT).show();


            } else {

                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private boolean passwordsMatch(String password, String confirmPass) {
        return password.equals(confirmPass);
    }
}

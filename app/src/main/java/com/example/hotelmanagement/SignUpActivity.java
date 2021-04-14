package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fullnameEt, usernameEt, passwordEt, emailEt;
    private Button signUpBt;
    private TextView loginTv;
    private ProgressBar progressBar;
    private static final String SIGNUP_URL = "http://192.168.0.105/android_hms/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullnameEt = findViewById(R.id.fullname);
        usernameEt = findViewById(R.id.username);
        passwordEt = findViewById(R.id.password);
        emailEt = findViewById(R.id.email);
        signUpBt = findViewById(R.id.buttonSignUp);
        loginTv = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        signUpBt.setOnClickListener(v -> {
            String fullname, username, password, email;
            fullname = String.valueOf(fullnameEt.getText());
            username = String.valueOf(usernameEt.getText());
            password = String.valueOf(passwordEt.getText());
            email = String.valueOf(emailEt.getText());

            if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[4];
                    field[0] = "fullname";
                    field[1] = "username";
                    field[2] = "password";
                    field[3] = "email";
                    String[] data = new String[4];
                    data[0] = fullname;
                    data[1] = username;
                    data[2] = password;
                    data[3] = email;
                    PutData putData = new PutData(SIGNUP_URL, "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Sign Up Success")) {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(SignUpActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
            }
        });

        loginTv.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
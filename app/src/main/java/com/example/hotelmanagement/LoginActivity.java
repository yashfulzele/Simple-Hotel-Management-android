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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEt, passwordEt;
    private Button loginBt;
    private TextView signUpTv;
    private ProgressBar progressBar;
    private static final String LOGIN_URL = "http://192.168.0.105/android_hms/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEt = findViewById(R.id.username);
        passwordEt = findViewById(R.id.password);
        loginBt = findViewById(R.id.buttonLogin);
        signUpTv = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        loginBt.setOnClickListener(v -> {
            String username, password;
            username = String.valueOf(usernameEt.getText());
            password = String.valueOf(passwordEt.getText());

            if (!username.equals("") && !password.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";
                    String[] data = new String[2];
                    data[0] = username;
                    data[1] = password;
                    PutData putData = new PutData(LOGIN_URL, "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Login Success")) {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
            }
        });

        signUpTv.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
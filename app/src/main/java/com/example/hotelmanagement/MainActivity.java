package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {

    private TextView fullnameTv, usernameTv, emailTv;
    private String fullname, username, email;
    private static final String READDEATAILS_URL = "http://192.168.0.105/android_hms/readDetails.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");

        fullnameTv = findViewById(R.id.fullname);
        usernameTv = findViewById(R.id.username);
        emailTv = findViewById(R.id.email);

        usernameTv.setText(username);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "username";
            String[] data = new String[1];
            data[0] = username;
            PutData putData = new PutData(READDEATAILS_URL, "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    String[] arrOfStr = result.split(" ");
                    fullname = arrOfStr[0];
                    email = arrOfStr[1];
                }
            }
        });

        fullnameTv.setText(fullname);
        emailTv.setText(email);
    }
}
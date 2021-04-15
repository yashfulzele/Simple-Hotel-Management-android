package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {

    private TextView fullnameTv, usernameTv, emailTv;
    private String fullname, username, email;
    private TextInputEditText serviceEt;
    private Button serviceBookingBt, roomBookingBt;
    private static final String READDETAILS_URL = "http://192.168.0.105/android_hms/readDetails.php";
    private static final String SERVICEBOOKING_URL = "http://192.168.0.105/android_hms/serviceBooking.php";
    private static final String MYTAG = "mytag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        Log.d(MYTAG, "hi, there");

        fullnameTv = findViewById(R.id.fullname);
        usernameTv = findViewById(R.id.username);
        emailTv = findViewById(R.id.email);
        serviceEt = findViewById(R.id.service);
        serviceBookingBt = findViewById(R.id.service_booking);
        roomBookingBt = findViewById(R.id.room_booking);

        usernameTv.setText(username);

        if (!username.equals("")) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = new String[1];
                field[0] = "username";
                String[] data = new String[1];
                data[0] = username;
                PutData putData = new PutData(READDETAILS_URL, "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.d(MYTAG, result);
                        String[] arrOfStr = result.split("_");
                        fullname = arrOfStr[0];
                        email = arrOfStr[1];
                        Log.d(MYTAG, fullname);
                        fullnameTv.setText(fullname);
                        emailTv.setText(email);
                    }
                }
            });
        }

        serviceBookingBt.setOnClickListener(v -> {
            String service = String.valueOf(serviceEt.getText());
            if (!service.equals("")) {
                Handler handler2 = new Handler(Looper.getMainLooper());
                handler2.post(() -> {
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "service";
                    String[] data = new String[2];
                    data[0] = username;
                    data[1] = service;
                    PutData putData = new PutData(SERVICEBOOKING_URL, "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Service Success")) {
                                serviceEt.getText().clear();
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Type the service you wish for!", Toast.LENGTH_SHORT).show();
            }
        });

        roomBookingBt.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        });
    }
}
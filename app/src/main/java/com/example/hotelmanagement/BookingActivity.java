package com.example.hotelmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hotelmanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class BookingActivity extends AppCompatActivity {

    private Button btn_book;
    private Spinner room_list;
    private TextInputEditText check_in_edit, check_out_edit;
    private static final String Booking_URL = "http://192.168.0.105/android_hms/booking.php";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        room_list = findViewById(R.id.room_list);
        String[] room_type = new String[]{"Select a Room", "Small", "Medium", "Large"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, room_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room_list.setAdapter(adapter);

        btn_book = findViewById(R.id.btn_book);
        check_in_edit = findViewById(R.id.check_in);
        check_out_edit = findViewById(R.id.check_out);

        username = getIntent().getStringExtra("username");

        btn_book.setOnClickListener(v -> {
            String room_type_name = room_list.getSelectedItem().toString();
            String check_out = String.valueOf(check_out_edit.getText());
            String check_in = String.valueOf(check_in_edit.getText());

            if (!(room_type_name.equals("")) && !(check_in.equals("")) && !(check_out.equals(""))) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[4];
                    field[0] = "room_type_name";
                    field[1] = "check_in";
                    field[2] = "check_out";
                    field[3] = "username";
                    String[] data = new String[4];
                    data[0] = room_type_name;
                    data[1] = check_in;
                    data[2] = check_out;
                    data[3] = username;
                    PutData putData = new PutData(Booking_URL, "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            Log.d("mytag", result);
                            AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
                            alertDialog.setTitle("Alert");
                            String[] temp = result.split("----");
                            if (temp.length == 1) alertDialog.setMessage(result);
                            else alertDialog.setMessage(temp[0] + "\n" + temp[1]);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    (dialog, which) -> dialog.dismiss());
                            alertDialog.show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Format of Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
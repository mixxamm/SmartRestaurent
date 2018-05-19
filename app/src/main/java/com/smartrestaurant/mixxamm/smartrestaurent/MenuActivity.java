package com.smartrestaurant.mixxamm.smartrestaurent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Vars
        TextView txtTest = findViewById(R.id.txtTest);

        Intent intent = getIntent();
        String QRCODE = intent.getStringExtra("QR-code");
        txtTest.setText(QRCODE);


    }
}

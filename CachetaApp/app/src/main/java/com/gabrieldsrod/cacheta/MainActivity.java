package com.gabrieldsrod.cacheta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private Button tableBtn, reportBtn;

    private void init() {
        context = MainActivity.this;
        tableBtn = findViewById(R.id.btnMesas);
        reportBtn = findViewById(R.id.btnRelatorios);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.white));

        tableBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, TableActivity.class);
            startActivity(intent);
        });

        reportBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReportActivity.class);
            startActivity(intent);
        });

    }
}
package com.example.mehranm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int num =50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.chart1).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ChartOneActivity.class)));
        findViewById(R.id.chart2).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ChartTwoActivity.class)));
        findViewById(R.id.chart3).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ChartThreeActivity.class)));
        findViewById(R.id.chart4).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Chart4Activity.class)));
        findViewById(R.id.chart5).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Chart5Activity.class)));


    }
}
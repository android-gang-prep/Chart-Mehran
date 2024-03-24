package com.example.mehranm2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart1);
        ChartOne chartOne = findViewById(R.id.chart);
        List<DataChartOne> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Random random = new Random();
            list.add(new DataChartOne(random.nextInt(50), random.nextInt(50), random.nextInt(50), random.nextInt(50), random.nextInt(50)));
        }
        chartOne.addAllData(list);
    }
}
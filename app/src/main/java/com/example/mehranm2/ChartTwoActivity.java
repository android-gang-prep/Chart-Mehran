package com.example.mehranm2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart2);
        ChartTwo chart = findViewById(R.id.chart);
        List<DataChartTwo> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            list.add(new DataChartTwo(random.nextInt(23), random.nextInt(59), random.nextInt(23), random.nextInt(59)));
        }
        chart.addAllData(list);
    }
}
package com.example.mehranm2;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chart4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart4);

        List<ChartItemData> data = new ArrayList<>();
        Random random = new Random();
        int[] colors = new int[]{getResources().getColor(R.color.gr), getResources().getColor(R.color.bl), getResources().getColor(R.color.yl)};

        for (int i = 0; i < 30; i++) {
            data.add(new ChartItemData(random.nextInt(80) + 20, colors[random.nextInt(3)]));
        }

        Chart4 chart4 = findViewById(R.id.chart4);

        AdapterRec adapterRec = new AdapterRec(data, chartItemData -> {
            chart4.setNum(chartItemData.getPercent());
            chart4.setColor(chartItemData.getColor());
        });

        RecyclerView recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapterRec);

        chart4.setColor(data.get(0).getColor());
        new Handler().postDelayed(() -> chart4.setNum(data.get(0).getPercent()),500);


    }


}
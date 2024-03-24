package com.example.mehranm2;

import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart3);
        ChartThree chart = findViewById(R.id.chart);
        List<DataChartThree> list = new ArrayList<>();
        List<Integer> heartList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 12; i++) {

            if (i > 0)
                list.add(new DataChartThree(getType(list.get(i - 1).getType()), random.nextInt(50)));
            else
                list.add(new DataChartThree(getType(null), random.nextInt(50)));
        }
        heartList.add(0);
        for (int i = 0; i < 12; i++) {
            heartList.add(random.nextInt(120));
        }

        chart.addData(list,heartList);
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> chart.setHeartRateVisible(isChecked));
    }

    private ChartThreeType getType(ChartThreeType prev) {
        Random random = new Random();
        ChartThreeType[] enums = new ChartThreeType[]{ChartThreeType.DEEP, ChartThreeType.LIGHT, ChartThreeType.REM, ChartThreeType.AWAKE};
        ChartThreeType newEnum = enums[random.nextInt(4)];
        if (prev == newEnum)
            return getType(prev);

        return newEnum;

    }
}
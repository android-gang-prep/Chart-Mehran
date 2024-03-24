package com.example.mehranm2;

import android.graphics.Color;

public class ChartItemData {
    private int percent;
    private int color = Color.parseColor("#FB8C00");

    public ChartItemData(int percent, int color) {
        this.percent = percent;
        this.color = color;
    }


    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

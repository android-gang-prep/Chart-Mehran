package com.example.mehranm2;

public class DataChartThree {
    private ChartThreeType type;
    private int minute;

    public DataChartThree(ChartThreeType type, int minute) {
        this.type = type;
        this.minute = minute;
    }

    public ChartThreeType getType() {
        return type;
    }

    public int getMinute() {
        return minute;
    }
}

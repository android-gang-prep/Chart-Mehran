package com.example.mehranm2;

import java.text.SimpleDateFormat;

public class DataChartTwo {
    private int wokeUPHour;
    private int wokeUPMinute;
    private int fellSleepHour;
    private int fellSleepMinute;


    public DataChartTwo(int wokeUPHour, int wokeUPMinute, int fellSleepHour, int fellSleepMinute) {
        this.wokeUPHour = wokeUPHour;
        this.wokeUPMinute = wokeUPMinute;
        this.fellSleepHour = fellSleepHour;
        this.fellSleepMinute = fellSleepMinute;
    }

    public String getTimeWokeUP() {
        return wokeUPHour+":"+wokeUPMinute;
    }

    public String getTimeFellSleep() {
        return fellSleepHour+":"+fellSleepMinute;

    }
    public int getWokeUPHour() {
        return wokeUPHour;
    }

    public int getWokeUPMinute() {
        return wokeUPMinute;
    }

    public int getFellSleepHour() {
        return fellSleepHour;
    }

    public int getFellSleepMinute() {
        return fellSleepMinute;
    }
    public int getWokeUP() {
        return (wokeUPHour*60)+wokeUPMinute;
    }

    public int getFellSleep() {
        return (fellSleepHour*60)+fellSleepMinute;
    }
}

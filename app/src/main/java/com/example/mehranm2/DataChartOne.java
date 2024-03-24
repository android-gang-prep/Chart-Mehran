package com.example.mehranm2;

public class DataChartOne {

    private int deep;
    private int light;
    private int rem;
    private int awake;
    private int naps;

    public DataChartOne(int deep, int light, int rem, int awake, int naps) {
        this.deep = deep;
        this.light = light;
        this.rem = rem;
        this.awake = awake;
        this.naps = naps;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getRem() {
        return rem;
    }

    public void setRem(int rem) {
        this.rem = rem;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }

    public int getNaps() {
        return naps;
    }

    public void setNaps(int naps) {
        this.naps = naps;
    }
}

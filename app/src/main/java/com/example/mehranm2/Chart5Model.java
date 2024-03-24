package com.example.mehranm2;

public class Chart5Model {
    private float open;
    private float close;
    private float high;
    private float low;

    public Chart5Model(float open, float close, float high, float low) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }
}

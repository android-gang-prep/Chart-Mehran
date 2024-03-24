package com.example.mehranm2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ItemChart4View extends View {
    public ItemChart4View(Context context) {
        super(context);
        init();
    }

    public ItemChart4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemChart4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ItemChart4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    Paint paint;
    Paint progressPaint;
    int percent;

    private void init() {
        percent = 0;
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.gray));
        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#FB8C00"));
    }

    public void setColor(int color) {
        progressPaint.setColor(color);
        invalidate();
    }

    public void setPercent(int percent) {
        this.percent = percent;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float onePercent = ((getHeight() - dpToPx(20)) / 100);
        canvas.drawRoundRect(dpToPx(10), dpToPx(10), getWidth() - dpToPx(10), getHeight() - dpToPx(10), dpToPx(80), dpToPx(80), paint);
        canvas.drawRoundRect(dpToPx(10), getHeight() - dpToPx(10), getWidth() - dpToPx(10), (onePercent * (100 - percent)) + dpToPx(10), dpToPx(80), dpToPx(80), progressPaint);


    }

    private float dpToPx(float d) {
        return getResources().getDisplayMetrics().density * d;
    }
}

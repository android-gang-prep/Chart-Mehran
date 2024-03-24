package com.example.mehranm2;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartOne extends View {

    List<DataChartOne> list;
    Paint deep;
    Paint light;
    Paint rem;
    Paint awake;
    Paint naps;
    Paint line;
    TextPaint textPaint;

    public ChartOne(Context context) {
        super(context);
        init();

    }

    public ChartOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ChartOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public ChartOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        list = new ArrayList<>();


        deep = new Paint();
        deep.setStyle(Paint.Style.FILL);
        deep.setColor(getResources().getColor(R.color.pr));

        light = new Paint();
        light.setStyle(Paint.Style.FILL);
        light.setColor(getResources().getColor(R.color.bl));

        rem = new Paint();
        rem.setStyle(Paint.Style.FILL);
        rem.setColor(getResources().getColor(R.color.gr));

        awake = new Paint();
        awake.setStyle(Paint.Style.FILL);
        awake.setColor(getResources().getColor(R.color.yl));

        naps = new Paint();
        naps.setStyle(Paint.Style.FILL);
        naps.setColor(getResources().getColor(R.color.bll));

        line = new Paint();
        line.setStyle(Paint.Style.FILL);
        line.setColor(getResources().getColor(R.color.gray));


        textPaint = new TextPaint();
        textPaint.setColor(getResources().getColor(R.color.gray));
        textPaint.setTextSize(dpToPx(12));

    }



    public void addAllData(List<DataChartOne> dataChartOne){
        list.addAll(dataChartOne);
        invalidate();
        startAnimation();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.drawLine(dpToPx(20), dpToPx(20), getWidth() - dpToPx(40), dpToPx(20), line);
        canvas.drawLine(dpToPx(20), getHeight() / 2, getWidth() - dpToPx(40), getHeight() / 2, line);
        canvas.drawLine(dpToPx(20), getHeight() - dpToPx(20), getWidth() - dpToPx(40), (getHeight() - dpToPx(20)), line);

        float textSpace = (getWidth() - dpToPx(50)) / 12;

        for (int i = 0; i < 12; i++) {
            float x = textSpace * (i);
            x += dpToPx(25);
            canvas.drawText((i + 1) + "", x, getHeight(), textPaint);
        }
        float count = 0;

        for (int i = 0; i < (Math.min(list.size(), 12)); i++) {
            float c = 0;
            DataChartOne data = list.get(i);
            c += data.getDeep();
            c += data.getLight();
            c += data.getRem();
            c += data.getAwake();
            c += data.getNaps();
            if (c > count)
                count = c;
        }

        canvas.drawText(((int) count) + "h", getWidth() - dpToPx(35), dpToPx(25), textPaint);
        canvas.drawText(((int) count / 2) + "h", getWidth() - dpToPx(35), (getHeight() / 2) + dpToPx(5), textPaint);
        canvas.drawText("0h", getWidth() - dpToPx(35), getHeight() - dpToPx(15), textPaint);
        //  canvas.drawLine(dpToPx(10), getHeight() / 2, getWidth() - dpToPx(20), getHeight() / 2, line);
        //canvas.drawLine(dpToPx(10), getHeight() - dpToPx(20), getWidth() - dpToPx(20), (getHeight() - dpToPx(20)), line);


        for (int i = 0; i < (Math.min(list.size(), 12)); i++) {
            float startY = 0;
            float stopY = 0;

            float startX = textSpace * (i);
            startX += dpToPx(20);
            float stopX = (textSpace * (i + 1)) + dpToPx(10);

            DataChartOne data = list.get(i);


            float spaceChart = (getHeight() - dpToPx(40)) / count;


            if (data.getDeep() != 0) {
                startY = getHeight() - dpToPx(20);
                stopY = startY - (spaceChart * data.getDeep());
                if (stopY / animValue < startY)
                    stopY /= animValue;
                else
                    stopY=startY;



                canvas.drawRect(startX, stopY, stopX, startY, deep);

            }

            if (animValue == 0)
                return;

            if (data.getLight() != 0) {
                if (startY == 0) {
                    startY = getHeight() - dpToPx(20);
                } else {
                    startY = stopY;
                }
                stopY = startY - (spaceChart * data.getLight());
                if (stopY / animValue < startY)
                    stopY /= animValue;
                else
                    stopY=startY;



                canvas.drawRect(startX, stopY, stopX, startY, light);
            }

            if (data.getRem() != 0) {
                if (startY == 0) {
                    startY = getHeight() - dpToPx(20);
                } else {
                    startY = stopY;
                }
                stopY = startY - (spaceChart * data.getRem());
                if (stopY / animValue < startY)
                    stopY /= animValue;
                else
                    stopY=startY;



                canvas.drawRect(startX, stopY, stopX, startY, rem);
            }

            if (data.getAwake() != 0) {
                if (startY == 0) {
                    startY = getHeight() - dpToPx(20);
                } else {
                    startY = stopY;
                }
                stopY = startY - (spaceChart * data.getAwake());

                if (stopY / animValue < startY)
                    stopY /= animValue;
                else
                    stopY=startY;

                canvas.drawRect(startX, stopY, stopX, startY, awake);
            }

            if (data.getNaps() != 0) {
                if (startY == 0) {
                    startY = getHeight() - dpToPx(20);
                } else {
                    startY = stopY;
                }
                stopY = startY - (spaceChart * data.getNaps());
                if (stopY / animValue < startY)
                    stopY /= animValue;
                else
                    stopY=startY;



                canvas.drawRect(startX, stopY, stopX, startY, naps);
            }

        }

    }

    private float animValue = 0f;
    private ValueAnimator animator = null;

    public void startAnimation() {
        if (animator != null)
            animator.cancel();
        animValue = 0f;
        animator = ObjectAnimator.ofFloat(0.7f, 1f);
        animator.setDuration(2000);
        animator.addUpdateListener(animation -> {
            animValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    private float dpToPx(float d) {
        return getResources().getDisplayMetrics().density * d;
    }
}

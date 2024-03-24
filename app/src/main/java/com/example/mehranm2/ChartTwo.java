package com.example.mehranm2;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartTwo extends View {
    Paint wokeUP;
    Paint wokeUPLine;
    /*Paint fellSleep;*/
    Paint fellSleepLine;
    List<DataChartTwo> list;
    Paint line;
    TextPaint textPaint;
    Path path;
    Path pathLine;
    /*Path pathFell;*/
    Path pathFellLine;
    float visible = 0;
    PathMeasure pathMeasureWokeUP;
    PathMeasure pathMeasureFellLine;
    PathMeasure pathMeasureWokeUPLine;
    final Path visibleWokeUPLine = new Path();
    final Path visibleFellLine = new Path();

    public ChartTwo(Context context) {
        super(context);
        init();

    }

    public ChartTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ChartTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public ChartTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        wokeUP = new Paint(Paint.ANTI_ALIAS_FLAG);
        wokeUP.setStyle(Paint.Style.FILL);


        wokeUPLine = new Paint();
        wokeUPLine.setColor(Color.parseColor("#FDCE66"));
        wokeUPLine.setStyle(Paint.Style.STROKE);
        wokeUPLine.setStrokeWidth(5);
        wokeUPLine.setStrokeCap(Paint.Cap.ROUND);


    /*    fellSleep = new Paint(Paint.ANTI_ALIAS_FLAG);
        fellSleep.setStyle(Paint.Style.FILL);*/


        fellSleepLine = new Paint();
        fellSleepLine.setColor(Color.parseColor("#9760FC"));
        fellSleepLine.setStyle(Paint.Style.STROKE);
        fellSleepLine.setStrokeWidth(5);
        fellSleepLine.setStrokeCap(Paint.Cap.ROUND);


        line = new Paint();
        line.setStyle(Paint.Style.FILL);
        line.setColor(getResources().getColor(R.color.gray));

        textPaint = new TextPaint();
        textPaint.setColor(getResources().getColor(R.color.textColor));
        textPaint.setTextSize(dpToPx(12));

        path = new Path();
        pathLine = new Path();

        /*pathFell = new Path();*/
        pathFellLine = new Path();

        list = new ArrayList<>();


    }

    public void addAllData(List<DataChartTwo> data) {
        list.addAll(data);
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(dpToPx(40), getHeight() - dpToPx(20), getWidth() - dpToPx(20), getHeight() - dpToPx(20), line);

        float textSpace = (getWidth() - dpToPx(40)) / 12;
        float x = dpToPx(40);
        for (int i = 0; i < 12; i++) {
            canvas.drawText((i + 1) + "", x, getHeight(), textPaint);
            x += textSpace;
        }

        float most = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFellSleep() > most)
                most = list.get(i).getFellSleep();

            if (list.get(i).getWokeUP() > most)
                most = list.get(i).getWokeUP();
        }

        double space = (getHeight() - dpToPx(20)) / most;


        for (int i = 0; i < Math.min(list.size(), 12); i++) {
            float yWokeUP = (getHeight() - dpToPx(20)) - (float) (list.get(i).getWokeUP() * space);
            float yFellSleep = (getHeight() - dpToPx(20)) - (float) (list.get(i).getFellSleep() * space);
            if (i == 0) {
                if (yWokeUP < dpToPx(10))
                    canvas.drawText(list.get(i).getTimeWokeUP(), dpToPx(5), yWokeUP + dpToPx(15), textPaint);
                else
                    canvas.drawText(list.get(i).getTimeWokeUP(), dpToPx(5), yWokeUP + dpToPx(5), textPaint);

                if (yFellSleep < dpToPx(10))
                    canvas.drawText(list.get(i).getTimeFellSleep(), dpToPx(5), yFellSleep + dpToPx(15), textPaint);
                else
                    canvas.drawText(list.get(i).getTimeFellSleep(), dpToPx(5), yFellSleep + dpToPx(5), textPaint);

            }
        }

        if (list.size() == 0)
            return;

        if (animator == null) {
            data();
            startAnimation();
            return;
        }


        if (visible < 1f) {
            visibleWokeUPLine.reset();
            visibleFellLine.reset();
            pathMeasureWokeUPLine.getSegment(0f, pathMeasureWokeUPLine.getLength() * visible, visibleWokeUPLine, true);
            pathMeasureFellLine.getSegment(0f, pathMeasureFellLine.getLength() * visible, visibleFellLine, true);
            canvas.drawPath(visibleWokeUPLine, wokeUPLine);
            canvas.drawPath(visibleFellLine, fellSleepLine);
        } else {
            canvas.drawPath(pathLine, wokeUPLine);
            canvas.drawPath(pathFellLine, fellSleepLine);
        }
        wokeUP.setAlpha((int) (255 * visible));
        canvas.drawPath(path, wokeUP);

    }

    private float dpToPx(float d) {
        return getResources().getDisplayMetrics().density * d;
    }

    private ValueAnimator animator = null;

    public void startAnimation() {
        if (animator != null)
            animator.cancel();
        visible = 0f;

        pathMeasureWokeUP = new PathMeasure(path, false);
        pathMeasureFellLine = new PathMeasure(pathFellLine, false);
        pathMeasureWokeUPLine = new PathMeasure(pathLine, false);
        invalidate();
        animator = ObjectAnimator.ofFloat(0f, 1f);
        animator.setStartDelay(300);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            visible = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.start();

    }


    private void data() {

        wokeUP.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.parseColor("#36FDCE66"), Color.parseColor("#00FDCE66"), Shader.TileMode.CLAMP));


        float textSpace = (getWidth() - dpToPx(40)) / 12;

        float most = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFellSleep() > most)
                most = list.get(i).getFellSleep();

            if (list.get(i).getWokeUP() > most)
                most = list.get(i).getWokeUP();
        }

        double space = (getHeight() - dpToPx(20)) / most;


        float lastXWokeUP = 0;
        float lastYWokeUP = 0;

        float lastXFellSleep = 0;
        float lastYFellSleep = 0;

        for (int i = 0; i < Math.min(list.size(), 12); i++) {
            float xWokeUP = (textSpace * i) + dpToPx(40);
            float yWokeUP = (getHeight() - dpToPx(20)) - (float) (list.get(i).getWokeUP() * space);
            if (i == 0) {
                path.moveTo(xWokeUP, yWokeUP);
                pathLine.moveTo(xWokeUP, yWokeUP);
            } else {
                if (list.get(i).getWokeUP() == 0) {
                    path.lineTo((textSpace * i) + dpToPx(40), getHeight() - dpToPx(20));
                    pathLine.lineTo((textSpace * i) + dpToPx(20), getHeight() - dpToPx(20));
                } else {
                    path.quadTo(lastXWokeUP, lastYWokeUP, (xWokeUP + lastXWokeUP) / 2, (yWokeUP + lastYWokeUP) / 2);
                    pathLine.quadTo(lastXWokeUP, lastYWokeUP, (xWokeUP + lastXWokeUP) / 2, (yWokeUP + lastYWokeUP) / 2);
                }

            }

            lastXWokeUP = xWokeUP;
            lastYWokeUP = yWokeUP;

            float xFellSleep = (textSpace * i) + dpToPx(40);
            float yFellSleep = (getHeight() - dpToPx(20)) - (float) (list.get(i).getFellSleep() * space);
            if (i == 0) {
                pathFellLine.moveTo(xFellSleep, yFellSleep);
            } else {
                if (list.get(i).getFellSleep() == 0) {
                    pathFellLine.lineTo((textSpace * i) + dpToPx(40), getHeight() - dpToPx(20));
                } else {
                    pathFellLine.quadTo(lastXFellSleep, lastYFellSleep, (xFellSleep + lastXFellSleep) / 2, (yFellSleep + lastYFellSleep) / 2);
                }

            }


            lastXFellSleep = xFellSleep;
            lastYFellSleep = yFellSleep;
        }


        if (list.size() < 12)
            for (int i = list.size() - 1; i < 12; i++) {
                path.lineTo((textSpace * i) + dpToPx(20), getHeight() - dpToPx(20));
                pathLine.lineTo((textSpace * i) + dpToPx(20), getHeight() - dpToPx(20));
                pathFellLine.lineTo((textSpace * i) + dpToPx(20), getHeight() - dpToPx(20));
            }


        path.lineTo(getWidth() - dpToPx(20), getHeight() - dpToPx(20));
        path.lineTo(dpToPx(20), getHeight() - dpToPx(20));

    }


}

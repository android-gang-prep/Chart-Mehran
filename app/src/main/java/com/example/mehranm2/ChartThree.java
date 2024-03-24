package com.example.mehranm2;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartThree extends View {

    List<DataChartThree> list;
    List<Integer> heartList;
    Paint deep;
    Paint light;
    Paint rem;
    Paint awake;
    Paint naps;
    Paint line;
    Paint heart;
    Path heartPath;
    Path heartPathVisible;
    Path pathCircle;
    Paint paintCircle;
    TextPaint textPaint;
    Paint cir1;
    Paint cir2;
    PathMeasure pathMeasure;
    final float[] pos = new float[2];

    float visible = 0f;
    ValueAnimator heartAnimation = null;

    public void startAnimation(boolean revers) {
        if (heartAnimation != null)
            heartAnimation.cancel();

        pathMeasure = new PathMeasure(heartPath, false);
        invalidate();
        if (revers)
            heartAnimation = ObjectAnimator.ofFloat(visible, 0f);
        else
            heartAnimation = ObjectAnimator.ofFloat(visible, 1f);
        heartAnimation.setDuration(2000);
        heartAnimation.addUpdateListener(animation -> {
            visible = (float) animation.getAnimatedValue();
            invalidate();
        });
        heartAnimation.start();
    }

    public ChartThree(Context context) {
        super(context);
        init();
    }

    public ChartThree(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ChartThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    boolean heartRateVisible = false;

    public void setHeartRateVisible(boolean visible) {
        heartRateVisible = visible;
        startAnimation(!visible);

    }

    public void addData(List<DataChartThree> data, List<Integer> data2) {
        list.addAll(data);
        heartList.addAll(data2);
        invalidate();
    }

    private void init() {
        list = new ArrayList<>();
        heartList = new ArrayList<>();

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
        textPaint.setColor(getResources().getColor(R.color.textColor));
        textPaint.setTextSize(dpToPx(12));

        heartPath = new Path();
        heartPathVisible = new Path();

        pathCircle = new Path();

        heart = new Paint();
        heart.setColor(Color.RED);
        heart.setStyle(Paint.Style.STROKE);
        heart.setStrokeWidth(5);

        cir1 = new Paint();
        cir1.setColor(Color.WHITE);
        cir1.setStyle(Paint.Style.FILL);
        cir1.setShadowLayer(6, 0, 0, Color.GRAY);

        cir2 = new Paint();
        cir2.setColor(Color.RED);
        cir2.setStyle(Paint.Style.FILL);

        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(getResources().getColor(R.color.gray));
        paintCircle.setStrokeCap(Paint.Cap.ROUND);
        CornerPathEffect corEffect = new CornerPathEffect(30);
        paintCircle.setPathEffect(corEffect);
    }


    private float dpToPx(float d) {
        return getResources().getDisplayMetrics().density * d;
    }


    private boolean drag = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!heartRateVisible)
            return false;

        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            drag = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            drag = false;
        }

        if (drag) {
            if (!(x > xToggle - dpToPx(30) && x < xToggle + dpToPx(30) && y > yToggle - dpToPx(30) && y < yToggle + dpToPx(30)))
                return false;

            xToggle = event.getX();

            invalidate();
        }


        return true;
    }

    String[] names = new String[]{"Deep", "Light", "REM", "Awake"};

    private float xToggle = 0f;
    private float yToggle = 0f;

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (xToggle == 0) {
            xToggle = getWidth() - dpToPx(40);
            yToggle = getHeight() - dpToPx(20);
        }

        float spaceH = (getHeight() - dpToPx(80)) / 4;

        float y = getHeight() - dpToPx(60);
        float yDeep = 0;
        float yLight = 0;
        float yREM = 0;
        float yAwake = 0;


        for (int i = 0; i < 4; i++) {
            canvas.drawLine(dpToPx(20), y, getWidth() - dpToPx(40), y, line);
            canvas.drawText(names[i], getWidth() - dpToPx(35), y - (spaceH / 2), textPaint);
            if (i == 0)
                yDeep = y;
            else if (i == 1)
                yLight = y;
            else if (i == 2)
                yREM = y;
            else if (i == 3)
                yAwake = y;
            y -= spaceH;
        }

        if (list.size() == 0)
            return;

        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getMinute();
        }
        double spaceW = (getWidth() - dpToPx(60)) / total;


        float left = dpToPx(20);
        for (int i = 0; i < list.size(); i++) {
            Paint paint = null;
            float top = 0;
            Log.i("TAG", "onDraw: " + spaceW + " " + list.get(i).getMinute() + " " + left + " " + list.get(i).getType());
            float right = left + ((float) (list.get(i).getMinute() * spaceW));
            float bottom = 0;

            if (list.get(i).getType() == ChartThreeType.DEEP) {
                paint = deep;
                top = yLight;
                bottom = yDeep;
            } else if (list.get(i).getType() == ChartThreeType.LIGHT) {
                paint = light;
                top = yREM;
                bottom = yLight;
            } else if (list.get(i).getType() == ChartThreeType.REM) {
                paint = rem;
                top = yAwake;
                bottom = yREM;
            } else if (list.get(i).getType() == ChartThreeType.AWAKE) {
                paint = awake;
                top = y;
                bottom = yAwake;
            }
            if (heartRateVisible && heartList.size() > 0)
                paint.setAlpha(150);
            else
                paint.setAlpha(255);

            canvas.drawRoundRect(left, top, right, bottom, 5, 5, paint);
            left += ((float) (list.get(i).getMinute() * spaceW));

        }

        if (heartList.size() == 0)
            return;

        data();


        if (visible == 0)
            return;


        if (visible < 1f) {
            heartPathVisible.reset();
            pathMeasure.getSegment(0, pathMeasure.getLength() * visible, heartPathVisible, true);
            canvas.drawPath(heartPathVisible, heart);

            pathCircle.reset();
            pathCircle.moveTo(0, getHeight() - dpToPx(25) * visible);
            pathCircle.lineTo(xToggle - dpToPx(21), getHeight() - dpToPx(25) * visible);
            pathCircle.lineTo(xToggle - dpToPx(20), getHeight() - dpToPx(25) * visible);
            pathCircle.lineTo(xToggle, getHeight() - dpToPx(35) * visible);
            pathCircle.lineTo(xToggle + dpToPx(20), getHeight() - dpToPx(25) * visible);
            pathCircle.lineTo(xToggle + dpToPx(21), getHeight() - dpToPx(25) * visible);
            pathCircle.lineTo(getWidth(), getHeight() - dpToPx(25) * visible);
            pathCircle.lineTo(getWidth(), getHeight());
            pathCircle.lineTo(0, getHeight());
            pathCircle.lineTo(0, getHeight() - dpToPx(25) * visible);

            canvas.drawRect(0, getHeight() - dpToPx(25) * visible, getWidth(), getHeight(), line);

            canvas.drawPath(pathCircle, paintCircle);
        } else {

            float biggest = 0;
            for (int i = 0; i < heartList.size(); i++) {
                if (heartList.get(i) > biggest)
                    biggest = heartList.get(i);
            }




            pathMeasure.getPosTan((pathMeasure.getLength() * (xToggle)) / (getWidth() - dpToPx(60)), pos, null);


            canvas.drawCircle(pos[0], pos[1], dpToPx(6), cir1);
            canvas.drawCircle(pos[0], pos[1], dpToPx(4), cir2);


            canvas.drawPath(heartPath, heart);


            canvas.drawLine(pos[0], dpToPx(20), pos[0], getHeight() - dpToPx(60), line);

            pathCircle.reset();
            pathCircle.moveTo(0, getHeight() - dpToPx(25));
            pathCircle.lineTo(pos[0] - dpToPx(21), getHeight() - dpToPx(25));
            pathCircle.lineTo(pos[0] - dpToPx(20), getHeight() - dpToPx(25));
            pathCircle.lineTo(pos[0], getHeight() - dpToPx(35));
            pathCircle.lineTo(pos[0] + dpToPx(20), getHeight() - dpToPx(25));
            pathCircle.lineTo(pos[0] + dpToPx(21), getHeight() - dpToPx(25));
            pathCircle.lineTo(getWidth(), getHeight() - dpToPx(25));
            pathCircle.lineTo(getWidth(), getHeight());
            pathCircle.lineTo(0, getHeight());
            pathCircle.lineTo(0, getHeight() - dpToPx(25));

            canvas.drawRect(0, getHeight() - dpToPx(25), getWidth(), getHeight(), line);

            canvas.drawPath(pathCircle, paintCircle);

            canvas.drawCircle(pos[0], getHeight() - dpToPx(20), dpToPx(10), cir1);

        }
    }

    private void data() {
        heartPath.reset();
        float biggest = 0;
        for (int i = 0; i < heartList.size(); i++) {
            if (heartList.get(i) > biggest)
                biggest = heartList.get(i);
        }

        float heartSpaceY = (getHeight() - dpToPx(80)) / biggest;
        float heartSpaceX = (getWidth() - dpToPx(60)) / heartList.size();
        float xH = 0;
        float lastX = 0;
        float lastY = 0;
        float lastY2 = 0;
        for (int i = 0; i < heartList.size(); i++) {
            float yH = ((getHeight() - dpToPx(60)) - (heartSpaceY * heartList.get(i)));
            if (i == 0) {
                xH = dpToPx(20);
                heartPath.moveTo(xH, yH);
            } else {
                xH += heartSpaceX;
                heartPath.quadTo(lastX, lastY, (lastX + xH) / 2, (lastY + yH) / 2);
                // heartPath.lineTo(xH,yH);
            }
            lastY2 = lastY;

            lastX = xH;
            lastY = yH;
        }

        heartPath.lineTo(getWidth() - dpToPx(40), (lastY2 + lastY) / 2);

    }
}

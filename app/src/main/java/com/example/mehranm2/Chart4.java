package com.example.mehranm2;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chart4 extends View {
    public Chart4(Context context) {
        super(context);
        init();

    }

    public Chart4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public Chart4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public Chart4(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    List<Paint> paints;
    List<Path> paths;
    Path percentPath;
    Path visPercentPath;
    TextPaint bigTextPaint;
    TextPaint medTextPaint;
    private float num;

    private void init() {
        num = 0;
        paints = new ArrayList<>();
        paths = new ArrayList<>();
        visPercentPath = new Path();

        bigTextPaint = new TextPaint();
        bigTextPaint.setColor(Color.WHITE);
        bigTextPaint.setTextSize(dpToPx(40));
        medTextPaint = new TextPaint();
        medTextPaint.setColor(Color.WHITE);
        medTextPaint.setTextSize(dpToPx(20));
        medTextPaint.setTextAlign(Paint.Align.CENTER);
        bigTextPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < 5; i++) {
            Paint shapeStyle = new Paint();
            shapeStyle.setColor(Color.parseColor("#FB8C00"));
            if (i > 2)
                shapeStyle.setPathEffect(new CornerPathEffect(30));
            shapeStyle.setStrokeWidth(dpToPx(2));
            if (i != 0)
                shapeStyle.setStyle(Paint.Style.STROKE);
            if (i == 1) {
                shapeStyle.setAlpha(30);
            }

            paths.add(new Path());
            paints.add(shapeStyle);
        }


    }

    public void setColor(int color) {
        for (int i = 0; i < paints.size(); i++) {
            paints.get(i).setColor(color);
            if (i == 1) {
                paints.get(i).setAlpha(30);
            }
        }
        invalidate();
    }


    ValueAnimator animatorValue = null;

    PathMeasure pathMeasure;

    public void setNum(int i) {
        if (animatorValue != null)
            animatorValue.cancel();

        if (num == i)
            return;
        if (i > 100)
            i = 100;

        pathMeasure = new PathMeasure(percentPath, false);
        invalidate();

        animatorValue = ObjectAnimator.ofFloat(num, i);
        animatorValue.setDuration(2000);
        animatorValue.addUpdateListener(animation -> {
            num = (float) animation.getAnimatedValue();
            invalidate();
        });
        animatorValue.start();
    }

    float visible = 0f;


    ValueAnimator animator = null;
    private boolean revers = false;

    private void startAnimation() {
        if (animator != null)
            animator.cancel();

        visible = 0f;
        if (revers)
            animator = ObjectAnimator.ofFloat(1f, 0f);
        else
            animator = ObjectAnimator.ofFloat(0f, 1f);

        animator.setDuration(1500);
        animator.addUpdateListener(animation -> {
            visible = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                revers = !revers;
                startAnimation();
            }
        });
        animator.start();
    }




    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (animator == null) {
            startAnimation();
            return;
        }
        float radius = (Math.min(getWidth(), getHeight()) / 2) - dpToPx(50);

        for (int i = 0; i < paths.size(); i++) {
            Paint paint = paints.get(i);
            Path path = drawPolygon(paths.get(i), (float) (getWidth() / 2), (float) (getHeight() / 2), 6, radius, false);


            if (i == 0)
                radius += dpToPx(5);
            else if (i == 2) {
                percentPath = path;
                radius += (dpToPx(15) - (dpToPx(4) * visible));
            } else if (i == 3) {
                float vis = Math.abs(1 - visible);
                paint.setAlpha(45 + ((int) (210 * vis)));
                radius += dpToPx(10) + (dpToPx(7) * visible);
            } else if (i == 4)
                paint.setAlpha(45 + ((int) (210 * visible)));

            if (i == 2) {
                if (pathMeasure != null) {
                    visPercentPath.reset();
                    pathMeasure.getSegment(0, pathMeasure.getLength() * num / 100, visPercentPath, true);
                    canvas.drawPath(visPercentPath, paint);
                }
            } else
                canvas.drawPath(path, paint);


        }


        int xPos = (canvas.getWidth() / 2);

        canvas.drawText(((int) num) + "", xPos, (canvas.getHeight() / 2), bigTextPaint);
        canvas.drawText("PAI", xPos, (canvas.getHeight() / 2) + dpToPx(35), medTextPaint);


    }

    private Path drawPolygon(Path path, float x, float y, float sides, float radius, boolean anticlockwise) {
        if (sides < 3) {
            return null;
        }
        path.reset();
        float a = ((float) Math.PI * 2) / sides * (anticlockwise ? -1 : 1);
        path.moveTo(x + radius, y);
        for (int i = 1; i < sides; i++) {
            path.lineTo(x + (radius * (float) Math.cos(a * i)), y + (radius * (float) Math.sin(a * i)));
        }
        path.close();
        return path;
    }


    private float dpToPx(float d) {
        return getResources().getDisplayMetrics().density * d;
    }

}

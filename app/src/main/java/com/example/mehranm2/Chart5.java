package com.example.mehranm2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chart5 extends View {

    List<Chart5Model> list;
    List<Chart5Model> visible;


    public Chart5(Context context) {
        super(context);
    }

    public Chart5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Chart5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public Chart5(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    Paint green;
    TextPaint text;
    TextPaint textBlack;
    Paint red;
    Paint line;

    private void init() {
        list = new ArrayList<>();
        visible = new ArrayList<>();
        float previousClose = 100f;

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            float open = previousClose;
            float close = open + random.nextFloat() * 10f - 5f;
            float high = Math.max(open, close) + random.nextFloat() * 5f;
            float low = Math.min(open, close) - random.nextFloat() * 5f;
            previousClose = close;
            list.add(new Chart5Model(open, close, high, low));
        }


        green = new Paint();
        green.setColor(Color.GREEN);
        line = new Paint();
        line.setColor(getResources().getColor(R.color.gray));
        red = new Paint();
        red.setColor(Color.RED);

        text = new TextPaint();
        text.setTextSize(dpToPx(13));
        text.setColor(Color.WHITE);
        text.setTextAlign(Paint.Align.CENTER);

        textBlack = new TextPaint();
        textBlack.setTextSize(dpToPx(14));
        textBlack.setColor(Color.BLACK);
        textBlack.setTextAlign(Paint.Align.CENTER);

    }


    float x, y;
    float xChart;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            x = ev.getX();
            y = ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (list.size() > 0) {
                float currX = ev.getX();
                float newOffsetX = (x - currX);

                if (xChart + newOffsetX <= dpToPx(5) * list.size()) {
                    xChart += newOffsetX;
                    x = ev.getX();
                }
                if (xChart < 0)
                    xChart = 0;

                invalidate();
            }
        }

        return true;

    }


    int from = 0;
    int to = 0;


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        float countItem = (getWidth() - dpToPx(45)) / dpToPx(6);

        if (xChart > 0)
            from = (int) (xChart / dpToPx(6));
        if (xChart > 0)
            to = (int) (from + countItem);
        else
            to = (int) countItem;
        addAll(from, to);
        float biggest = visible.get(0).getHigh();
        float lowest = visible.get(0).getLow();
        for (int i = 0; i < visible.size(); i++) {
            if (visible.get(i).getHigh() > biggest)
                biggest = visible.get(i).getHigh();

            if (visible.get(i).getLow() < lowest)
                lowest = visible.get(i).getLow();
        }
        float main = biggest - lowest;

        float plus = main / 5;


        float spaceH = (getHeight()-dpToPx(20)) / main;
        canvas.drawText(String.format("%.2f", biggest), getWidth() - dpToPx(27), spaceH+dpToPx(10), textBlack);
        canvas.drawText(String.format("%.2f", lowest), getWidth() - dpToPx(27), (spaceH * (biggest - lowest))+dpToPx(10), textBlack);

        canvas.drawLine(0,spaceH ,getWidth(),spaceH,line);
        canvas.drawLine(0,(spaceH * (biggest - lowest)),getWidth(),(spaceH * (biggest - lowest)),line);

        float c = biggest - plus;
        for (int i = 0; i < 4; i++) {
            canvas.drawText(String.format("%.2f", c), getWidth() - dpToPx(27), (spaceH * (biggest - c))+dpToPx(10) , textBlack);

            canvas.drawLine(0,(spaceH * (biggest - c)) + dpToPx(10),getWidth(),(spaceH * (biggest - c)),line);

            c -= plus;
        }
        float x = 0;
        for (int i = 0; i < visible.size(); i++) {
            float prevNum = biggest - visible.get(i).getOpen();
            float thisNum = biggest - visible.get(i).getClose();
            float prevNumLow = biggest - visible.get(i).getClose();
            float thisNumLow = biggest - visible.get(i).getLow();
            float prevNumHigh = biggest - visible.get(i).getHigh();
            float thisNumHigh = biggest - visible.get(i).getOpen();
            float num = prevNum - thisNum;
            if (num == 0)
                continue;
            Paint paint;

            boolean positive = num > 0;
            float startPos = 0;
            float stopPos = 0;


            if (positive) {
                stopPos = prevNum * spaceH;
                startPos = thisNum * spaceH;
                paint = green;
            } else {
                paint = red;
                startPos = prevNum * spaceH;
                stopPos = thisNum * spaceH;
            }


            float startX = x + dpToPx(1);
            x = startX + dpToPx(5);
            canvas.drawRect(startX, startPos, x, stopPos, paint);

            float startXH = ((x - startX) / 2) + startX - dpToPx(0.5f);
            canvas.drawRect(startXH, thisNumHigh * spaceH, startXH + dpToPx(0.5f), prevNumHigh * spaceH, positive ? green : red);


            float startXL = ((x - startX) / 2) + startX - dpToPx(0.5f);
            canvas.drawRect(startXL, prevNumLow * spaceH, startXL + dpToPx(0.5f), thisNumLow * spaceH, positive ? green : red);

            if (i == visible.size() - 1) {
                canvas.drawRect(getWidth() - dpToPx(40), ((stopPos - startPos) / 2) + startPos - dpToPx(10), getWidth(), ((stopPos - startPos) / 2) + startPos + dpToPx(10), positive ? green : red);
                canvas.drawText(String.format("%.2f", visible.get(i).getClose()), (getWidth() - dpToPx(20)), (((stopPos - startPos) / 2) + startPos + dpToPx(4f)), text);

            }
        }


    }

    private void addAll(int from, int to) {

        if (from > list.size() - 1)
            return;
        if (to > list.size() - 1)
            to = list.size() - 1;
        visible.clear();
        Log.i("TAG", "addAll: " + from + " " + to + " " + (to - from));

        int b = 0;
        for (int i = from; i < to; i++) {
            visible.add(list.get(i));

            b++;
        }

        Log.i("TAG", "addAll 2: " + b + " " + visible.size());

    }


    private float dpToPx(float d) {
        return getResources().getDisplayMetrics().density * d;
    }

}

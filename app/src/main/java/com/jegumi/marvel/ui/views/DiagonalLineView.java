package com.jegumi.marvel.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DiagonalLineView extends View {
    private Paint paint = new Paint();

    public DiagonalLineView(Context context) {
        this(context, null);
    }

    public DiagonalLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiagonalLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
    }
}
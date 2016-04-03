package com.jegumi.marvel.ui.views;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class TrapezoidTransformation implements Transformation {

    private int triangleSize;
    private boolean withBorder;
    private int borderColor;

    public TrapezoidTransformation(int triangleSize) {
        this(triangleSize, false, Color.TRANSPARENT);
    }

    public TrapezoidTransformation(int triangleSize, boolean withBorder, int borderColor) {
        this.triangleSize = triangleSize;
        this.withBorder = withBorder;
        this.borderColor = borderColor;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        int initY = height - triangleSize;

        Point[] points = {new Point(0, initY), new Point(width, initY), new Point(width, height)};
        drawArrows(points, canvas, paint);
        canvas.drawRect(new RectF(0, 0, width, initY), paint);
        canvas.drawLine(0, initY, width, initY, paint);

        if (withBorder) {
            Paint paintLine = new Paint();
            paintLine.setAntiAlias(true);
            paintLine.setStrokeWidth(6f);
            paintLine.setColor(borderColor);
            paintLine.setStyle(Paint.Style.STROKE);
            canvas.drawLine(0, initY, width, height, paintLine);
        }

        source.recycle();

        return bitmap;
    }

    private void drawArrows(Point[] point, Canvas canvas, Paint paint) {
        float [] points  = new float[8];
        points[0] = point[0].x;
        points[1] = point[0].y;
        points[2] = point[1].x;
        points[3] = point[1].y;
        points[4] = point[2].x;
        points[5] = point[2].y;
        points[6] = point[0].x;
        points[7] = point[0].y;

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 8, points, 0, null, 0, null, 0, null, 0, 0, paint);
        Path path = new Path();
        path.moveTo(point[0].x , point[0].y);
        path.lineTo(point[1].x,point[1].y);
        path.lineTo(point[2].x,point[2].y);
        canvas.drawPath(path, paint);
    }

    @Override
    public String key() {
        return "trapezoid";
    }
}

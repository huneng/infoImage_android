package com.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Histogram {
	public Paint paint;
	public float x1, x2, y1, y2;

	public Histogram() {
		paint = new Paint();
		paint.setColor(Color.BLACK);
		x1 = x2 = y1 = y2 = 0;

	}

	public Histogram(float topX, float topY, float bottomX, float bottomY,
			Paint p) {
		paint = new Paint(p);
		paint.setAntiAlias(true);
		x1 = topX;
		x2 = bottomX;
		y1 = topY;
		y2 = bottomY;
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(x1, y1, x2, y2, paint);
	}
}

package com.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Line {
	public Paint paint;
	public float x1, x2, y1, y2;

	public Line() {
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}

	public Line(float x1, float y1, float x2, float y2, Paint p) {
		paint = new Paint(p);
		paint.setAntiAlias(true);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	public void draw(Canvas canvas){
		canvas.drawLine(x1, y1, x2, y2, paint);
	}
}

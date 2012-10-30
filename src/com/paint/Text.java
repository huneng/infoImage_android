package com.paint;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Text {
	public float x, y;
	public String data;
	public Paint paint;
	public Text() {
		x = y = 0;
		data = "";
	}

	public Text setPaint(Paint p){
		paint = new Paint(p);
		return this;
	}
	
	public Text(String str) {
		x = y = 0;
		data = str;
	}

	public Text setLocation(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Text(String str, float x, float y) {
		this.x = x;
		this.y = y;
		data = str;
	}

	public void draw(Canvas canvas) {
		canvas.drawText(data, x, y, paint);
	}
}

package com.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class AxisDisplay {
	public int left, right, top, bottom;
	public int startYear;
	public float unit_w;

	public AxisDisplay(int l, int t, int r, int b, float u, int s) {
		left = l;
		right = r;
		top = t;
		bottom = b;
		startYear = s;
		unit_w = u;
	}

	public void draw(Canvas canvas) {
		int t = (int) ((right - left) / unit_w);
		for (int i = 0; i < t; i++) {
			Paint p = new Paint();
			p.setARGB(255, i % 2 * 100 + 100, i % 2 * 100 + 100,
					i % 2 * 100 + 100);
			canvas.drawRect(i * unit_w, top, (i + 1) * unit_w, bottom, p);
		}
		Paint p = new Paint();
		p.setTextSize(20);
		p.setColor(Color.BLACK);
		for (int i = 0; i < t; i++) {
			canvas.drawText("" + (startYear+i), i * unit_w + left + 10, bottom - 5,
					p);
		}
	}

}

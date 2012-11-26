package com.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Axis {
	public int left, top, width, height;
	public int x0, y0;
	private float xmapco, ymapco;
	public int xCount, yCount;
	private float axisW, axisH, axisY0, axisX0;
	public String title, titleX, titleY;

	public Axis(int l, int t, int w, int h, int xCount, int yCount, int x,
			int y, String title) {
		left = l;
		top = t;
		width = w;
		height = h;
		x0 = x;
		y0 = y;
		this.xCount = xCount;
		this.yCount = yCount;

		axisW = w - 40;
		axisH = h - 40;
		axisX0 = left + 30;
		axisY0 = top + 10;

		xmapco = xCount / axisW;
		ymapco = yCount / axisH;
		this.title = title;
		titleX = "Time";
		titleY = "Score";
	}

	public TimeScore map(float x, float y) {
		x = x - axisX0;
		y = y - axisY0;
		float x1 = x * xmapco;
		float y1 = y * ymapco;
		int year = (int) x1;
		int month = (int) ((x1 - year) * 12 + 1);
		if (month > 12) {
			month = 1;
			year++;
		}
		year += x0;
		int score = (int) (yCount - y1);
		score += y0;
		return new TimeScore(year, month, score);

	}

	public float[] inverseMap(TimeScore timescore) {
		float[] p = new float[2];
		int year = timescore.year;
		int month = timescore.month;
		int score = timescore.score;
		month = month - 1;
		if (month < 0) {
			month = 0;
			year--;
		}
		float x = year + ((float) month) / 12 - x0;
		x = x / xmapco;
		float y = yCount - score + y0;
		y = y / ymapco;
		p[0] = x + left;
		p[1] = y + top;
		return p;
	}

	public boolean contains(float x, float y) {
		if (x > axisX0 && y > axisY0) {
			if (x < axisX0 + axisW && y < axisY0 + axisH)
				return true;
		}
		return false;
	}

	public void drawAxis(Canvas canvas) {

		Paint p = new Paint();
		p.setColor(Color.BLACK);
		p.setStrokeWidth(5);
		canvas.drawLine(axisX0, axisY0 + axisH, axisX0, axisY0, p);
		canvas.drawLine(axisX0, axisY0 + axisH, axisX0 + axisW, axisY0 + axisH,
				p);
		p.setTextSize(10);
		for (int i = 0; i < xCount; i++) {
			float x = ((float) i) / xmapco + 10 + axisX0;
			float y = ((float) 10) / ymapco + 20 + axisY0;
			canvas.drawText("" + (x0 + i), x, y, p);
		}
		for (int i = 1; i < yCount; i++) {
			float x = 15 + left;
			float y = ((float) 10 - i) / ymapco + top;
			canvas.drawText("" + (i + y0), x, y, p);
		}
		p.setTextSize(24);
		canvas.drawText(title, left + width / 2, top + 20, p);
		p.setTextSize(15);
		if (!titleX.equals("")) {
			canvas.drawText(titleX, axisX0 + axisW, axisY0 + axisH, p);
		}
		p.setTextSize(15);
		if (!titleY.equals("")) {
			canvas.drawText(titleY, left + 10, top + 5, p);
		}
	}
}

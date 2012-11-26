package com.paint;

import android.graphics.PointF;

public class LineAxis {
	public int left, top, right, bottom;
	public int x0, y0;
	public float unit_w, unit_h;
	public float xCount, yCount;
	private float xmapco, ymapco;
	private int width, height;
	public LineAxis(int l, int t, int r, int b, int xCount, int yCount,
			int x, int y) {
		left = l;
		top = t;
		right = r;
		bottom = b;
		x0 = x;
		y0 = y;

		this.xCount = xCount;
		this.yCount = yCount;
		
		width = r-l;
		height = b-t;
		
		xmapco = this.xCount/width;
		ymapco = this.yCount/height;
	}

	public TimeScore map(float x, float y) {
		float x_ = x - left;
		float y_ = y - top;
		float x1 = x_ * xmapco;
		float y1 = y_ * ymapco;
		int year = (int) x1;
		int month = (int) ((x1 - year) * 12 + 1);
		if (month > 12) {
			month = 1;
			year++;
		}
		year += x0;
		float t =(yCount - y1+1);
		
		int score = (int) t ;
		if(t-score>=0.5)
			score++;
		score += y0;
		return new TimeScore(year, month, score);

	}

	public PointF inverseMap(TimeScore timescore) {
		int year = timescore.year;
		int month = timescore.month;
		int score = timescore.score;
		month = month - 1;
		if (month < 0) {
			month = 1;
			year--;
		}
		float x = year + ((float) month) / 12-this.x0;
		x = x / xmapco;
		float y = y0+yCount - score;
		y = y / ymapco+top;
		PointF p = new PointF(x, y);
		return p;
	}
	
	public boolean contains(float x, float y) {
		if (x > left && x < right && y < bottom && y > top)
			return true;
		return false;

	}
}

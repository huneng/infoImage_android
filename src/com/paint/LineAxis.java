package com.paint;

import android.graphics.PointF;

public class LineAxis {
	public int left, top, right, bottom;
	public int x0, y0;
	public float unit_w, unit_h;
	public LineAxis(int l, int t, int r, int b, float uw, float uh, int x, int y) {
		left = l;
		top = t;
		right = r;
		bottom = b;
		x0 = x;
		y0 = y;
		unit_w = uw;
		unit_h = uh;
	}

	public TimeScore inversemap(float x, float y) {
		float t = (x - left) / unit_w;
		int year = (int) t;
		int month = (int) ((t - year) * 12 + 1);

		year = year + x0;
		if (month > 12) {
			month = 1;
			year++;
		}
		int score = (int) ((bottom - y) / unit_h);

		TimeScore r = new TimeScore(year, month, score);
		return r;
	}

	public boolean contains(float x, float y){
		if(x>left&&x<right&&y<bottom&&y>top)
			return true;
		return false;
				
	}
	public PointF map(TimeScore t) {
		float x, y;
		x = t.year - x0;
		x += ((float)t.month - 1) / 12;
		y = t.score - y0;

		x *= unit_w;
		y *= unit_h;
		x += left;
		y = bottom-y;
		PointF point = new PointF(x, y);
		return point;
	}
}

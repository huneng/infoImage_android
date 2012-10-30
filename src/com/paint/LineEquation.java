package com.paint;

public class LineEquation {
	float x1, x2, y2, y1;

	public LineEquation(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public float getPoint_of_intersection(float x) {
		float y = -1;
		if (x >= x1 && x <= x2)
			y = (y2 - y1) / (x2 - x1) * (x - x1) + y1;
		return y;
	}
}

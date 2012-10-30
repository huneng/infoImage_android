package com.paint;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TextDisplay {
	List<String> texts;
	public int left, right, bottom, top;
	public TextDisplay(int l, int t, int r, int b) {
		left = l;
		right = r;
		top = t;
		bottom = b;
		texts = new LinkedList<String>();
	}

	public void add(String str) {
		texts.add(str);
	}

	public boolean contains(float x, float y) {
		if (x > left && x < right && y < bottom && y > top)
			return true;
		return false;
	}

	public void draw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		p.setTextSize(10);
		int size = texts.size();
		int t = 10;
		for (int i = 0; i < size; i++) {
			canvas.drawText(texts.get(i), left + t, i * 15 + 20, p);
			if((i+1)*15>bottom)
				t += 70;
		}

	}
}

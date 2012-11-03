package com.apk;

import java.util.List;

import com.json.SkillData;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class ArcGraph extends Activity {
	List<SkillData> skilldata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		skilldata = InputInterface.resume.myData.skills;
		int size = skilldata.size();
		float score[] = new float[size+1];
		String text[] = new String[size];
		for(int i = 0; i < size; i++){
			score[i+1] = skilldata.get(i).getAverage();
			text [i] = skilldata.get(i).getName();
		}
		MyView mv = new MyView(this, score, text);
		setContentView(mv);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyView extends View {
		Paint paint;
		RectF oval;
		float[] angle;
		String[] str;
		float x0, y0;
		float r;
		int color[] = { 0xff630000, 0xff000063, 0xff006300, 0xff00f800,
				0xff0000f8, 0xfff80000, 0xff00ff00, 0xffff0000, 0xff0000ff,
				0xff63f800, 0xff6300f8, 0xff0063f8, 0xfff86300, 0xfff80063,
				0xff00f863, 0xfff8ff00, 0xfff800ff, 0xff00f8ff, 0xffff00f8,
				0xff00fff8, 0xfffff800, 0xff0063ff, 0xff00ff63, 0xff6300ff,
				0xff63ff00, 0xffff0063, 0xffff6300, 0xff63f8ff, 0xff63fff8,
				0xfff863ff, 0xfff8ff63, 0xffff63f8, 0xfffff863 };

		public MyView(Context context, float[] score, String[] text) {
			super(context);
			int width = InputInterface.width;
			int height = InputInterface.height;
			paint = new Paint();
			oval = new RectF();
			r = (width - 100) / 2;

			x0 = width / 2;
			y0 = height / 2-40;
			oval.left = x0 - r;
			oval.right = x0 + r;
			oval.top = y0 - r;
			oval.bottom = y0 + r;
			angle = score;
			str = text;
			int len = angle.length-1;
			angle[0]=0;
			float sum = 0;
			for (int i = 0; i < len; i++) {
				angle[i + 1] = i + 1;
				sum += i + 1;
			}
			for (int i = 2; i < len + 1; i++) {
				angle[i] += angle[i - 1];
			}
			for (int i = 1; i < len + 1; i++) {
				angle[i] /= sum;
				angle[i] *= 360;
			}


		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			paint.setTextSize(20);
			for (int i = 0; i < angle.length - 1; i++) {
				paint.setColor(color[i * 2]);
				float t = angle[i + 1] - angle[i];
				canvas.drawArc(oval, angle[i], t, true, paint);

				paint.setColor(Color.BLACK);
				double a = angle[i] + t / 2;
				a /= 360;
				a *= 2 * 3.14;
				float x = (float) (x0 + r * Math.cos(a));
				float y = (float) (y0 + r * Math.sin(a));
				if (x > x0)
					x += 20;
				else
					x -= 20;
				if (y > y0)
					y += 20;
				else
					y -= 20;
				canvas.drawText(str[i], x, y, paint);
			}
		}

	}

}

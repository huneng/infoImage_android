package com.apk;

import java.util.LinkedList;
import java.util.List;

import com.dialog.ColorPickerDialog;
import com.dialog.OnInputListener;
import com.dialog.SimpleInputDialog;
import com.json.MyJson;
import com.json.SkillData;
import com.json.WorkData;
import com.paint.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Painting extends Activity implements
		ColorPickerDialog.OnColorChangedListener, OnInputListener {
	int height, width;
	int id;
	String title, textSize;
	Paint mPaint;
	MyView mv;
	MyJson myjson;
	Button colorBtn, sizeBtn;
	private boolean flag;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		flag = false;
		myjson = InputInterface.resume.myData;
		height = getWindowManager().getDefaultDisplay().getHeight();
		width = getWindowManager().getDefaultDisplay().getWidth();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		title = "";
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(false);
		mPaint.setStrokeWidth(2);
		initView();
	}

	private void initView() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		mv = new MyView(this);
		mv.setLayoutParams(new LayoutParams(width, height - 50));
		LinearLayout layout = new LinearLayout(this);
		LinearLayout btnLayout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		colorBtn = new Button(this);
		colorBtn.setLayoutParams(new LayoutParams(140, 50));
		colorBtn.setText("Color");
		colorBtn.setTextSize(10);
		colorBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new ColorPickerDialog(Painting.this, Painting.this, mPaint
						.getColor()).show();
			}
		});

		sizeBtn = new Button(this);
		sizeBtn.setText("Size");
		sizeBtn.setLayoutParams(new LayoutParams(140, 50));
		sizeBtn.setTextSize(10);
		sizeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SimpleInputDialog(Painting.this, Painting.this, "字号")
						.show();
			}
		});

		btnLayout.setGravity(Gravity.CENTER);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);
		btnLayout.addView(colorBtn);
		btnLayout.addView(sizeBtn);
		btnLayout.setBackgroundColor(Color.GRAY);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(mv);
		layout.addView(btnLayout);
		setContentView(layout);
	}

	public void colorChanged(int color) {
		mPaint.setColor(color);
	}

	@Override
	public void inputFinish(String str) {
		try {
			int t = Integer.parseInt(str);
			mPaint.setTextSize(t);
			return;
		} catch (Exception e) {
		}
		if (flag) {
			if (!str.equals(""))
				mv.addRemark(str);
			flag = false;
			return;
		}
		title = str;
	}

	class MyView extends View {
		AxisDisplay axis;
		LineAxis lineAxis;
		HistogramAxis rectAxis;
		List<Line> lines;
		List<Histogram> histograms;
		List<PointF> points;
		List<Text> texts;
		TextDisplay textDisplay;
		MyJson paintdata;
		Bitmap bitmap;
		int startValue, endValue;
		PointF point1, point2, curPoint;
		Paint textPaint, rectPaint;
		int colorIndex;
		int color[] = { 0xff630000, 0xff000063, 0xff006300, 0xff00f800,
				0xff0000f8, 0xfff80000, 0xff00ff00, 0xffff0000, 0xff0000ff,
				0xff63f800, 0xff6300f8, 0xff0063f8, 0xfff86300, 0xfff80063,
				0xff00f863, 0xfff8ff00, 0xfff800ff, 0xff00f8ff, 0xffff00f8,
				0xff00fff8, 0xfffff800, 0xff0063ff, 0xff00ff63, 0xff6300ff,
				0xff63ff00, 0xffff0063, 0xffff6300, 0xff63f8ff, 0xff63fff8,
				0xfff863ff, 0xfff8ff63, 0xffff63f8, 0xfffff863 };

		public MyView(Context context) {
			super(context);

			if (InputInterface.resume.photo == null) {
				bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_launcher);
				bitmap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

			} else {
				bitmap = Bitmap.createScaledBitmap(InputInterface.resume.photo,
						70, 70, true);
			}

			paintdata = InputInterface.resume.myData;
			startValue = paintdata.starttime;
			endValue = paintdata.endtime;

			rectPaint = new Paint();
			lines = new LinkedList<Line>();
			histograms = new LinkedList<Histogram>();
			texts = new LinkedList<Text>();
			points = new LinkedList<PointF>();
			point1 = new PointF(0, 0);
			point2 = new PointF(0, 0);
			curPoint = new PointF(0, 0);

			colorIndex = 0;

			textDisplay = new TextDisplay(width / 2, 0, width, 150);
			float unit_h = (height - 50 - 300 - 30) / 20;
			float unit_w = ((float) width) / (endValue - startValue + 1);
			float h1 = 150;
			float h2 = h1 + 10 * unit_h;
			float h3 = 30 + h2;
			float h4 = h3 + 10 * unit_h;
			axis = new AxisDisplay(0, (int) h2, width, (int) h3, unit_w,
					startValue);
			lineAxis = new LineAxis(0, (int) h1, width, (int) h2, unit_w,
					unit_h, startValue, 0);
			rectAxis = new HistogramAxis(0, (int) h3, width, (int) h4, unit_w,
					unit_h, startValue, 0);

			textPaint = new Paint();
			textPaint.setColor(Color.BLACK);
			textPaint.setStrokeWidth(1);
			textPaint.setTextSize(13);
			changeSkillDataToLine();
			changeWorkToRect();
			changeBaseInfoToText();
		}

		public void addRemark(String str) {
			textDisplay.add(str);
			paintdata.remarks.add(new String(str));
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawColor(Color.WHITE);

			rectPaint.setColor(Color.YELLOW);
			canvas.drawRect(0, 0, width, 150, rectPaint);
			rectPaint.setColor(Color.GRAY);
			canvas.drawRect(0, rectAxis.bottom, width, height - 50, rectPaint);
			rectPaint.setColor(Color.RED);
			canvas.drawRect(width / 2, 0, width, 150, rectPaint);

			canvas.drawBitmap(bitmap, 10, 10, null);

			axis.draw(canvas);
			int size = lines.size();
			for (int i = 0; i < size; i++) {
				Line line = lines.get(i);
				line.draw(canvas);
				if (line.y1 == lineAxis.top + 20)
					continue;
				String text = "" + lineAxis.inversemap(line.x1, line.y1).score;
				canvas.drawText(text, line.x1, line.y1 - 10, textPaint);
				text = "" + lineAxis.inversemap(line.x2, line.y2).score;
				canvas.drawText(text, line.x2, line.y2 - 10, textPaint);
			}
			size = histograms.size();
			for (int i = 0; i < size; i++)
				histograms.get(i).draw(canvas);
			size = texts.size();
			for (int i = 0; i < size; i++)
				texts.get(i).draw(canvas);
			textDisplay.draw(canvas);

			if (!curPoint.equals(0, 0)) {
				canvas.drawCircle(curPoint.x, curPoint.y, 5, mPaint);
				if (lineAxis.contains(curPoint.x, curPoint.y)) {
					String text = lineAxis.inversemap(curPoint.x, curPoint.y)
							.changeToString();
					canvas.drawText(text, curPoint.x - 40, curPoint.y - 70,
							textPaint);
				} else if (rectAxis.contains(curPoint.x, curPoint.y)) {
					String text = rectAxis.inverseMap(curPoint.x, curPoint.y)
							.changeToString();
					canvas.drawText(text, curPoint.x - 100, curPoint.y+20,
							textPaint);
				}
			}
			size = points.size();

			if (size != 0) {
				if (!curPoint.equals(0, 0)) {
					canvas.drawLine(points.get(size - 1).x,
							points.get(size - 1).y, curPoint.x, curPoint.y,
							mPaint);
				}
			}

			if (size == 1) {
				canvas.drawCircle(points.get(0).x, points.get(0).y, 5, mPaint);
			} else if (size > 1) {
				for (int i = 0; i < size - 1; i++)
					canvas.drawLine(points.get(i).x, points.get(i).y,
							points.get(i + 1).x, points.get(i + 1).y, mPaint);
			}

			if (!point1.equals(0, 0)) {
				canvas.drawCircle(point1.x, point1.y, 5, mPaint);
				if (!curPoint.equals(0, 0))
					canvas.drawRect(point1.x, rectAxis.top, curPoint.x,
							curPoint.y, mPaint);
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				curPoint.set(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				curPoint.set(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				curPoint.set(0, 0);
				touch_up(x, y);
				invalidate();
				break;
			}
			return true;
		}

		void touch_up(float x, float y) {
			if (lineAxis.contains(x, y)) {
				point1.set(0, 0);
				if (points.isEmpty()) {
					new SimpleInputDialog(Painting.this, Painting.this,
							"Skill Name").show();
				} else if (title.equals("")) {
					points.clear();
					return;
				} else if (points.get(points.size() - 1).x >= x) {
					changePointsToSkill();
					return;
				}
				points.add(new PointF(x, y));
			} else if (rectAxis.contains(x, y)) {
				if (points.size() != 0) {
					if (points.size() > 1)
						changePointsToSkill();
					points.clear();
				}
				if (point1.equals(0, 0)) {
					point1.set(x, y);
					new SimpleInputDialog(Painting.this, Painting.this,
							"Work,Company").show();
				} else if (title.equals("")) {
					point1.set(0, 0);
				} else {
					point2.set(x, y);
					changePointsToWork();
				}
			} else if (textDisplay.contains(x, y)) {
				if (points.size() != 0) {
					if (points.size() > 1)
						changePointsToSkill();
					points.clear();
				}
				flag = true;
				new SimpleInputDialog(Painting.this, Painting.this, "Remark")
						.show();
			}
		}

		void changeSkillDataToLine() {
			int size = paintdata.skills.size();
			for (int i = 0; i < size; i++) {
				Paint lineP = new Paint();
				lineP.setColor(color[colorIndex++]);
				lineP.setStrokeWidth(2);
				SkillData skill = paintdata.skills.get(i);
				float x0 = i * 20 + 20;
				float y0 = lineAxis.top + 20;
				float x_ = i * 20 + 20;
				float y_ = -1;
				int len = skill.length();
				float[] time_score = skill.getTimeScore();
				float x1, y1, x2, y2;
				x1 = x2 = y1 = y2 = 0;
				for (int j = 0; j < len - 1; j++) {
					TimeScore timeScore = new TimeScore(time_score[j]);
					PointF pt = lineAxis.map(timeScore);
					x1 = pt.x;
					y1 = pt.y;
					timeScore = new TimeScore(time_score[j + 1]);
					pt = lineAxis.map(timeScore);
					x2 = pt.x;
					y2 = pt.y;

					lines.add(new Line(x1, y1, x2, y2, mPaint));
					if (y_ == -1) {
						LineEquation equation = new LineEquation(x1, y1, x2, y2);
						y_ = equation.getPoint_of_intersection(x_);
					}
					if (y_ == -1 && x1 > x0 && x2 > x0) {
						y_ = y1;
						x_ = x1;
					}
				}
				if (y_ == -1) {
					x_ = x2;
					y_ = y2;
				}

				Text t = new Text(skill.getName());
				t.setLocation(x0, y0);
				t.setPaint(textPaint);
				texts.add(t);
				Paint paint = new Paint(lineP);
				paint.setStrokeWidth(1);
				paint.setAlpha(50);
				lines.add(new Line(x0, y0, x_, y_, paint));
			}
		}

		void changeWorkToRect() {
			int size = paintdata.works.size();
			for (int i = 0; i < size; i++) {
				Paint rectPaint = new Paint();
				rectPaint.setColor(color[colorIndex++]);
				WorkData work = paintdata.works.get(i);
				int time1 = work.getBeginTime();
				int time2 = work.getEndTime();
				int score = work.getScore();
				TimeScore timescore1 = new TimeScore(time1 / 100, time1 % 100,
						score);
				TimeScore timescore2 = new TimeScore(time2 / 100, time2 % 100,
						score);
				PointF point = rectAxis.map(timescore1);
				float x1 = point.x;
				float y1 = point.y;
				point = rectAxis.map(timescore2);
				float x2 = point.x;
				float y2 = point.y;
				histograms.add(new Histogram(x1, rectAxis.top, x2, y2,
						rectPaint));
				texts.add(new Text(work.getName()+work.getCompany()).setPaint(textPaint)
						.setLocation(x1 + 5, y1 + 10));
			}
		}

		void changeBaseInfoToText() {
			textDisplay.add(paintdata.job);
			textDisplay.add(paintdata.holiday);
			textDisplay.add(paintdata.salary);

			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(20);
			Text text = new Text(paintdata.name);
			text.setPaint(paint);
			text.setLocation(100, 30);
			texts.add(text);

			paint.setTextSize(10);
			text = new Text(paintdata.birth);
			text.setPaint(paint);
			text.setLocation(100, 50);
			texts.add(text);

			paint.setTextSize(20);
			text = new Text(paintdata.address);
			text.setPaint(paint);
			text.setLocation(15, 100);
			texts.add(text);

			paint.setTextSize(15);
			text = new Text(paintdata.phone);
			text.setPaint(paint);
			text.setLocation(15, 125);
			texts.add(text);
		}

		void changePointsToSkill() {
			String str = new String(title);
			title = "";
			int size = points.size();
			SkillData skill = new SkillData();
			skill.setName(str);
			for (int i = 0; i < size; i++) {
				TimeScore t = lineAxis.inversemap(points.get(i).x,
						points.get(i).y);
				float f = t.toFloat();
				skill.add(f);
			}
			int s = paintdata.skills.size();
			paintdata.skills.add(skill);
			float x0 = s * 20 + 20;
			float y0 = lineAxis.top + 20;
			float x_ = s * 20 + 20;
			float y_ = -1;
			float x1, x2, y1, y2;
			x1 = x2 = y1 = y2 = 0;
			for (int i = 0; i < size - 1; i++) {
				PointF point1 = points.get(i);
				PointF point2 = points.get(i + 1);
				x1 = point1.x;
				y1 = point1.y;
				x2 = point2.x;
				y2 = point2.y;
				lines.add(new Line(x1, y1, x2, y2, mPaint));
				if (y_ == -1) {
					LineEquation equation = new LineEquation(x1, y1, x2, y2);
					y_ = equation.getPoint_of_intersection(x_);
				}
				if (y_ == -1 && x1 > x0 && x2 > x0) {
					y_ = y1;
					x_ = x1;
				}
			}
			if (y_ == -1) {
				x_ = x2;
				y_ = y2;
			}
			Paint paint = new Paint(mPaint);
			paint.setAlpha(50);
			lines.add(new Line(x0, y0, x_, y_, paint));
			Text t = new Text(skill.getName());
			t.setLocation(x0, y0);
			t.setPaint(textPaint);
			texts.add(t);

			points.clear();
		}

		void changePointsToWork() {
			String str = new String(title);
			title = "";
			WorkData work = new WorkData();
			try{
			work.setName(str.substring(0, str.indexOf(',')));
			work.setCompany(str.substring(str.indexOf(',')+1));}
			catch(Exception e){
				point1.set(0, 0);
				point2.set(0, 0);
				Toast.makeText(getApplicationContext(), "Dialog input wrong", Toast.LENGTH_SHORT).show();
				return;
			}
			TimeScore t = rectAxis.inverseMap(point1.x, point1.y);
			work.setBeginTime(t.year * 100 + t.month);
			t = rectAxis.inverseMap(point2.x, point2.y);
			work.setEndTime(t.year * 100 + t.month);
			work.setScore(t.score);
			paintdata.works.add(work);
			float x1 = point1.x;
			float x2 = point2.x;
			float y = point2.y;
			histograms.add(new Histogram(x1, rectAxis.top, x2, y, mPaint));

			Text text = new Text(str);
			text.setPaint(textPaint);
			text.setLocation(x1 + 5, y + 10);
			texts.add(text);

			point1.set(0, 0);
			point2.set(0, 0);

		}
	}

}
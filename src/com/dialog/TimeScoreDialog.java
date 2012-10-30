package com.dialog;

import com.apk.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TimeScoreDialog extends Dialog {
	private EditText ed1, ed2, ed3;
	public static int year, month, score;
	public Button setBtn, cancelBtn;
	private Button yearPlusBtn, yearAddBtn;
	private OnInputListener mListener;
	private int startYear, endYear;

	public TimeScoreDialog(Context context, OnInputListener listener,
			int start, int end) {
		super(context);
		setContentView(R.layout.skill_time_score);
		mListener = listener;
		year = start;
		month = 9;
		score = 9;
		startYear = start;
		endYear = end;
		initBtn();

	}

	private void initBtn() {
		yearAddBtn = (Button) findViewById(R.id.year_add);
		yearAddBtn.setText("+");
		yearAddBtn.setOnClickListener(l);
		yearPlusBtn = (Button) findViewById(R.id.year_plus);
		yearPlusBtn.setText("-");
		yearPlusBtn.setOnClickListener(l);
		yearPlusBtn.setEnabled(false);

		Button btn = (Button) findViewById(R.id.month_add);
		btn.setText("+");
		btn.setOnClickListener(l);
		btn = (Button) findViewById(R.id.month_plus);
		btn.setText("-");
		btn.setOnClickListener(l);

		btn = (Button) findViewById(R.id.score_add);
		btn.setText("+");
		btn.setOnClickListener(l);
		btn = (Button) findViewById(R.id.score_plus);
		btn.setText("-");
		btn.setOnClickListener(l);

		setBtn = (Button) findViewById(R.id.set_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		setBtn.setOnClickListener(l);
		cancelBtn.setOnClickListener(l);

		ed1 = (EditText) findViewById(R.id.year_edit);
		ed1.setText("" + year);
		ed2 = (EditText) findViewById(R.id.month_edit);
		ed2.setText("" + month);
		ed3 = (EditText) findViewById(R.id.score_edit);
		ed3.setText("" + score);
	}

	private void update_year_btn() {
		yearPlusBtn.setEnabled(true);
		yearAddBtn.setEnabled(true);
		if (year == startYear)
			yearPlusBtn.setEnabled(false);
		if (year == endYear)
			yearAddBtn.setEnabled(false);
	}

	android.view.View.OnClickListener l = new android.view.View.OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.year_add:
				year++;
				ed1.setText("" + year);
				update_year_btn();
				break;
			case R.id.year_plus:
				year--;
				ed1.setText("" + year);
				update_year_btn();
				break;
			case R.id.month_add:
				month++;
				if (month > 12)
					month = 1;
				ed2.setText("" + month);
				break;
			case R.id.month_plus:
				month--;
				if (month < 1)
					month = 12;
				ed2.setText("" + month);
				break;
			case R.id.score_add:
				score++;
				score %= 10;
				ed3.setText("" + score);
				break;
			case R.id.score_plus:
				score--;
				score += 10;
				score %= 10;
				ed3.setText("" + score);
				break;
			case R.id.set_btn:
				String m = "" + month;
				if (month < 10) {
					m = "0" + month;
				}
				String str = "" + year + m + ".0" + score + " ";
				mListener.inputFinish(str);
				dismiss();
				break;
			case R.id.cancel_btn:
				dismiss();
				break;
			}
		}

	};
}

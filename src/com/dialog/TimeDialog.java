package com.dialog;

import com.apk.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TimeDialog extends Dialog {
	int time1, time2;
	Button btn1, btn2, btn3, btn4;
	EditText et1, et2;
	Button ed;

	public TimeDialog(Context context, Button ed) {
		super(context);
		setContentView(R.layout.time_dialog);
		time1 = time2 = 2010;
		btn1 = (Button) findViewById(R.id.time1_add);
		btn2 = (Button) findViewById(R.id.time1_plus);
		btn3 = (Button) findViewById(R.id.time2_add);
		btn4 = (Button) findViewById(R.id.time2_plus);
		btn1.setOnClickListener(l);
		btn2.setOnClickListener(l);
		btn3.setOnClickListener(l);
		btn4.setOnClickListener(l);
		btn1.setText("+");
		btn3.setText("+");
		btn2.setText("-");
		btn4.setText("-");
		btn4.setEnabled(false);
		et1 = (EditText) findViewById(R.id.time1_display);
		et1.setText("2010");
		et2 = (EditText) findViewById(R.id.time2_display);
		et2.setText("2010");
		Button btn = (Button) findViewById(R.id.time_set);
		btn.setOnClickListener(l);
		btn = (Button) findViewById(R.id.time_cancel);
		
		this.ed = ed;
	}

	android.view.View.OnClickListener l = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.time1_add:
				time1++;
				et1.setText("" + time1);
				if (time2 == time1) {
					btn4.setEnabled(false);
				} else if (time1 > time2) {
					time2++;
					et2.setText("" + time2);
				}
				break;
			case R.id.time1_plus:
				time1--;
				if (!btn4.isEnabled())
					btn4.setEnabled(true);
				et1.setText("" + time1);
				break;
			case R.id.time2_add:
				time2++;
				et2.setText("" + time2);
				if (!btn4.isEnabled())
					btn4.setEnabled(true);
				break;
			case R.id.time2_plus:
				time2--;
				et2.setText("" + time2);
				if (time2 == time1)
					btn4.setEnabled(false);
				break;
			case R.id.time_set:
				ed.setText("" + time1 + '-' + time2);
			case R.id.time_cancel:
				dismiss();
				break;
			}
		}
	};

}

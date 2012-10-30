package com.apk;

import java.util.LinkedList;
import java.util.List;

import com.json.WorkData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Work extends Activity {
	private EditText name_ed, company_ed, time_ed;
	private Button btn1, btn2;
	public static List<String> name = new LinkedList<String>();
	public static List<String> company = new LinkedList<String>();
	public static List<String> time = new LinkedList<String>();
	int now;
	InputInterface parent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parent = InputInterface.resume;
		setContentView(R.layout.work);

		btn1 = (Button) findViewById(R.id.work_front);
		btn2 = (Button) findViewById(R.id.work_next);
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn1.setEnabled(false);
		btn1.setText("    <");
		btn2.setText(">    ");
		btn2.setEnabled(false);

		Button btn = (Button) findViewById(R.id.work_new);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.work_delete);
		btn.setOnClickListener(listener);
		name_ed = (EditText) findViewById(R.id.work_edit);
		company_ed = (EditText) findViewById(R.id.company_edit);
		time_ed = (EditText) findViewById(R.id.time_time_score_edit);
	}

	@Override
	protected void onResume() {
		name.clear();
		company.clear();
		time.clear();
		List<WorkData> works = parent.myData.works;
		int size = works.size();
		for(int i = 0; i < size; i++){
			WorkData w = works.get(i);
			name.add(new String(w.getName()));
			company.add(new String(w.getCompany()));
			String str = ""+w.getBeginTime()+'-'+w.getEndTime()+'-'+w.getScore();
			time.add(str);
		}
		if (name.size() == 0) {
			name.add("");
			company.add("");
			time.add("");
			initEdit("", "", "");
			now = 0;
		} else {
			if (now >= name.size())
				now = 0;
			initEdit(name.get(now), company.get(now), time.get(now));
			update_btn();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		saveData();
		int size = name.size();
		if (name.get(size - 1).equals("")) {
			name.remove(size - 1);
			company.remove(size - 1);
			time.remove(size - 1);
		}

		List<WorkData> works = parent.myData.works;
		works.clear();
		size = name.size();
		for(int i = 0; i < size; i++){
			WorkData w = new WorkData();
			w.setName(name.get(i));
			w.setCompany(company.get(i));
			w.setTimeScore(time.get(i));
			works.add(w);
		}
		super.onPause();
	}

	private void initEdit(String a, String b, String c) {
		name_ed.setText(a);
		company_ed.setText(b);
		time_ed.setText(c);
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.work_front:
				front();
				break;
			case R.id.work_next:
				next();
				break;
			case R.id.work_new:
				newWork();
				break;
			case R.id.work_delete:
				delWork();
				break;
			}
		}

	};

	private void next() {
		saveData();
		if (!check_edit())
			return;
		now++;
		initEdit(name.get(now), company.get(now), time.get(now));
		update_btn();
	}

	private void update_btn() {
		btn1.setEnabled(true);
		btn2.setEnabled(true);
		if (now == 0)
			btn1.setEnabled(false);
		if (now >= name.size() - 1)
			btn2.setEnabled(false);
	}

	private void front() {
		saveData();
		if (!check_edit())
			return;
		now--;
		initEdit(name.get(now), company.get(now), time.get(now));
		update_btn();
	}

	private void delWork() {
		int size = name.size();
		if (size == 1) {
			initEdit("", "", "");
			now = 0;
		}
		if (size > 1) {
			name.remove(now);
			company.remove(now);
			time.remove(now);
			now--;
			if (now == -1)
				now = 0;
			initEdit(name.get(now), company.get(now), time.get(now));
		}
	}

	private void newWork() {
		saveData();
		if (!check_edit())
			return;
		name.add("");
		company.add("");
		time.add("");
		initEdit("", "", "");
		now = name.size() - 1;
		update_btn();
	}

	private void saveData() {
		name.set(now, name_ed.getText().toString());
		company.set(now, company_ed.getText().toString());
		time.set(now, time_ed.getText().toString());
	}

	private boolean check_edit() {
		if (name.get(now) == "") {
			Toast.makeText(this, "Name is blank", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (company.get(now) == "") {
			Toast.makeText(this, "Company is blank", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (time.get(now) == "") {
			Toast.makeText(this, "Time is blank", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}
}
package com.apk;

import java.util.LinkedList;
import java.util.List;

import com.dialog.OnInputListener;
import com.dialog.TimeScoreDialog;
import com.json.MyJson;
import com.json.SkillData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Skill extends Activity implements OnInputListener {
	private EditText skill_ed, time_score_ed;
	private Button btn1, btn2, btn3;
	private int now;
	private List<String> skillname = new LinkedList<String>();
	private List<String> time_score = new LinkedList<String>();;
	int yearStart, yearEnd;
	InputInterface parent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parent = InputInterface.resume;
		setContentView(R.layout.skill);
		btn1 = (Button) findViewById(R.id.skill_front);
		btn2 = (Button) findViewById(R.id.skill_next);
		btn3 = (Button) findViewById(R.id.time_score_add);
		btn1.setText("    <");
		btn2.setText(">    ");
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		
		Button btn = (Button) findViewById(R.id.skill_new);;
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.skill_delete);
		btn.setOnClickListener(listener);
		skill_ed = (EditText) findViewById(R.id.skill_edit);
		
		time_score_ed = (EditText) findViewById(R.id.time_score_edit);
		

	}

	OnClickListener listener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.skill_front:
				front();
				break;
			case R.id.skill_next:
				next();
				break;
			case R.id.time_score_add:
				new TimeScoreDialog(Skill.this, Skill.this, yearStart++, yearEnd).show();
				break;
			case R.id.skill_new:
				newSkill();
				break;
			case R.id.skill_delete:
				delSkill();
				break;
			}
		}

	};

	private void initEdit(String a, String b) {
		skill_ed.setText(a);
		time_score_ed.setText(b);
	}

	@Override
	protected void onResume() {
		MyJson myjson = parent.myData;
		List<SkillData> skills = myjson.skills;
		skillname.clear();
		time_score.clear();
		int size = skills.size();
		for(int i = 0; i < size; i++){
			skillname.add(new String(skills.get(i).getName()));
			time_score.add(new String(skills.get(i).getTime_Score()));
		}
		if (skillname.size() == 0) {
			skillname.add("");
			time_score.add("");
			initEdit("", "");
			now = 0;
		} else {
			if (now >= skillname.size())
				now = 0;
			initEdit(skillname.get(now), time_score.get(now));
			update_button();
		}
		yearStart = myjson.starttime;
		yearEnd = myjson.endtime;

		super.onResume();
	}

	@Override
	protected void onPause() {
		saveData();
		int size = skillname.size();
		if (skillname.get(size - 1).equals("")) {
			skillname.remove(size - 1);
			time_score.remove(size - 1);
		}
		MyJson myjson = parent.myData;
		List<SkillData> skills = myjson.skills;
		skills.clear();
		size = skillname.size();
		for(int i = 0; i < size; i++){
			SkillData s = new SkillData();
			s.setName(skillname.get(i));
			s.setTimeScore(time_score.get(i));
			skills.add(s);
		}
		super.onPause();
	}

	private void saveData() {
		skillname.set(now, skill_ed.getText().toString());
		time_score.set(now, time_score_ed.getText().toString());

	}

	private boolean check_edit() {
		if (skillname.get(now) == "") {
			Toast.makeText(this, "Skill name is blank", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (time_score.get(now) == "") {
			Toast.makeText(this, "Time is blank", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	private void next() {
		saveData();
		if (!check_edit())
			return;
		now++;
		initEdit(skillname.get(now), time_score.get(now));
		update_button();
	}

	private void front() {
		saveData();
		if (!check_edit())
			return;
		now--;
		initEdit(skillname.get(now), time_score.get(now));
		update_button();
	}

	private void newSkill() {
		saveData();
		if (!check_edit())
			return;
		now++;
		skillname.add("");
		time_score.add("");
		initEdit("", "");
		update_button();
	}

	private void delSkill() {
		int size = skillname.size();
		if (size == 1) {
			initEdit("", "");
		}
		if (size > 1) {
			skillname.remove(now);
			time_score.remove(now);
			now--;
			if (now == -1) {
				now = 0;
			}
			initEdit(skillname.get(now), time_score.get(now));
		}
		update_button();
	}

	private void update_button() {
		btn1.setEnabled(true);
		btn2.setEnabled(true);
		if (now == 0)
			btn1.setEnabled(false);
		if (now >= skillname.size() - 1)
			btn2.setEnabled(false);
	}


	public void inputFinish(String str) {
		String t = time_score_ed.getText().toString();
		t += str;
		time_score_ed.setText(t);

	}

}

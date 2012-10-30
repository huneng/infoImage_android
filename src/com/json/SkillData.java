package com.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SkillData {
	private String skillname;
	private float time_score[];
	private int size;

	public SkillData() {
		skillname = "";
		time_score = new float[10];
		size = 0;
	}

	public SkillData(JSONObject object) throws JSONException {
		time_score = new float[10];
		size = 0;
		skillname = object.getString("skillname");
		JSONArray array = object.getJSONArray("evaluates");
		int s = array.length();
		for (int i = 0; i < s; i++) {
			JSONObject o = array.getJSONObject(i);
			float time = (float)o.getInt("time");
			float score = (float)o.getInt("score");
			time_score[size++] = time + score / 100;
		}
	}

	public void add(float a) {
		time_score[size++] = a;
	}

	public SkillData setName(String str) {
		skillname = str;
		return this;
	}
	public int length(){
		return size;
	}
	public SkillData setTimeScore(String str) {
		size = 0;
		String t = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == ' ') {
				if (t == "")
					continue;
				time_score[size++] = Float.parseFloat(t);
				t = "";
			} else {
				t += c;
			}
		}
		return this;
	}

	public String getName() {
		return skillname;
	}

	public String getTime_Score() {
		String str = "";
		for (int i = 0; i < size; i++) {
			str += time_score[i] + " ";
		}
		return str;
	}
	public float[] getTimeScore() {
		return time_score;
	}
	public JSONObject changeToJson() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("skillname", skillname);
		JSONArray evaluates = new JSONArray();
		for (int i = 0; i < size; i++) {
			JSONObject o = new JSONObject();
			int time = (int) time_score[i];
			int score = (int) ((time_score[i] - time) * 100);
			o.put("time", time);
			o.put("score", score);
			evaluates.put(o);
		}
		object.put("evaluates", evaluates);
		return object;
	}

}

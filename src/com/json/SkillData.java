package com.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SkillData {
	private String skillname;
	private float time_score[];
	private int size;
	private int average;
	public SkillData() {
		skillname = "";
		time_score = new float[10];
		size = 0;
		average = 0;
	}

	public SkillData(JSONObject object) throws JSONException {
		time_score = new float[10];
		size = 0;
		skillname = object.getString("skillname");
		JSONArray array = object.getJSONArray("evaluates");
		int s = array.length();
		int sum = 0;
		for (int i = 0; i < s; i++) {
			JSONObject o = array.getJSONObject(i);
			float time = (float)o.getInt("time");
			float score = (float)o.getInt("score");
			time_score[size++] = time + score / 100;
			sum+=score;
		}
		average = 0;
		if(size>0)
		average = sum/size;
		
	}

	public void add(float a) {
		average = average*size;
		time_score[size++] = a;
		float t = a-(int)a;
		t*=100;
		average = (int)(average+t)/size;
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
		int sum = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == ' ') {
				if (t == "")
					continue;
				time_score[size++] = Float.parseFloat(t);
				sum+=(time_score[size-1]-(int)time_score[size-1])*100;
				t = "";
			} else {
				t += c;
			}
		}
		if(sum!=0)
			average = sum/size;
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
	public int getAverage(){
		return average;
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

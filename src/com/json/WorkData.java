package com.json;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkData {
	private String workname;
	private String company;
	private int begintime;
	private int endtime;
	private int score;

	public WorkData() {
		workname = "";
		company = "";
		begintime = 0;
		endtime = 0;
		score = 0;
	}

	public WorkData(JSONObject object) throws JSONException {
		workname = object.getString("position");
		company = object.getString("company");
		begintime = object.getInt("begintime");
		endtime = object.getInt("endtime");
		score = object.getInt("score");
	}

	public WorkData setName(String str) {
		workname = str;
		return this;
	}

	public JSONObject changToJson() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("position", workname);
		object.put("company", company);
		object.put("begintime", begintime);
		object.put("endtime", endtime);
		object.put("score", score);
		return object;
	}

	public WorkData setCompany(String str) {
		company = str;
		return this;
	}

	public WorkData setBeginTime(int time) {
		begintime = time;
		return this;
	}

	public WorkData setEndTime(int time) {
		endtime = time;
		return this;
	}

	public WorkData setScore(int score) {
		this.score = score;
		return this;
	}

	public String getName() {
		return workname;
	}

	public String getCompany() {
		return company;
	}

	public int getBeginTime() {
		return begintime;
	}

	public int getEndTime() {
		return endtime;
	}

	public int getScore() {
		return score;
	}

	public void setTimeScore(String str) {
		if (str.equals("")) 
			return;
		
		int i1, i2;
		i1 = 0;
		i2 = str.indexOf('-');
		begintime = Integer.parseInt(str.substring(i1, i2));
		i1 = i2 + 1;
		i2 = str.indexOf('-', i1);
		endtime = Integer.parseInt(str.substring(i1, i2));
		i1 = i2 + 1;
		score = Integer.parseInt(str.substring(i1));
	}

	public void setTimeScore(int time1, int time2, int score) {
		begintime = time1;
		endtime = time2;
		this.score = score;
	}
}
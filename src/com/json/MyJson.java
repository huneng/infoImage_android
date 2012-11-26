package com.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJson {
	public String name, birth, phone, address, job, salary, holiday, sex;
	public List<SkillData> skills;
	public List<WorkData> works;
	public int starttime, endtime;
	public List<String> remarks;
	public String usr;
	public int id;

	public MyJson() {
		id = 0;
		usr = "";
		name = "huneng";
		birth = "1991-7-18";
		phone = "110";
		address = "zhejiang,hangzhou";
		job = "programmer";
		holiday = "10 day";
		salary = "10000 dollar";
		sex="male";
		starttime = 2010;
		endtime = 2014;
		skills = new LinkedList<SkillData>();
		works = new LinkedList<WorkData>();
		remarks = new LinkedList<String>();
	}

	public void set_id_usr(int id, String usr) {
		this.id = id;
		this.usr = usr;
	}

	public void setTime(String str) {
		try {
			starttime = Integer.parseInt(str.substring(0, str.indexOf('-')));
			endtime = Integer.parseInt(str.substring(str.indexOf('-') + 1));
		} catch (Exception e) {
			return;
		}
	}

	public void setRemark(String str) {
		remarks.clear();
		int length = str.length();
		int index = 0;
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == ';') {
				remarks.add(new String(str.substring(index, i)));
				index = i+1;
			}
		}
	}

	public String getRemark() {
		StringBuffer str = new StringBuffer(100);
		str.append("");
		int size = remarks.size();
		for (int i = 0; i < size; i++) {
			str.append(remarks.get(i) + ';');
		}
		return new String(str);
	}

	public MyJson(JSONObject object) throws JSONException {

		skills = new LinkedList<SkillData>();
		works = new LinkedList<WorkData>();
		remarks = new LinkedList<String>();
		id = object.getInt("id");
		usr = object.getString("username");

		JSONObject base = object.getJSONObject("fundamental");
		name = base.getString("people");
		sex = base.getString("sex");
		birth = base.getString("birth");
		phone = base.getString("phone");
		
		address = base.getString("address");
		starttime = base.getInt("starttime");
		endtime = base.getInt("endtime");
		JSONObject want = object.getJSONObject("wanted");
		job = want.getString("job");
		salary = want.getString("salary");
		holiday = want.getString("holiday");

		JSONArray s = object.getJSONArray("studys");
		int size = s.length();
		for (int i = 0; i < size; i++) {
			JSONObject o = s.getJSONObject(i);
			skills.add(new SkillData(o));
		}

		JSONArray w = object.getJSONArray("works");
		size = w.length();
		for (int i = 0; i < size; i++) {
			JSONObject o = w.getJSONObject(i);
			works.add(new WorkData(o));
		}
		JSONArray remark = object.getJSONArray("remark");
		size = remark.length();
		for (int i = 0; i < size; i++) {
			String str = remark.getString(i);
			remarks.add(str);
		}

	}

	public String changToJsonData() throws JSONException {
		JSONObject resume = new JSONObject();
		JSONObject base = new JSONObject();
		base.put("people", name);
		base.put("sex", sex);
		base.put("birth", birth);
		base.put("phone", phone);
		base.put("address", address);
		base.put("starttime", starttime);
		base.put("endtime", endtime);
		JSONObject want = new JSONObject();
		want.put("job", job);
		want.put("salary", salary);
		want.put("holiday", holiday);
		resume.put("fundamental", base);
		resume.put("wanted", want);

		JSONArray s = new JSONArray();
		int size = skills.size();
		for (int i = 0; i < size; i++) {
			s.put(skills.get(i).changeToJson());
		}
		size = works.size();
		JSONArray w = new JSONArray();
		for (int i = 0; i < size; i++) {
			w.put(works.get(i).changToJson());
		}

		resume.put("id", id);
		resume.put("username", usr);
		resume.put("studys", s);
		resume.put("works", w);

		JSONArray r = new JSONArray();
		size = remarks.size();
		for (int i = 0; i < size; i++) {
			r.put(remarks.get(i));
			
		}
		resume.put("remark", r);
		return resume.toString();
	}

}

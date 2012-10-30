package com.paint;

public class TimeScore {
	public int year;
	public int month;
	public int score;

	public TimeScore(int year, int month, int score) {
		this.year = year;
		this.month = month;
		this.score = score;
	}

	public TimeScore(float time_score) {
		year = (int) time_score;
		score = (int) ((time_score - year) * 100);
		month = year % 100;
		year = year / 100;
	}

	public void setMember(float time_score) {
		year = (int) time_score;
		score = (int) ((time_score - year) * 100);
		month = year % 100;
		year = year / 100;
	}

	public float toFloat() {
		float r = 0;
		r = year * 100 + month + ((float) score / 100);
		return r;
	}

	public String changeToString() {
		String r = "";
		int time = year*100+month;
		r += "("+ + time + ',' + score + ")";
		return r;
	}
}

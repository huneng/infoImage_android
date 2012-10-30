package com.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class HttpWork {
	public String site;
	public String usr, pw;

	public HttpWork() {
		site = "";
	}

	public HttpWork(String s) {
		site = s;
	}

	public void setSite(String s) {
		site = s;
	}

	public void setUser(String username, String password) {
		usr = username;
		pw = password;
	}

	public String submit() {
		String url = site + "/android/login/" + usr + '/' + pw;
		String r = "";
		try {
			r = contactWithServer(url);
		} catch (IOException e) {
			r = "Can't submit";
		}

		return r;

	}

	public String submit(String usrname, String password) {
		String url = site + "/android/login/" + usrname + '/' + password;
		String r = "";
		try {
			r = contactWithServer(url);
		} catch (IOException e) {
			r = "Can't submit";
		}

		return r;
	}

	public String getResume(int id) {
		String result = "";
		String url = site + "/android/getresume/" + id;
		try {
			result = contactWithServer(url);
		} catch (IOException e) {
			result = "Can't get!";
		}
		if (result.equals("error"))
			return "";
		return result;
	}

	@SuppressWarnings("deprecation")
	public String pushResume(String data) {
		data = URLEncoder.encode(data);
		String url = site + "/android/pushresume/" + data;
		String r = "";
		try {
			r = contactWithServer(url);
		} catch (IOException e) {
			r = "Can't save!";
		}

		return r;
	}

	@SuppressWarnings("deprecation")
	public String update(String data) {
		String r = "";
		data = URLEncoder.encode(data);
		String url = site + "/android/update/" + data;
		try {
			r = contactWithServer(url);
		} catch (IOException e) {
			r = "Can't save!";
		}
		return r;
	}

	@SuppressLint("NewApi")
	private String contactWithServer(String myUrl) throws IOException {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		URL url = new URL(myUrl);
		String result = "";
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		InputStream in = con.getInputStream();
		StringBuffer buffer = new StringBuffer();
		int length = con.getContentLength();
		for (int i = 0; i < length; i++) {
			buffer.append((char) in.read());
		}
		result = new String(buffer);
		in.close();
		con.disconnect();
		return result;
	}

}
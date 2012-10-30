package com.apk;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.json.MyJson;
import com.network.HttpWork;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ResumeList extends ListActivity {
	List<String> idlist;
	public static HttpWork hw;
	private String parentstr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// get data from parent activity
		Intent intent = getIntent();
		parentstr = intent.getStringExtra("idlist");
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");

		String site = "http://192.168.1.112:8080";
		hw = new HttpWork();
		hw.setSite(site);
		hw.setUser(username, password);

		idlist = new LinkedList<String>();
	}

	void init(String str) {
		idlist.clear();
		try {
			toIdList(str);
		} catch (JSONException e) {
		}
		idlist.add(new String("New ID"));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, idlist);
		setListAdapter(adapter);
	}

	@Override
	protected void onResume() {
		if (parentstr != null) {
			// 登录后第一次查看数据
			init(parentstr);
			parentstr = null;
		} else {
			// 登录后非第一次查看数据
			String str = hw.submit();
			init(str);
		}
		super.onResume();
	}

	private void toIdList(String str) throws JSONException {
		JSONArray array = new JSONArray(str);
		int len = array.length();
		for (int i = 0; i < len; i++) {
			int t = array.getInt(i);
			idlist.add("" + t);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String resume = "";
		if (position == idlist.size() - 1) {
			MyJson data = new MyJson();
			data.id = -1;
			data.usr = hw.usr;
			try {
				resume = data.changToJsonData();
			} catch (JSONException e) {
			}
		} else {
			int resumeid = Integer.parseInt(idlist.get(position));
			resume = hw.getResume(resumeid);
			try {
				JSONObject object = new JSONObject(resume);
				object.put("id", resumeid);
				resume = object.toString();
			} catch (JSONException e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			}

		}
		Intent intent = new Intent();
		intent.setClass(ResumeList.this, InputInterface.class);
		intent.putExtra("resume", resume);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

}

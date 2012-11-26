package com.apk;

import org.json.JSONException;
import org.json.JSONObject;

import com.json.MyJson;
import com.network.HttpWork;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class InputInterface extends TabActivity implements OnGestureListener {
	private static int TAB_NO = 0;
	private static int TAB_COUNT = 4;
	private Button btn1, btn3;
	private GestureDetector gestureDetector;
	public static InputInterface resume;
	public MyJson myData;
	public Bitmap photo;
	private HttpWork hw;
	public static int width, height;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// init window titlebar

		height = getWindowManager().getDefaultDisplay().getHeight();
		width = getWindowManager().getDefaultDisplay().getWidth();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// get data from parent activity
//		Intent appIntent = getIntent();
//		String str = appIntent.getStringExtra("resume");
//		try {
//			myData = new MyJson(new JSONObject(str));
//		} catch (JSONException e) {
//			myData = new MyJson();
//		}
		myData = new MyJson();
		resume = this;
		hw = ResumeList.hw;
		
		// init interface
		setContentView(R.layout.main);
		btn1 = (Button) findViewById(R.id.ok);
		btn3 = (Button) findViewById(R.id.save);
		btn1.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		// 添加tab
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent = new Intent().setClass(InputInterface.this, Base.class);
		spec = tabHost.newTabSpec("base").setIndicator("Base")
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(InputInterface.this, Skill.class);
		spec = tabHost.newTabSpec("skill").setIndicator("Skill")
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(InputInterface.this, Work.class);
		spec = tabHost.newTabSpec("work").setIndicator("Work")
				.setContent(intent);
		tabHost.addTab(spec);
		getTabHost().setCurrentTab(TAB_NO);

		// 设置手势监听器
		gestureDetector = new GestureDetector(this);
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ok:
				getTabHost().setCurrentTab(0);
				// base 界面是否有空
				if (Base.state) {
					break;
				}
				Intent intent1 = new Intent();
				intent1.setClass(InputInterface.this, Painting.class);
				startActivity(intent1);
				break;
			case R.id.save:
				String b = "";

				try {
					b = myData.changToJsonData();
				} catch (JSONException e) {
					Toast.makeText(InputInterface.this, "Input data wrong",
							Toast.LENGTH_LONG).show();
					return;
				}
				b = hw.pushResume(b);
				
				if (!b.equals("Can't save!"))
					b = "Save successful!";
				Toast.makeText(InputInterface.this, b, Toast.LENGTH_LONG)
						.show();
				finish();
				break;
			}
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return gestureDetector.onTouchEvent(event);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > 80) {
			TAB_NO = ++TAB_NO % TAB_COUNT;
			getTabHost().setCurrentTab(TAB_NO);
			return true;
		} else if (e1.getX() - e2.getX() < -80) {
			TAB_NO = (--TAB_NO + TAB_COUNT) % TAB_COUNT;
			getTabHost().setCurrentTab(TAB_NO);
			return true;
		}
		return true;
	}

	public void onLongPress(MotionEvent e) {

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		return false;
	}

	public void onShowPress(MotionEvent e) {

	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

}
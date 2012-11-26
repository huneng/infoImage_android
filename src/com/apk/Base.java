package com.apk;

import java.util.Calendar;

import com.dialog.TimeDialog;
import com.json.MyJson;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Base extends Activity {
	EditText name_ed, phone_ed, addr_ed;
	EditText job_ed, holi_ed, salary_ed;
	EditText remark_ed;
	Button btn, getPhoneBtn, timeBtn, sexBtn;
	String name, sex, birth, addr, phone, job, holiday, salary, time;
	String remark;
	ImageView image;
	InputInterface parent;
	public static boolean state;

	private static int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);
		state = false;

		btn = (Button) findViewById(R.id.age_btn);
		name_ed = (EditText) findViewById(R.id.name_edit);
		phone_ed = (EditText) findViewById(R.id.phone_edit);
		addr_ed = (EditText) findViewById(R.id.address_edit);
		job_ed = (EditText) findViewById(R.id.job1_edit);
		holi_ed = (EditText) findViewById(R.id.holiday1_edit);
		salary_ed = (EditText) findViewById(R.id.salary1_edit);
		remark_ed = (EditText) findViewById(R.id.remark_edit);
		timeBtn = (Button) findViewById(R.id.start_end_edit);
		getPhoneBtn = (Button) findViewById(R.id.get_tel);
		sexBtn = (Button)findViewById(R.id.sex_btn);
		sexBtn.setText("male");
		sex = "male";
		sexBtn.setOnClickListener(l);
		btn.setOnClickListener(l);
		getPhoneBtn.setOnClickListener(l);
		timeBtn.setOnClickListener(l);

		image = (ImageView) findViewById(R.id.photo_view);
		image.setOnClickListener(l);

		parent = InputInterface.resume;
	}

	OnClickListener l = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.age_btn:
				Calendar calendar = Calendar.getInstance();
				new DatePickerDialog(Base.this, listener,
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH)).show();

				break;
			case R.id.sex_btn:
				if(sex.equals("male"))
					sex = "female";
				else 
					sex = "male";
				sexBtn.setText(sex);
				break;
			case R.id.get_tel:
				TelephonyManager tm = (TelephonyManager) Base.this
						.getSystemService(Context.TELEPHONY_SERVICE);
				String str = "";
				str = tm.getLine1Number();
				if (str == null || str.length() == 0) {
					phone_ed.setText("");
					Toast.makeText(getApplicationContext(), "Can't get",
							Toast.LENGTH_SHORT).show();
					break;
				}
				phone_ed.setText(str);

				break;
			case R.id.start_end_edit:
				new TimeDialog(Base.this, timeBtn).show();
				break;
			case R.id.photo_view:
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			parent.photo = BitmapFactory.decodeFile(picturePath);

			parent.photo = Bitmap.createScaledBitmap(parent.photo,
					image.getHeight(), image.getHeight(), true);
			image.setImageBitmap(parent.photo);
		}

	}

	@Override
	public void onUserInteraction() {
		saveData();
		super.onUserInteraction();
	}

	OnDateSetListener listener = new OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String str = "" + year + '-' + monthOfYear + '-' + dayOfMonth;
			btn.setText(str);
		}

	};

	@Override
	protected void onResume() {
		if (parent.photo != null)
			image.setImageBitmap(parent.photo);
		MyJson myjson = parent.myData;
		name = myjson.name;
		sex = myjson.sex;
		birth = myjson.birth;
		addr = myjson.address;
		phone = myjson.phone;
		time = "" + myjson.starttime + '-' + myjson.endtime;
		job = myjson.job;
		salary = myjson.salary;
		holiday = myjson.holiday;
		remark = myjson.getRemark();
		init();
		state = name.equals("") || phone.equals("") || birth.equals("")
				|| addr.equals("") || time.equals("") || job.equals("")
				|| salary.equals("") || holiday.equals("");
		super.onResume();
	}

	private void init() {
		name_ed.setText(name);
		sexBtn.setText(sex);
		phone_ed.setText(phone);
		addr_ed.setText(addr);
		timeBtn.setText(time);
		btn.setText(birth);
		job_ed.setText(job);
		holi_ed.setText(holiday);
		salary_ed.setText(salary);
		remark_ed.setText(remark);
	}

	private void saveData() {
		name = name_ed.getText().toString();
		birth = btn.getText().toString();
		phone = phone_ed.getText().toString();
		addr = addr_ed.getText().toString();
		time = timeBtn.getText().toString();
		job = job_ed.getText().toString();
		holiday = holi_ed.getText().toString();
		salary = salary_ed.getText().toString();
		remark = remark_ed.getText().toString();
		state = name.equals("") || phone.equals("") || birth.equals("")
				|| addr.equals("") || time.equals("") || job.equals("")
				|| salary.equals("") || holiday.equals("");

		MyJson myjson = parent.myData;
		myjson.name = name;
		myjson.birth = birth;
		myjson.address = addr;
		myjson.sex = sex;
		myjson.phone = phone;
		myjson.setTime(time);
		myjson.job = job;
		myjson.salary = salary;
		myjson.holiday = holiday;
		myjson.setRemark(remark);
	}
}

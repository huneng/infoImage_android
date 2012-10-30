package com.apk;

import com.network.HttpWork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResumeActivity extends Activity {
	private EditText usrEdit, passwordEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.submit);

		TextView text = (TextView) findViewById(R.id.register_text);
		text.setMovementMethod(LinkMovementMethod.getInstance());
		text.setText(Html
				.fromHtml("<a href=\"http://192.168.1.108：8080/regisit\">注册>></a> "));
		Button btn = (Button) findViewById(R.id.submit_btn);
		usrEdit = (EditText) findViewById(R.id.usrname);
		passwordEdit = (EditText) findViewById(R.id.password);
		usrEdit.setText("huneng1991");
		passwordEdit.setText("123");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpWork hw = new HttpWork();
				hw.setSite("http://192.168.1.112:8080");
				String username = usrEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				//String str = hw.submit("huneng1991", "123");
				
				String str = hw.submit(username, password);
				if (str.equals("Can't submit")) {
					Toast.makeText(ResumeActivity.this, str, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Toast.makeText(ResumeActivity.this, "Submit success",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("username", "huneng1991");
				intent.putExtra("password", "123");
				intent.putExtra("idlist", str);
				intent.setClass(ResumeActivity.this, ResumeList.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
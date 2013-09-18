package com.javaone.assistant.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.javaone.assistant.R;
import com.javaone.assistant.model.JavaOneAppContext;

public class LoginActivity extends Activity {
	
	private static final String LOG_TAG = LoginActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		authenticate();
	}

	private void authenticate() {
		// Initiate the request to the auth service
		Button submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				JavaOneAppContext context = JavaOneAppContext.getInstance();
				
				EditText editText = (EditText) findViewById(R.id.username);
				context.setUsername(editText.getText().toString());

				editText = (EditText) findViewById(R.id.password);
				context.setPassword(editText.getText().toString());

				context.setBaseUrl(getString(R.string.base_uri) + "/" + context.getUsername());
				
				Log.d(LOG_TAG, "Executing Login background task");
				new LoginAsyncTask(LoginActivity.this).execute();
				Log.d(LOG_TAG, "Background task in excution");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}

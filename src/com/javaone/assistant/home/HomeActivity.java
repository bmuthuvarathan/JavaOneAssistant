package com.javaone.assistant.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.javaone.assistant.R;
import com.javaone.assistant.chatter.Chat2Activity;
import com.javaone.assistant.todo.ListToDosAsyncTask;

public class HomeActivity extends Activity {
	
	private static final String LOG_TAG = HomeActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		process();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	private void process() {
		// Initiate the request to list all ToDos for the authenticated user
		Button submitButton = (Button) findViewById(R.id.todolist);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				Log.d(LOG_TAG, "Executing List ToDo items background task");
				new ListToDosAsyncTask(HomeActivity.this).execute();
				Log.d(LOG_TAG, "Background task in excution");
			}
		});
		
		// chat
		Button chat2Button = (Button) findViewById(R.id.chat);
		chat2Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(LOG_TAG, "Invoking Chat2 Activity");
				Intent intent = new Intent(HomeActivity.this, Chat2Activity.class);
				startActivity(intent);
			}
		});
	}

}

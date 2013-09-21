package com.javaone.assistant.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.javaone.assistant.R;
import com.javaone.assistant.model.ToDoItem;

public class AddTodoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_todo);
		process();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_todo, menu);
		return true;
	}

	private void process() {

		// Initiate the request to the auth service
		Button submitButton = (Button) findViewById(R.id.add);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ToDoItem item = new ToDoItem();

				EditText editText = (EditText) findViewById(R.id.title);
				item.setTitle(editText.getText().toString());
				editText = (EditText) findViewById(R.id.description);
				item.setDescription(editText.getText().toString());

				new AddToDoAsyncTask(AddTodoActivity.this, item).execute();
			}
		});
	}

}

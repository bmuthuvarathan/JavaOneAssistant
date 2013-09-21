package com.javaone.assistant.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.javaone.assistant.R;
import com.javaone.assistant.model.ToDoItem;

public class UpdateTodoActivity extends Activity {
	
	public static ToDoItem itemToUpdate = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_todo);
		
		initialize();
		
		process();
	}

	private void initialize() {
		Intent i = getIntent();
		ToDoItem item = (ToDoItem) i.getSerializableExtra("item");
		
		EditText editText = (EditText) findViewById(R.id.title);
		editText.setText(item.getTitle());
		
		editText = (EditText) findViewById(R.id.description);
		editText.setText(item.getDescription());
		
		Switch todoCompletedSwith = (Switch) findViewById(R.id.completed_switch);
		todoCompletedSwith.setChecked(item.isCompleted());
		
		Button submitButton = (Button) findViewById(R.id.update);
		submitButton.setTag(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_todo, menu);
		return true;
	}
	
	private void process() {
		Button submitButton = (Button) findViewById(R.id.update);
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ToDoItem item = (ToDoItem) v.getTag();
				EditText editText = (EditText) findViewById(R.id.title);
				item.setTitle(editText.getText().toString());
				editText = (EditText) findViewById(R.id.description);
				item.setDescription(editText.getText().toString());
				Switch todoCompletedSwith = (Switch) findViewById(R.id.completed_switch);
				item.setCompleted(todoCompletedSwith.isChecked());

				new UpdateToDoAsyncTask(UpdateTodoActivity.this, item).execute();
			}
		});
	}

}

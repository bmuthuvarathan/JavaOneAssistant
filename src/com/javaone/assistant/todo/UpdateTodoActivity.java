package com.javaone.assistant.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.javaone.assistant.R;
import com.javaone.assistant.model.ToDoItem;

public class UpdateTodoActivity extends Activity {
	
	private  ToDoItem todoItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_todo);
		
		initialize();
		
		process();
	}

	private void initialize() {
		Intent i = getIntent();
		todoItem = (ToDoItem) i.getSerializableExtra("item");
		
		EditText editText = (EditText) findViewById(R.id.title);
		editText.setText(todoItem.getTitle());
		
		editText = (EditText) findViewById(R.id.description);
		editText.setText(todoItem.getDescription());
		
		Switch todoCompletedSwitch = (Switch) findViewById(R.id.completed_switch);
		todoCompletedSwitch.setChecked(todoItem.isCompleted());
		
		//this works too, but since a instance item is needed for 
		//onOptionsItemSelected, this is not needed anymore
		//Button submitButton = (Button) findViewById(R.id.update);
		//submitButton.setTag(item); 
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

				EditText editText = (EditText) findViewById(R.id.title);
				todoItem.setTitle(editText.getText().toString());
				editText = (EditText) findViewById(R.id.description);
				todoItem.setDescription(editText.getText().toString());
				Switch todoCompletedSwith = (Switch) findViewById(R.id.completed_switch);
				todoItem.setCompleted(todoCompletedSwith.isChecked());

				new UpdateToDoAsyncTask(UpdateTodoActivity.this, todoItem).execute();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.todoDelete:
			new DeleteToDoAsncTask(UpdateTodoActivity.this, todoItem.getId()).execute();
			return true;
		default:
			return true;
		}
	}

}

package com.javaone.assistant.todo;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.javaone.assistant.R;
import com.javaone.assistant.model.ToDoItem;

public class ListToDoActivity extends ListActivity {
	
	private static String[] itemTitles = new String[]{};
	private static ToDoItem[] items;

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_to_do_list, itemTitles));

	ListView listView = getListView();
	listView.setTextFilterEnabled(true);

	listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
		    // When clicked, show a toast with the TextView text
		    //Toast.makeText(getApplicationContext(),
			//((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(ListToDoActivity.this, UpdateTodoActivity.class);
			intent.putExtra("item", items[position]);
			startActivity(intent); 
		}
	});

}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.todoAdd:
			Intent intent = new Intent(ListToDoActivity.this, AddTodoActivity.class);
			startActivity(intent); 
			return true;
		default:
			return true;
		}
	}
	
	public static void setToDoItems(List<ToDoItem> itemsList) {
		if(itemsList == null)
			return;
		
		itemTitles = new String[itemsList.size()];
		items = new ToDoItem[itemsList.size()];
		
		int i = 0;
		for(ToDoItem item: itemsList) {
			itemTitles[i] = item.getTitle();
			items[i++] = item;
		} 
	}

}

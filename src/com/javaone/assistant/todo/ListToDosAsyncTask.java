package com.javaone.assistant.todo;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.model.ToDoItem;

public class ListToDosAsyncTask extends AsyncTask<Void, Void, List<ToDoItem>> {
	
	private Context activityContext = null;
	
	private static final String LOG_TAG = ListToDosAsyncTask.class.getName();
	
	public ListToDosAsyncTask(Activity context) {
		activityContext = context;
	}

	@Override
	protected List<ToDoItem> doInBackground(Void... params) {
		
		JavaOneAppContext context = JavaOneAppContext.getInstance();

		try {
			//requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Make the network request
			ResponseEntity<ToDoItem[]> response = context.getDefaultRestTemplate().exchange(context.getBaseUrl(),
					HttpMethod.GET, new HttpEntity<Object>(context.getDefaultHeaders()), ToDoItem[].class);
			ToDoItem[] todos = response.getBody();
			Log.d(LOG_TAG, "Received: " + todos.length);
			return Arrays.asList(todos);
		} catch (Exception e) {
			//TODO Need error handling
			Log.e(LOG_TAG, e.getLocalizedMessage(), e);
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<ToDoItem> todos) {
		Log.d(LOG_TAG, "In onPostExecute: ");
		super.onPostExecute(todos);
		ListToDoActivity.setToDoItems(todos); 
		Intent intent = new Intent(activityContext, ListToDoActivity.class);
		activityContext.startActivity(intent); 
	}

}
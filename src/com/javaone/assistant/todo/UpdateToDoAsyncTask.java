package com.javaone.assistant.todo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.model.ToDoItem;

public class UpdateToDoAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private Activity activityContext = null;
	private ToDoItem todoItem = null;
	
	private static final String LOG_TAG = UpdateToDoAsyncTask.class.getName();
	
	public UpdateToDoAsyncTask(UpdateTodoActivity context, ToDoItem item) {
		activityContext = context;
		todoItem = item;
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		JavaOneAppContext context = JavaOneAppContext.getInstance();

		try {


			String url = context.getBaseUrl() + "/" + todoItem.getId();
			
			Log.d(LOG_TAG, "Updating ID: " + todoItem.getId());
			
			context.getDefaultRestTemplate().exchange(url, HttpMethod.PUT, 
					new HttpEntity<ToDoItem>(todoItem, context.getDefaultHeaders()), Void.class);

			return null;
		} catch (Exception e) {
			//TODO Need error handling
			Log.e(LOG_TAG, e.getLocalizedMessage(), e);
			return null;
		}
	}

	@Override
	protected void onPostExecute(Void result) {
		Log.d(LOG_TAG, "In onPostExecute: ");
		super.onPostExecute(result);
		new ListToDosAsyncTask(activityContext).execute();
	}
	
	

}

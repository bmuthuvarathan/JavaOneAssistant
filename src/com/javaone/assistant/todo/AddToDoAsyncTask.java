package com.javaone.assistant.todo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.model.ToDoItem;

public class AddToDoAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private Activity activityContext = null;
	private ToDoItem todoItem = null;
	
	private static final String LOG_TAG = AddToDoAsyncTask.class.getName();
	
	public AddToDoAsyncTask(AddTodoActivity context, ToDoItem item) {
		activityContext = context;
		todoItem = item;
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		JavaOneAppContext context = JavaOneAppContext.getInstance();

		try {
			// Create a new RestTemplate instance
			RestTemplate restTemplate = context.getDefaultRestTemplate();
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(context.getBaseUrl(), HttpMethod.POST, 
					new HttpEntity<ToDoItem>(todoItem, context.getDefaultHeaders()), String.class);
			
			String id = responseEntity.getBody();
			
			Log.d(LOG_TAG, "Received ID: " + id);
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

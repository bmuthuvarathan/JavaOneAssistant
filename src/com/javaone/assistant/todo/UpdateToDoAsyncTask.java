package com.javaone.assistant.todo;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.model.JavaOneAppContext;
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
			HttpAuthentication authHeader = new HttpBasicAuthentication(context.getUsername(), context.getPassword());
			HttpHeaders requestHeaders = new HttpHeaders();	
			requestHeaders.setAuthorization(authHeader);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			String url = context.getBaseUrl() + "/" + todoItem.getId();
			
			Log.d(LOG_TAG, "Updating ID: " + todoItem.getId());
			
			restTemplate.exchange(url, HttpMethod.PUT, 
					new HttpEntity<ToDoItem>(todoItem, requestHeaders), Void.class);

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

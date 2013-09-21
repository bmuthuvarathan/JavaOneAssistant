package com.javaone.assistant.todo;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.model.JavaOneAppContext;
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
			HttpAuthentication authHeader = new HttpBasicAuthentication(context.getUsername(), context.getPassword());
			HttpHeaders requestHeaders = new HttpHeaders();	

			requestHeaders.setAuthorization(authHeader);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

			String url = context.getBaseUrl();
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, 
					new HttpEntity<ToDoItem>(todoItem, requestHeaders), String.class);
			
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

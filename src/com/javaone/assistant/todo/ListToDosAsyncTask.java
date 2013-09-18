package com.javaone.assistant.todo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.home.HomeActivity;
import com.javaone.assistant.model.JavaOneAppContext;
import com.javaone.assistant.model.ToDoItem;

public class ListToDosAsyncTask extends AsyncTask<Void, Void, List<ToDoItem>> {
	
	private Context activityContext = null;
	
	private static final String LOG_TAG = ListToDosAsyncTask.class.getName();
	
	public ListToDosAsyncTask(HomeActivity context) {
		activityContext = context;
	}

	@Override
	protected List<ToDoItem> doInBackground(Void... params) {
		
		JavaOneAppContext context = JavaOneAppContext.getInstance();

		try {
			// Call Spring Rest

			HttpAuthentication authHeader = new HttpBasicAuthentication(context.getUsername(), context.getPassword());
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAuthorization(authHeader);
			requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			String url = context.getBaseUrl();

			// Make the network request
			ResponseEntity<ToDoItem[]> response = restTemplate.exchange(url,HttpMethod.GET, new HttpEntity<Object>(requestHeaders), ToDoItem[].class);
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
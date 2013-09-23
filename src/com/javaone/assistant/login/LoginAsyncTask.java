package com.javaone.assistant.login;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.home.HomeActivity;
import com.javaone.assistant.model.ToDoItem;

public class LoginAsyncTask extends AsyncTask<Void, Void, List<ToDoItem>> {
	
	private Context activityContext = null;
	
	private static final String LOG_TAG = LoginAsyncTask.class.getName();
	
	public LoginAsyncTask(LoginActivity context) {
		activityContext = context;
	}

	@Override
	protected List<ToDoItem> doInBackground(Void... params) {
		
		JavaOneAppContext context = JavaOneAppContext.getInstance();

		try {
			// Make the network request
			ResponseEntity<ToDoItem[]> response = context.getDefaultRestTemplate().exchange(context.getBaseUrl(), 
					HttpMethod.GET, new HttpEntity<Object>(context.getDefaultHeaders()), ToDoItem[].class);
			
			ToDoItem[] todos = response.getBody();
			Log.d(LOG_TAG, "Authentication succeeded");
			return Arrays.asList(todos);
		} catch (Exception e) {
			//TODO Need error handling
			Log.e(LOG_TAG, e.getLocalizedMessage(), e);
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<ToDoItem> result) {
		Log.d(LOG_TAG, "In LoginAsyncTask onPostExecute: ");
		super.onPostExecute(result);
		Intent intent = new Intent(activityContext, HomeActivity.class);
		activityContext.startActivity(intent); 
	}
}
package com.javaone.assistant.login;

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

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.home.HomeActivity;
import com.javaone.assistant.model.ToDoItem;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

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
			HttpAuthentication authHeader = new HttpBasicAuthentication(context.getUsername(), context.getPassword());
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAuthorization(authHeader);
			requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			//restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

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
	protected void onPostExecute(List<ToDoItem> result) {
		Log.d(LOG_TAG, "In LoginAsyncTask onPostExecute: ");
		super.onPostExecute(result);
		Intent intent = new Intent(activityContext, HomeActivity.class);
		activityContext.startActivity(intent); 
	}

	private final WebSocketConnection mConnection = new WebSocketConnection();
	
	private void doAutobahnWSS() {
		final String wsuri = "ws://10.0.2.2:8080/javaee-mobile-server/chat";
		 
		   try {
		      mConnection.connect(wsuri, new WebSocketHandler() {
		 
		         @Override
		         public void onOpen() {
		            Log.d(LOG_TAG, "Status: Connected to " + wsuri);
		            mConnection.sendTextMessage("  {\"user\": \"the name of the user\", \"message\": \"the chat message\"}");
		         }
		 
		         @Override
		         public void onTextMessage(String payload) {
		            Log.d(LOG_TAG, "Got echo: " + payload);
		         }
		 
		         @Override
		         public void onClose(int code, String reason) {
		            Log.d(LOG_TAG, "Connection lost.");
		         }
		      });
		   } catch (WebSocketException e) {
		 
		      Log.d(LOG_TAG, e.toString());
		   }
	}
}
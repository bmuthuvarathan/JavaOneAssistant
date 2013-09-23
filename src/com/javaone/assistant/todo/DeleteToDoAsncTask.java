package com.javaone.assistant.todo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.javaone.assistant.JavaOneAppContext;

public class DeleteToDoAsncTask extends AsyncTask<Void, Void, Void> {
	private Activity activityContext = null;
	private Long todoId = null;
	
	private static final String LOG_TAG = DeleteToDoAsncTask.class.getName();
	
	public DeleteToDoAsncTask(Activity context, Long id) {
		activityContext = context;
		todoId = id;
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		JavaOneAppContext context = JavaOneAppContext.getInstance();

		try {
			String url = context.getBaseUrl() + "/" + todoId;
			
			Log.d(LOG_TAG, "Deleting ID: " + todoId);
			
			context.getDefaultRestTemplate().exchange(url, HttpMethod.DELETE, 
					new HttpEntity<Void>(null, context.getDefaultHeaders()), Void.class);

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

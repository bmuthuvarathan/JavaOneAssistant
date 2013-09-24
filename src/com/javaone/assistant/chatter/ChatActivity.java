package com.javaone.assistant.chatter;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.R;
import com.javaone.assistant.model.ChatMessage;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class ChatActivity extends Activity {

	static final String TAG = ChatActivity.class.getName();

	static EditText mMessage;
	private final WebSocketConnection mConnection = new WebSocketConnection();
	private final ObjectMapper objectM = new ObjectMapper();

	private void alert(String message) {
		Toast toast = Toast.makeText(getApplicationContext(), message,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}



	private void start() {

		final String wsuri = getString(R.string.chat_url);
		final ChatMessage message = new ChatMessage(JavaOneAppContext.getInstance().getUsername());
		
		Log.d(TAG, "Status: Connecting to " + wsuri + " ..");

		try {
			mConnection.connect(wsuri, new WebSocketHandler() {
				@Override
				public void onOpen() {
					Log.d(TAG, "Connected to " + wsuri + " ..");

					mMessage.setOnKeyListener(new OnKeyListener() {
						public boolean onKey(View v, int keyCode, KeyEvent event) {
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								switch (keyCode) {
								case KeyEvent.KEYCODE_DPAD_CENTER:
								case KeyEvent.KEYCODE_ENTER:
									message.setMessage(mMessage.getText().toString());
									try {
										String jsonMessage = objectM.writeValueAsString(message);
										Log.d(TAG, "Sending message: " + jsonMessage);
										mConnection.sendTextMessage(jsonMessage);
									} catch(Exception ex) {
										Log.d(TAG, "Parsing to Json string failed: " + ex.getMessage());
									}
									return true;
								default:
									break;
								}
							}
							return false;
						}
					});

				}

				@Override
				public void onTextMessage(String payload) {
					Log.d(TAG, "Got back: " + payload);
					try {
						ChatMessage message = objectM.readValue(payload, ChatMessage.class);
						alert(message.getUser());
					} catch (Exception ex) {
						//TODO Handle error
						Log.d(TAG, "Json Parsing Exception: " + ex);
						alert(payload);
					}
					
					
//					LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//					TextView tv=new TextView(ChatActivity.this);
//					tv.setText("test");
//					tv.setLayoutParams(lparams);
//					ChatActivity.scrollView.addView(tv);
				}

				@Override
				public void onClose(int code, String reason) {
					Log.d(TAG, "Connection closed or lost");
				}
			});
		} catch (WebSocketException e) {

			Log.d(TAG, e.toString());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		mMessage = (EditText) findViewById(R.id.message);
		//scrollView = (ScrollView) findViewById(R.id.scrollView);
		start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mConnection.isConnected()) {
			mConnection.disconnect();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit:
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}

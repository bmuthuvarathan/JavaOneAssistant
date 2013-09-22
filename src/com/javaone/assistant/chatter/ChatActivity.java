package com.javaone.assistant.chatter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.R;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class ChatActivity extends Activity {

	static final String TAG = ChatActivity.class.getName();

	static EditText mMessage;
	static Button mSendMessage;
	static Button mOpenClose;

	private void alert(String message) {
		Toast toast = Toast.makeText(getApplicationContext(), message,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}

	private void setButtonConnect() {
		mOpenClose.setText("Connect");
		mOpenClose.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				start();
			}
		});
	}

	private void setButtonDisconnect() {
		mOpenClose.setText("Disconnect");
		mOpenClose.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mConnection.disconnect();
			}
		});
	}

	private final WebSocketConnection mConnection = new WebSocketConnection();

	private void start() {

		final String wsuri = getString(R.string.chat_url);
		final String message = "{\"user\": \"" + JavaOneAppContext.getInstance().getUsername() +
				", \"message\": \"";
		Log.d(TAG, "Status: Connecting to " + wsuri + " ..");

		setButtonDisconnect();

		try {
			mConnection.connect(wsuri, new WebSocketHandler() {
				@Override
				public void onOpen() {
					Log.d(TAG, "Connected to " + wsuri + " ..");
					mSendMessage.setEnabled(true);
					mMessage.setEnabled(true);
					mSendMessage.setOnClickListener(new Button.OnClickListener() {
						public void onClick(View v) {
							
							String fullMessage = message + mMessage.getText().toString() + "\"}";
							Log.d(TAG, "Sending message: " + fullMessage);
							mConnection.sendTextMessage("  {\"user\": \"the TRTRTR\", \"message\": \"the chat message\"}");
					        Log.d(TAG, "!!!!!!!!!!!Done Sending");
							//mConnection.sendTextMessage(fullMessage);
						}
					});
				}

				@Override
				public void onTextMessage(String payload) {
					Log.d(TAG, "YHOOOO: " + payload);
					alert("Got echo: " + payload);
				}

				@Override
				public void onClose(int code, String reason) {
					alert("Connection lost.");
					setButtonConnect();
					mSendMessage.setEnabled(false);
					mMessage.setEnabled(false);
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

		mOpenClose = (Button) findViewById(R.id.connectDisconnect);
		mMessage = (EditText) findViewById(R.id.message);
		mSendMessage = (Button) findViewById(R.id.sendMessage);

		setButtonConnect();
		mSendMessage.setEnabled(false);
		mMessage.setEnabled(false);
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

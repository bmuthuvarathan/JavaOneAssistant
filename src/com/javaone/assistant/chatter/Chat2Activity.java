package com.javaone.assistant.chatter;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import com.javaone.assistant.JavaOneAppContext;
import com.javaone.assistant.R;
import com.javaone.assistant.model.ChatMessage;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class Chat2Activity extends ListActivity {
	/** Called when the activity is first created. */

	static final String TAG = Chat2Activity.class.getName();

	private ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
	private Chat2MessageAdapter adapter;
	private EditText text;
	private final ObjectMapper mapper = new ObjectMapper();
	private final WebSocketConnection mConnection = new WebSocketConnection();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat2);

		text = (EditText) this.findViewById(R.id.text);

		adapter = new Chat2MessageAdapter(this, messages);
		setListAdapter(adapter);
		start();
	}

	private void start() {

		final String wsuri = getString(R.string.chat_url);
		
		Log.d(TAG, "Status: Connecting to " + wsuri + " ..");

		try {
			mConnection.connect(wsuri, new WebSocketHandler() {
				@Override
				public void onOpen() {
					Log.d(TAG, "Connected to " + wsuri + " ..");

					text.setOnKeyListener(new OnKeyListener() {
						public boolean onKey(View v, int keyCode, KeyEvent event) {
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								switch (keyCode) {
								case KeyEvent.KEYCODE_DPAD_CENTER:
								case KeyEvent.KEYCODE_ENTER:
									ChatMessage msg = new ChatMessage(JavaOneAppContext.getInstance().getUsername(), 
											text.getText().toString());
									
									Chat2Activity.this.addNewMessage(msg);
									try {
										String jsonMessage = mapper.writeValueAsString(msg);
										Log.d(TAG, "Sending message: " + jsonMessage);
										mConnection.sendTextMessage(jsonMessage);
										text.setText("");
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
						 addNewMessage(mapper.readValue(payload, ChatMessage.class));
					} catch (Exception ex) {
						//TODO Handle error
						Log.d(TAG, "Json Parsing Exception: " + ex);
					}
				}

				@Override
				public void onClose(int code, String reason) {
					Log.d(TAG, "Connection closed or lost");
				}
			});
		} catch (WebSocketException e) {
			//TODO error handling
			Log.d(TAG, e.toString());
		}
	}

	void addNewMessage(ChatMessage msg) {
		messages.add(msg);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size() - 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat2, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mConnection.isConnected()) {
			mConnection.disconnect();
		}
	}

}
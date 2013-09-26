package com.javaone.assistant.chatter;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.javaone.assistant.R;
import com.javaone.assistant.model.ChatMessage;

/**
 * AwesomeAdapter is a Custom class to implement custom row in ListView
 * 
 * @author Adil Soomro
 * 
 */
public class Chat2MessageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ChatMessage> mMessages;

	public Chat2MessageAdapter(Context context, ArrayList<ChatMessage> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessages.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage message = (ChatMessage) this.getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat2_msg_row, parent, false);
			holder.message = (TextView) convertView
					.findViewById(R.id.message_text);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.message.setText(message.getMessage());

		LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		
			// Check whether message is mine to show green background and alignt right
			if (message.getTimestamp() == null) {
				holder.message.setText("You: " + message.getMessage());
				holder.message.setTextColor(0xFF9FB3D1);
				lp.gravity = Gravity.RIGHT;
			}
			// If not mine, then it is from sender and align to left
			else {
				holder.message.setText(message.getUser() + ": " + message.getMessage());
				holder.message.setTextColor(0xFFA1E177);
				lp.gravity = Gravity.LEFT;
			}
			holder.message.setLayoutParams(lp);

		return convertView;
	}

	private static class ViewHolder {
		TextView message;
	}

	@Override
	public long getItemId(int position) {
		// Unimplemented, because we aren't using Sqlite.
		return 0;
	}

}

package com.javaone.assistant.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String user;
	
	private String message;
	
	private String timestamp = null;
	
	public ChatMessage() {
		super();
	}
	
	public ChatMessage(String name, String msg) {
		this.user = name;
		this.message = msg;
	}
	
	public ChatMessage(String username) {
		this.user = username;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timeStamp) {
		this.timestamp = timeStamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "ChatRequest [user=" + user + ", message=" + message + "]";
	}

}

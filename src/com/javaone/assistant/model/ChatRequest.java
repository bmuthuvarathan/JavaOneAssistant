package com.javaone.assistant.model;

import java.io.Serializable;

public class ChatRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String user;
	
	private String message;

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

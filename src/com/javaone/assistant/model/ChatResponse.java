package com.javaone.assistant.model;

public class ChatResponse extends ChatRequest {

	private static final long serialVersionUID = 1L;
	
	private String timeStamp;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return super.toString() + " ChatResponse [timeStamp=" + timeStamp + "]";
	}

}

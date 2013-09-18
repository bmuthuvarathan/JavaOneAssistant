package com.javaone.assistant.model;

/**
 * Single class to hold username and password, so
 * it could be used for rest calls
 * @author bmuthuvarathan
 *
 */
public class JavaOneAppContext {
	
	private static JavaOneAppContext instance = null;
	
	private String username;
	
	private String password;
	
	private String baseUrl;
	
	private JavaOneAppContext() {
		
	}
	
	public static JavaOneAppContext getInstance() {
		if(instance == null) {
			instance = new JavaOneAppContext();
		}
		
		return instance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}

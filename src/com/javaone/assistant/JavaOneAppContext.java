package com.javaone.assistant;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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
	
	private HttpHeaders defaultHeaders = null;
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
	
	public HttpHeaders getDefaultHeaders() {
		if(defaultHeaders == null) {
			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
			defaultHeaders = new HttpHeaders();
			defaultHeaders.setAuthorization(authHeader);
			//requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		}
		return defaultHeaders;
	}
	
	public RestTemplate getDefaultRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		/*There is a bug with Spring/HTTP Client and sometimes it causes 
		 * EOFException when POSTing. But adding the following line, setting new HTTP
		 * client request factory solves that problem. But when SSL with self-signed 
		 * will not work if this is set. So uncomment  the following line if SSL is not
		 * used. Since SSL is being used for ToDo resource, I had comment this out for now
		 * TODO: Research more and solves this or stop using Spring RestTemplate
		 */
		//restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return restTemplate;
	}

}

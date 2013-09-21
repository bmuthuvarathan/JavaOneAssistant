package com.javaone.assistant.model;

import java.io.Serializable;

public class ToDoItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
    
    private String username;
     
    private String title;
    
    private String description;
    
    private boolean completed;
 
    /**
     * Default constructor
     */
    public ToDoItem() {
        // Default constructor
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "ToDoItem [id=" + id + ", username=" + username + ", title="
				+ title + ", description=" + description + ", completed="
				+ completed + "]";
	}
}

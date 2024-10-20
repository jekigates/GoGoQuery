package util;

import entity.User;

public class UserSession {
	// Volatile ensures that changes made in one thread are visible to others
	private static volatile UserSession instance;

	// Store the logged-in User
	private User user;

	// Private constructor (singleton pattern)
	private UserSession(User user) {
		this.user = user;
	}

	// Double-checked locking for thread safety
	public static UserSession getInstance(User user) {
		if (instance == null) { // First check (without locking)
			synchronized (UserSession.class) { // Lock the class
				if (instance == null) { // Second check (after locking)
					instance = new UserSession(user);
				}
			}
		}
		
		return instance;
	}

	// Method to get the current instance (without passing user)
	public static UserSession getInstance() {
		return instance;
	}

	// Getters to retrieve user information
	public User getUser() {
		return user;
	}

	public int getUserId() {
		return user.getId();
	}

	public String getUserEmail() {
		return user.getEmail();
	}

	public String getUserRole() {
		return user.getRole();
	}

	public String getUsername() {
		int idxAt = this.user.getEmail().indexOf("@");
		
		return this.user.getEmail().substring(0, idxAt);
	}
	
	// Clear the session (logout)
	public void cleanUserSession() {
		user = null;
		instance = null; // Clears the session
	}
}

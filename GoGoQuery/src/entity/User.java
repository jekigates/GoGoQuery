package entity;

import java.util.ArrayList;

public class User {
	private int id;
	private String dob;
	private String email;
	private String password;
	private String gender;
	private String role;
	private ArrayList<Cart> carts;
	
//	ALT + SHIFT + Z -> Generate Constructors using Fields...
	public User(int id, String dob, String email, String password, String gender, String role) {
		this.id = id;
		this.dob = dob;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.role = role;
		this.carts = new ArrayList<Cart>();
	}

//	ALT + SHIFT + Z -> Generate Getters and Setters...
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ArrayList<Cart> getCarts() {
		return carts;
	}

	public void setCarts(ArrayList<Cart> carts) {
		this.carts = carts;
	}
}
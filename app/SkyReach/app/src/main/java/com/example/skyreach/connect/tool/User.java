package com.example.skyreach.connect.tool;

public class User {
	private String userId;
	private String userName;
	private String userPassword;
	private String Email;
	private int userType;

	public User(String name, String pw, String email, int t)
	{
		userId = null;
		userName = name;
		userPassword = pw;
		Email = email;
		userType = t;
	}
	public User()
	{
		userId = "";
		userName = "";
		userPassword = "";
		Email = "";
		userType = -1;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	
}

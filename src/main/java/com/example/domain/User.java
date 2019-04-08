package com.example.domain;

import java.util.Date;

/**
 * 
 */
public class User {

	private String username;
	private Date birthday;

	public User() {

	}

	public User(String username, Date birthday) {
		this.username = username;
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", birthday=" + birthday + "]";
	}

}

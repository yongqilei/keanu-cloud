package com.keanu.cloud.account.domain;

import java.util.Date;

public class Account {

	
	private String name;
	
	private Date lastSeen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}
	
	
}

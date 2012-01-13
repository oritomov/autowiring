package com.google.code.autowiring.beans;

import com.google.code.autowiring.Bean;

public class Pattern implements Bean {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

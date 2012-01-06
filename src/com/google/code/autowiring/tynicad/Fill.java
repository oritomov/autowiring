package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Fill extends com.google.code.autowiring.beans.Fill implements RefBean {

	private String id;

	protected Fill() {
		super();
	}

	@Override 
	public String getId() {
		return id;
	}
	@Override 
	public void setId(String id) {
		this.id = id;
	}
}

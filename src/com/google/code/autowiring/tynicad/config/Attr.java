package com.google.code.autowiring.tynicad.config;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Attr {

	private String name;
	private String className;

	public Attr(String name, String className) {
		super();
		this.name = name;
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public String getClassName() {
		return className;
	}
}

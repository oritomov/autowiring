package com.google.code.autowiring.tynicad.config;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Ref {

	private String name;
	private String className;

	public Ref(String name, String className) {
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

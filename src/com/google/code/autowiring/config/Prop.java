package com.google.code.autowiring.config;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Prop {

	private String name;
	private String className;

	public Prop(String name, String className) {
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

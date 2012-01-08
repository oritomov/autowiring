package com.google.code.autowiring.config;

import com.google.code.autowiring.util.Xml;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public abstract class CfgEng extends Xml {

	private String name;

	public CfgEng(String name, String xmlName) {
		super(xmlName);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public abstract String getClassName();
}

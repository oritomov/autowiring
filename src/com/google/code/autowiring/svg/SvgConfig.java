package com.google.code.autowiring.svg;

import org.w3c.dom.Node;

import com.google.code.autowiring.config.CfgEng;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class SvgConfig  extends CfgEng {

	private static final String ROOT = "svg";
	private static final String CLASS = "class";

	private String className;

	public SvgConfig(String name, String xmlName) {
		super(name, xmlName);
		Node root = getNode(doc, ROOT);
		className = getAttrValue(root, CLASS);
	}

	@Override
	public String getClassName() {
		return className;
	}
}

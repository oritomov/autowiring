package com.google.code.autowiring.util;

import org.w3c.dom.Node;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Props extends Xml{

	public Props(String fileName) {
		super(fileName);
	}

	public String getProperty(String key) {
		Node root = getNode(doc,"wiring");
		Node engine = getNode(root, "engine");
		while (engine != null) {
			String name = getAttrValue(engine, "name");
			if (key.equalsIgnoreCase(name)) {
				return getAttrValue(engine, "class");
			}
			engine = nextNode(engine, "engine");
		}
		return null;
	}

}

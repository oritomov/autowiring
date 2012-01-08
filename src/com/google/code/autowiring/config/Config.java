package com.google.code.autowiring.config;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.util.Xml;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Config extends Xml {

	private static final String WIRING = "wiring";
	private static final String ENGINE = "engine";
	
	private List<CfgEng> engines = new ArrayList<CfgEng>();

	public Config(String fileName) {
		super(fileName);
		init();
	}

	private void init() {
		Node root = getNode(doc,WIRING);
		Node node = getNode(root, ENGINE);
		while (node != null) {
			CfgEng engine = createEngine(node);
			engines.add(engine);
			node = nextNode(node, ENGINE);
		}
	}

	private CfgEng createEngine(Node node) throws WiringException {
		String name = getAttrValue(node, "name");
		String className = getAttrValue(node, "class");
		String xmlName = getAttrValue(node, "xml");
		try {
			@SuppressWarnings("unchecked")
			Class<CfgEng> configClass = (Class<CfgEng>) Class.forName(className);
			Constructor<CfgEng> configConstructor = configClass.getDeclaredConstructor(new Class[]{String.class, String.class});
			CfgEng config = configConstructor.newInstance(new Object[] {name, xmlName});
			return config;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	public CfgEng getEngine(String name) {
		for (CfgEng engine: engines) {
			if (engine.getName().equalsIgnoreCase(name)) {
				return engine;
			}
		}
		return null;
	}

}

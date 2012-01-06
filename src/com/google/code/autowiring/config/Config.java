package com.google.code.autowiring.config;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.util.Xml;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Config extends Xml{

	private static final String WIRING = "wiring";
	private static final String ENGINE = "engine";
	private static final String TAG = "tag";
	private static final String PROP = "prop";
	private static final String REF = "ref";
	
	private List<CfgEng> engines = new ArrayList<CfgEng>();

	public Config(String fileName) {
		super(fileName);
		init();
	}

	private void init() {
		Node root = getNode(doc,WIRING);
		Node node = getNode(root, ENGINE);
		while (node != null) {
			CfgEng engine = engine(node);
			engines.add(engine);
			node = nextNode(node, ENGINE);
		}
	}

	private CfgEng engine(Node node) {
		String name = getAttrValue(node, "name");
		String className = getAttrValue(node, "class");
		CfgEng engine = new CfgEng(name, className);
		beans(engine, node);
		return engine;
	}

	private void beans(CfgEng engine, Node root) {
		Node node = getNode(root, TAG);
		while (node != null) {
			Tag tag = tag(node);
			engine.addTag(tag);
			node = nextNode(node, TAG);
		}
	}

	private Tag tag(Node node) {
		String name = getAttrValue(node, "name");
		String className = getAttrValue(node, "class");
		Tag tag = new Tag(name, className);
		props(tag, node);
		refs(tag, node);
		return tag;
	}

	private void refs(Tag tag, Node root) {
		Node node = getNode(root, REF);
		while (node != null) {
			Ref ref = ref(node);
			tag.addRef(ref);
			node = nextNode(node, REF);
		}
	}

	private Ref ref(Node node) {
		String name = getAttrValue(node, "name");
		String className = getAttrValue(node, "class");
		Ref ref = new Ref(name, className);
		return ref;
	}

	private void props(Tag tag, Node root) {
		Node node = getNode(root, PROP);
		while (node != null) {
			Prop prop = prop(node);
			tag.addProp(prop);
			node = nextNode(node, PROP);
		}
	}

	private Prop prop(Node node) {
		String name = getAttrValue(node, "name");
		String className = getAttrValue(node, "class");
		Prop prop = new Prop(name, className);
		return prop;
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

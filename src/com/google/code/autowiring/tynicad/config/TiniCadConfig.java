package com.google.code.autowiring.tynicad.config;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.config.CfgEng;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class TiniCadConfig  extends CfgEng {

	private static final String ROOT = "tynicad";
	private static final String TAG = "tag";
	private static final String PROP = "prop";
	private static final String REF = "ref";
	private static final String NAME = "name";
	private static final String CLASS = "class";
	private static final String ARRAY = "array";

	private String className;
	private List<Tag> tags = new ArrayList<Tag>();

	public TiniCadConfig(String name, String xmlName) {
		super(name, xmlName);
		Node root = getNode(doc, ROOT);
		className = getAttrValue(root, CLASS);
		beans(root );
	}

	@Override
	public String getClassName() {
		return className;
	}

	public Tag getTag(String name) {
		for(Tag tag: tags) {
			if (tag.getName().equalsIgnoreCase(name)) {
				return tag;
			}
		}
		return null;
	}

	private void beans(Node root) {
		Node node = getNode(root, TAG);
		while (node != null) {
			Tag tag = tag(node);
			tags.add(tag);
			node = nextNode(node, TAG);
		}
	}

	private Tag tag(Node node) {
		String name = getAttrValue(node, NAME);
		String className = getAttrValue(node, CLASS);
		String propName = getAttrValue(node, PROP);
		String arrayName = getAttrValue(node, ARRAY);
		Tag tag = new Tag(name, className, propName, arrayName);
		props(tag, node);
		refs(tag, node);
		tags(tag, node);
		return tag;
	}

	private void tags(Tag tag, Node root) {
		Node node = getNode(root, TAG);
		while (node != null) {
			Tag subTag = tag(node);
			tag.addTag(subTag);
			node = nextNode(node, TAG);
		}
	}

	private void refs(Tag tag, Node root) {
		Node node = getNode(root, REF);
		while (node != null) {
			Prop ref = ref(node);
			tag.addRef(ref);
			node = nextNode(node, REF);
		}
	}

	private Prop ref(Node node) {
		String name = getAttrValue(node, NAME);
		String className = getAttrValue(node, CLASS);
		Prop ref = new Prop(name, className);
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
		String name = getAttrValue(node, NAME);
		String className = getAttrValue(node, CLASS);
		Prop prop = new Prop(name, className);
		return prop;
	}
}

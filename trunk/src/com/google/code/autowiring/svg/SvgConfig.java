package com.google.code.autowiring.svg;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.config.CfgEng;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class SvgConfig  extends CfgEng {

	private static final String ROOT = "svg";
	private static final String TAG = "tag";
	private static final String NAME = "name";
	private static final String CLASS = "class";
	private static final String ATTRS = "attrs";
	private static final String TEXT = "text";

	private String className;
	private List<Tag> tags = new ArrayList<Tag>();

	public SvgConfig(String name, String xmlName) {
		super(name, xmlName);
		Node root = getNode(doc, ROOT);
		className = getAttrValue(root, CLASS);
		beans(root);
	}

	@Override
	public String getClassName() {
		return className;
	}

	public Tag getTag(Class<?> clazz) {
		for(Tag tag: tags) {
			Class<?> zuper = clazz;
			while (!tag.getClassName().equalsIgnoreCase(zuper.getName())) {
				zuper = (Class<?>) zuper.getGenericSuperclass();
				if (zuper.equals(Object.class)) {
					break;
				}
			}
			if (tag.getClassName().equalsIgnoreCase(zuper.getName())) {
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
		String attrsName = getAttrValue(node, ATTRS);
		String textName = getAttrValue(node, TEXT);
		Tag tag = new Tag(name, className, attrsName, textName);
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
}

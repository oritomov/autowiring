package com.google.code.autowiring.svg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Tag {

	private String name;
	private String className;
	private String attrsName;
	private String textName;
	private List<Tag> tags = new ArrayList<Tag>();

	public Tag(String name, String className, String attrsName, String textName) {
		this.name = name;
		this.className = className;
		this.attrsName = attrsName;
		this.textName = textName;
	}

	public String getName() {
		return name;
	}
	public String getClassName() {
		return className;
	}
	public String getAttrsName() {
		return attrsName;
	}
	public String getTextName() {
		return textName;
	}
	public void addTag(Tag tag) {
		tags .add(tag);
	}
	public List<Tag> getTags() {
		return tags;
	}
}

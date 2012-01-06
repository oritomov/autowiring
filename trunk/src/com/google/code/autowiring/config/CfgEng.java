package com.google.code.autowiring.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class CfgEng {

	private String name;
	private String className;
	private List<Tag> tags = new ArrayList<Tag>();

	public CfgEng(String name, String className) {
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
	public void addTag(Tag tag) {
		tags.add(tag);
	}
	public Tag getTag(String name) {
		for(Tag tag: tags) {
			if (tag.getName().equalsIgnoreCase(name)) {
				return tag;
			}
		}
		return null;
	}
}

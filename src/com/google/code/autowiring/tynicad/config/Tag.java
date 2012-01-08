package com.google.code.autowiring.tynicad.config;

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
	private String propName;
	private String arrayName;
	private List<Prop> props = new ArrayList<Prop>();
	private List<Ref> refs = new ArrayList<Ref>();
	private List<Tag> tags = new ArrayList<Tag>();

	public Tag(String name, String className, String propName, String arrayName) {
		this.name = name;
		this.className = className;
		this.propName = propName;
		this.arrayName = arrayName;
	}

	public String getName() {
		return name;
	}
	public String getClassName() {
		return className;
	}
	public void addProp(Prop prop) {
		props .add(prop);
	}
	public List<Prop> getProps() {
		return props;
	}
	public String getPropName() {
		return propName;
	}
	public String getArrayName() {
		return arrayName;
	}
	public void addRef(Ref ref) {
		refs .add(ref);
	}
	public List<Ref> getRefs() {
		return refs;
	}
	public void addTag(Tag tag) {
		tags .add(tag);
	}
	public List<Tag> getTags() {
		return tags;
	}
}

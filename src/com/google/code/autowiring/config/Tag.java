package com.google.code.autowiring.config;

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
	private List<Prop> props = new ArrayList<Prop>();
	private List<Ref> refs = new ArrayList<Ref>();

	public Tag(String name, String className) {
		this.name = name;
		this.className = className;
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
	public void addRef(Ref ref) {
		refs .add(ref);
	}
	public List<Ref> getRefs() {
		return refs;
	}
}

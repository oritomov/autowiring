package com.google.code.autowiring.tynicad.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Tag extends Prop {

	private String propName;
	private String arrayName;
	private List<Prop> props = new ArrayList<Prop>();
	private List<Prop> refs = new ArrayList<Prop>();
	private List<Tag> tags = new ArrayList<Tag>();

	public Tag(String name, String className, String propName, String arrayName) {
		super(name, className);
		this.propName = propName;
		this.arrayName = arrayName;
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
	public void addRef(Prop ref) {
		refs .add(ref);
	}
	public List<Prop> getRefs() {
		return refs;
	}
	public void addTag(Tag tag) {
		tags .add(tag);
	}
	public List<Tag> getTags() {
		return tags;
	}
}

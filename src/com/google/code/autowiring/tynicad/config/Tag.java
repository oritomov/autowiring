package com.google.code.autowiring.tynicad.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Tag extends Attr {

	private String root;
	private String arrayName;
	private List<Attr> props = new ArrayList<Attr>();
	private List<Attr> refs = new ArrayList<Attr>();
	private List<Tag> tags = new ArrayList<Tag>();
	private Attr text;

	public Tag(String name, String root, String className, String arrayName) {
		super(name, className);
		this.root = root;
		this.arrayName = arrayName;
	}
	public void addProp(Attr prop) {
		props.add(prop);
	}
	public List<Attr> getProps() {
		return props;
	}
	public String getRoot() {
		return root;
	}
	public String getArrayName() {
		return arrayName;
	}
	public void addRef(Attr ref) {
		refs .add(ref);
	}
	public List<Attr> getRefs() {
		return refs;
	}
	public void addTag(Tag tag) {
		tags .add(tag);
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setText(Attr text) {
		this.text = text;
	}
	public Attr getText() {
		return text;
	}
}

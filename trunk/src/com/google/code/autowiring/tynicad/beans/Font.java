package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Font implements RefBean {

	private String id;
	//<HEIGHT>-13</HEIGHT>
	private double width;
	//<ITALIC>0</ITALIC>
	//<UNDERLINE>0</UNDERLINE>
	//<STRIKEOUT>0</STRIKEOUT>
	//<CHARSET>0</CHARSET>
	private String facename;

	public Font() {
		super();
	}

	@Override 
	public String getId() {
		return id;
	}
	@Override 
	public void setId(String id) {
		this.id = id;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = Double.parseDouble(width) * TyniCAD.FONT;
	}
	public String getFacename() {
		return facename;
	}
	public void setFacename(String facename) {
		this.facename = facename;
	}
}

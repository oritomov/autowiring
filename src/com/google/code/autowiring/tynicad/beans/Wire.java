package com.google.code.autowiring.tynicad.beans;

import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Path;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Wire extends Path {

	private static final String COLOR_WIRE = "colorWire";

	public Wire() {
		super();
	}

	public Wire(List<Bean> refs) {
		this();
		setColor(refs, COLOR_WIRE);
	}

	public void setA(Position a) {
		setX1(a.getX());
		setY1(a.getY());
	}
	public void setB(Position b) {
		setX2(b.getX());
		setY2(b.getY());
	}
	public void setColor(List<Bean> refs, String name) {
		setColor(TyniCAD.getColor(TyniCAD.getOption(refs, name)));
	}
}

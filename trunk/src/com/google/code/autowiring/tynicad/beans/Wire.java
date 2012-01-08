package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.beans.Path;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Wire extends Path {

	public Wire() {
		super();
	}

	public void setA(Position a) {
		setX1(a.getX());
		setY1(a.getY());
	}
	public void setB(Position b) {
		setX2(b.getX());
		setY2(b.getY());
	}
}

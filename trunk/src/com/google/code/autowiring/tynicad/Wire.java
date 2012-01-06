package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Wire extends com.google.code.autowiring.beans.Wire {

	protected Wire() {
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

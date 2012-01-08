package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.beans.Path;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Junction extends Path {

	private final static double R = 2.5;

	public Junction() {
		super();
		setJunction(true);
	}

	public void setPos(Position pos) {
		setX1(pos.getX());
		setY1(pos.getY());
		setX2(R);
		setY2(R);
	}
}

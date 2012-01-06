package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Junction extends com.google.code.autowiring.beans.Junction {

	protected Junction() {
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
}

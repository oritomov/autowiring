package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Polygon extends com.google.code.autowiring.beans.Polygon {

	protected Polygon() {
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setStyle(Style style) {
		// TODO
	}
	public void setFill(Fill fill) {
		// TODO
	}
}

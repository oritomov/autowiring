package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Rectangle extends com.google.code.autowiring.beans.Rectangle {

	protected Rectangle() {
	}
	public void setA(Position a) {
		setX(a.getX());
		setY(a.getY());
	}
	public void setB(Position b) {
		double x = getX();
		double y = getY();
		double x2 = b.getX();
		double y2 = b.getX();
		setX(Math.min(x,x2));
		setY(Math.min(y,y2));
		setWidth(Math.max(x,x2) - getX());
		setHeigh(Math.max(y,y2) - getY());
	}
	public void setStyle(Style style) {
		super.setStyle(style);
	}
	public void setFill(Fill fill) {
		super.setFill(fill);
	}
}

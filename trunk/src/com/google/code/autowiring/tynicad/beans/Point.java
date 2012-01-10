package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.beans.Pnt;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Point extends Pnt {

	public Point() {
		super();
	}
	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setArc(String code) {
		setArc(Arc.get(Integer.parseInt(code)));
	}
}

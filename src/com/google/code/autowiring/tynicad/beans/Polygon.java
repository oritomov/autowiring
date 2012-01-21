package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.beans.Path;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Polygon extends Path {

	public Polygon() {
		super();
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setStyle(Style style) {
		setStrokeColor(style.getColor());
		setStrokeWidth(style.getThickness());
	}
	public void setFill(Fill fill) {
		// TODO
		//switch (fill.getFill()) {
		//	case None:
		//		setFillColor(null);
		//		break;
		//	default:
		//		setFillColor(fill.getColor());
		//}
	}
}

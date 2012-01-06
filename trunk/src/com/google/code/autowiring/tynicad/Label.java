package com.google.code.autowiring.tynicad;

import java.awt.Color;

import com.google.code.autowiring.Wiring.Direction;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Label extends com.google.code.autowiring.beans.Label {

	protected Label() {
		super();
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setDirection(String direction) {
		// TODO
		super.setDirection(Direction.get(direction));
	}
	public void setColor(String bgr) {
		// TODO
		int r = Integer.parseInt(bgr.substring(4), 16);
		int g = Integer.parseInt(bgr.substring(2, 4), 16);
		int b = Integer.parseInt(bgr.substring(0, 2), 16);
		Color color = new Color(r, g, b);
		super.setColor(color);
	}
	public void setFont(Font font) {
		// TODO
	}
	public void setStyle(Style style) {
		// TODO
	}
}

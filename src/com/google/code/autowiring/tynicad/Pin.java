package com.google.code.autowiring.tynicad;

import com.google.code.autowiring.Wiring.Direction;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Pin extends com.google.code.autowiring.beans.Pin {

	protected Pin() {
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setDirection(String direction) {
		// TODO
		super.setDirection(Direction.get(direction));
	}
	@Override
	public void setNumber(String number) {
		super.setNumber(number);
	}
	public void setLength(String length) {
		super.setLength(Double.parseDouble(length));
	}
}

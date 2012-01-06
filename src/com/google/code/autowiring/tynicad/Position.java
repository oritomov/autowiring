package com.google.code.autowiring.tynicad;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Position extends com.google.code.autowiring.beans.Position {

	public Position(double x, double y) {
		super(x, y);
	}

	public Position(String position) {
		super(Double.valueOf(position.split(",")[0]), Double.valueOf(position.split(",")[1]));
	}
}

package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Position implements Bean {

	private double x;
	private double y;

	public Position(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Position(String position) {
		this(Double.valueOf(position.split(",")[0]) * TyniCAD.XY, Double.valueOf(position.split(",")[1]) * TyniCAD.XY);
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public Position move(Position displace) {
		x+=displace.getX();
		y+=displace.getY();
		return this;
	}
}

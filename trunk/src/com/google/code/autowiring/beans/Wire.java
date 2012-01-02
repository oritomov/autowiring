package com.google.code.autowiring.beans;

import java.awt.Color;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Wire implements Bean {

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private Color color;

	public Wire(double x1, double y1, double x2, double y2, Color color) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
	public double getX1() {
		return x1;
	}
	public double getY1() {
		return y1;
	}
	public double getX2() {
		return x2;
	}
	public double getY2() {
		return y2;
	}
	public Color getColor() {
		return color;
	}
}

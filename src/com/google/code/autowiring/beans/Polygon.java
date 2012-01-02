package com.google.code.autowiring.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Polygon implements Bean {

	private double x;
	private double y;
	private List<Point> points = new ArrayList<Point>();

	public Polygon(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public List<Point> getPoints() {
		return points;
	}
	public void add(Point point) {
		points .add(point);
	}
}

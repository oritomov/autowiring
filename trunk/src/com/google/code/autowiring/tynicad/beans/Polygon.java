package com.google.code.autowiring.tynicad.beans;

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

	public Polygon() {
		super();
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public List<Point> getPoints() {
		return points;
	}
	public void addPoints(Point point) {
		points .add(point);
	}
	public void setStyle(Style style) {
		// TODO
	}
	public void setFill(Fill fill) {
		// TODO
	}
}

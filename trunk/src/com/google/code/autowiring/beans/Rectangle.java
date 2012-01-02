package com.google.code.autowiring.beans;

import java.awt.Color;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Rectangle implements Bean {

	private double x;
	private double y;
	private double width;
	private double heigh; 
	private Fill fill;
	private Color border;
	private int thickness;

	public Rectangle(double x, double y, double x2, double y2, Fill fill, Color border, int thickness) {
		this.x = Math.min(x,x2);
		this.y = Math.min(y,y2);
		this.width = Math.max(x,x2) - this.x;
		this.heigh = Math.max(y,y2) - this.y;
		this.fill = fill;
		this.border = border;
		this.thickness = thickness;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getWidth() {
		return width;
	}
	public double getHeigh() {
		return heigh;
	}
	public Fill getFill() {
		return fill;
	}
	public Color getBorder() {
		return border;
	}
	public int getThickness() {
		return thickness;
	}
}

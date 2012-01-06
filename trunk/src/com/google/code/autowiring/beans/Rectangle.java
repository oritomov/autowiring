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
	private Style style;

	protected Rectangle() {
	}
//	public Rectangle(double x, double y, double x2, double y2, Fill fill, Color border, int thickness) {
//		this.x = Math.min(x,x2);
//		this.y = Math.min(y,y2);
//		this.width = Math.max(x,x2) - this.x;
//		this.heigh = Math.max(y,y2) - this.y;
//		this.fill = fill;
//		this.border = border;
//		this.thickness = thickness;
//	}
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
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeigh() {
		return heigh;
	}
	public void setHeigh(double heigh) {
		this.heigh = heigh;
	}
	public Fill getFill() {
		return fill;
	}
	public void setFill(Fill fill) {
		this.fill = fill;
	}
	public Color getBorder() {
		return border;
	}
	public void setBorder(Color border) {
		this.border = border;
	}
	public int getThickness() {
		return thickness;
	}
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
}

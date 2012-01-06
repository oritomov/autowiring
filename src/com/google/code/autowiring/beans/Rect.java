package com.google.code.autowiring.beans;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Rect implements Bean {

	private double x;
	private double y;
	private double width;
	private double heigh; 
	private String fillColor;
	private String strokeColor;
	private int strokeWidth;

	protected Rect() {
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
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public String getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public int getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getStyle() {
		String style = "stroke:#"+getStrokeColor();
		style += ";stroke-width:"+getStrokeWidth();
		if (getFillColor() == null) {
			style += ";fill:none";
		} else {
			style += ";fill:#"+getFillColor();
		}
		return style;
	}
}

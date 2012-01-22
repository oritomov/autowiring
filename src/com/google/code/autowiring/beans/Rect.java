package com.google.code.autowiring.beans;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Pattern.Color;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Rect implements Bean {

	private double x;
	private double y;
	private double width;
	private double height; 
	private Color fillColor;
	private Color strokeColor;
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
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public Color getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	public int getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getStyle() {
		String style = "stroke-width:"+getStrokeWidth();
		if (strokeColor == null) {
			style += ";stroke:none";
		} else {
			style += ";stroke:#"+strokeColor.getRgb();
		}
		if (fillColor == null) {
			style += ";fill:none";
		} else {
			style += ";fill:#"+fillColor.getRgb();
		}
		return style;
	}
}

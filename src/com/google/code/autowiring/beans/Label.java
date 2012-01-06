package com.google.code.autowiring.beans;

import java.awt.Color;
import java.awt.Font;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring.Direction;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Label implements Bean {

	private double x;
	private double y;
	private Color color;
	private Direction direction;
	private Font font;
	private String label;

//	public Label(double x, double y, Color color, Direction direction, Font font, String label) {
//		super();
//		this.x = x;
//		this.y = y;
//		this.color = color;
//		this.direction = direction;
//		this.font = font;
//		this.label = label;
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
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}

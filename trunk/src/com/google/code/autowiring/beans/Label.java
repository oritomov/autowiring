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

	public Label(double x, double y, Color color, Direction direction, Font font, String label) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.direction = direction;
		this.font = font;
		this.label = label;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Color getColor() {
		return color;
	}
	public Direction getDirection() {
		return direction;
	}
	public Font getFont() {
		return font;
	}
	public String getLabel() {
		return label;
	}
}

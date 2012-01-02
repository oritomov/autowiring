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
public class Pin implements Bean {

	private double x;
	private double y;
	private Direction direction;
	private double length;
	private String number;
	private String name;
	private Color color;
	private Font font;

	public Pin(double x, double y, Direction direction, double length, String number, String name, Color color, Font font) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.length = length;
		this.number = number;
		this.name = name;
		this.color = color;
		this.font = font;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Direction getDirection() {
		return direction;
	}
	public double getLength() {
		return length;
	}
	public String getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	public Font getFont() {
		return font;
	}
}

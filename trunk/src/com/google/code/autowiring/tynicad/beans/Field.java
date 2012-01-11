package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.Bean;

public class Field implements Bean {

	private double x;
	private double y;
	private int type;
	private String description;
	private String value;
	private int show;

	public Field() {
		super();
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setType(String type) {
		setType(Integer.parseInt(type));
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getShow() {
		return show;
	}
	public void setShow(int show) {
		this.show = show;
	}
	public void setShow(String show) {
		setShow(Integer.parseInt(show));
	}
}

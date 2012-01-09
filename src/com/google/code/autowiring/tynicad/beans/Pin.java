package com.google.code.autowiring.tynicad.beans;

import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring.Direction;
import com.google.code.autowiring.beans.Group;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Pin extends Group {

	private static final String FONT_ID = "0";
	private static final String COLOR_PIN = "colorPin";

	private Direction direction;
	private double length;
	private String number;
	private String name;
	private Font font;
	private String color;

	public Pin(List<Bean> refs) {
		setFont(refs, FONT_ID);
		setColor(refs, COLOR_PIN);
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}

	public Direction getDirection() {
		return direction;
	}
	public void setDirection(String code) {
		this.direction = Direction.get(Integer.parseInt(code));
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public void setLength(String length) {
		setLength(Double.parseDouble(length));
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(List<Bean> refs, String refId) {
		this.font = (Font) TyniCAD.getRef(refs, Font.class, refId);
	}
	public String getColor() {
		return color;
	}
	public void setColor(List<Bean> refs, String name) {
		this.color = TyniCAD.getColor(TyniCAD.getOption(refs, name));
	}
	@Override
	public List<Bean> getBeans() {
		if (super.getBeans().size() == 0) {
			Wire wire = createWire();
			addBean(wire);
			if ((getNumber() != null) || (!getNumber().isEmpty())) {
				Label number = createNumber();
				addBean(number);
			}
			if ((getName() != null) || (!getName().isEmpty())) {
				Label name = createName();
				addBean(name);
			}
		}
		return super.getBeans();
	}
	private Label createName() {
		double x = getX();
		double y = getY();
		switch (getDirection()) {
			case Up:
			case Down:
				x += getFont().getWidth();
				break;
			case Left:
			case Right:
				y += getFont().getWidth();
				break;
		}
		Label name = new Label();
		name.setX(x);
		name.setY(y);
		// TODO label.setColor(Color.BLACK));
		name.setDirection(getDirection());
		name.setFontName(getFont().getFacename());
		name.setText(getName());
		return name;
	}

	private Label createNumber() {
		Label number = new Label();
		number.setX(getX());
		number.setY(getY());
		// TODO number.setColor(Color.BLACK));
		number.setDirection(getDirection());
		number.setFontName(getFont().getFacename());
		number.setText(getNumber());
		return number;
	}
	private Wire createWire() {
		double x = 0;
		double y = 0;
		switch (getDirection()) {
			case Up:
				y = -getLength()/5;
				break;
			case Down:
				y = getLength()/5;
				break;
			case Left:
				x = -getLength()/5;
				break;
			case Right:
				x = getLength()/5;
				break;
		}
		Wire wire = new Wire();
		wire.setX1(getX());
		wire.setY1(getY());
		wire.setX2(getX()+x);
		wire.setY2(getY()+y);
		wire.setColor(getColor());
		return wire;
	}
}
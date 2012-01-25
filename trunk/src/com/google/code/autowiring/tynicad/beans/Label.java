package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.Wiring.Direction;
import com.google.code.autowiring.beans.Text;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Label extends Text {

	public Label() {
		super();
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setDirection(String code) {
		super.setDirection(Direction.valueOf(Integer.parseInt(code)));
	}
	public void setColor(BgrColor bgrColor) {
		super.setColor(bgrColor.getColor());
	}
	public void setLabel(String label) {
		super.setText(label);
	}
	public void setFont(Font font) {
		setFontName(font.getFacename());
		setFontSize(font.getWidth());
	}
	public void setStyle(Style style) {
		super.setColor(style.getColor());
	}
}

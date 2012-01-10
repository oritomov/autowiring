package com.google.code.autowiring.tynicad.beans;

import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Pnt.Arc;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Wire extends Polygon {

	private static final String COLOR_WIRE = "colorWire";
	private static final int WIDTH_WIRE = 1;

	public Wire() {
		super();
	}

	public Wire(List<Bean> refs) {
		this();
		setColor(refs, COLOR_WIRE);
		setStrokeWidth(WIDTH_WIRE);
	}

	public void setA(Position a) {
		setX(0);
		setY(0);
		Point p = getPoint(a);
		addPoints(p);
	}
	public void setB(Position b) {
		Point p = getPoint(b);
		addPoints(p);
	}
	private Point getPoint(Position pos) {
		Point point = new Point();
		point.setPos(pos);
		point.setArc(Arc.No);
		return point;
	}
	public void setColor(List<Bean> refs, String name) {
		setStrokeColor(TyniCAD.getOption(refs, name));
	}
}

package com.google.code.autowiring.tynicad.beans;

import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Path;
import com.google.code.autowiring.beans.Pnt.Arc;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Junction extends Path {

	private static final String COLOR_JUNCTION = "colorJunction";
	private final static double R = 2.5;
	private static final int WIDTH = 2;

	public Junction() {
		super();
	}

	public Junction(List<Bean> refs) {
		super();
		setStrokeColor(null);
		setStrokeWidth(WIDTH);
		setFillColor(refs, COLOR_JUNCTION);
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());

		Point p1 = new Point();
		p1.setX(R);
		p1.setY(0);
		p1.setArc(Arc.No);
		addPoints(p1);

		Point p2 = new Point();
		p2.setX(-R);
		p2.setY(0);
		p2.setRx(R);
		p2.setRy(R);
		p2.setArc(Arc.In);
		addPoints(p2);

		Point p3 = new Point();
		p3.setX(R);
		p3.setY(0);
		p3.setRx(R);
		p3.setRy(R);
		p3.setArc(Arc.In);
		addPoints(p3);
	}
	public void setFillColor(List<Bean> refs, String name) {
		setFillColor(TyniCAD.getOption(refs, name));
	}
}

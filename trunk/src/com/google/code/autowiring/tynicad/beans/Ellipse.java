package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.beans.Path;
import com.google.code.autowiring.beans.Pnt.Arc;

public class Ellipse extends Path {

	public Ellipse() {
		super();
	}
	public void setA(Position a) {
		setX(a.getX());
		setY(a.getY());
	}
	public void setB(Position b) {
		double x1 = getX();
		double y1 = getY();
		double x2 = b.getX();
		double y2 = b.getY();
		double rX = Math.abs(x2-x1)/2.;
		double rY = Math.abs(y2-y1)/2.;
		setX(Math.min(x1,x2) + rX);
		setY(Math.min(y1,y2) + rY);

		Point p1 = new Point();
		p1.setX(rX);
		p1.setY(0);
		p1.setArc(Arc.No);
		addPoints(p1);

		Point p2 = new Point();
		p2.setX(-rX);
		p2.setY(0);
		p2.setRx(rX);
		p2.setRy(rY);
		p2.setArc(Arc.In);
		addPoints(p2);

		Point p3 = new Point();
		p3.setX(rX);
		p3.setY(0);
		p3.setRx(rX);
		p3.setRy(rY);
		p3.setArc(Arc.In);
		addPoints(p3);
	}
	public void setStyle(Style style) {
		setStrokeColor(style.getColor());
		setStrokeWidth(style.getThickness());
	}
	public void setFill(Fill fill) {
		switch (fill.getFill()) {
			case None:
				setFillColor(null);
				break;
			default:
				setFillColor(fill.getColor());
		}
	}
}

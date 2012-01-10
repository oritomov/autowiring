package com.google.code.autowiring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Pnt.Arc;
import com.google.code.autowiring.svg.Svg;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Path implements Bean {

	private double x;
	private double y;
	private String strokeColor;
	private int strokeWidth;
	private List<Pnt> points = new ArrayList<Pnt>();

	protected Path() {
		super();
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
	public void addPoints(Pnt point) {
		points.add(point);
	}
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getD() {
		String d="";
		for (Pnt point: points) {
			if (d.length() == 0) {
				d="M ";
			} else {
				switch (point.getArc()) {
					case No:
						d+=" L ";
						break;
					case In:
					case Out:
						d+=" A ";
				}
			}
			d += point.getD(x, y);
		}
		if (isClosed()) {
			d += " z";
		}
		return d;
	}
	public String getStyle() {
		String style;
		style = "stroke-width:"+strokeWidth;
		if (strokeColor == null) {
			style += ";stroke:none";
		} else {
			style += ";stroke:#"+strokeColor;
		}
		if (isClosed()) {
			style += ";fill:#000000;fill-opacity:1"+
			";fill-rule:nonzero"+
			";stroke-linecap:square"+
			";stroke-linejoin:miter"+
			";stroke-miterlimit:4"+
			";stroke-dasharray:none"+
			";stroke-dashoffset:0"+
			";stroke-opacity:1";
		}
		return style;
	}
	public Map<String, String> getDetails() {
		Pnt arc = getArc();
		if (arc == null) {
			return null;
		}
		Map<String, String> details = new HashMap<String,String>();
		details.put("sodipodi:type", "arc");
		details.put("sodipodi:cx", Svg.getX(getX()));
		details.put("sodipodi:cy", Svg.getY(getY()));
		details.put("sodipodi:rx", Svg.getX(arc.getRx()));
		details.put("sodipodi:ry", Svg.getY(arc.getRy()));
		return details;
	}
	private boolean isClosed() {
		return (points.size() > 0) &&
				(points.get(0).getX() == points.get(points.size()-1).getX()) &&
				(points.get(0).getY() == points.get(points.size()-1).getY());
	}
	private Pnt getArc() {
		for (Pnt point: points) {
			if (!point.getArc().equals(Arc.No)) {
				return point;
			}
		}
		return null;
	}
}

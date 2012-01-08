package com.google.code.autowiring.beans;

import java.util.HashMap;
import java.util.Map;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.svg.Svg;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Path implements Bean {

	private double x1;
	private double y1;
	private Double x2;
	private Double y2;
	private String color;
	private boolean junction = false;

	protected Path() {
		super();
	}

	public double getX1() {
		return x1;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	public double getY1() {
		return y1;
	}
	public void setY1(double y1) {
		this.y1 = y1;
	}
	public double getX2() {
		return x2;
	}
	public void setX2(Double x2) {
		this.x2 = x2;
	}
	public double getY2() {
		return y2;
	}
	public void setY2(Double y2) {
		this.y2 = y2;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getD() {
		String d;
		if (junction) {
			d="M "+(getX1()+getX2())+" "+getY1()+
			" A "+getX2()+" "+getY2()+" 0 1 1  "+(getX1()-getX2())+","+getY1()+
			" A "+getX2()+" "+getY2()+" 0 1 1  "+(getX1()+getX2())+" "+getY1()+
			" z";
		} else {
			d="M "+getX1()+","+getY1()+" L "+getX2()+","+getY2();
		}
		return d;
	}
	public String getStyle() {
		String style;
		if (junction) {
			style = "fill:#000000;fill-opacity:1"+
			";fill-rule:nonzero"+
			";stroke:none"+
			";stroke-width:2"+
			";stroke-linecap:square"+
			";stroke-linejoin:miter"+
			";stroke-miterlimit:4"+
			";stroke-dasharray:none"+
			";stroke-dashoffset:0"+
			";stroke-opacity:1";
		} else {
			style = "stroke:#"+getColor()+
			";stroke-width:1";
		}
		return style;
	}
	public void setJunction(boolean junction) {
		this.junction = junction;
	}
	public Map<String, String> getDetails() {
		if (!junction) {
			return null;
		}
		Map<String, String> details = new HashMap<String,String>();
		details.put("sodipodi:type", "arc");
		details.put("sodipodi:cx", Svg.getX(getX1()));
		details.put("sodipodi:cy", Svg.getY(getY1()));
		details.put("sodipodi:rx", "2.5");
		details.put("sodipodi:ry", "2.5");
		return details;
	}
}

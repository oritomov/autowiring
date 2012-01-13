package com.google.code.autowiring.beans;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Pnt implements Bean {

	public enum Arc {
		No(0), In(1), Out(2);
	
		private int code;

		private Arc(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static Arc get(int code) {
			for(Arc arc: Arc.values()) {
				if (arc.getCode() == code) {
					return arc;
				}
			}
			return null;
		}
	};

	private double x;
	private double y;
	private double rx;
	private double ry;
	private Arc arc;

	public Pnt() {
		super();
	}

	public Pnt(double x, double y) {
		this.x = x;
		this.y = y;
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
	public double getRx() {
		return rx;
	}
	public void setRx(double rx) {
		this.rx = rx;
	}
	public double getRy() {
		return ry;
	}
	public void setRy(double ry) {
		this.ry = ry;
	}
	public Arc getArc() {
		return arc;
	}
	public void setArc(Arc arc) {
		this.arc = arc;
	}
	public String getD(double x, double y) {
		switch (arc) {
			case No:
				return (x+getX())+","+(y+getY());
			case In:
			case Out:
				return rx+" "+ry+" 0 1 1  "+(x+getX())+","+(y+getY());
			default:
				return null;
		}
	}

	public double distance(Pnt point) {
		double x = point.getX() - this.getX();
		double y = point.getY() - this.getY();
		return Math.sqrt(x * x + y * y);
	}

	public double distance(double x, double y) {
		x -= this.getX();
		y -= this.getY();
		return Math.sqrt(x * x + y * y);
	}
}

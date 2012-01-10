package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Point implements Bean {

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

	public Point() {
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
	public void setArc(String code) {
		this.arc = Arc.get(Integer.parseInt(code));
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
}

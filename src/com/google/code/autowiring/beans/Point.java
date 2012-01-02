package com.google.code.autowiring.beans;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Point implements Bean {

	public enum Arc {
		No(0);
	
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
	private Arc arc;

	public Point(double x, double y, Arc arc) {
		super();
		this.x = x;
		this.y = y;
		this.arc = arc;
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Arc getArc() {
		return arc;
	}
}

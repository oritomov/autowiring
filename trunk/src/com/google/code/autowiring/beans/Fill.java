package com.google.code.autowiring.beans;

import java.awt.Color;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Fill implements Bean {

	public enum FillInx {
		None(-1), Solid(0);

		private int code;

		private FillInx(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static FillInx get(int code) {
			for(FillInx fill: FillInx.values()) {
				if (fill.getCode() == code) {
					return fill;
				}
			}
			return null;
		}
	};

	private FillInx fill;
	private Color color;

	public Fill(FillInx fill, Color color) {
		super();
		this.fill = fill;
		this.color = color;
	}
	public FillInx getFill() {
		return fill;
	}
	public Color getColor() {
		return color;
	}
}

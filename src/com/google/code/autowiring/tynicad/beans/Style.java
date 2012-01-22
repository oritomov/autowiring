package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.beans.Pattern.Color;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Style implements RefBean {

	public enum EStyle {
		Solid(0);

		private int code;

		private EStyle(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static EStyle get(int code) {
			for(EStyle style: EStyle.values()) {
				if (style.getCode() == code) {
					return style;
				}
			}
			return null;
		}
	}

	private String id;
	private EStyle style;
	private Color color;
	private int thickness;

	public Style() {
	}

	@Override 
	public String getId() {
		return id;
	}
	@Override 
	public void setId(String id) {
		this.id = id;
	}
	public EStyle getStyle() {
		return style;
	}
	public void setStyle(String code) {
		this.style = EStyle.get(Integer.parseInt(code));
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setColor(String bgr) {
		Color color = TyniCAD.getColor(bgr);
		this.color = color;
	}
	public int getThickness() {
		return thickness;
	}
	public void setThickness(String thickness) {
		this.thickness = Integer.parseInt(thickness);
	}
}

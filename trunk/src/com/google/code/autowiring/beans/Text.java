package com.google.code.autowiring.beans;

import java.util.HashMap;
import java.util.Map;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring.Direction;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Text implements Bean {

	private double x;
	private double y;
	private int fontSize;
	private String color;
	private String fontName;
	private Direction direction;
	private String text;

	public double getX() {
		switch (getDirection()) {
			case Up:
			case Down:
				return -y;
			default:
				return x;
		}
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		switch (getDirection()) {
			case Up:
			case Down:
				return x;
				//x="720.0" y="38.0">
				//x="-37.720947" y="721.8772"
			default:
				return y;
		}
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public String getStyle() {
		String style = "font-size:"+getFontSize()+"px"+
		";fill:#"+getColor()+
		";font-family:"+getFontName();
		//;font-weight:normal
		//;font-style:normal
		//;font-stretch:normal
		//;font-variant:normal
		//;writing-mode:lr
		//;line-height:125%
		switch (getDirection()) {
			case Up:
			case Left:
				style += ";text-align:start"+
				";text-anchor:start";
				break;
			case Down:
			case Right:
				style += ";text-anchor:end" +
				";text-align:end";
				break;
		}
		return style;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Map<String, String> getDetails() {
		switch (getDirection()) {
			case Up:
			case Down:
				Map<String, String> details = new HashMap<String,String>();
				details.put("transform", "matrix(0,-1,1,0,0,0)");
				return details;
			default:
				return null;
		}
	}
	public String getSodipodi_role() {
		return "line";
	}
}

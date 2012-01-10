package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Options implements RefBean {

	public final static String OPTIONS = "options";

	private String colorWire;
	//COLOR_BUS 0000FF
	private String colorJunction;
	//COLOR_NOCONNECT 000000
	//COLOR_LABEL 208000
	//COLOR_POWER 000000
	private String colorPin;
	//COLOR_HIDDEN_PIN 208020
	//COLOR_BACKGROUND FFFFFF

	public Options() {
		super();
	}

	@Override
	public String getId() {
		return OPTIONS;
	}
	@Override
	public void setId(String id) {
	}

	public String getColorWire() {
		return colorWire;
	}
	public void setColorWire(String colorWire) {
		this.colorWire = colorWire;
	}
	public String getColorJunction() {
		return colorJunction;
	}
	public void setColorJunction(String bgr) {
		this.colorJunction = TyniCAD.getColor(bgr);
	}
	public String getColorPin() {
		return colorPin;
	}
	public void setColorPin(String bgr) {
		this.colorPin = TyniCAD.getColor(bgr);
	}
}

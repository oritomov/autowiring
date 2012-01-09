package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Options implements RefBean {

	public final static String OPTIONS = "options";

	private String colorPin;
	private String colorWire;

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

	public String getColorPin() {
		return colorPin;
	}
	public void setColorPin(String bgr) {
		this.colorPin = TyniCAD.getColor(bgr);
	}
	public String getColorWire() {
		return colorWire;
	}
	public void setColorWire(String colorWire) {
		this.colorWire = colorWire;
	}
}

package com.google.code.autowiring.tynicad.beans;

import com.google.code.autowiring.Bean;

/**
 * In order to transform TyniCAD color description BlueGreenRed to SVG description RedGreenBlue
 * @author	Orlin Tomov
 * @version	1.0
 */
public class BgrColor implements Bean {

	private String color;

	public BgrColor(String bgr) {
		super();
		this.color = getColor(bgr);
	}

	private String getColor(String bgr) {
		int r = Integer.parseInt(bgr.substring(4), 16);
		int g = Integer.parseInt(bgr.substring(2, 4), 16);
		int b = Integer.parseInt(bgr.substring(0, 2), 16);
		return
			("0" + Integer.toHexString(r)).substring(Integer.toHexString(r).length()-1) + 
			("0" + Integer.toHexString(g)).substring(Integer.toHexString(g).length()-1) + 
			("0" + Integer.toHexString(b)).substring(Integer.toHexString(b).length()-1);
	}

	public String getColor() {
		return color;
	}
}

package com.google.code.autowiring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Conv;

public class Pattern implements Bean {

	public enum Color {
		red(   0xff,0x00,0x00), 
		green( 0x00,0xff,0x00), 
		blue(  0x00,0x00,0xff), 
		black( 0x00,0x00,0x00),
		white( 0xbf,0xbf,0xbf),
		gray(  0x7f,0x7f,0x7f),
		yellow(0xff,0xff,0x00),
		purple(0xff,0x00,0xff),
		broun( 0x9f,0x6f,0x2f),
		fuzebox(0xCF,0xAF,0x9F);
		
		private int r;
		private int g;
		private int b;

		private Color(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
		public String getRgb() {
			return ("0" + Integer.toHexString(r)).substring(Integer.toHexString(r).length()-1) + 
				   ("0" + Integer.toHexString(g)).substring(Integer.toHexString(g).length()-1) + 
				   ("0" + Integer.toHexString(b)).substring(Integer.toHexString(b).length()-1);
		}
		public static Color valueOf(int r, int g, int b) {
			for(Color color: Color.values()) {
				if ((color.r == r) && (color.g == g) && (color.b == b)) {
					return color;
				}
			}
			Conv.log().warn("unknown color: " + r + ", " + g + ", " + b);
			return null;
		}
	}

	private static final int SIZE = 6;
	private String id;
	private int width;
	private int height;
	private List<Bean> beans = new ArrayList<Bean>();

	public Pattern(String id, Color[] colors) {
		this.id = id;
		setWidth(SIZE*colors.length);
		setHeight(SIZE*colors.length);
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors.length; j++) {
				Color color = colors[(i * colors.length  + i + j) % colors.length];
				double x = i * SIZE;
				double y = j * SIZE;
				Rect rect = new Rect();
				rect.setX(x);
				rect.setY(y);
				rect.setHeight(SIZE);
				rect.setWidth(SIZE);
				rect.setFillColor(color.getRgb());
				beans.add(rect);
			}
		}
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public List<Bean> getBeans() {
		return beans;
	}
	public Map<String, String> getDetails(Double x, Double y) {
		Map<String, String> details = new HashMap<String,String>();
		details.put("patternUnits","userSpaceOnUse");
		return details;
	}
}

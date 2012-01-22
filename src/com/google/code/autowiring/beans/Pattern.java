package com.google.code.autowiring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.autowiring.Bean;

public class Pattern implements Bean {

	public enum Color {
		red("ff0000"), 
		green("00ff00"), 
		blue("0000ff"), 
		black("000000"),
		white("bfbfbf"),
		gray("7f7f7f"),
		yellow("ffff00"),
		purple("ff00ff"),
		broun("9f6f2f");
		
		private String rgb;

		private Color(String rgb) {
			this.rgb = rgb;
		}
		public String getRgb() {
			return rgb;
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

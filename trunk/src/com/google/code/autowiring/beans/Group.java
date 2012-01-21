package com.google.code.autowiring.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.code.autowiring.Bean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Group  implements Bean {

	private double x;
	private double y;
	private List<Bean> beans;

	protected Group() {
		super();
		beans = new ArrayList<Bean>();
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
	public List<Bean> getBeans() {
		return beans;
	}
	public void addBeans(Bean bean) {
		beans.add(bean);
	}

	public String getTransform() {
		String transform = "translate("+getX()+","+getY()+")";
		return transform;
	}
}

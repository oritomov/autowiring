package com.google.code.autowiring.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring.Direction;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Group  implements Bean {

	private double x;
	private double y;
	private List<Bean> beans;
	private Direction direction;
	private boolean mirror;

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
	public boolean getMirror() {
		return mirror;
	}
	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
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
	// 558.0,154.0
	// 90 matrix(0,1,-1,0,715.0481,-408.9519)
	//180 matrix(-1,0,0,-1,1124,306.0962)
	//270 matrix(0,-1,1,0,408.9519,715.0481)
	//mirror
	// matrix(-1,0,0,1,1124,0)
	
}

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
		if (direction == null) {
			return "translate("+x+","+y+")";
		}
		String transform = direction.getTransform(x, y);
		return transform;
	}
}

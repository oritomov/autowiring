package com.google.code.autowiring.tynicad.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.tynicad.beans.RefBean;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class SymbolDef implements RefBean {

	private String id;
	private double x;
	private double y;
	private List<Bean> beans = new ArrayList<Bean>();

	public SymbolDef() {
		super();
	}

	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
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
	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public List<Bean> detBeans() {
		return beans;
	}
	public void addBeans(Bean bean) {
		beans.add(bean);
	}
}

package com.google.code.autowiring.tynicad.beans;

import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Group;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Symbol extends Group {

	private SymbolDef def;

	public Symbol() {
		super();
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setId(SymbolDef def) {
		this.def = def;
	}
	@Override
	public List<Bean> getBeans() {
		// TODO
		return def.detBeans();
	}
}

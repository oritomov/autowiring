package com.google.code.autowiring.tynicad.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring.Direction;
import com.google.code.autowiring.beans.Group;
import com.google.code.autowiring.tynicad.TyniCAD;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Symbol extends Group {

	private SymbolDef def;
	private String color;
	private Font font;
	private List<Field> fields = new ArrayList<Field>();

	public Symbol(List<Bean> refs) {
		super();
		setFont(refs, Pin.FONT_ID);
		setColor(refs, Pin.COLOR_PIN);
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setId(SymbolDef def) {
		this.def = def;
	}
	private void setFont(List<Bean> refs, String refId) {
		this.font = (Font) TyniCAD.getRef(refs, Font.class, refId);
	}
	private void setColor(List<Bean> refs, String name) {
		this.color = TyniCAD.getOption(refs, name);
	}
	public void addFields(Field field) {
		fields.add(field);
	}
	@Override
	public List<Bean> getBeans() {
		List<Bean> beans = new ArrayList<Bean>();
		for (Field field: fields) {
			if (field.getShow() > 0) {
				Label label = new Label();
				label.setX(field.getX()+def.getX());
				label.setY(field.getY()+def.getY());
				label.setLabel(field.getValue());
				label.setDirection(Direction.Left);
				label.setColor(color);
				label.setFont(font);
				if (label.getFontSize() == 0) {
					label.setFontSize(Pin.FONT_SIZE);
				}
				beans.add(label);
			}
		}
		beans.addAll(def.detBeans());
		return beans;
	}
	public double getOffsX() {
		return getX() - def.getX();
	}
	public double getOffsY() {
		return getY() - def.getY();
	}
}

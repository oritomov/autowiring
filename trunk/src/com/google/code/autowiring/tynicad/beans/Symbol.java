package com.google.code.autowiring.tynicad.beans;

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

	private static final String COLOR = "color";
	private SymbolDef def;
	private String fieldColor;
	private Font fieldFont;
	private String color;

	public Symbol(List<Bean> refs) {
		super();
		setFieldFont(refs, Pin.FONT_ID);
		setFieldColor(refs, Pin.COLOR_PIN);
	}

	public void setPos(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	public void setId(SymbolDef def) {
		this.def = def;
		/** for all */
		for (Bean bean: def.detBeans()) {
			addBeans(TyniCAD.clone(bean));
		}
	}
	public void setRotate(String rotate) {
		int code = Integer.parseInt(rotate);
		Direction direction = Direction.valueOf(code);
		setDirection(direction);
	}
	private void setFieldFont(List<Bean> refs, String refId) {
		this.fieldFont = (Font) TyniCAD.getRef(refs, Font.class, refId);
	}
	private void setFieldColor(List<Bean> refs, String name) {
		this.fieldColor = TyniCAD.getOption(refs, name);
	}
	public String getColor() {
		return color;
	}
	public void addFields(Field field) {
		if (COLOR.equalsIgnoreCase(field.getDescription())) {
			color = field.getValue();
		} else {
			if (field.getShow() > 0) {
				Label label = new Label();
				label.setX(field.getX()+def.getX());
				label.setY(field.getY()+def.getY());
				label.setLabel(field.getValue());
				label.setDirection(Direction.Right);
				label.setColor(fieldColor);
				label.setFont(fieldFont);
				if (label.getFontSize() == 0) {
					label.setFontSize(Pin.FONT_SIZE);
				}
				addBeans(label);
			}
		}
	}
	public double getDefX() {
		return def.getX();
	}
	public double getDefY() {
		return def.getY();
	}
}

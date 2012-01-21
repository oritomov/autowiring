package com.google.code.autowiring.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Conv;
import com.google.code.autowiring.beans.Pattern;
import com.google.code.autowiring.beans.Pattern.Color;
import com.google.code.autowiring.beans.Pnt;
import com.google.code.autowiring.beans.Path;
import com.google.code.autowiring.beans.Rect;
import com.google.code.autowiring.beans.Text;
import com.google.code.autowiring.tynicad.beans.Symbol;

public class WireTool {

	public static List<? extends Bean> colorLines(List<Bean> beans) {
		return colorLines(beans, 0, 0);
	}
	private static List<Pattern> colorLines(List<Bean> beans, double x, double y) {
		List<Pattern> defs = new ArrayList<Pattern>();
		Iterator<Bean> i = beans.iterator();
		while (i.hasNext()) {
			Bean bean1 = i.next();
			if (bean1 instanceof Text) {
				Text text = (Text) bean1;
				for (Bean bean2: beans) {
					if (bean2 instanceof Path) {
						Path wire = (Path) bean2;
						if (pointOnWire(text.getX(x, y), text.getY(x, y), wire)) {
							if (colorLines(defs, text.getText(), wire)) {
								i.remove();
								break;
							}
						}
					}
				}
			}
			if (bean1 instanceof Symbol) {
				Symbol group = (Symbol) bean1;
				List<Bean> groupBeans = group.getBeans();
				if (group.getColor() != null) {
					colorSymbol(groupBeans, group.getColor());
				}
				double groupX = x - group.getDefX();
				double groupY = y - group.getDefY();
				defs.addAll(colorLines(groupBeans, groupX, groupY));
			}
		}
		return defs;
	}

	private static boolean colorLines(List<Pattern> defs, String text, Path wire) {
		Color color;
		try {
			color = Color.valueOf(text);
		} catch (IllegalArgumentException e) {
			return createPatern(defs, text, wire);
		}
		wire.setStrokeColor(color.getRgb());
		return true;
	}

	private static boolean createPatern(List<Pattern> defs, String text, Path wire) {
		String[] splet = text.split("-");
		Color[] colors = new Color[splet.length];
		for (int i = 0; i < splet.length; i++) {
			try {
				colors[i] = Color.valueOf(splet[i]);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		Pattern pattern = null;
		for(Pattern p: defs) {
			if (text.equalsIgnoreCase(p.getId())) {
				pattern = p;
				break;
			}
		}
		if (pattern == null) {
			pattern = new Pattern(text, colors);
			defs.add(pattern);
		}
		wire.setStrokeColor(pattern.getId());
		wire.setStrokeUrl(true);
		return true;
	}

	private static boolean pointOnWire(Double x, Double y, Path wire) {
		for (int i = 1; i < wire.getPoints().size(); i++) {
			Pnt point1 = wire.getPoints().get(i-1);
			Pnt point2 = wire.getPoints().get(i);
			if (distanceToSegment(x, y, point1, point2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the distance of point to the segment defined by p1,p2 is zero;
	 * 
	 * @param x of Point to which we want to know the distance of the segment defined by p1,p2
	 * @param y of Point to which we want to know the distance of the segment defined by p1,p2
	 * @param p1 First point of the segment
	 * @param p2 Second point of the segment
	 * @return Does the distance of point to the segment defined by p1,p2 zero
	 */
	private static boolean distanceToSegment(double x, double y, Pnt p1, Pnt p2) {

		final double xDelta = p2.getX() - p1.getX();
		final double yDelta = p2.getY() - p1.getY();

		if ((xDelta == 0) && (yDelta == 0)) {
			Conv.log().warn("p1 and p2 cannot be the same point");
			return false;
		}

		final double u = ((x - p1.getX()) * xDelta + (y - p1.getY()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

		final Pnt closestPoint;
		if (u < 0) {
			closestPoint = p1;
		} else if (u > 1) {
			closestPoint = p2;
		} else {
			closestPoint = new Pnt(p1.getX() + u * xDelta, p1.getY() + u * yDelta);
		}

		return closestPoint.distance(x, y) == 0;
	}

	public static boolean joinWire(List<Bean> beans, Bean bean2) {
		boolean result = false;
		if (bean2 instanceof Path) {
			Iterator<Bean> i = beans.iterator();
			while (i.hasNext()) {
				Bean bean1 = i.next();
				if (bean1 instanceof Path) {
					Path wire1 = (Path) bean1;
					Path wire2 = (Path) bean2;
					if ((!wire1.isClosed()) &&
						(!wire2.isClosed()) &&
						(wire1.getStrokeColor() != null) &&
						(wire1.getStrokeColor().equalsIgnoreCase(wire2.getStrokeColor())) && 
						(wire1.getStrokeWidth() == wire2.getStrokeWidth()) && 
						((wire1.getFillColor() == null) && (wire2.getFillColor() == null))) {
						if (checkContWire(wire1, wire2)) {
							joinWires(wire1, wire2);
							result = true;
						} else
						if (checkContWire(wire2, wire1)) {
							insertWire(wire1, wire2);
							result = true;
						} else
						if (checkBeginWire(wire1, wire2)) {
							insertBackWire(wire1, wire2);
							result = true;
						} else
						if (checkEndWire(wire1, wire2)) {
							joinBackWires(wire1, wire2);
							result = true;
						}
						if (result) {
							i.remove();
							if (!joinWire(beans, bean1)) {
								beans.add(bean1);
							}
							break;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Result A-B-C
	 * @param wire1 B-C
	 * @param wire2 A-B
	 */
	private static void insertWire(Path wire1, Path wire2) {
		wire1.getPoints().remove(0);
		for (int i = wire2.getPoints().size() - 1; i >= 0; i--) {
			Pnt point = wire2.getPoints().get(i);
			double x = point.getX() + wire1.getX() - wire2.getX();
			double y = point.getY() + wire1.getY() - wire2.getY();
			point.setX(x);
			point.setY(y);
			wire1.getPoints().add(0,point);
		}
	}

	/**
	 * Result A-B-C
	 * @param wire1 A-B
	 * @param wire2 B-C
	 */
	private static void joinWires(Path wire1, Path wire2) {
		for (int i = 1; i < wire2.getPoints().size(); i++) {
			Pnt point = wire2.getPoints().get(i);
			double x = point.getX() + wire1.getX() - wire2.getX();
			double y = point.getY() + wire1.getY() - wire2.getY();
			point.setX(x);
			point.setY(y);
			wire1.addPoints(point);
		}
	}

	/**
	 * Result A-B-C
	 * @param wire1 B-C
	 * @param wire2 B-A
	 */
	private static void insertBackWire(Path wire1, Path wire2) {
		for (int i = 1; i < wire2.getPoints().size(); i++) {
			Pnt point = wire2.getPoints().get(i);
			double x = point.getX() + wire1.getX() - wire2.getX();
			double y = point.getY() + wire1.getY() - wire2.getY();
			point.setX(x);
			point.setY(y);
			wire1.getPoints().add(0,point);
		}
	}

	/**
	 * Result A-B-C
	 * @param wire1 A-B
	 * @param wire2 C-B
	 */
	private static void joinBackWires(Path wire1, Path wire2) {
		for (int i = wire2.getPoints().size() - 2; i >= 0; i--) {
			Pnt point = wire2.getPoints().get(i);
			double x = point.getX() + wire1.getX() - wire2.getX();
			double y = point.getY() + wire1.getY() - wire2.getY();
			point.setX(x);
			point.setY(y);
			wire1.addPoints(point);
		}
	}

	/**
	 * 
	 * @param wire1 ..B
	 * @param wire2 B..
	 * @return true if wire1 ends with start of wire2 
	 */
	private static boolean checkContWire(Path wire1, Path wire2) {
		Pnt p1last = wire1.getPoints().get(wire1.getPoints().size() - 1);
		Pnt p2first = wire2.getPoints().get(0);
		return ((p1last.getX()+wire1.getX()==p2first.getX()+wire2.getX()) && 
				(p1last.getY()+wire1.getY()==p2first.getY()+wire2.getY()));
	}

	/**
	 * 
	 * @param wire1 B..
	 * @param wire2 B..
	 * @return true if wire1 and wire2 start on same point 
	 */
	private static boolean checkBeginWire(Path wire1, Path wire2) {
		Pnt p1first = wire1.getPoints().get(0);
		Pnt p2first = wire2.getPoints().get(0);
		return ((p1first.getX()+wire1.getX()==p2first.getX()+wire2.getX()) && 
				(p1first.getY()+wire1.getY()==p2first.getY()+wire2.getY()));
	}

	/**
	 * 
	 * @param wire1 ..B
	 * @param wire2 B..
	 * @return true if wire1 and wire2 end on same point 
	 */
	private static boolean checkEndWire(Path wire1, Path wire2) {
		Pnt p1last = wire1.getPoints().get(wire1.getPoints().size() - 1);
		Pnt p2last = wire2.getPoints().get(wire2.getPoints().size() - 1);
		return ((p1last.getX()+wire1.getX()==p2last.getX()+wire2.getX()) && 
				(p1last.getY()+wire1.getY()==p2last.getY()+wire2.getY()));
	}

	private static void colorSymbol(List<Bean> beans, String color) {
		Color fill;
		Color stroke = null;
		try {
			fill = Color.valueOf(color);
		} catch (IllegalArgumentException e) {
			String[] splet = color.split("-");
			fill = Color.valueOf(splet[0]);
			stroke = Color.valueOf(splet[1]);
		}
		for (Bean bean: beans) {
			if (bean instanceof Path) {
				Path path = (Path) bean;
				if (path.isClosed()) {
					path.setFillColor(fill.getRgb());
				}
				if (stroke != null) {
					path.setStrokeColor(stroke.getRgb());
				}
			} else 
			if (bean instanceof Rect) {
				Rect rect = (Rect) bean;
				rect.setFillColor(fill.getRgb());
				if (stroke != null) {
					rect.setStrokeColor(stroke.getRgb());
				}
			}
		}
	}
}

package com.google.code.autowiring.util;

import java.util.Iterator;
import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.beans.Pnt;
import com.google.code.autowiring.beans.Path;

public class WireTool {

	public static boolean cobineWire(List<Bean> beans, Bean bean2) {
		boolean result = false;
		if (bean2 instanceof Path) {
			Iterator<Bean> i = beans.iterator();
			while (i.hasNext()) {
				Bean bean1 = i.next();
				if (bean1 instanceof Path) {
					Path wire1 = (Path) bean1;
					Path wire2 = (Path) bean2;
					if ((wire1.getStrokeColor() != null) &&
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
							if (!cobineWire(beans, bean1)) {
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
}

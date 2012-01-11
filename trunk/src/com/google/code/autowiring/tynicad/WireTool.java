package com.google.code.autowiring.tynicad;

import java.util.List;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.tynicad.beans.Point;
import com.google.code.autowiring.tynicad.beans.Wire;

public class WireTool {

	public static boolean cobineWire(List<Bean> beans, Bean bean2) {
		if (bean2 instanceof Wire) {
			for (Bean bean1: beans) {
				if (bean1 instanceof Wire) {
					Wire wire1 = (Wire) bean1;
					Wire wire2 = (Wire) bean2;
					if ((wire1.getStrokeColor().equalsIgnoreCase(wire2.getStrokeColor())) && 
						(wire1.getStrokeWidth() == wire2.getStrokeWidth()) && 
						((wire1.getFillColor() == null) && (wire2.getFillColor() == null))) {
						if (checkContWire(wire1, wire2)) {
							joinWires(wire1, wire2);
							return true;
						} else
						if (checkContWire(wire2, wire1)) {
							insertWire(wire1, wire2);
							return true;
						} else
						if (checkBeginWire(wire1, wire2)) {
							insertBackWire(wire1, wire2);
							return true;
						} else
						if (checkEndWire(wire1, wire2)) {
							joinBackWires(wire1, wire2);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Result A-B-C
	 * @param wire1 B-C
	 * @param wire2 A-B
	 */
	private static void insertWire(Wire wire1, Wire wire2) {
		wire1.getPoints().remove(0);
		for (int i = wire2.getPoints().size() - 1; i >= 0; i--) {
			Point point = (Point) wire2.getPoints().get(i);
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
	private static void joinWires(Wire wire1, Wire wire2) {
		for (int i = 1; i < wire2.getPoints().size(); i++) {
			Point point = (Point) wire2.getPoints().get(i);
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
	private static void insertBackWire(Wire wire1, Wire wire2) {
		for (int i = 1; i < wire2.getPoints().size(); i++) {
			Point point = (Point) wire2.getPoints().get(i);
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
	private static void joinBackWires(Wire wire1, Wire wire2) {
		for (int i = wire2.getPoints().size() - 2; i >= 0; i--) {
			Point point = (Point) wire2.getPoints().get(i);
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
	private static boolean checkContWire(Wire wire1, Wire wire2) {
		Point p1last = (Point) wire1.getPoints().get(wire1.getPoints().size() - 1);
		Point p2first = (Point) wire2.getPoints().get(0);
		return ((p1last.getX()+wire1.getX()==p2first.getX()+wire2.getX()) && 
				(p1last.getY()+wire1.getY()==p2first.getY()+wire2.getY()));
	}

	/**
	 * 
	 * @param wire1 B..
	 * @param wire2 B..
	 * @return true if wire1 and wire2 start on same point 
	 */
	private static boolean checkBeginWire(Wire wire1, Wire wire2) {
		Point p1first = (Point) wire1.getPoints().get(0);
		Point p2first = (Point) wire2.getPoints().get(0);
		return ((p1first.getX()+wire1.getX()==p2first.getX()+wire2.getX()) && 
				(p1first.getY()+wire1.getY()==p2first.getY()+wire2.getY()));
	}

	/**
	 * 
	 * @param wire1 ..B
	 * @param wire2 B..
	 * @return true if wire1 and wire2 end on same point 
	 */
	private static boolean checkEndWire(Wire wire1, Wire wire2) {
		Point p1last = (Point) wire1.getPoints().get(wire1.getPoints().size() - 1);
		Point p2last = (Point) wire2.getPoints().get(wire2.getPoints().size() - 1);
		return ((p1last.getX()+wire1.getX()==p2last.getX()+wire2.getX()) && 
				(p1last.getY()+wire1.getY()==p2last.getY()+wire2.getY()));
	}
}

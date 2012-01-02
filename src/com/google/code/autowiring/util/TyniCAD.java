package com.google.code.autowiring.util;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring;
import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.beans.Fill;
import com.google.code.autowiring.beans.Fill.FillInx;
import com.google.code.autowiring.beans.Junction;
import com.google.code.autowiring.beans.Label;
import com.google.code.autowiring.beans.Pin;
import com.google.code.autowiring.beans.Point;
import com.google.code.autowiring.beans.Point.Arc;
import com.google.code.autowiring.beans.Polygon;
import com.google.code.autowiring.beans.Position;
import com.google.code.autowiring.beans.Rectangle;
import com.google.code.autowiring.beans.Wire;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class TyniCAD extends Xml implements Wiring {

	private static final String ROOT = "TinyCADSheets";
	private static final String TINY_CAD = "TinyCAD";
	private static final String OPTIONS = "OPTIONS";
	private static final String COLOR_WIRE = "COLOR_WIRE";
	private static final String COLOR_PIN = "COLOR_PIN";
	private static final String ID = "id";
	private static final String RECTANGLE = "RECTANGLE";
	private static final String STYLE = "style";
	private static final String FILL = "fill";
	private static final String SYMBOL = "SYMBOL";
	private static final String SYMBOLDEF = "SYMBOLDEF";
	private static final String REF_POINT = "REF_POINT";
	private static final String COLOR = "COLOR";
	private static final String THICKNESS = "THICKNESS";
	private static final String LABEL = "LABEL";
	private static final String WIRE = "WIRE";
	private static final String JUNCTION = "JUNCTION";
	private static final String PIN = "PIN";
	private static final String TEXT = "TEXT";
	private static final String FONT = "FONT";
	private static final String POLYGON = "POLYGON";
	private static final String POINT = "POINT";

	private Node dsnRoot;
	private Node child;
	private Map<Node, Position> stored = new LinkedHashMap<Node, Position>();
	private Color wireColor = new Color(0);
	private Font pinFont;
	private Color pinColor = new Color(0);
	private Position origin = new Position(0, 0);

	public TyniCAD(String fileName) throws WiringException {
		super(fileName);
		dsnRoot = getNode(doc, ROOT);
		Node tiny = getNode(dsnRoot, TINY_CAD);
		/** will look for next elements after "OPTIONS" */
		child = getNode(tiny, OPTIONS);
		wireColor = getColor(getNode(child, COLOR_WIRE).getTextContent());
		pinFont = getFont(find(dsnRoot, FONT, "1"));
		pinFont = pinFont.deriveFont(3.0f);
		pinColor = getColor(getNode(child, COLOR_PIN).getTextContent());
	}

	@Override
	public List<Bean> getBeans() throws WiringException {
		List<Bean> beans = new ArrayList<Bean>();
		Bean bean;
		while ((bean = nextBean()) != null) {
			beans.add(bean);
		}
		return beans;
	}

	private Bean nextBean() throws WiringException {
		if (child == null) {
			if (stored.size() > 0) {
				restore();
				return nextBean();
			} else {
				return null;
			}
		}
		child = child.getNextSibling();
		while ((child != null) && (child.getNodeType() != Node.ELEMENT_NODE)) {
			child = child.getNextSibling();
		}
		if (child == null) {
			if (stored.size() > 0) {
				restore();
				return nextBean();
			} else {
				return null;
			}
		}
		Bean bean = createBean(dsnRoot, child);
		return bean;
	}

	private void store(Node node, Position displace) {
		stored.put(child, origin);
		origin = displace.move(origin);
		child = node;
	}

	private void restore() {
		Iterator<Entry<Node, Position>> i = stored.entrySet().iterator();
		Entry<Node, Position> entry = null;
		while (i.hasNext()) entry  = i.next();
		child = entry.getKey();
		origin = entry.getValue();
		stored.remove(child);
	}

	@Override
	public void setBeans(List<Bean> beans) throws WiringException {
		throw new WiringException("Not implemted!");
	}

	@Override
	public void save() throws WiringException {
		throw new WiringException("Not implemted!");
	}

	private Bean createBean(Node root, Node node) throws WiringException {
		Bean bean = null;
		if (node.getNodeName().equalsIgnoreCase(RECTANGLE)) {
			bean = rectangle(root, node);
		} else
		if (node.getNodeName().equalsIgnoreCase(LABEL)) {
			bean = label(root, node);
		} else
		if (node.getNodeName().equalsIgnoreCase(TEXT)) {
			bean = label(root, node);
		} else
		if (node.getNodeName().equalsIgnoreCase(WIRE)) {
			bean = wire(node);
		} else
		if (node.getNodeName().equalsIgnoreCase(JUNCTION)) {
			bean = junction(node);
		} else
		if (node.getNodeName().equalsIgnoreCase(POLYGON)) {
			bean = polygon(node);
		} else
		if (node.getNodeName().equalsIgnoreCase(PIN)) {
			bean = pin(root, node);
		} else
		if (node.getNodeName().equalsIgnoreCase(SYMBOL)) {
			bean = symbol(root, node);
		} 
		else throw new WiringException("Unknown node: " + node.getNodeName());
		return bean;
	}

	private Bean symbol(Node root, Node node) {
		String pos = getAttrValue(node, "pos");
		double x = Double.parseDouble(pos.split(",")[0]);
		double y = Double.parseDouble(pos.split(",")[1]);
		String id = getAttrValue(node, ID);
		Node defRoot = find(dsnRoot, SYMBOLDEF, id);
		Node refPoint = getNode(defRoot, REF_POINT);
		pos = getAttrValue(refPoint, "pos");
		x -= Double.parseDouble(pos.split(",")[0]);
		y -= Double.parseDouble(pos.split(",")[1]);
		Position displace = new Position(x, y);
		Node def = getNode(defRoot, TINY_CAD);
		Node defChild = def.getFirstChild();
		store(defChild, displace);
		Bean bean = nextBean();
		return bean;
	}

	private Rectangle rectangle(Node root, Node node) {
		String a = getAttrValue(node, "a");
		double x = Double.valueOf(a.split(",")[0]);
		double y = Double.valueOf(a.split(",")[1]);
		String b = getAttrValue(node, "b");
		double x2 = Double.valueOf(b.split(",")[0]);
		double y2 = Double.valueOf(b.split(",")[1]);
		String styleId = getAttrValue(node, STYLE);
		Node style = find(root, STYLE, styleId);
		Color color = getColor(getNode(style, COLOR).getTextContent());
		int thickness = Integer.parseInt(getNode(style, THICKNESS).getTextContent());
		String fillId = getAttrValue(node, FILL);
		Fill fill = fill(find(root, FILL, fillId));
		//Rectangle rectangle = new Rectangle(Math.min(x,x2)+origin.getX(), Math.min(y,y2)+origin.getY(), Math.max(x,x2)+origin.getX(), Math.max(y,y2)+origin.getY(), fill, color, thickness);
		Rectangle rectangle = new Rectangle(x+origin.getX(), y+origin.getY(), x2+origin.getX(), y2+origin.getY(), fill, color, thickness);
		return rectangle;
	}

	private Fill fill(Node node) {
		int fillCode = Integer.parseInt(getNode(node, "INDEX").getTextContent());
		FillInx fillInx = FillInx.get(fillCode);
		Color color = getColor(getNode(node, COLOR).getTextContent());
		Fill fill = new Fill(fillInx, color);
		return fill;
	}

	private Bean polygon(Node node) {
		String pos = getAttrValue(node, "pos");
		double x = Double.parseDouble(pos.split(",")[0]);
		double y = Double.parseDouble(pos.split(",")[1]);
		// style
		// fill
		Polygon polygon = new Polygon(x, y);
		Node p = getNode(node, POINT);
		while (p != null) {
			pos = getAttrValue(p, "pos");
			x = Double.parseDouble(pos.split(",")[0]);
			y = Double.parseDouble(pos.split(",")[1]);
			int arcCode = Integer.parseInt(getAttrValue(p, "arc"));
			Arc arc = Arc.get(arcCode);
			Point point = new Point(x, y, arc);
			polygon.add(point);
			p = nextNode(p, POINT);
		}
		return polygon;
	}

	private Label label(Node root, Node node) {
		String pos = getAttrValue(node, "pos");
		double x = Double.parseDouble(pos.split(",")[0]);
		double y = Double.parseDouble(pos.split(",")[1]);
		int directionCode = Integer.parseInt(getAttrValue(node, "direction"));
		Direction direction = Direction.get(directionCode);
		Color color = getColor(getAttrValue(node, COLOR));
		String fontId = getAttrValue(node, "font");
		Font font = getFont(find(root, FONT, fontId));
		//String style = Xml.getAttrValue(child, "style");
		String text = node.getTextContent();
		Label label = new Label(x+origin.getX(), y+origin.getY(), color, direction, font, text);
		return label;
	}

	private Wire wire(Node node) {
		String a = getAttrValue(node, "a");
		double x1 = Double.parseDouble(a.split(",")[0]);
		double y1 = Double.parseDouble(a.split(",")[1]);
		String b = getAttrValue(node, "b");
		double x2 = Double.parseDouble(b.split(",")[0]);
		double y2 = Double.parseDouble(b.split(",")[1]);
		Wire wire = new Wire(x1+origin.getX(), y1+origin.getY(), x2+origin.getX(), y2+origin.getY(), wireColor);
		return wire;
	}

	private Junction junction(Node node) {
		String pos = getAttrValue(node, "pos");
		double x = Double.parseDouble(pos.split(",")[0]);
		double y = Double.parseDouble(pos.split(",")[1]);
		Junction junction = new Junction(x+origin.getX(), y+origin.getY());
		return junction;
	}

	private Pin pin(Node root, Node node) {
		String pos = getAttrValue(node, "pos");
		double x = Double.parseDouble(pos.split(",")[0]);
		double y = Double.parseDouble(pos.split(",")[1]);
		int directionCode = Integer.parseInt(getAttrValue(node, "direction"));
		Direction direction = Direction.get(directionCode);
		double length = Double.parseDouble(getAttrValue(node, "length"));
		String number = getAttrValue(node, "number");
		String name = node.getTextContent();
		//which='0' elec='0' part='0' show='3' number_pos='0' centre_name='0'
		Pin pin = new Pin(x+origin.getX(), y+origin.getY(), direction, length, number, name, pinColor, pinFont);
		return pin;
	}

	private Node find(Node dsnRoot, String name, String id) {
		Node tiny = getNode(dsnRoot, TINY_CAD);
		Node node = getNode(tiny, name);
		String nodeId = getAttrValue(node, ID);
		while (!id.equalsIgnoreCase(nodeId)) {
			node = nextNode(node, name);
			nodeId = getAttrValue(node, ID);
		}
		return node;
	}

	private Color getColor(String bgr) {
		int r = Integer.parseInt(bgr.substring(4), 16);
		int g = Integer.parseInt(bgr.substring(2, 4), 16);
		int b = Integer.parseInt(bgr.substring(0, 2), 16);
		Color color = new Color(r, g, b);
		return color;
	}

	private Font getFont(Node node) {
		String name = getNode(node, "FACENAME").getTextContent();
		int style = Font.PLAIN;
		int size = Integer.parseInt(getNode(node, "WIDTH").getTextContent());
		Font font = new Font(name, style, size);
		return font;
	}
}

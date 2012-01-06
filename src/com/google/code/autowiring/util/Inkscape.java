package com.google.code.autowiring.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring;
import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.beans.Fill.FillInx;
import com.google.code.autowiring.beans.Junction;
import com.google.code.autowiring.beans.Label;
import com.google.code.autowiring.beans.Pin;
import com.google.code.autowiring.beans.Rectangle;
import com.google.code.autowiring.beans.Wire;
import com.google.code.autowiring.config.CfgEng;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Inkscape extends Xml implements Wiring {

	private static final String ROOT = "svg";
	private static final String DEFS = "defs";
	private static final String SVG_G = "g";
	private static final String LAYER = "layer";
	private static final String RECT = "rect";
	private static final String TEXT = "text";
	private static final String TSPAN = "tspan";
	private static final String PATH = "path";

	private CfgEng engine;
	private Node layer;
	private String path;
	private String fileName;

	public Inkscape(CfgEng engine, String fileName) throws WiringException {
		super();
		this.engine = engine;
		try {
			path = new File(fileName).getCanonicalPath();
			path = path.substring(0, path.lastIndexOf(File.separator));
			this.fileName = fileName;
			layer = init(path, fileName);
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	@Override
	public List<Bean> getBeans() throws WiringException {
		throw new WiringException("Not implemted!");
	}

	@Override
	public void setBeans(List<Bean> beans) throws WiringException {
		for(Bean bean: beans) {
			addBean(bean);
		}
	}

	public void addBean(Bean bean) throws WiringException {
		if (bean instanceof Rectangle) {
			rectangle((Rectangle)bean);
		} else	
		if (bean instanceof Label) {
			text((Label)bean);
		} else	
		if (bean instanceof Wire) {
			path((Wire)bean);
		} else	
		if (bean instanceof Junction) {
			junction((Junction)bean);
		} else	
		if (bean instanceof Pin) {
			pin((Pin)bean);
		}
	}

	@Override
	public void save() throws WiringException {
		try {
			String result = toString(doc);
			System.out.println(result);
			FileWriter out = new FileWriter(path + File.separator + fileName, false);
			out.write(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private Node init(String path, String fileName) {
		Node root = addNode(doc, doc, ROOT);
		setAttrValue(root, "xmlns:dc", "http://purl.org/dc/elements/1.1/");
		setAttrValue(root, "xmlns:cc", "http://web.resource.org/cc/");
		setAttrValue(root, "xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		setAttrValue(root, "xmlns:svg", "http://www.w3.org/2000/svg");
		setAttrValue(root, "xmlns", "http://www.w3.org/2000/svg");
		setAttrValue(root, "xmlns:sodipodi", "http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd");
		setAttrValue(root, "xmlns:inkscape", "http://www.inkscape.org/namespaces/inkscape");
		setAttrValue(root, "width", "744.09448819");
		setAttrValue(root, "height", "1052.3622047");
		setAttrValue(root, "id", ROOT);
		setAttrValue(root, "sodipodi:version", "0.32");
		setAttrValue(root, "inkscape:version", "0.45.1");
		setAttrValue(root, "sodipodi:docbase", path);
		setAttrValue(root, "sodipodi:docname", fileName);
		setAttrValue(root, "inkscape:output_extension", "org.inkscape.output.svg.inkscape");

		Node defs = addNode(doc, root, DEFS);
		setAttrValue(defs, "id", DEFS);

		Node namedview = addNode(doc, root, "sodipodi:namedview");
		setAttrValue(namedview, "id", "base");
		setAttrValue(namedview, "pagecolor", "#ffffff");
		setAttrValue(namedview, "bordercolor", "#666666");
		setAttrValue(namedview, "borderopacity", "1.0");
		setAttrValue(namedview, "gridtolerance", "10000");
		setAttrValue(namedview, "guidetolerance", "10");
		setAttrValue(namedview, "objecttolerance", "10");
		setAttrValue(namedview, "inkscape:pageopacity", "0.0");
		setAttrValue(namedview, "inkscape:pageshadow", "2");
		setAttrValue(namedview, "inkscape:zoom", "0.35");
		setAttrValue(namedview, "inkscape:cx", "375");
		setAttrValue(namedview, "inkscape:cy", "520");
		setAttrValue(namedview, "inkscape:document-units", "px");
		setAttrValue(namedview, "inkscape:current-layer", LAYER);
		setAttrValue(namedview, "inkscape:window-width", "744");
		setAttrValue(namedview, "inkscape:window-height", "573");
		setAttrValue(namedview, "inkscape:window-x", "154");
		setAttrValue(namedview, "inkscape:window-y", "203");

		Node metadata = addNode(doc, root, "metadata");
		setAttrValue(metadata, "id", "metadata");
		Node rdf = addNode(doc, metadata, "rdf:RDF");
		Node cc_work = addNode(doc, rdf, "cc:Work");
		setAttrValue(cc_work, "rdf:about", "");
		Node dc_format = addNode(doc, cc_work, "dc:format");
		addText(dc_format, "image/svg+xml");
		Node dc_type = addNode(doc, cc_work, "dc:type");
		setAttrValue(dc_type, "rdf:resource", "http://purl.org/dc/dcmitype/StillImage");

		Node layer = addNode(doc, root, SVG_G);
		setAttrValue(layer, "id", LAYER);
		setAttrValue(layer, "inkscape:label", LAYER);
		setAttrValue(layer, "inkscape:groupmode", LAYER);
		return layer;
	}

	private Node rectangle(Rectangle rectangle) {
		Node rect = addNode(doc, layer, RECT);
		//setAttrValue(rect, "id", "1");
		setAttrValue(rect, "x", getX(rectangle.getX()));
		setAttrValue(rect, "y", getY(rectangle.getY()));
		setAttrValue(rect, "height", getX(rectangle.getHeigh()));
		setAttrValue(rect, "width", getY(rectangle.getWidth()));
		String style = "stroke:#"+getColor(rectangle.getBorder())+";stroke-width:"+rectangle.getThickness();
		if (FillInx.None.equals(rectangle.getFill().getFill())) {
			style += ";fill:none";
		} else {
			style += ";fill:#"+getColor(rectangle.getFill().getColor());
		}
		setAttrValue(rect, "style", style);
		return rect;
	}

	private Node text(Label label) {
		Node text = addNode(doc, layer, TEXT);
		//setAttrValue(text, "id", "1");
		setAttrValue(text, "x", getX(label.getX()));
		setAttrValue(text, "y", getY(label.getY()));
		Font font = label.getFont();
		String style="font-size:"+font.getSize()+"px"+
		";fill:#"+getColor(label.getColor())+
		";font-family:"+font.getFontName();
		//;font-weight:normal
		//;font-style:normal
		//;font-stretch:normal
		//;font-variant:normal
		//;writing-mode:lr
		//;line-height:125%
		switch (label.getDirection()) {
			case Up:
			case Left:
				style += ";text-align:start"+
				";text-anchor:start";
				break;
			case Down:
			case Right:
				style += ";text-anchor:end" +
				";text-align:end";
				break;
		}
		setAttrValue(text, "style", style);
		setAttrValue(text, "xml:space", "default");
		switch (label.getDirection()) {
			case Up:
			case Down:
				setAttrValue(text, "transform", "matrix(0,-1,1,0,0,0)");
				setAttrValue(text, "x", getX(-label.getY()));
				setAttrValue(text, "y", getY(label.getX()));
				//x="720.0" y="38.0">
				//x="-37.720947" y="721.8772"
		}
		Node tspan = addNode(doc, text, TSPAN);
		//setAttrValue(tspan, "id", "1");
		setAttrValue(tspan, "x", getX(label.getX()));
		setAttrValue(tspan, "y", getY(label.getY()));
		setAttrValue(tspan, "sodipodi:role", "line");
		tspan.setTextContent(label.getLabel());
		return text;
	}

	private Node path(Wire wire) {
		Node path = addNode(doc, layer, PATH);
		//setAttrValue(path, "id", "1");
		String d="M "+getX(wire.getX1())+","+getY(wire.getY1())+" L "+getX(wire.getX2())+","+getY(wire.getY2());
		setAttrValue(path, "d", d);
		String style="stroke:#"+getColor(wire.getColor())+";stroke-width:1";
		setAttrValue(path, "style", style);
		return path;
	}


	private Node junction(Junction junction) {
		Node path = addNode(doc, layer, PATH);
		//setAttrValue(path, "id", "1");
		setAttrValue(path, "sodipodi:type", "arc");
		String style="fill:#000000;fill-opacity:1;fill-rule:nonzero;stroke:none;stroke-width:2;stroke-linecap:square;stroke-linejoin:miter;stroke-miterlimit:4;stroke-dasharray:none;stroke-dashoffset:0;stroke-opacity:1";
		setAttrValue(path, "style", style);
		setAttrValue(path, "sodipodi:cx", getX(junction.getX()));
		setAttrValue(path, "sodipodi:cy", getY(junction.getY()));
		double rx = 2.5;
		double ry = 2.5;
		setAttrValue(path, "sodipodi:rx", "2.5");
		setAttrValue(path, "sodipodi:ry", "2.5");
		double cx = junction.getX();
		double cy = junction.getY();
		String d="M "+(cx+rx)+" "+cy+" A "+rx+" "+ry+" 0 1 1  "+(cx-rx)+","+cy+" A "+rx+" "+ry+" 0 1 1  "+(cx+rx)+" "+cy+" z";
		setAttrValue(path, "d", d);
		return path;
	}

	private void pin(Pin pin) {
		double x = 0;
		double y = 0;
		switch (pin.getDirection()) {
			case Up:
				y = -pin.getLength()/5;
				break;
			case Down:
				y = pin.getLength()/5;
				break;
			case Left:
				x = -pin.getLength()/5;
				break;
			case Right:
				x = pin.getLength()/5;
				break;
		}
		Wire wire = new Wire();
		wire.setX1(pin.getX());
		wire.setY1(pin.getY());
		wire.setX2(pin.getX()+x);
		wire.setY2(pin.getY()+y);
		wire.setColor(pin.getColor());
		path(wire);
		if ((pin.getNumber() != null) || (!pin.getNumber().isEmpty())) {
			Label label = new Label();
			label.setX(pin.getX());
			label.setY(pin.getY());
			// TODO label.setColor(Color.BLACK));
			label.setDirection(pin.getDirection());
			label.setFont(pin.getFont());
			label.setLabel(pin.getNumber());
			text(label);
		}
		if ((pin.getName() != null) || (!pin.getName().isEmpty())) {
			x = pin.getX();
			y = pin.getY();
			switch (pin.getDirection()) {
				case Up:
				case Down:
					x += pin.getFont().getSize();
					break;
				case Left:
				case Right:
					y += pin.getFont().getSize();
					break;
			}
			Label label = new Label();
			label.setX(x);
			label.setY(y);
			// TODO label.setColor(Color.BLACK));
			label.setDirection(pin.getDirection());
			label.setFont(pin.getFont());
			label.setLabel(pin.getName());
			text(label);
		}
	}

	private String getColor(Color color) {
		return Integer.toHexString(color.getRGB()).substring(2);
	}

	private String getY(double y) {
		return Double.toString(y);
	}

	private String getX(double x) {
		return Double.toString(x);
	}
}

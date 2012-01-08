package com.google.code.autowiring.svg;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring;
import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.beans.Path;
import com.google.code.autowiring.beans.Rect;
import com.google.code.autowiring.beans.Text;
import com.google.code.autowiring.config.CfgEng;
import com.google.code.autowiring.util.Xml;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Svg extends Xml implements Wiring {

	private static final String ROOT = "svg";
	private static final String DEFS = "defs";
	private static final String SVG_G = "g";
	private static final String LAYER = "layer";
	private static final String RECT = "rect";
	private static final String TEXT = "text";
	private static final String TSPAN = "tspan";
	private static final String PATH = "path";

	private SvgConfig engine;
	private Node layer;
	private String path;
	private String fileName;

	public Svg(CfgEng engine, String fileName) throws WiringException {
		this((SvgConfig)engine, fileName);
	}

	protected Svg(SvgConfig engine, String fileName) throws WiringException {
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

	private void addBean(Bean bean) throws WiringException {
		if (bean instanceof Rect) {
			rectangle((Rect)bean);
		} else	
		if (bean instanceof Text) {
			text((Text)bean);
		} else	
		if (bean instanceof Path) {
			path((Path)bean);
//		} else	
//		if (bean instanceof Pin) {
//			pin((Pin)bean);
		}
	}

	private Node rectangle(Rect rect) {
		Node node = addNode(doc, layer, RECT);
		//setAttrValue(node, "id", "1");
		setAttrValue(node, "x", getX(rect.getX()));
		setAttrValue(node, "y", getY(rect.getY()));
		setAttrValue(node, "height", getX(rect.getHeigh()));
		setAttrValue(node, "width", getY(rect.getWidth()));
		setAttrValue(node, "style", rect.getStyle());
		return node;
	}

	private Node text(Text text) {
		Node node = addNode(doc, layer, TEXT);
		//setAttrValue(text, "id", "1");
		setAttrValue(node, "x", getX(text.getX()));
		setAttrValue(node, "y", getY(text.getY()));
		setAttrValue(node, "style", text.getStyle());
		Map<String, String> details = text.getDetails();
		if (details != null) {
			for (Entry<String, String> detail: details.entrySet()) {
				String key = detail.getKey();
				String value = detail.getValue();
				setAttrValue(node, key, value);
			}
		}
		Node tspan = addNode(doc, node, TSPAN);
		//setAttrValue(tspan, "id", "1");
		setAttrValue(tspan, "x", getX(text.getX()));
		setAttrValue(tspan, "y", getY(text.getY()));
		setAttrValue(tspan, "sodipodi:role", "line");
		tspan.setTextContent(text.getText());
		return node;
	}

	private Node path(Path path) {
		Node node = addNode(doc, layer, PATH);
		//setAttrValue(path, "id", "1");
		setAttrValue(node, "d", path.getD());
		setAttrValue(node, "style", path.getStyle());
		Map<String, String> details = path.getDetails();
		if (details != null) {
			for (Entry<String, String> detail: details.entrySet()) {
				String key = detail.getKey();
				String value = detail.getValue();
				setAttrValue(node, key, value);
			}
		}
		return node;
	}

//	private void pin(Pin pin) {
//		double x = 0;
//		double y = 0;
//		switch (pin.getDirection()) {
//			case Up:
//				y = -pin.getLength()/5;
//				break;
//			case Down:
//				y = pin.getLength()/5;
//				break;
//			case Left:
//				x = -pin.getLength()/5;
//				break;
//			case Right:
//				x = pin.getLength()/5;
//				break;
//		}
//		Path wire = new Path();
//		wire.setX1(pin.getX());
//		wire.setY1(pin.getY());
//		wire.setX2(pin.getX()+x);
//		wire.setY2(pin.getY()+y);
//		wire.setColor(pin.getColor());
//		path(wire);
//		if ((pin.getNumber() != null) || (!pin.getNumber().isEmpty())) {
//			Text label = new Text();
//			label.setX(pin.getX());
//			label.setY(pin.getY());
//			// TODO label.setColor(Color.BLACK));
//			label.setDirection(pin.getDirection());
//			label.setFontName(pin.getFont());
//			label.setText(pin.getNumber());
//			text(label);
//		}
//		if ((pin.getName() != null) || (!pin.getName().isEmpty())) {
//			x = pin.getX();
//			y = pin.getY();
//			switch (pin.getDirection()) {
//				case Up:
//				case Down:
//					x += pin.getFont().getSize();
//					break;
//				case Left:
//				case Right:
//					y += pin.getFont().getSize();
//					break;
//			}
//			Text label = new Text();
//			label.setX(x);
//			label.setY(y);
//			// TODO label.setColor(Color.BLACK));
//			label.setDirection(pin.getDirection());
//			label.setFontName(pin.getFont());
//			label.setText(pin.getName());
//			text(label);
//		}
//	}

	public static String getY(double y) {
		return Double.toString(y);
	}

	public static String getX(double x) {
		return Double.toString(x);
	}
}

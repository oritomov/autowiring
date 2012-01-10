package com.google.code.autowiring.svg;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Conv;
import com.google.code.autowiring.Wiring;
import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.config.CfgEng;
import com.google.code.autowiring.tynicad.beans.Symbol;
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
			createNode(layer, bean, 0, 0);
		}
	}

	@Override
	public void save() throws WiringException {
		try {
			String result = toString(doc);
			Conv.log().debug(result);
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

	private Node createNode(Node parent, Bean bean, double x, double y) {
		Class<?> clazz = bean.getClass();
		Tag tag = engine.getTag(clazz);
		Node node = addNode(doc, parent, tag.getName());
		setTags(node, tag, bean, x, y);
		setAttrs(node, tag, bean, x, y);
		setDetails(node, tag, bean, x, y);
		setBeans(node, tag, bean, x, y);
		return node;
	}

	private void setTags(Node parentNode, Tag parentTag, Bean bean, double x, double y) {
		try {
			if (parentTag.getTags() != null) {
				for (Tag tag: parentTag.getTags()) {
					Node node = addNode(doc, parentNode, tag.getName());
					setAttrs(node, tag, bean, x, y);
					setText(node, tag, bean);
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setAttrs(Node node, Tag tag, Bean bean, double x, double y) {
		try {
			if (tag.getAttrsName() != null) {
				String[] attrs = tag.getAttrsName().split(",");
				for(String attr: attrs) {
					String getterName = getGetterName(attr);
					Object value = null;
					try{ 
						Method getter = Class.forName(tag.getClassName()).getDeclaredMethod(getterName, new Class[]{Double.class, Double.class});
						value = getter.invoke(bean, new Object[]{x, y}).toString();
					} catch (NoSuchMethodException e) {
						Method getter = Class.forName(tag.getClassName()).getDeclaredMethod(getterName, new Class[0]);
						value = getter.invoke(bean, new Object[0]);
						if (attr.equalsIgnoreCase("x") && (value instanceof Double)) {
							value = ((Double)value) + x; 
						}
						if (attr.equalsIgnoreCase("y") && (value instanceof Double)) {
							value = ((Double)value) + y; 
						}
					}
					setAttrValue(node, attr, value.toString());
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setDetails(Node node, Tag tag, Bean bean, double x, double y) {
		try {
			Method getter = null;
			try {
				getter = Class.forName(tag.getClassName()).getDeclaredMethod("getDetails", new Class[]{Double.class, Double.class});
			} catch (NoSuchMethodException e) {
				try {
					getter = Class.forName(tag.getClassName()).getDeclaredMethod("getDetails", new Class[0]);
				} catch (NoSuchMethodException e2) {
					// it's ok. do nothing.
				}
			}
			if (getter != null) {
				Map<String, String> details = null;
				try {
					details = (Map<String, String>) getter.invoke(bean, new Object[]{x, y});
				} catch (IllegalArgumentException e) {
					details = (Map<String, String>) getter.invoke(bean, new Object[0]);
				}
				if (details != null) {
					for (Entry<String, String> detail: details.entrySet()) {
						String key = detail.getKey();
						String value = detail.getValue();
						setAttrValue(node, key, value);
					}
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeans(Node node, Tag tag, Bean parentBean, double x, double y) {
		try {
			Method getter = null;
			try {
				getter = Class.forName(tag.getClassName()).getDeclaredMethod("getBeans", new Class[0]);
			} catch (NoSuchMethodException e) {
				// it's ok. do nothing.
			}
			if (getter != null) {
				@SuppressWarnings("unchecked")
				List<Bean> beans = (List<Bean>) getter.invoke(parentBean, new Object[0]);
				if (parentBean instanceof Symbol) {
					x += ((Symbol)parentBean).getOffsX();
					y += ((Symbol)parentBean).getOffsY();
				}
				if (beans != null) {
					for (Bean bean: beans) {
						createNode(node, bean, x, y);
					}
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setText(Node node, Tag tag, Bean bean) {
		try {
			if (tag.getTextName() != null) {
				String getterName = getGetterName(tag.getTextName());
				Method getter = Class.forName(tag.getClassName()).getDeclaredMethod(getterName, new Class[0]);
				String value = getter.invoke(bean, new Object[0]).toString();
				node.setTextContent(value);
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private static String getGetterName(String name) {
		String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
		getterName = getterName.replace(":", "_");
		return getterName;
	}

	public static String getY(double y) {
		return Double.toString(y);
	}

	public static String getX(double x) {
		return Double.toString(x);
	}
}

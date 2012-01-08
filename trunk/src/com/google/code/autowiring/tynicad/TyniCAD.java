package com.google.code.autowiring.tynicad;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Wiring;
import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.config.CfgEng;
import com.google.code.autowiring.tynicad.beans.Options;
import com.google.code.autowiring.tynicad.beans.RefBean;
import com.google.code.autowiring.tynicad.config.Prop;
import com.google.code.autowiring.tynicad.config.Ref;
import com.google.code.autowiring.tynicad.config.Tag;
import com.google.code.autowiring.tynicad.config.TiniCadConfig;
import com.google.code.autowiring.util.Xml;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class TyniCAD extends Xml implements Wiring {

	private static final String ROOT = "TinyCADSheets";
	private static final String TINY_CAD = "TinyCAD";
	private static final String OPTIONS = "OPTIONS";

	private TiniCadConfig engine;
	private Node dsnRoot;
//	private Color wireColor = new Color(0);
//	private Font pinFont;
//	private Color pinColor = new Color(0);
//	private Map<Node, Position> stored = new LinkedHashMap<Node, Position>();
//	private Position origin = new Position(0, 0);
	private List<Bean> refs;
	private List<Bean> beans;

	public TyniCAD(CfgEng engine, String fileName) throws WiringException {
		this((TiniCadConfig)engine, fileName);
	}

	protected TyniCAD(TiniCadConfig engine, String fileName) throws WiringException {
		super(fileName);
		this.engine = engine;
		dsnRoot = getNode(doc, ROOT);
		Node tiny = getNode(dsnRoot, TINY_CAD);
		refs = new ArrayList<Bean>();
		/** first extract options. */
		Bean options = createBean(getNode(tiny, OPTIONS));
		refs.add(options);
		/** extract all other */
		beans = ceateBeans(tiny.getFirstChild());
//		wireColor = getColor(getNode(options, COLOR_WIRE).getTextContent());
//		pinFont = getFont(find(dsnRoot, FONT, "1"));
//		pinFont = pinFont.deriveFont(3.0f);
//		pinColor = getColor(getNode(options, COLOR_PIN).getTextContent());
	}

	@Override
	public List<Bean> getBeans() throws WiringException {
		return beans;
	}

	@Override
	public void setBeans(List<Bean> beans) throws WiringException {
		throw new WiringException("Not implemted!");
	}

	@Override
	public void save() throws WiringException {
		throw new WiringException("Not implemted!");
	}

	private List<Bean> ceateBeans(Node node) {
		List<Bean> beans = new ArrayList<Bean>();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) { 
				if (engine.getTag(node.getNodeName()) != null) {
					Bean bean = createBean(node);
					if (bean instanceof RefBean) {
						refs.add(bean);
					} else {
						beans.add(bean);
					}
				}
			}
			node = node.getNextSibling();
		}
		return beans;
	}

	private Bean createBean(Node node) throws WiringException {
		try {
			String name = node.getNodeName();
			Tag tag = engine.getTag(name);
			Bean bean = instanceBean(tag.getClassName());
			setBeanProps(bean, tag.getProps(), node);
			setBeanProp(bean, tag.getPropName(), node);
			setBeanRefs(bean, tag.getRefs(), node);
			setBeanTags(bean, tag.getTags(), node);
			return bean;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	/**
	 * @return the instance of the bean.
	 */
	private Bean instanceBean(String className) {
		try {
			@SuppressWarnings("unchecked")
			Class<Bean> beanClass = (Class<Bean>) Class.forName(className);
			Bean bean;
			try {
				Constructor<Bean> beanConstructor = beanClass.getDeclaredConstructor(new Class[]{List.class});
				bean = beanConstructor.newInstance(new Object[]{refs});
			} catch (NoSuchMethodException e) {
				Constructor<Bean> beanConstructor = beanClass.getDeclaredConstructor(new Class[0]);
				bean = beanConstructor.newInstance(new Object[0]);
			}
			return bean;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanProp(Bean bean, String propName, Node node) {
		if (propName == null) {
			return;
		}
		try {
			String value = node.getTextContent();
			String setterName = getSetterName(propName);
			Method setter = bean.getClass().getDeclaredMethod(setterName, String.class);
			setter.invoke(bean, value);
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanProps(Bean bean, List<Prop> props, Node node) {
		try {
			for(Prop prop: props) {
				String value = getAttrValue(node, prop.getName());
				String setterName = getSetterName(prop.getName());
				if (prop.getClassName() == null) {
					Method setter = bean.getClass().getDeclaredMethod(setterName, String.class);
					setter.invoke(bean, value);
				} else {
					@SuppressWarnings("unchecked")
					Class<Bean> paramClass = (Class<Bean>) Class.forName(prop.getClassName());
					Constructor<Bean> paramConstructor = paramClass.getDeclaredConstructor(String.class);
					Bean param = paramConstructor.newInstance(value);
					Method setter = bean.getClass().getDeclaredMethod(setterName, paramClass);
					setter.invoke(bean, param);
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanRefs(Bean bean, List<Ref> refs, Node node) {
		try {
			for(Ref ref: refs) {
				String refId = getAttrValue(node, ref.getName());
				String setterName = getSetterName(ref.getName());
				@SuppressWarnings("unchecked")
				Class<Bean> paramClass = (Class<Bean>) Class.forName(ref.getClassName());
				Bean param = getRef(this.refs, paramClass, refId);
				Method setter = bean.getClass().getDeclaredMethod(setterName, paramClass);
				setter.invoke(bean, param);
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanTags(Bean bean, List<Tag> tags, Node node) {
		try {
			for(Tag tag: tags) {
				Node child = getNode(node, tag.getName());
				if (tag.getPropName() != null) {
					setBeanProps(bean, tag.getProps(), child);
					setBeanProp(bean, tag.getPropName(), child);
				} else 
				if (tag.getArrayName() != null) {
					List<Bean> values = ceateBeans(child.getFirstChild());
					@SuppressWarnings("unchecked")
					Class<Bean> paramClass = (Class<Bean>) Class.forName(tag.getClassName());
					String adderName = getAdderName(tag.getArrayName());
					Method adder = bean.getClass().getDeclaredMethod(adderName, paramClass);
					for (Bean value: values) {
						adder.invoke(bean, value);
					}
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private static String getGetterName(String name) {
		String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
		return getterName;
	}

	private String getSetterName(String name) {
		String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
		return setterName;
	}

	private String getAdderName(String name) {
		String setterName = "add" + name.substring(0, 1).toUpperCase() + name.substring(1);
		return setterName;
	}

//	private void store(Node node, Position displace) {
//		stored.put(child, origin);
//		origin = displace.move(origin);
//		child = node;
//	}

//	private void restore() {
//		Iterator<Entry<Node, Position>> i = stored.entrySet().iterator();
//		Entry<Node, Position> entry = null;
//		while (i.hasNext()) entry  = i.next();
//		child = entry.getKey();
//		origin = entry.getValue();
//		stored.remove(child);
//	}

//	private Bean symbol(Node node) {
//		String pos = getAttrValue(node, "pos");
//		double x = Double.parseDouble(pos.split(",")[0]);
//		double y = Double.parseDouble(pos.split(",")[1]);
//		String id = getAttrValue(node, ID);
//		Node defRoot = find(dsnRoot, SYMBOLDEF, id);
//		Node refPoint = getNode(defRoot, REF_POINT);
//		pos = getAttrValue(refPoint, "pos");
//		x -= Double.parseDouble(pos.split(",")[0]);
//		y -= Double.parseDouble(pos.split(",")[1]);
//		Position displace = new Position(x, y);
//		Node def = getNode(defRoot, TINY_CAD);
//		Node defChild = def.getFirstChild();
//		store(defChild, displace);
//		Bean bean = nextBean();
//		return bean;
//	}

//	private Node find(Node dsnRoot, String name, String id) {
//		Node tiny = getNode(dsnRoot, TINY_CAD);
//		Node node = getNode(tiny, name);
//		String nodeId = getAttrValue(node, ID);
//		while (!id.equalsIgnoreCase(nodeId)) {
//			node = nextNode(node, name);
//			nodeId = getAttrValue(node, ID);
//		}
//		return node;
//	}

//	private Fill fill(Node node) {
//		int fillCode = Integer.parseInt(getNode(node, "INDEX").getTextContent());
//		FillInx fillInx = FillInx.get(fillCode);
//		Color color = getColor(getNode(node, COLOR).getTextContent());
//		Fill fill = new Fill(fillInx, color);
//		return fill;
//	}

//	private Rectangle rectangle(Node root, Node node) {
//		String a = getAttrValue(node, "a");
//		double x = Double.valueOf(a.split(",")[0]);
//		double y = Double.valueOf(a.split(",")[1]);
//		String b = getAttrValue(node, "b");
//		double x2 = Double.valueOf(b.split(",")[0]);
//		double y2 = Double.valueOf(b.split(",")[1]);
//		String styleId = getAttrValue(node, STYLE);
//		Node style = find(root, STYLE, styleId);
//		Color color = getColor(getNode(style, COLOR).getTextContent());
//		int thickness = Integer.parseInt(getNode(style, THICKNESS).getTextContent());
//		String fillId = getAttrValue(node, FILL);
//		Fill fill = fill(find(root, FILL, fillId));
//		//Rectangle rectangle = new Rectangle(Math.min(x,x2)+origin.getX(), Math.min(y,y2)+origin.getY(), Math.max(x,x2)+origin.getX(), Math.max(y,y2)+origin.getY(), fill, color, thickness);
//		Rectangle rectangle = new Rectangle(x+origin.getX(), y+origin.getY(), x2+origin.getX(), y2+origin.getY(), fill, color, thickness);
//		return rectangle;
//	}

//	private Bean polygon(Node node) {
//		String pos = getAttrValue(node, "pos");
//		double x = Double.parseDouble(pos.split(",")[0]);
//		double y = Double.parseDouble(pos.split(",")[1]);
//		// style
//		// fill
//		Polygon polygon = new Polygon(x, y);
//		Node p = getNode(node, POINT);
//		while (p != null) {
//			pos = getAttrValue(p, "pos");
//			x = Double.parseDouble(pos.split(",")[0]);
//			y = Double.parseDouble(pos.split(",")[1]);
//			int arcCode = Integer.parseInt(getAttrValue(p, "arc"));
//			Arc arc = Arc.get(arcCode);
//			Point point = new Point(x, y, arc);
//			polygon.add(point);
//			p = nextNode(p, POINT);
//		}
//		return polygon;
//	}

//	private Label label(Node root, Node node) {
//		String pos = getAttrValue(node, "pos");
//		double x = Double.parseDouble(pos.split(",")[0]);
//		double y = Double.parseDouble(pos.split(",")[1]);
//		int directionCode = Integer.parseInt(getAttrValue(node, "direction"));
//		Direction direction = Direction.get(directionCode);
//		Color color = getColor(getAttrValue(node, COLOR));
//		String fontId = getAttrValue(node, "font");
//		Font font = getFont(find(root, FONT, fontId));
//		//String style = Xml.getAttrValue(child, "style");
//		String text = node.getTextContent();
//		Label label = new Label(x+origin.getX(), y+origin.getY(), color, direction, font, text);
//		return label;
//	}

//	private Wire wire(Node node) {
//		String a = getAttrValue(node, "a");
//		double x1 = Double.parseDouble(a.split(",")[0]);
//		double y1 = Double.parseDouble(a.split(",")[1]);
//		String b = getAttrValue(node, "b");
//		double x2 = Double.parseDouble(b.split(",")[0]);
//		double y2 = Double.parseDouble(b.split(",")[1]);
//		Wire wire = new Wire(x1+origin.getX(), y1+origin.getY(), x2+origin.getX(), y2+origin.getY(), wireColor);
//		return wire;
//	}

//	private Junction junction(Node node) {
//		String pos = getAttrValue(node, "pos");
//		double x = Double.parseDouble(pos.split(",")[0]);
//		double y = Double.parseDouble(pos.split(",")[1]);
//		Junction junction = new Junction(x+origin.getX(), y+origin.getY());
//		return junction;
//	}

//	private Pin pin(Node root, Node node) {
//		String pos = getAttrValue(node, "pos");
//		double x = Double.parseDouble(pos.split(",")[0]);
//		double y = Double.parseDouble(pos.split(",")[1]);
//		int directionCode = Integer.parseInt(getAttrValue(node, "direction"));
//		Direction direction = Direction.get(directionCode);
//		double length = Double.parseDouble(getAttrValue(node, "length"));
//		String number = getAttrValue(node, "number");
//		String name = node.getTextContent();
//		//which='0' elec='0' part='0' show='3' number_pos='0' centre_name='0'
//		Pin pin = new Pin(x+origin.getX(), y+origin.getY(), direction, length, number, name, pinColor, pinFont);
//		return pin;
//	}

//	private Font getFont(Node node) {
//		String name = getNode(node, "FACENAME").getTextContent();
//		int style = Font.PLAIN;
//		int size = Integer.parseInt(getNode(node, "WIDTH").getTextContent());
//		Font font = new Font(name, style, size);
//		return font;
//	}

	public static Bean getRef(List<Bean> refs, Class<? extends Bean> paramClass, String refId) {
		for (Bean ref: refs) {
			if (ref.getClass().equals(paramClass)) {
				if (((RefBean) ref).getId().equalsIgnoreCase(refId)) {
					return ref;
				}
			}
		}
		return null;
	}

	public static String getOption(List<Bean> refs, String optionName) throws WiringException {
		try {
			Options options = (Options)getRef(refs, Options.class, Options.OPTIONS);
			Method getter = options.getClass().getDeclaredMethod(getGetterName(optionName), new Class[0]);
			String value = (String) getter.invoke(options, new Object[0]);
			return value;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	public static String getColor(String bgr) {
		int r = Integer.parseInt(bgr.substring(4), 16);
		int g = Integer.parseInt(bgr.substring(2, 4), 16);
		int b = Integer.parseInt(bgr.substring(0, 2), 16);
		Color color = new Color(r, g, b);
		return Integer.toHexString(color.getRGB()).substring(2);
	}
}

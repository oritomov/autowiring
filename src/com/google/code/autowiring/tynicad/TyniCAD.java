package com.google.code.autowiring.tynicad;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.google.code.autowiring.Bean;
import com.google.code.autowiring.Conv;
import com.google.code.autowiring.Wiring;
import com.google.code.autowiring.WiringException;
import com.google.code.autowiring.config.CfgEng;
import com.google.code.autowiring.tynicad.beans.Options;
import com.google.code.autowiring.tynicad.beans.RefBean;
import com.google.code.autowiring.tynicad.config.Attr;
import com.google.code.autowiring.tynicad.config.Tag;
import com.google.code.autowiring.tynicad.config.TiniCadConfig;
import com.google.code.autowiring.util.WireTool;
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
//	private Font pinFont;
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
//		pinFont = getFont(find(dsnRoot, FONT, "1"));
//		pinFont = pinFont.deriveFont(3.0f);
	}

	@Override
	public List<Bean> getBeans() throws WiringException {
		return beans;
	}

	@Override
	public void setBeans(List<Bean> beans1, List<Bean> beans2) throws WiringException {
		throw new WiringException("Not implemted!");
	}

	@Override
	public void save() throws WiringException {
		throw new WiringException("Not implemted!");
	}

	@SuppressWarnings("unchecked")
	public static Bean clone(Bean bean) throws WiringException {
		try {
			Bean clone;
			Class<Bean> beanClass = (Class<Bean>) Class.forName(bean.getClass().getName());
			Constructor<Bean> beanConstructor = beanClass.getConstructor(new Class[0]);
			clone = beanConstructor.newInstance(new Object[0]);
			for(Method setter: beanClass.getMethods()) {
				if (setter.getName().startsWith("set")) {
					String getterName = "get" + setter.getName().substring(3);
					try {
						Method getter = beanClass.getMethod(getterName, new Class[0]);
						if ((setter.getParameterTypes().length == 1) && 
							(getter.getParameterTypes().length == 0) && 
							(setter.getParameterTypes()[0].equals(getter.getReturnType()))) {
							setter.invoke(clone, getter.invoke(bean, new Object[0]));
						}
					} catch (NoSuchMethodException e) {
						/** it's ok. do nothing */
					}
				}
				if (setter.getName().startsWith("add")) {
					String getterName = "get" + setter.getName().substring(3);
					try {
						Method getter = beanClass.getMethod(getterName, new Class[0]);
						if ((setter.getParameterTypes().length == 1) && 
							(getter.getParameterTypes().length == 0) && 
							(getter.getReturnType().equals(List.class))) {
							List<Bean> beans = (List<Bean>) getter.invoke(bean, new Object[0]);
							for (Bean item: beans) {
								setter.invoke(clone, clone(item));
							}
						}
					} catch (NoSuchMethodException e) {
						/** it's ok. do nothing */
					}
				}
			}
			return clone;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
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
						if (!WireTool.joinWire(beans, bean)) {
							beans.add(bean);
						}
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
			setBeanText(bean, tag.getText(), node);
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
				Constructor<Bean> beanConstructor = beanClass.getConstructor(new Class[]{List.class});
				bean = beanConstructor.newInstance(new Object[]{refs});
			} catch (NoSuchMethodException e) {
				Constructor<Bean> beanConstructor = beanClass.getConstructor(new Class[0]);
				bean = beanConstructor.newInstance(new Object[0]);
			}
			return bean;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanText(Bean bean, Attr text, Node node) {
		String value = node.getTextContent();
		setBeanProp(bean, text, node, value);
	}

	private void setBeanProps(Bean bean, List<Attr> props, Node node) {
		try {
			for(Attr prop: props) {
				String value = getAttrValue(node, prop.getName());
				setBeanProp(bean, prop, node, value);
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanProp(Bean bean, Attr prop, Node node, String value) {
		try {
			if (prop != null) {
				String name = prop.getName();
				String setterName = getSetterName(name);
				String className = prop.getClassName();
				if (className == null) {
					Method setter = bean.getClass().getMethod(setterName, String.class);
					setter.invoke(bean, value);
				} else {
					@SuppressWarnings("unchecked")
					Class<Bean> paramClass = (Class<Bean>) Class.forName(className);
					Constructor<Bean> paramConstructor = paramClass.getConstructor(String.class);
					Bean param = paramConstructor.newInstance(value);
					Method setter = bean.getClass().getMethod(setterName, paramClass);
					setter.invoke(bean, param);
				}
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	private void setBeanRefs(Bean bean, List<Attr> refs, Node node) {
		try {
			for(Attr ref: refs) {
				String refId = getAttrValue(node, ref.getName());
				String setterName = getSetterName(ref.getName());
				@SuppressWarnings("unchecked")
				Class<Bean> paramClass = (Class<Bean>) Class.forName(ref.getClassName());
				Bean param = getRef(this.refs, paramClass, refId);
				if (param == null) {
					Conv.log().error("couldn't set "+ref.getName()+" of "+bean.getClass().getName());
					continue;
				}
				Method setter = bean.getClass().getMethod(setterName, paramClass);
				setter.invoke(bean, param);
			}
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setBeanTags(Bean bean, List<Tag> tags, Node parent) {
		try {
			for(Tag tag: tags) {
				if (tag.getArrayName() != null) {
					List<Bean> values;
					if (tag.getRoot() == null) {
						Node child = getNode(parent, tag.getName());
						values = ceateBeans(child);
					} else {
						Node root = getNode(parent, tag.getRoot());
						values = ceateBeans(root.getFirstChild());
					}
					Class<Bean> paramClass = (Class<Bean>) Class.forName(tag.getClassName());
					String adderName = getAdderName(tag.getArrayName());
					Method adder = null;
					while (adder==null) {
						try {
							adder = bean.getClass().getMethod(adderName, paramClass);
						} catch (NoSuchMethodException e) {
							paramClass = (Class<Bean>) paramClass.getSuperclass();
						}
					}
					for (Bean value: values) {
						adder.invoke(bean, value);
					}
				} else {
					Node child = getNode(parent, tag.getName());
					setBeanProps(bean, tag.getProps(), child);
					if (tag.getText() != null) {
						setBeanText(bean, tag.getText(), child);
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

	public static Bean getRef(List<Bean> refs, Class<? extends Bean> paramClass, String refId) {
		for (Bean ref: refs) {
			if (ref.getClass().equals(paramClass)) {
				if (((RefBean) ref).getId().equalsIgnoreCase(refId)) {
					return ref;
				}
			}
		}
		Conv.log().error("couldn't find a ref id:"+refId+" of "+paramClass);
		return null;
	}

	public static String getOption(List<Bean> refs, String optionName) throws WiringException {
		try {
			Options options = (Options)getRef(refs, Options.class, Options.OPTIONS);
			Method getter = options.getClass().getMethod(getGetterName(optionName), new Class[0]);
			String value = (String) getter.invoke(options, new Object[0]);
			return value;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}
}

package com.google.code.autowiring.util;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.code.autowiring.WiringException;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public abstract class Xml {

	private static final boolean validation = false;
	private static final boolean ignoreWhitespace = true;
	private static final boolean ignoreComments = true;
	private static final boolean putCDATAIntoText = true;
	private static final boolean createEntityRefs = true;

	protected Document doc;

	protected Xml() throws WiringException {
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	protected Xml(String fileName) throws WiringException {
		try {
			doc = parse(fileName);
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	protected Document parse(String fileName) throws WiringException {
		// Step 1: create a DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Optional: set various configuration options
		factory.setValidating(validation);
		factory.setIgnoringComments(ignoreComments);
		factory.setIgnoringElementContentWhitespace(ignoreWhitespace);
		factory.setCoalescing(putCDATAIntoText);
		// The opposite of creating entity ref nodes is expanding them inline
		factory.setExpandEntityReferences(createEntityRefs);

		// Step 2: create a DocumentBuilder
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}

		// Step 3: parse the input file to get a Document object
		try {
			return builder.parse(fileName);
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	protected Node nextNode(Node node, String name) {
		if (node != null) {
			node = node.getNextSibling();
			node = findNode(node, name);
		}
		return node;
	}

	protected Node findNode(Node node, String name) {
		while (node != null && (node.getNodeType() != Node.ELEMENT_NODE || !node.getNodeName().equalsIgnoreCase(name))) {
			node = node.getNextSibling();
			if (node == null) {
				return null;
			}
		}
		return node;
	}

	protected Node getNode(Node parent, String name) {
		if (parent == null) {
			return null;
		} else {
			Node node = parent.getFirstChild();
			return findNode(node, name);
		}
	}

	protected Node addNode(Document dom, Node parent, String name) {
		if (parent == null) {
			return null;
		} else {
			Node child = dom.createElement(name);
			parent.appendChild(child);
			return child;
		}
	}

	protected Node addText(Node parent, String name) {
		if (parent == null) {
			return null;
		} else {
			Document dom = parent.getOwnerDocument(); 
			Node text = dom.createTextNode(name);
			parent.appendChild(text);
			return text;
		}
	}

	protected String nodeValue(Node node) {
		if (node == null || node.getFirstChild() == null) {
			return null;
		} else {
			String value = node.getFirstChild().getNodeValue();
			return value.trim();
		}
	}

	protected String getAttrValue(Node node, String name) {
		for (int i = 0; i < node.getAttributes().getLength(); i++) {
			Node nodeAttr = node.getAttributes().item(i);
			if (nodeAttr.getNodeName().equalsIgnoreCase(name)) {
				return nodeAttr.getNodeValue().trim();
			}
		}
		return null;
	}

	protected void setAttrValue(Node node, String name, String value) {
		((Element)node).setAttribute(name, value);
	}

	protected String toString(Document document) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			//initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);

			return result.getWriter().toString();
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}
}

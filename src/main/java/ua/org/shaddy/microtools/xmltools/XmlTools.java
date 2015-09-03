package ua.org.shaddy.microtools.xmltools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlTools {
	/**
	 * parses xml from specified stream, and returns the Document
	 * @param xml
	 * @return
	 */
	public static Document parseXML(InputStream xml) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true); // never forget this!
		builderFactory.setValidating(false);
		builderFactory.setIgnoringComments(true);
		builderFactory.setIgnoringElementContentWhitespace(false);
		builderFactory.setExpandEntityReferences(false);
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
			return builder.parse(xml);
		} catch (Exception e) {
			throw new ParseException("Can not parse xml", e);
		}
	}
	/**
	 * parses xml file, and returns the Document
	 * @param xml
	 * @return
	 */
	public static Document parseXML(File xml) {
		InputStream is = null;
		try {
			is = new FileInputStream(xml);
			return parseXML(is);
		} catch (FileNotFoundException e) {
			throw new ParseException("Can not parse xml, file not found", e);
		} finally {
			try {
				if (is!=null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * parses xml string, and returns the Document
	 * @param xml
	 * @return
	 */
	public static Document parseXML(String xml, String encoding) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
			return builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes(encoding))));
		} catch (Exception e) {
			throw new ParseException("Can not parse xml", e);
		}
	}
	/**
	 * searchs the xPath in the node (from)
	 * @param from
	 * @param xPath
	 * @return
	 */
	public static NodeList findByXpath(Node from, String xPath) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile(xPath);
			return (NodeList) expr.evaluate(from, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new ParseException("Can not execute xPath", e);
		}
	}
}

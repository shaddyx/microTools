package ua.org.shaddy.microtools.xmltools;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlParser {
	private Document doc;
	
	public void parse(String xml) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
			 doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		} catch (Exception e) {
			throw new ParseException("Can not parse xml", e);
		} 
	}
	/**
	 * returns node list by Xpath
	 * @param xPath
	 * @return
	 */
	public NodeList getByXpath(String xPath){
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr;
		NodeList result;
		try {
			expr = xpath.compile(xPath);
			result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new ParseException("Can not parse xml", e);
		}
		return result;
	}
}

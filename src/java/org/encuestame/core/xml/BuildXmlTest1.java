package org.encuestame.core.xml;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class BuildXmlTest1 {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static void main(String[] args) throws JDOMException, IOException {
		SAXBuilder sb = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
		sb.setValidation(true);
		sb.setFeature("http://apache.org/xml/features/validation/schema", true);
		sb.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", "gofer.xsd");

		System.out.print(sb.build(new StringReader("test"))); 

	}

}

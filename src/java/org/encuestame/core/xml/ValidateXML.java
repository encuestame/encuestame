package org.encuestame.core.xml;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ValidateXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// true activates validation
		   SAXBuilder saxBuilder = new SAXBuilder(true);
		// this line activates schema validation
		   saxBuilder.setFeature("http://apache.org/xml/features/validation/schema", true);
		 		   
		   try {
			Document document = saxBuilder.build(new File("order-xsd.xml"));
			XMLOutputter outputter = new XMLOutputter(
		  	Format.getPrettyFormat());
			outputter.output(document, System.out);
		   } catch (JDOMException e) {
		     e.printStackTrace();
		   } catch (IOException e) {
		     e.printStackTrace();
		   }

	}

}

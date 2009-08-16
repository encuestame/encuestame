
package org.encuestame.core.xml;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.IOException;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2008-2009 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: JDOMChecker.java 1822 25/06/2009 18:32:16
 * @author juanpicado
 * @version 1.0
 * package org.encuestame.core.xml
 */
public class JDOMChecker {
	public static void main(String[] args) {
		  
	    if (args.length == 0) {
	      System.out.println("Usage: java JDOMChecker URL"); 
	      return;
	    } 
	      
	    SAXBuilder builder = new SAXBuilder();
	     
	    // command line should offer URIs or file names
	    try {
	      builder.build(args[0]);
	      // If there are no well-formedness errors, 
	      // then no exception is thrown
	      System.out.println(args[0] + " is well-formed.");
	    }
	    // indicates a well-formedness error
	    catch (JDOMException e) { 
	      System.out.println(args[0] + " is not well-formed.");
	      System.out.println(e.getMessage());
	    }  
	    catch (IOException e) { 
	      System.out.println("Could not check " + args[0]);
	      System.out.println(" because " + e.getMessage());
	    }  
	  
	  }
}

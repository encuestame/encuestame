package org.pao.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 * Id: PageTwoBean.java 1822 26/04/2009 18:30:59
 * @author juanpicado
 * @version 1.0
 * package org.pao.beans
 */
public class PageTwoBean {
	
	private String texto3="Diana";
	private String texto4="Urbina";
	private List listaNombre;
	
	public PageTwoBean() {
		listaNombre=new ArrayList();
		//Iterator  e = listaNombre.iterator();
		listaNombre.add("Juan");
		listaNombre.add("Diego");
		listaNombre.add("Ines");
		listaNombre.add("Carolina");
		listaNombre.add("Jose");
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	

	public List getListaNombre() {
		return listaNombre;
	}



	public void setListaNombre(List listaNombre) {
		this.listaNombre = listaNombre;
	}



	public String getTexto3() {
		return texto3;
	}

	public void setTexto3(String texto3) {
		this.texto3 = texto3;
	}

	public String getTexto4() {
		return texto4;
	}

	public void setTexto4(String texto4) {
		this.texto4 = texto4;
	}
		
	

}

package org.pao.beans;

import org.jp.web.beans.MasterBean;

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
 * Id: PageOneBean.java 1822 26/04/2009 18:30:44
 * @author juanpicado
 * @version 1.0
 * package org.pao.beans
 */
public class PageOneBean{


	private String texto1="Paola";
	private String texto2="Morales";
	
	
	public PageOneBean() {
		// TODO Auto-generated constructor stub
	}


	public String getTexto1() {
		return texto1;
	}
	
	public void cambiar(){
		System.out.println("-------->"+getServicelocatebean().getStateDao());
		setTexto1(getTexto1());		
	}


	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}


	public String getTexto2() {
		return texto2;
	}


	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}
	
	
}

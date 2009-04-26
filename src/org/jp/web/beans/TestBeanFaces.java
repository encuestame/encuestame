package org.jp.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jp.core.persistence.dao.CatLocationDaoImp;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * encuestame: system online surveys Copyright (C) 2008-2009 encuestame
 * Development Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: TestBeanFaces.java 1822 23/04/2009 13:11:15
 * 
 * @author juanpicado
 * @version 1.0 package org.jp.web.beans
 */
public class TestBeanFaces {

	private String condega = "hola como esta amigo";
	private List listaLocacizaciones;
	private static Logger log = Logger.getLogger(TestBeanFaces.class);

	public TestBeanFaces() {
		// TODO Auto-generated constructor stub
	}

	public TestBeanFaces(String condega) {
		this.condega = condega;
	}

	/**
	 * @return the condega
	 */
	public String getCondega() {
		return condega;
	}

	/**
	 * @param condega
	 *            the condega to set
	 */
	public void setCondega(String condega) {
		this.condega = condega;
	}

	/**
	 * @return the listaLocacizaciones
	 */
	public List getListaLocacizaciones() {
		
		ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
		log.info("context->"+context);
		ApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		log.info("ApplicationContext->"+appContext);		
		log.info("ApplicationContext getBeanDefinitionCount->"+appContext.getBeanDefinitionCount());
		log.info("**>"+appContext.getBean("sessionFactory"));
		CatLocationDaoImp od = (CatLocationDaoImp) appContext.getBean("catLocationDaoImp");
		log.info("CatLocationDaoImp->"+od);	
		//CatLocationDaoImp od = new CatLocationDaoImp();
		listaLocacizaciones = (List) od.getAccountsumById();
		log.info("listaLocacizaciones->"+listaLocacizaciones);
		if (listaLocacizaciones != null) {
			log.info("listaLocacizaciones lista->"+listaLocacizaciones);
			return listaLocacizaciones;
		} else {
			log.error("listaLocacizaciones vacio");
			listaLocacizaciones = new ArrayList();
			listaLocacizaciones.add("Error en la consulta");
			return listaLocacizaciones;
		}
	}

	/**
	 * @param listaLocacizaciones
	 *            the listaLocacizaciones to set
	 */
	public void setListaLocacizaciones(List listaLocacizaciones) {
		this.listaLocacizaciones = listaLocacizaciones;
	}

}

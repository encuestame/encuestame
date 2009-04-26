package org.jp.core.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
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
 * Id: BaseBeanManager.java 1822 08/02/2009 
 * @author juanpicado
 * @version 1.0
 * package org.jp.developer.web.beans
 */

public class StartupSystem implements ServletContextListener {

	private static final Log elog = LogFactory.getLog(StartupSystem.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		elog.info("encuesta.me - closed session hibernate");
		//HibernateFactory.getSessionFactory().close();
		elog.info("encuesta.me -- shutdown complete");

	}

	public void contextInitialized(ServletContextEvent arg0) {
		elog.info("encuesta.me -- started system");
		synchronized (this) {
			try {
				org.jp.core.persistence.util.HibernateFactory.buildSessionFactory();
			} catch (HibernateException ex) {
				elog.info("encuesta.me -- error loading hibernate " + ex);
			}

		}

	}

}

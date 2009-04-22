package org.jp.core.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;


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

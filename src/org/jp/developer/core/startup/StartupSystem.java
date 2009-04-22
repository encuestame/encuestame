package org.jp.developer.core.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jp.developer.persistence.util.HibernateFactory;

public class StartupSystem implements ServletContextListener {

	private static final Log elog = LogFactory.getLog(StartupSystem.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		elog.info("encuesta.me - closed session hibernate");
		//HibernateFactory.getSessionFactory().close();
		elog.info("encuesta.me -- shutdown complete");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		elog.info("encuesta.me -- started system");
		synchronized (this) {
			try {
				HibernateFactory.buildSessionFactory();
			} catch (HibernateException ex) {
				elog.info("encuesta.me -- error loading hibernate " + ex);
			}

		}

	}

}

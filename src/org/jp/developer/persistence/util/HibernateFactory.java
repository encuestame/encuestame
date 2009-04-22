package org.jp.developer.persistence.util;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.*;

public class HibernateFactory {
	private static SessionFactory sessionFactory;
	private static Log log = LogFactory.getLog(HibernateFactory.class);

	/**
	 * Constructs a new Singleton SessionFactory
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public static SessionFactory buildSessionFactory()
			throws HibernateException {
		if (sessionFactory != null) {
			closeFactory();

		}
		return configureSessionFactory();
	}

	/**
	 * Builds a SessionFactory, if it hasn't been already.
	 */
	public static SessionFactory buildIfNeeded()
			throws DataAccessLayerException {
		if (sessionFactory != null) {
			return sessionFactory;
		}
		try {
			return configureSessionFactory();
		} catch (HibernateException e) {
			log.error("Hibernate: ");
			throw new DataAccessLayerException(e);
		}
	}
	
	/**
	 * Obteniendo Sesion Factory
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Abriendo Sesión
	 * @return Sesión
	 * @throws HibernateException
	 */
	public static Session openSession() throws HibernateException {
		buildIfNeeded();
		return sessionFactory.openSession();
	}

	/**
	 * Cerrando Sesión Factory
	 */
	public static void closeFactory() {
		if (sessionFactory != null) {
			try {
				sessionFactory.close();
				log.debug("Hibernate, Cerrando SesionFactory");
			} catch (HibernateException ignored) {
				log.error("Couldn't close SessionFactory", ignored);
			}
		}
	}

	public static void close(Session session) {
		if (session != null) {
			try {
				// Si lo activo da este error
				// ERROR [LazyInitializationException] could not initialize
				// proxy
				session.close();
				log.debug("Hibernate, Cerrando Sesión");
			} catch (HibernateException ignored) {
				log.error("Couldn't close Session", ignored);
			}
		}
	}

	public static void rollback(Transaction tx) {
		try {
			if (tx != null) {
				tx.rollback();
			}
		} catch (HibernateException ignored) {
			log.error("Couldn't rollback Transaction", ignored);
		}
	}

	/**
	 * 
	 * @return
	 * @throws HibernateException
	 */
	private static SessionFactory configureSessionFactory()
			throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure();
		log.debug("Hibernate, Leyendo Archivo de Configuración");
		sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}

}
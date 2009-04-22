package org.jp.core.persistence.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jp.core.persistence.util.DataAccessLayerException;
import org.jp.core.persistence.util.HibernateFactory;

public abstract class AbstractDao {
	protected Session session;
	protected Transaction tx;

	private static final Log alog = LogFactory.getLog(AbstractDao.class);

	public AbstractDao() {
		HibernateFactory.buildIfNeeded();
	}

	/**
	 * Actualiza o Crea un Elemento en la Base de Datos
	 * 
	 * @param obj
	 */
	protected void saveOrUpdate(Object obj) {
		try {
			startOperation();
			session.saveOrUpdate(obj);
			comitTransaction();
			alog.debug("Se agregado un registro a una entidad");
		} catch (HibernateException e) {
			System.out.println("Error Save or Update: " + e);
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
	}

	/**
	 * Elimina un elemento en la Base de Datos
	 * 
	 * @param obj
	 */
	protected void delete(Object obj) {
		try {
			startOperation();
			session.delete(obj);
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
	}

	/**
	 * Busca un elemento por id en una entidad, en la Base de Datos
	 * 
	 * @param clazz
	 * @param id
	 */
	protected Object find(Class clazz, Integer id) {
		Object obj = null;
		try {
			startOperation();
			obj = session.get(clazz, id);
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);			
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	/**
	 * Regresa todos los elementos de una entidad en la Base de Datos
	 * 
	 * @param clazz
	 */

	protected List findAll(Class clazz) {
		List objects = null;
		try {
			startOperation();
			Query query = session.createQuery("from " + clazz.getName());
			objects = query.list();
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	/**
     * 
     */

	protected List findAllOrderPor(Class clazz, String ordenado) {
		List objects = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);
			crit.addOrder(Order.desc(ordenado));
			objects = crit.list();
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	/**
	 * Busca Todos los Elementos de una Tabla por un Parametro y valor
	 * Determinado
	 * 
	 * @param clazz
	 * @param param
	 * @param campo
	 */

	protected Object findAllUnParametro(Class clazz, String param, String campo) {
		List objects = null;
		Object objeto = null;
		try {
			startOperation();
			objects = session.createCriteria(clazz).add(
					Restrictions.like(param.trim(), campo.trim())).list();
			Iterator iter = objects.iterator();
			while (iter.hasNext()) {
				objeto = iter.next();
			}
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return objeto;

	}

	protected List findAllForParamList(Class clazz, List params, List campo) {
		Criteria criteria = null;
		List objects = null;
		Object objeto = null;
		try {
			startOperation();
			criteria = session.createCriteria(clazz);
			criteria.add(Restrictions.like(campo.get(0).toString(), params.get(
					0).toString()));
			criteria.add(Restrictions.like(campo.get(1).toString(), params.get(
					1).toString()));
			criteria.add(Restrictions.like(campo.get(2).toString(), params.get(
					2).toString()));
			objects = criteria.list();
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return objects;

	}

	/**
	 * Busca Todos los Elementos de una Tabla por un Parametro y valor
	 * Determinado
	 * 
	 * @param clazz
	 * @param param
	 * @param campo
	 */

	protected List ListfindAllUnParametro(Class clazz, String param,
			String campo) {
		List objects = null;

		try {
			startOperation();
			objects = session.createCriteria(clazz).add(
					Restrictions.like(param.trim(), campo.trim())).list();

			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return objects;

	}

	/**
	 * Busca resultados en una Entidad con Paginación
	 * 
	 * @param clazz
	 * @param inicia
	 * @param termina
	 * @return
	 */
	protected Object buscarPaginandoResultados(Class clazz, Integer inicia,
			Integer termina) {
		List objects = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);
			crit.setFirstResult(inicia);
			crit.setMaxResults(termina);
			objects = crit.list();
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	// Transacciones

	/**
	 * Crea una nueva sesion e inicia la transacción
	 * 
	 * @throws HibernateException
	 */
	protected void startOperation() throws HibernateException {
		if (session == null || !session.isOpen()) {

			session = HibernateFactory.openSession();

		} else {

		}
		tx = session.beginTransaction();
	}

	protected void closeOperation() throws HibernateException {
		if (session != null) {
			session.close();
		}
	}

	/**
	 * Termina una Transaccion
	 * 
	 * @throws HibernateException
	 */
	protected void comitTransaction() throws HibernateException {
		tx.commit();

	}

	// Metodos para Manejar Errores

	/**
	 * Manejador de Excepciones personalizado
	 * 
	 * @param e
	 */
	protected void handleException(HibernateException e)
			throws DataAccessLayerException {
		alog.fatal("Ha habido una HibernateException->" + e);
		HibernateFactory.rollback(tx);
		throw new DataAccessLayerException(e);
	}

	/**
	 * 
	 * @return
	 * @throws DataAccessLayerException
	 */
	public Integer ultimoRegistro(String clase, String id)
			throws DataAccessLayerException {
		Integer fid = null;
		try {
			startOperation();
			fid = (Integer) session.createQuery(
					"select max(" + id + ") from " + clase.trim())
					.uniqueResult();
			comitTransaction();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateFactory.close(session);
		}
		return fid;
	}

	public void SetSession(Session s) {
		this.session = s;
	}

	public Session startCn() {
		startOperation();
		return this.session;
	}

	public void closeCn() {
		closeOperation();
	}
}
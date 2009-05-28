package org.jp.core.persistence.dao;

import org.hibernate.HibernateException;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.jp.core.persistence.dao.imp.IProyect;
import org.jp.core.persistence.pojo.Proyect;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
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
 * Id: IProyectDao.java Date: 26/05/2009 14:46:20
 * 
 * @author juanpicado package: org.jp.core.persistence.dao.imp
 * @version 1.0
 */
public class ProyectDaoImp extends HibernateDaoSupport implements IProyect {

	/**
	 * 
	 * @param proyect
	 * @throws HibernateException
	 */
	public void createProyect(Proyect proyect) throws HibernateException {
		getHibernateTemplate().save(proyect);
	}

	/**
	 * update proyect
	 * 
	 * @param proyect
	 * @throws HibernateExceptio
	 */
	public void updateProyect(Proyect proyect) throws HibernateException {
		getHibernateTemplate().update(proyect);

	}

	/**
	 * delete proyect
	 * 
	 * @param proyect
	 * @throws HibernateException
	 */
	public void deleteProyect(Proyect proyect) throws HibernateException {
		getHibernateTemplate().delete(proyect);

	}

}

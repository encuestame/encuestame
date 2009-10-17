/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
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
 * this program; if not, writes to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.pojo.CatState;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Catalog State Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since April 26, 2009
 */
public class CatStateDaoImp extends HibernateDaoSupport implements ICatState {


    /**
     * Delete catalog state.
     * @param state catalog state
     * @throws HibernateException exception
     */
    public void delete(final CatState state) throws HibernateException{
        getHibernateTemplate().delete(state);
    }

    /**
     * Save catalog state.
     * @param state catalog state
     * @throws HibernateException exception
     */
    public void save(final CatState state) throws HibernateException {
        getHibernateTemplate().save(state);
    }

    /**
     * Retrieve all states.
     * @return list of states
     */
    public List<CatState> findAll() throws HibernateException {
        return getHibernateTemplate().find("from CatState");
    }

    /**
     * Get catalog state by id.
     * @param id id state
     * @return state
     */
    public CatState getState(final Integer id) throws HibernateException {
        return (CatState) getHibernateTemplate().findByNamedQuery(
                "State.loadStateByUsername", id);
    }

}

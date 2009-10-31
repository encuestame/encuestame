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
**/
package org.encuestame.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.sql.Delete;
import org.encuestame.core.persistence.dao.imp.ISecGroups;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * UserDao.
 *
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since May 05, 2009
 */
public class SecGroupDaoImp extends HibernateDaoSupport implements ISecGroups {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * delete group
     *
     * @param newG
     */
    public void delete(SecGroups newG) {
        getHibernateTemplate().delete(newG);
    }

    /**
     * find a group
     *
     * @param id
     * @return
     */
    public SecGroups find(Integer id) {
        List<SecGroups> s = getHibernateTemplate().findByNamedQuery(
                "Groupr.loadGroup", id);
        if (s != null && s.size() > 0) {
            SecGroups g = s.get(0);

            return g;
        } else {
            return null;
        }

    }

    public Collection<SecGroupUser> loadGroupsByUser(Integer id) {
        return getHibernateTemplate().find(
                "from SecGroupUser d where d.secUsers.uid =" + id);

    }

    public Collection<SecGroups> findAllGroups() {
        return getHibernateTemplate().find("from SecGroups");

    }

    public Integer lastRow(Class clase, String id) {
        return null;
    }

    public void newGroup(SecGroups newG) {
        getHibernateTemplate().save(newG);

    }

    public void update(SecGroups update) {
        getHibernateTemplate().update(update);
    }

    public void Delete(SecGroups group) {
        getHibernateTemplate().delete(group);
    }

    public SecGroups getGroupById(Long groupId) throws HibernateException{
            return (SecGroups) getSession().get(SecGroups.class, Integer.valueOf(groupId.toString()));
        }
}

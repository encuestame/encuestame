package org.encuestame.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.dao.imp.IGroupPermissionDao;
import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Id: GroupPermissionDAO.java 1822 08/02/2009
 *
 * @author juanpicado
 * @version 1.0 package org.encuestame.developer.web.beans
 */

public class GroupPermissionDaoImp extends AbstractHibernateDaoSupport implements
        IGroupPermissionDao {

    /**
     *
     * @return
     * @throws HibernateException
     */
    public List<SecGroupPermission> findAll() throws HibernateException {
        return super.findAll("from SecGroupPermission");
    }
}

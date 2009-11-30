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
 */
package org.encuestame.core.service;

import java.util.Collection;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.location.LocationTypeBean;
import org.encuestame.web.beans.project.UnitProjectBean;
import org.hibernate.HibernateException;

/**
 * Data Services.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public interface IDataSource {

    public Collection<UnitProjectBean> loadListProjects();
    public UnitProjectBean loadProjectInfo(UnitProjectBean project) throws EnMeExpcetion;
    public void createProject(UnitProjectBean project) throws EnMeExpcetion,
    HibernateException, Exception;
    public void createCatLocationType(LocationTypeBean locatType) throws EnMeExpcetion,HibernateException, Exception;
    public void createCatLocation(LocationBean location) throws EnMeExpcetion, HibernateException, Exception;
    public CatState getState(Long stateId) throws Exception;
    public ICatState getStateDao();
    public ICatLocation getCatLocationDao();
    public IProject getProjectDaoImp();
}

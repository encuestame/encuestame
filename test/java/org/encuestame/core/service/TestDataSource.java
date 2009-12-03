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

import org.encuestame.test.config.AbstractBaseTest;
import org.encuestame.web.beans.project.UnitProjectBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since 02/12/2009 22:26:24
 */
public class TestDataSource extends AbstractBaseTest{

    /** {@link IDataSource} **/
    @Autowired
    IDataSource  dataSource;

    /**
     * Before.
     */
    @Before
    public void initService(){
        createProject("project 1");
        createProject("project 2");
    }

    /**
     * Load List of Projects.
     */
    @Test
    public void testloadListProjects(){
        Collection<UnitProjectBean> listProjects = dataSource.loadListProjects();
        assertEquals(2, listProjects.size());
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }





}

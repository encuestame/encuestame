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
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.test.config.AbstractBeanBaseTest;
import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.project.UnitProjectBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since 02/12/2009 22:26:24
 * @version $Id: $
 */
public class TestDataSource extends AbstractBeanBaseTest {

    /** {@link IDataSource} **/
    @Autowired
    IDataSource  dataSource;

    /** {@link Project} **/
    Project project;

    /**
     * Before.
     */
    public void initService(){
        project = createProject("project 1");
        createProject("project 2");
    }

    /**
     * Load List of Projects.
     */
    @Test
    public void testloadListProjects(){
        this.initService();
        Collection<UnitProjectBean> listProjects = dataSource.loadListProjects();
        assertEquals(2, listProjects.size());
    }


    /**
     * Load List of Projects WithoutResults.
     */
    @Test
    public void testloadListProjectsWithoutResults(){
        Collection<UnitProjectBean> listProjects = dataSource.loadListProjects();
        assertEquals(0, listProjects.size());
    }


    /**
     * Test loadProjectInfo.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadProjectInfo() throws EnMeExpcetion{
          this.initService();
          final UnitProjectBean projectBean = new UnitProjectBean();
          projectBean.setId(project.getProyectId());
          final UnitProjectBean projectRetrieve = dataSource.loadProjectInfo(projectBean);
          assertNotNull(projectRetrieve);
          assertEquals("Should be",project.getProyectId(),projectRetrieve.getId());
    }

    /**
     * Test loadProjectInfo id null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testloadProjectInfoIdNull() throws EnMeExpcetion{
          final UnitProjectBean projectBean = new UnitProjectBean();
          final UnitProjectBean projectRetrieve = dataSource.loadProjectInfo(projectBean);
    }

    /**
     * Test loadProjectInfo project null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testloadProjectInfoProjectNull() throws EnMeExpcetion {
          final UnitProjectBean projectBean = new UnitProjectBean();
          projectBean.setId(444L);
          final UnitProjectBean projectRetrieve = dataSource.loadProjectInfo(projectBean);
    }

    /**
     * Test create project.
     * @throws EnMeExpcetion encuestame exception.
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testcreateProjectNull()throws EnMeExpcetion{
        dataSource.createProject(null);
    }

    /**
     * Test create project.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testcreateProject()throws EnMeExpcetion {
        final UnitProjectBean projectBean = createProjectBean("encuestame");
        dataSource.createProject(projectBean);
        final UnitProjectBean projectRetrieve = dataSource.loadProjectInfo(projectBean);
        assertNotNull(projectRetrieve);
        assertEquals("Should be equals ",projectBean.getName(),projectRetrieve.getName());
    }

    /**
     * Test Create Location Type.
     * @throws EnMeExpcetion exception
     */
    /* @Test
    public void testcreateCatLocationType()throws EnMeExpcetion {
        LocationTypeBean locationTypeBean = createLocationTypeBean("nicaragua",0);
        locationTypeBean = dataSource.createCatLocationType(locationTypeBean);
        final CatLocationType locationTypeDomain = getCatLocationTypeDao().getLocationById(locationTypeBean.getLocationTypeId());
        assertNotNull(locationTypeDomain);
        assertEquals("Should be equals ",locationTypeDomain.getLocationTypeId(),locationTypeBean.getLocationTypeId());
    }*/


    /**
     * Test Create Location Type Null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testcreateCatLocationTypeNull()throws EnMeExpcetion {
       dataSource.createCatLocationType(null);
    }

    /**
     * Test Create Location Null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testcreateCatLocationNull() throws EnMeExpcetion{
        dataSource.createCatLocationType(null);
    }

    /**
     * Test Create Location.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testcreateCatLocation() throws EnMeExpcetion{
        final LocationBean locationBean = createLocationBean("S", "managua",1F, 2F, 0);
        final LocationBean locationBeanResponse = dataSource.createCatLocation(locationBean);
        assertNotNull(locationBean);
        assertEquals("Should be equasl",locationBean.getDescription(),
        locationBeanResponse.getDescription());
    }


    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
}

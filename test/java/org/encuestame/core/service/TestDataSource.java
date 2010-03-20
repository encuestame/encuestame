/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.util.Collection;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.test.config.AbstractBeanBaseTest;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.web.beans.location.LocationBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since 02/12/2009 22:26:24
 * @version $Id$
 */
public class TestDataSource extends AbstractBeanBaseTest {

    /** {@link IDataSource} **/
    @Autowired
    IDataSource  dataSource;

    /** {@link Project} **/
    Project project;

    /** {@link SecUsers}. **/
    SecUsers user;

    /**
     * Before.
     */
    @Before
    public void initService(){
        this.user = createUser();
        this.project = createProject("project 1","TIC Project","Project", createState("active"), this.user);
        createProject("project 2","Education Project","Project", createState("active"), this.user);
    }

    /**
     * Load List of Projects.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadListProjects() throws EnMeExpcetion{
        Collection<UnitProjectBean> listProjects = dataSource.loadListProjects(this.user.getUid());
        assertEquals(2, listProjects.size());
    }


    /**
     * Load List of Projects WithoutResults.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadListProjectsWithoutResults() throws EnMeExpcetion{
        final Collection<UnitProjectBean> listProjects = dataSource.loadListProjects(this.user.getUid());
        assertEquals(2, listProjects.size());
    }


    /**
     * Test loadProjectInfo.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadProjectInfo() throws EnMeExpcetion{
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
        final SecUserSecondary lead = createSecondaryUser("leader", this.user);
        final UnitProjectBean projectBean = createProjectBean("encuestame", lead.getUid(), this.user.getUid());
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
        final LocationBean locationBean = createLocationBean("S", "managua",1F, 2F, 0, "aaa");
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

    /**
     * Test Update Location.
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testUpdateLocation() throws EnMeExpcetion{
        final CatLocation catLoc = createCatLocation("Masaya", "Departamento", 1);
        Long idLocation = catLoc.getLocateId();
        System.out.println("**********************");
        UnitLocationBean locationBean = ConvertDomainBean.convertLocationToBean(catLoc);
        locationBean.setDescriptionLocation("Granada");
        System.out.println(locationBean.getTid());
        dataSource.updateCatLocation(locationBean);
        System.out.println(idLocation);
        CatLocation locRetrieve = getCatLocation().getLocationById(idLocation);
        assertEquals("Should be", "Granada", locRetrieve.getLocationDescription());
      }

    /**
     * Test Update Location Type.
     * @throws EnMeExpcetion  EnMeExpcetion
     */
    @Test
    public void testUpdateLocationType() throws EnMeExpcetion{
        final CatLocationType catLocType = createCatLocationType("Comarca");
        Long idLocationType = catLocType.getLocationTypeId();
        System.out.println("IDLOCATION-->"+idLocationType);
        UnitLocationTypeBean locationTypeBean = ConvertDomainBean.convertLocationTypeToBean(catLocType);
        locationTypeBean.setLocTypeDesc("pueblo");
        dataSource.updateCatLocationType(locationTypeBean);
        CatLocationType locTypeRetrieve = getCatLocationTypeDao().getLocationById(idLocationType);
        assertEquals("should be","pueblo", locTypeRetrieve.getLocationTypeDescription());


    }

}

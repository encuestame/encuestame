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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.service.IDataSource;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ProjectDaoImp.
 * * @author Morales, Diana Paola paola@encuestame.org
 * @since 16/12/2009
 **/
public class TestProjectDaoImp extends AbstractBaseTest{

    /** {@link IProject} **/
    @Autowired
    IProject  projectI;

    /** {@link Project} **/
    Project project;

    /**
     * Before.
     */
    @Before
    public void initService(){
        project = createProject("project 1","TIC Project","Project", "active");

    }
    /**
     * Test Find All Projects.
     */
    @Test
    public void testFindAllProjects(){
     //   createProject("project 3","Health Project","Project", "active");
        final List<Project> projectAll = getProjectDaoImp().findAll();
        assertNotNull(projectAll);
        assertEquals("Should be",1, projectAll.size());
    }

    /**
     * Test Get Project by Id.
     */
    @Test
    public void testGetProjectById(){
        final Project prj = getProjectDaoImp().getProjectbyId(this.project.getProyectId());
        assertNotNull(prj);

}

}

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
package org.encuestame.core.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.IProjectService;
import org.encuestame.core.test.service.config.AbstractBase;
import org.encuestame.utils.web.UnitProjectBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Project Service Test.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since April 13, 2009
 * @version $Id:$
 */
public class TestProjectService extends AbstractBase {


       /** {@link SecUsers}. **/

         SecUsers user;

        /** {@link IProjectService} **/
        @Autowired
        private IProjectService projectService;

        /** {@link Project} **/

        private Project project;

        /**
         * Before.
         */
        @Before
        public void initService(){
            this.user = createUser();
            createProject("project 1","TIC Project","Project", createState("active"), this.user);
           project= createProject("project 2","Education Project","Project", createState("active"), this.user);
        }

        /**
         * Load List of Projects.
         * @throws EnMeExpcetion exception
         */
        @Test
        public void testloadListProjects() throws EnMeExpcetion{
            Collection<UnitProjectBean> listProjects = projectService.loadListProjects(this.user.getUid());
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
             final UnitProjectBean projectRetrieve = projectService.loadProjectInfo(projectBean);
             assertNotNull(projectRetrieve);
             assertEquals("Should be",project.getProyectId(),projectRetrieve.getId());
       }


       /**
       * Load List of Projects WithoutResults.
       * @throws EnMeExpcetion exception
       */
      @Test
      public void testloadListProjectsWithoutResults() throws EnMeExpcetion{
          final Collection<UnitProjectBean> listProjects = projectService.loadListProjects(this.user.getUid());
          assertEquals(2, listProjects.size());
      }



      /**
       * Test loadProjectInfo id null.
       * @throws EnMeExpcetion exception
       */
      @Test
      @ExpectedException(EnMeExpcetion.class)
      public void testloadProjectInfoIdNull() throws EnMeExpcetion{
            final UnitProjectBean projectBean = new UnitProjectBean();
            final UnitProjectBean projectRetrieve =projectService.loadProjectInfo(projectBean);
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
            final UnitProjectBean projectRetrieve = projectService.loadProjectInfo(projectBean);
      }

      /**
       * Test Create project Null.
       * @throws EnMeExpcetion encuestame exception.
       */
      @Test
      @ExpectedException(EnMeExpcetion.class)
      public void testcreateProjectNull()throws EnMeExpcetion{
          projectService.createProject(null);
      }



        public IProjectService getProjectService() {
            return projectService;
        }

        public void setProjectService(IProjectService projectService) {
            this.projectService = projectService;
        }


}

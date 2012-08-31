/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.encuestame.core.service.imp.IProjectService;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.web.UnitProjectBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Project Service Test.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 13, 2009
 */
@Category(DefaultTest.class)
public class TestProjectService extends AbstractServiceBase {


       /** {@link Account}. **/
        private Account user;

        /** {@link IProjectService} **/
        @Autowired
        private IProjectService projectService;

        /** {@link Project} **/

        private Project project;

        /** {@link UserAccount}. **/
        private UserAccount userPrincipal;

        /**
         * Before.
         */
        @Before
        public void initService(){
           this.user = createAccount();
           this.userPrincipal = createUserAccount("jhon", this.user);
           createProject("project 1","TIC Project","Project", this.user);
           this.project= createProject("project 2","Education Project","Project", this.user);
        }

        /**
         * Load List of Projects.
         * @throws EnMeExpcetion exception
         */
        @Test
        public void testloadListProjects() throws EnMeExpcetion{
            List<UnitProjectBean> listProjects = projectService.loadListProjects(this.userPrincipal.getUsername());
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
          final List<UnitProjectBean> listProjects = projectService.loadListProjects(this.userPrincipal.getUsername());
          assertEquals(2, listProjects.size());
      }



      /**
       * Test loadProjectInfo id null.
       * @throws EnMeExpcetion exception
       */
      @Test(expected = EnMeExpcetion.class)
      public void testloadProjectInfoIdNull() throws EnMeExpcetion{
            final UnitProjectBean projectBean = new UnitProjectBean();
            projectService.loadProjectInfo(projectBean);
      }

      /**
       * Test loadProjectInfo project null.
       * @throws EnMeExpcetion exception
       */
      @Test(expected = EnMeExpcetion.class)
      public void testloadProjectInfoProjectNull() throws EnMeExpcetion {
            final UnitProjectBean projectBean = new UnitProjectBean();
            projectBean.setId(444L);
            projectService.loadProjectInfo(projectBean);
      }

      /**
       * Test Create project Null.
       * @throws EnMeExpcetion encuestame exception.
       */
      @Test(expected = EnMeExpcetion.class)
      public void testcreateProjectNull()throws EnMeExpcetion{
          projectService.createProject(null, null);
      }



        public IProjectService getProjectService() {
            return projectService;
        }

        public void setProjectService(IProjectService projectService) {
            this.projectService = projectService;
        }


}

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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.encuestame.persistence.dao.IProjectDao;
import org.encuestame.persistence.domain.Attachment;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ProjectDaoImp.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 16/12/2009
 * @version $Id$
 **/
public class TestProjectDaoImp extends AbstractBase{

    /** {@link IProjectDao} **/
    @Autowired
    IProjectDao  projectI;

    /** {@link Project} **/
    Project project;

    /** {@link UserAccount}.**/
    UserAccount user;

    private Attachment attachment;

    private Date uploadDate = Calendar.getInstance().getTime();
    /**
     * Before.
     */
    @Before
    public void initService(){
        user  = createUserAccount("user 1", createAccount());
        project = createProject("project 1","TIC Project","Project", user.getAccount());
        attachment = createAttachment("Spring Reference.pdf", uploadDate, project);

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

    /**
     * Test Add Locations to Project.
     */
    public void testGetProjectByLocationId(){
        final GeoPoint loc1 = createGeoPoint("managua", "mga", 1, this.user.getAccount());
        final GeoPoint loc2 = createGeoPoint("diriomo", "drm", 1, this.user.getAccount());
        project.getLocations().add(loc1);
        project.getLocations().add(loc2);
        getProjectDaoImp().saveOrUpdate(project);
        assertEquals("Should be equals", 2, project.getLocations().size());
    }

    /**
     * Test findProjectsByUserID.
     */
    @Test
    public void testFindProjectsByUserID(){
        createProject("encuestame", "survey system", "the best", user.getAccount());
        final List<Project> projectList = getProjectDaoImp().findProjectsByUserID(user.getAccount().getUid());
        assertEquals("Should be equals", 2, projectList.size());
    }

    /**
     * Test Get Attachment Info by Attachment Id.
     */
    @Test
    public void tesGetAttachmentbyId(){
        assertNotNull(attachment);
        Attachment attach = getProjectDaoImp().getAttachmentbyId(this.attachment.getAttachmentId());
        log.debug("Attachment id created before--> "+ attachment.getAttachmentId());
        log.debug("Attachment id retrieved -> "+ attach.getAttachmentId());
        assertEquals("Should be equals", this.attachment.getAttachmentId(), attach.getAttachmentId());
    }

    @Test
    public void testGetAttachmentsListbyProject(){
        Attachment attachment2 = createAttachment("DojoReference.pdf", uploadDate, this.project);
        assertNotNull(attachment);
        assertNotNull(attachment2);
        final List<Attachment> attachList = getProjectDaoImp().getAttachmentsListbyProject(this.project.getProyectId());
        log.debug("Attach List -->" + attachList.size());
        assertEquals("Should be equals", 2, attachList.size());
    }

}

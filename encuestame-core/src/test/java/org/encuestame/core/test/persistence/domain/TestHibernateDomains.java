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
package org.encuestame.core.test.persistence.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.encuestame.core.persistence.domain.CatEmailLists;
import org.encuestame.core.persistence.domain.CatEmails;
import org.encuestame.core.persistence.domain.CatLocation;
import org.encuestame.core.persistence.domain.CatLocationFolder;
import org.encuestame.core.persistence.domain.CatLocationType;
import org.encuestame.core.persistence.domain.CatState;
import org.encuestame.core.persistence.domain.LocationFolderType;
import org.encuestame.core.persistence.domain.Project;
import org.encuestame.core.persistence.domain.Question;
import org.encuestame.core.persistence.domain.QuestionColettion;
import org.encuestame.core.persistence.domain.QuestionDependenceSurvey;
import org.encuestame.core.persistence.domain.QuestionDependencies;
import org.encuestame.core.persistence.domain.QuestionPattern;
import org.encuestame.core.persistence.domain.QuestionsAnswers;
import org.encuestame.core.persistence.domain.SecGroup;
import org.encuestame.core.persistence.domain.SecPermission;
import org.encuestame.core.persistence.domain.SecUser;
import org.encuestame.core.persistence.domain.SecUserSecondary;
import org.encuestame.core.persistence.domain.Status;
import org.encuestame.core.persistence.domain.SurveyFolder;
import org.encuestame.core.persistence.domain.SurveyFormat;
import org.encuestame.core.persistence.domain.SurveyGroup;
import org.encuestame.core.persistence.domain.SurveyPagination;
import org.encuestame.core.persistence.domain.Surveys;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Test;

/**
 * Hibernate Domains.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 16/12/2009
 * @version $Id$
 **/
public class TestHibernateDomains extends AbstractBase{

    /**
     * Test Catalog Location.
     */
    @Test
    public void testCatLocation(){
        final CatLocation catLoc = new CatLocation();
        catLoc.setLocationStatus(Status.ACTIVE);
        catLoc.setLocationDescription("Managua");
        catLoc.setLocationLatitude(2F);
        catLoc.setLocationLongitude(3F);
        catLoc.setTidtype(createCatLocationType("aldea"));
        catLoc.getProjects().add(createProject("encuestame", "survey", "open source", createState("active"), createUser()));
        getCatLocationDao().saveOrUpdate(catLoc);
        assertNotNull(catLoc.getLocateId());
    }

    /**
    * Test Catalog Location Type.
    */
   @Test
   public void testLocationType(){
       final CatLocationType catLocType = new CatLocationType();
       catLocType.setLocationTypeDescription("Departamento");
       catLocType.setLocationTypeLevel(1);
       getCatLocationTypeDao().saveOrUpdate(catLocType);
       assertNotNull(catLocType.getLocationTypeId());
   }

    /**
     * Test Catalog State.
     */
    @Test
    public void testCatState(){
        final CatState catState = new CatState();
        catState.setDescState("Disabled");
        catState.setStateImage("Image");
        getCatStateDaoImp().saveOrUpdate(catState);
        assertNotNull(catState.getIdState());
     }

    /**
     * Test Project.
     **/
    @Test
    public void testProject(){
        final Project project = new Project();
        project.setProjectDescription("Tic Municipal");
        project.setProjectInfo("Tic Project in Nicaraguan Municipalities");
        project.setProjectDateStart(new Date());
        project.setProjectDateFinish(new Date());
        project.setProjectName("Name");
         project.getSurveyGroups().add(createSurveyGroup("Education"));
        project.getSecUserSecondaries().add(createSecondaryUser("Diana",createUser()));
        project.getGroups().add(createGroups("editor"));
        project.getLocations().add(createCatLocation("Matagalpa","Matalgalpa Department",2, createUser()));
        getProjectDaoImp().saveOrUpdate(project);
        assertNotNull(project.getProyectId());
    }

    /**
    *Test Question Location Domain.
    **/
    @Test
    public void testQuestionCollection(){
        final QuestionColettion questionCollect = new QuestionColettion();
        questionCollect.setSecUsers(createUser());
        questionCollect.setDesColeccion("Generalities Collection");
        questionCollect.setCreationDate(new Date());
        getQuestionDaoImp().saveOrUpdate(questionCollect);
        assertNotNull(questionCollect.getIdQColection());

    }


    /**
    * Test Question Pattern Domain.
    **/
    @Test
    public void testQuestionPattern(){
        final QuestionPattern questionPattern = new QuestionPattern();
        questionPattern.setPatternType("yes or No");
        questionPattern.setDesQid("1");
        questionPattern.setLabelQid("Y/N");
        questionPattern.setLabelQid("Yes or No");
        questionPattern.setFinallity("Select an option");
        questionPattern.setClass_("class1.java");
        questionPattern.setPatternTemplate("patter.template.java");
        questionPattern.setLevel(2);
        getQuestionDaoImp().saveOrUpdate(questionPattern);
        assertNotNull(questionPattern.getPatternId());

    }

    /**
    * Test Questions Domain.
    **/
    @Test
    public void testQuestions(){
        final Question questions = new Question();
        questions.setQuestion("What is your Name");
        questions.setQidKey("2");
        questions.setCatState(createState("Inactive"));
        questions.setQuestionPattern(createQuestionPattern("options"));
        questions.getQuestionColettions().add(createQuestionCollect("options"));
        questions.setSecUsersQuestion(createUser());
        getQuestionDaoImp().saveOrUpdate(questions);
        assertNotNull(questions.getQid());

    }

    /**
     * Test Questions Answer Domain.
     **/
    @Test
    public void testQuestionsAnswer(){
        final QuestionsAnswers questionsAns = new QuestionsAnswers();
        questionsAns.setQuestions(createQuestion("how old are you", "textbox"));
        questionsAns.setAnswer("25");
        questionsAns.setUniqueAnserHash("");
        getQuestionDaoImp().saveOrUpdate(questionsAns);
        assertNotNull(questionsAns.getQuestionAnswerId());
    }

    /**
     * Test Sec Groups Domain.
     **/
    @Test
    public void testSecGroups(){
        final SecGroup groups = new SecGroup();
        groups.setGroupName("writers");
        groups.setGroupDescriptionInfo("writers");
        groups.setIdState(createState("Active").getIdState());
        groups.setSecUsers(createUser());
        groups.getSecPermissions().add(createPermission("administrator"));
        groups.getProjects().add(createProject("TIC", "TIC", "TIC", createState("active"), createUser()));
        getSecGroup().saveOrUpdate(groups);
        assertNotNull(groups.getGroupId());
    }

    /**
     * Test SecPermission Domain.
     **/
    @Test
    public void testSecPermission(){
        final SecPermission permission = new SecPermission();
        permission.setPermission("Administrator");
        permission.setPermissionDescription("Administrator of alls options");
        permission.getSecGroups().add(createGroups("administrator"));
        permission.getSecUserSecondaries().add(createSecondaryUser("juan carlos", createUser()));
        getSecPermissionDaoImp().saveOrUpdate(permission);
        assertNotNull(permission.getIdPermission());
    }

    /**
    *Test Sec User Domain.
    */
    @Test
    public void testSecUser(){
        final SecUser user = new SecUser();
        user.setTwitterAccount("");
        user.setTwitterPassword("");
        getSecUserDao().saveOrUpdate(user);
        assertNotNull(user.getUid());
    }

    /**
    * Test SecUser Secondary Domain.
    **/
    @Test
    public void testSecUserSecondary(){
        final SecUserSecondary userSec = new SecUserSecondary();
        userSec.setCompleteName("Juan Carlos Picado");
        userSec.setUserEmail("juan@encuestame.org");
        userSec.setUsername("jpicado");
        userSec.setPassword("123456");
        userSec.setInviteCode("S");
        userSec.setEnjoyDate(new Date());
        userSec.setUserStatus(true);
        userSec.setUserTwitterAccount("si");
        userSec.setSecUser(createUser());
        getSecUserDao().saveOrUpdate(userSec);
        assertNotNull(userSec.getUid());
    }


    /**
    * Test Survey Format Domain.
    **/
 @Test
     public void testSurveyFormat(){
         final SurveyFormat surveyformat = new SurveyFormat();
         surveyformat.setDateCreated(new Date());
         surveyformat.setSurveyFormatName("Education");
         surveyformat.getSurveyGroups().add(createSurveyGroup("Education National"));
         getSurveyFormatDaoImp().saveOrUpdate(surveyformat);
         assertNotNull(surveyformat.getIdSidFormat());

     }


     /**
     * Test Survey Group Domain.
     **/
     @Test
     public void testSurveyGroup(){
         final SurveyGroup surveyGroup = new SurveyGroup();
         surveyGroup.setGroupName("Education");
         surveyGroup.setDateCreate(new Date());
         surveyGroup.setCatState(createState("disabled"));
         surveyGroup.getSurveyFormats().add(createSurveyFormat());
         surveyGroup.getProjects().add(createProject("TIC", "TIC", "TIC", createState("active"), createUser()));
         getSurveyDaoImp().saveOrUpdate(surveyGroup);
         assertNotNull(surveyGroup.getSgId());
     }

     /**
      * Test Surveys Domain.
      **/
     @Test
     public void testSurveys(){
         final Surveys surveys = new Surveys();
         surveys.setSecUsers(createUser());
         surveys.setTicket(1);
         surveys.setStartDate(new Date());
         surveys.setEndDate(new Date());
         surveys.setDateInterview(new Date());
         surveys.setComplete("y");
         surveys.setSurveyFormat(createSurveyFormat());
         getSurveyDaoImp().saveOrUpdate(surveys);
         assertNotNull(surveys.getSid());
     }

    /**
     *
     */
     @Test
     public void testLocationFolder(){
         final CatLocationFolder catLocationFolder = new CatLocationFolder();
         catLocationFolder.setFolderType(LocationFolderType.GROUPING);
         catLocationFolder.setLocationFolderName("test folder");
         catLocationFolder.setSecUsers(createUser());
         getCatLocationDao().saveOrUpdate(catLocationFolder);
     }

     /**
      * Test Email Catalog.
      **/
     @Test
     public void testCatEmail(){
         final CatEmails catEmailList = new CatEmails();
         catEmailList.setEmail("paola@jotadeveloper.com");
         catEmailList.setIdListEmail(createDefaultListEmail());
         getCatEmailDao().saveOrUpdate(catEmailList);
         assertNotNull(catEmailList.getIdEmail());
     }

     /**
      * Test Email List Catalog.
      **/
     @Test
     public void testCatEmailList(){
         final CatEmailLists catListEmails = new CatEmailLists();
         catListEmails.setCreatedAt(new Date());
         catListEmails.setListName("default encuestame list");
         catListEmails.setUsuarioEmail(createUser());
         getCatEmailDao().saveOrUpdate(catListEmails);
         assertNotNull(catListEmails.getIdList());
     }

     /**
      * Test Survey Folders
      */
    // @Test
     public void testSurveyFolders(){
         final SurveyFolder surveyFolders = new SurveyFolder();
         surveyFolders.setCreatedAt(new Date());
         surveyFolders.setFolderName("My Surveys");
         surveyFolders.setSurvey(createSurvey("", new Date(), new Date(), createUser(), new Date(), createSurveyFormat()));
         surveyFolders.setUsers(createUser());
         getSurveyDaoImp().saveOrUpdate(surveyFolders);
         assertNotNull(surveyFolders.getSurveyFolderId());
     }

     /**
      * Test Survey Pagination
      */
     //@Test
     public void testSurveyPagination(){
         final SurveyPagination surveyPagination = new SurveyPagination();
         //surveyPagination.setAnswers(createQuestionAnswer("Yes", createQuestion("DD", createUser()), "DD"));
         surveyPagination.setPageNumber(2L);
         //surveyPagination.setQuestions(createQuestion("DD", createUser()));
         //surveyPagination.setSurveys(createSurvey("", new Date(), new Date(), createUser(), new Date(), createSurveyFormat()));
         getSurveyDaoImp().saveOrUpdate(surveyPagination);
         assertNotNull(surveyPagination.getPaginationId());

     }

     /**
      * Test Question Dependence Survey
      */
     //@Test
     public void testQuestionDependenceSurvey(){
         final QuestionDependenceSurvey questionDepSurvey = new QuestionDependenceSurvey();
         questionDepSurvey.setSurvey(createSurvey("", new Date(), new Date(), createUser(), new Date(), createSurveyFormat()));
         getQuestionDaoImp().saveOrUpdate(questionDepSurvey);
         assertNotNull(questionDepSurvey.getQuestionDependenceId());
     }

     /**
      * Test Question Dependencies
      */
     //@Test
     public void testQuestionDependencies(){
         final QuestionDependencies questionDep = new QuestionDependencies();
         questionDep.setAnswers((createQuestionAnswer("Yes", createQuestion("DD", createUser()), "DD")));
         questionDep.setDescriptionDependence("My question dependencies");
         questionDep.setQuestionId_from(5L);
         questionDep.setQuestionId_to(3L);

     }
}
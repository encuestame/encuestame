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
package org.encuestame.test.persistence.domain;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.Client;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.Status;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.question.CatQuestionCategory;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionColettion;
import org.encuestame.persistence.domain.question.QuestionDependenceSurvey;
import org.encuestame.persistence.domain.question.QuestionDependencies;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.test.config.AbstractBase;
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
    public void testGeoPoint(){
        final GeoPoint catLoc = new GeoPoint();
        catLoc.setLocationStatus(Status.ACTIVE);
        catLoc.setLocationDescription("Managua");
        catLoc.setLocationLatitude(2F);
        catLoc.setLocationLongitude(3F);
        catLoc.setTidtype(createGeoPointType("aldea"));
        catLoc.getProjects().add(createProject("encuestame", "survey", "open source", createAccount()));
        getGeoPointDao().saveOrUpdate(catLoc);
        assertNotNull(catLoc.getLocateId());
    }

    /**
    * Test Catalog Location Type.
    */
   @Test
   public void testLocationType(){
       final GeoPointType catLocType = new GeoPointType();
       catLocType.setLocationTypeDescription("Departamento");
       catLocType.setLocationTypeLevel(1);
       getGeoPointDao().saveOrUpdate(catLocType);
       assertNotNull(catLocType.getLocationTypeId());
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
        project.getSecUserSecondaries().add(createUserAccount("Diana",createAccount()));
        project.getGroups().add(createGroups("editor"));
        project.getLocations().add(createGeoPoint("Matagalpa","Matalgalpa Department",2, createAccount()));
        getProjectDaoImp().saveOrUpdate(project);
        assertNotNull(project.getProyectId());
    }

    /**
    *Test Question Location Domain.
    **/
    @Test
    public void testQuestionCollection(){
        final QuestionColettion questionCollect = new QuestionColettion();
        questionCollect.setSecUsers(createAccount());
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
        questions.setHits(0L);
        questions.setSharedQuestion(Boolean.FALSE);
        questions.setSlugQuestion(" "+RandomStringUtils.randomAscii(10));
        questions.setQuestionPattern(createQuestionPattern("options"));
        questions.getQuestionColettions().add(createQuestionCollect("options"));
        questions.setAccountQuestion(createAccount());
        getQuestionDaoImp().saveOrUpdate(questions);
        assertNotNull(questions.getQid());

    }

    /**
     * Test Questions Answer Domain.
     **/
    @Test
    public void testQuestionsAnswer(){
        final QuestionAnswer questionsAns = new QuestionAnswer();
        questionsAns.setQuestions(createQuestion("how old are you", "textbox"));
        questionsAns.setAnswer("25");
        questionsAns.setUniqueAnserHash("");
        questionsAns.setColor("#RRRRRR");
        questionsAns.setCreatedDate(new Date());
        getQuestionDaoImp().saveOrUpdate(questionsAns);
        assertNotNull(questionsAns.getQuestionAnswerId());
    }

    /**
     * Test Sec Groups Domain.
     **/
    @Test
    public void testSecGroups(){
        final Group groups = new Group();
        groups.setGroupName("writers");
        groups.setGroupDescriptionInfo("writers");
        groups.setAccount(createAccount());
        groups.getPermissions().add(createPermission("administrator"));
        groups.getProjects().add(createProject("TIC", "TIC", "TIC", createAccount()));
        getGroup().saveOrUpdate(groups);
        assertNotNull(groups.getGroupId());
    }

    /**
     * Test SecPermission Domain.
     **/
    @Test
    public void testSecPermission(){
        final Permission permission = new Permission();
        permission.setPermission(EnMePermission.getPermissionString("ENCUESTAME_ADMIN"));
        permission.setPermissionDescription("Administrator of alls options");
        permission.getGroups().add(createGroups("administrator"));
        permission.getSecUserSecondaries().add(createUserAccount("juan carlos", createAccount()));
        getPermissionDaoImp().saveOrUpdate(permission);
        assertNotNull(permission.getIdPermission());
    }

    /**
    *Test Sec User Domain.
    */
    @Test
    public void testSecUser(){
        final Account user = new Account();
         getAccountDao().saveOrUpdate(user);
        assertNotNull(user.getUid());
    }

    /**
    * Test SecUser Secondary Domain.
    **/
    @Test
    public void testSecUserSecondary(){
        final UserAccount userSec = new UserAccount();
        userSec.setCompleteName("Juan Carlos Picado");
        userSec.setUserEmail("juan@encuestame.org");
        userSec.setUsername("jpicado");
        userSec.setPassword("123456");
        userSec.setInviteCode("S");
        userSec.setEnjoyDate(new Date());
        userSec.setUserStatus(true);
        userSec.setAccount(createAccount());
        getAccountDao().saveOrUpdate(userSec);
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
         surveyGroup.getSurveyFormats().add(createSurveyFormat("Schools",new Date()));
         surveyGroup.getProjects().add(createProject("TIC", "TIC", "TIC", createAccount()));
         getSurveyDaoImp().saveOrUpdate(surveyGroup);
         assertNotNull(surveyGroup.getSgId());
     }

     /**
      * Test Surveys Domain.
      **/
     @Test
     public void testSurveys(){
         final Survey surveys = new Survey();
         surveys.setSecUsers(createAccount());
         surveys.setTicket(1);
         surveys.setStartDate(new Date());
         surveys.setEndDate(new Date());
         surveys.setDateInterview(new Date());
         surveys.setComplete("y");
         surveys.setSurveyFormat(createSurveyFormat("Schools",new Date()));
         //surveys.setSurveysfolder(createSurveyFolders());
         getSurveyDaoImp().saveOrUpdate(surveys);
         assertNotNull(surveys.getSid());
     }

    /**
     *
     */
     @Test
     public void testLocationFolder(){
         final GeoPointFolder geoPointFolder = new GeoPointFolder();
         geoPointFolder.setFolderType(GeoPointFolderType.GROUPING);
         geoPointFolder.setFolderName("test folder");
         geoPointFolder.setUsers(createAccount());
         getGeoPointDao().saveOrUpdate(geoPointFolder);
     }

     /**
      * Test Email Catalog.
      **/
     @Test
     public void testCatEmail(){
         final Email email = new Email();
         email.setEmail("paola@jotadeveloper.com");
         email.setIdListEmail(createDefaultListEmail());
         getCatEmailDao().saveOrUpdate(email);
         assertNotNull(email.getIdEmail());
     }

     /**
      * Test Email List Catalog.
      **/
     @Test
     public void testCatEmailList(){
         final EmailList catListEmails = new EmailList();
         catListEmails.setCreatedAt(new Date());
         catListEmails.setListName("default encuestame list");
         catListEmails.setUsuarioEmail(createAccount());
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
         //surveyFolders.setSurvey(createSurvey("", new Date(), new Date(), createUser(), new Date(), createSurveyFormat()));
         surveyFolders.setUsers(createAccount());
         getSurveyDaoImp().saveOrUpdate(surveyFolders);
         assertNotNull(surveyFolders.getId());
     }

     /**
      * Test Survey Pagination
      */
     //@Test
     public void testSurveyPagination(){
         final SurveyPagination surveyPagination = new SurveyPagination();
         //surveyPagination.setAnswers(createQuestionAnswer("Yes", createQuestion("DD", createUser()), "DD"));
         surveyPagination.setPageNumber(1);
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
         questionDepSurvey.setSurvey(createSurvey("", new Date(), new Date(), createAccount(), new Date(), createSurveyFormat("Schools",new Date()),"FirstSurvey"));
         getQuestionDaoImp().saveOrUpdate(questionDepSurvey);
         assertNotNull(questionDepSurvey.getQuestionDependenceId());
     }

     /**
      * Test Question Dependencies
      */
     //@Test
     public void testQuestionDependencies(){
         final QuestionDependencies questionDep = new QuestionDependencies();
         questionDep.setAnswers((createQuestionAnswer("Yes", createQuestion("DD", createAccount()), "DD")));
         questionDep.setDescriptionDependence("My question dependencies");
         questionDep.setQuestionId_from(5L);
         questionDep.setQuestionId_to(3L);

     }

     @Test
     public void testQuestionCategory(){
         final CatQuestionCategory questionCategory = new CatQuestionCategory();
         //questionCategory.setQuestionCategoryId(1L);
         questionCategory.setCategory("Education");
         getQuestionDaoImp().saveOrUpdate(questionCategory);
         assertNotNull(questionCategory.getQuestionCategoryId());
     }

     @Test
     public void testClient(){
         final Client client = new Client();
         client.setClientDescription("description");
         client.setClientEmail("juan@encuestame.org");
         client.setClientFacebook("juancarlospicado");
         client.setClientFax("123134");
         client.setClientName("Jhon");
         client.setClientTelephone("34232423432");
         client.setClientTwitter("encuestame");
         client.setClientUrl("http://www.encuestame.org");
         client.setProject(createProject("encuestame","open source", "info", createAccount()));
     }

     /** HashTag domain. **/
     @Test
     public void testHashTag(){
        final String hashTag = "";
        final Long hits = 1L;
        final Long size = 10L;
        final HashTag tag = new HashTag();
        tag.setHashTag(hashTag);
        tag.setHits(hits);
        tag.setSize(size);
        tag.getTweetPoll().add(createTweetPollPublicated(
                Boolean.TRUE,
                Boolean.TRUE,
                new Date(),
                createUserAccount("juan carlos", createAccount()),
                createQuestion("Did you do the homework?", "YesNo")));
        getHashTagDao().saveOrUpdate(tag);
        assertNotNull(tag.getHashTagId());
     }

     /** HashTag hits domain. **/
     @Test
     public void testHashTagHits(){
        final Date hitDate = new Date();
        final String ipAddress = "";
        final String tagName = "programmer";
        final HashTagHits tagHits = new HashTagHits();
        tagHits.setHitDate(hitDate);
        tagHits.setIpAddress(ipAddress);
        tagHits.setUserAccount(createUserAccount("juan carlos", createAccount()));
        tagHits.setHashTagId(createHashTag(tagName));
        getHashTagDao().saveOrUpdate(tagHits);
        assertNotNull(tagHits.getHashTagId());
     }

     /** Dashboard domain. **/
     @Test
     public void testDashboard(){
        final Dashboard board = new Dashboard();
        board.setPageBoardName("First dashboard");
        board.setDescription("My first dashboard");
        board.setFavorite(Boolean.TRUE);
        board.setFavoriteCounter(1);
        board.setPageLayout("AAA");
        board.setBoardSequence(1);
        board.setUserBoard(createUserAccount("juan carlos", createAccount()));
        getDashboardDao().saveOrUpdate(board);
      }

     /** Gadget domain **/
     @Test
     public void testGadget(){
        final Gadget gadget = new Gadget();
        gadget.setGadgetName("Notifications");
        gadget.setGadgetType("Poll");
        getDashboardDao().saveOrUpdate(gadget);
     }

     /** Gadget Properties **/
     @Test
     public void testGadgetProperties(){
    	 final GadgetProperties gadgetProp = new GadgetProperties();
    	 gadgetProp.setGadgetPropName("maxResults");
    	 gadgetProp.setGadgetPropValue("10");
    	 gadgetProp.setUserAccount(createUserAccount("diana paola", createAccount()));
    	 gadgetProp.setGadget(createGadgetDefault());
    	 getDashboardDao().saveOrUpdate(gadgetProp);
     }
}
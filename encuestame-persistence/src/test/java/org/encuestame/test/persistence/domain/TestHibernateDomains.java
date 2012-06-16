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
package org.encuestame.test.persistence.domain;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.Client;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagRanking;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.question.CatQuestionCategory;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionColettion;
import org.encuestame.persistence.domain.question.QuestionDependenceSurvey;
import org.encuestame.persistence.domain.question.QuestionDependencies;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.enums.GadgetType;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.LayoutEnum;
import org.encuestame.utils.enums.Status;
import org.junit.Before;
import org.junit.Test;

/**
 * Hibernate Domains.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 16/12/2009
 * @version $Id$
 **/
public class TestHibernateDomains extends AbstractBase{
	  
	private Hit initHit;
	
	private Poll initPoll;
	
	private Survey initSurvey;
	
	private UserAccount initUser;
	
	private Account initAccount; 
	
	private TweetPoll initTweetPoll;
	
	private Question initQuestion;
	
	private HashTag initHashTag;
	
	private HitCategory hitCat  = HitCategory.VISIT;
	
	/**
	 * 
	 */
	@Before
	public void initData(){
	 
		initHashTag = createHashTag("health");
		initQuestion = createQuestion(
				"Who will win the Spain SuperCup?", "");
		initAccount = createAccount();
		initUser = createUserAccount("Betty", initAccount);
		initPoll = createDefaultPoll(initQuestion, initUser);
		initSurvey = createDefaultSurvey(initAccount);
		initTweetPoll = createPublishedTweetPoll(initQuestion, initUser);
		
		initHit = createHit(initTweetPoll, initPoll, initSurvey, initHashTag, "192.168.1.1"); 
 
	}

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
         surveys.setOwner(createAccount());
         surveys.setTicket(1);
         surveys.setStartDate(new Date());
         surveys.setEndDate(new Date());
         surveys.setDateInterview(new Date());
         surveys.setComplete("y");
         //surveys.setSurveysfolder(createSurveyFolders());
         getSurveyDaoImp().saveOrUpdate(surveys);
         assertNotNull(surveys.getSid());
     }

    /**
     *
     */
     @Test
     public void testLocationFolder(){
         final Account account = createAccount();
         final GeoPointFolder geoPointFolder = new GeoPointFolder();
         geoPointFolder.setFolderType(GeoPointFolderType.GROUPING);
         geoPointFolder.setFolderName("test folder");
         geoPointFolder.setUsers(account);
         geoPointFolder.setCreatedAt(Calendar.getInstance().getTime());
         geoPointFolder.setCreatedBy(createUserAccount("juan carlos", account));
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
         final SurveyFormat mySurveyFormat =  createSurveyFormat("Schools",new Date());
         final Survey mySurvey = createSurvey("", new Date(), new Date(), createAccount(), new Date(),mySurveyFormat,"FirstSurvey", new Date());
         questionDepSurvey.setSurvey(mySurvey);
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
     
     /** Dashboard domain. **/
     @Test
     public void testDashboard(){
        final Dashboard board = new Dashboard();
        board.setPageBoardName("First dashboard");
        board.setDescription("My first dashboard");
        board.setFavorite(Boolean.TRUE);
        board.setFavoriteCounter(1);
        board.setPageLayout(LayoutEnum.AAA_COLUMNS);
        board.setBoardSequence(1);
        board.setUserBoard(createUserAccount("juan carlos", createAccount()));
        getDashboardDao().saveOrUpdate(board);
      }

     /** Gadget domain **/
     @Test
     public void testGadget(){
        final Gadget gadget = new Gadget();
        final UserAccount user = createUserAccount("diana paola", createAccount());
        gadget.setGadgetName("Notifications");
        gadget.setGadgetType(GadgetType.ACTIVITY_STREAM);
        gadget.setGadgetColor("blue");
        gadget.setGadgetColumn(1);
        gadget.setGadgetPosition(1);
        gadget.setDashboard(createDashboardDefault(user));
        getDashboardDao().saveOrUpdate(gadget);
     }

     /** Gadget Properties **/
     @Test
     public void testGadgetProperties(){
         final UserAccount user = createUserAccount("diana paola", createAccount());
         final Dashboard board = createDashboardDefault(user);
         final GadgetProperties gadgetProp = new GadgetProperties();
         gadgetProp.setGadgetPropName("maxResults");
         gadgetProp.setGadgetPropValue("10");
         gadgetProp.setUserAccount(user);
         gadgetProp.setGadget(createGadgetDefault(board));
         getDashboardDao().saveOrUpdate(gadgetProp);
     }

     /** Test Comments **/
     @Test
     public void testComments(){
         final Comment comments = new Comment();
         final UserAccount user = createUserAccount("diana", createAccount());
         final Question question = createQuestion("Who I am?", "");
         final TweetPoll tpoll = createPublishedTweetPoll(user.getAccount(), question);
         comments.setComment("First comment");
         comments.setCreatedAt(new Date());
         comments.setLikeVote(1L);
         comments.setDislikeVote(2L);
          comments.setUser(user);
         comments.setTweetPoll(tpoll);
         getCommentsOperations().saveOrUpdate(comments);
     }

     /** Test Poll. **/
     public void testPoll(){
         final Poll poll = new Poll();
         final Question question = createQuestion("Where do you live?", "");
         final UserAccount user = createUserAccount("diana", createAccount());
         final PollFolder pollFolder = createPollFolder("My polls", user);
         poll.setPollCompleted(null);
         poll.setCreatedAt(Calendar.getInstance().getTime());
         poll.setEndDate(Calendar.getInstance().getTime());
         poll.setUpdatedDate(Calendar.getInstance().getTime());
         poll.setPollHash(RandomStringUtils.randomAlphanumeric(5));
         poll.setQuestion(question);
         poll.setEditorOwner(user);
         poll.setPublish(Boolean.TRUE);
         poll.setPollFolder(pollFolder);
         poll.setUpdatedDate(null);
         getPollDao().saveOrUpdate(poll);
     }

     /** Test item vote. **/
     @Test
     public void testRate(){
         final Question question = createQuestion("Who will win the Champions League match today?", "");
         final Account account = createAccount();
         final UserAccount user = createUserAccount("carlos", account);
         final TweetPoll tpoll = createPublishedTweetPoll(account, question);
         final AccessRate rateItem = new AccessRate();
         rateItem.setRate(Boolean.TRUE);
         rateItem.setUser(user);
         rateItem.setTweetPoll(tpoll);
         rateItem.setPoll(null);
         rateItem.setSurvey(null);
         rateItem.setUpdatedDate(Calendar.getInstance().getTime());
    }

    /**
     *  Test {@link HashTagRanking} Domain.
     */
    @Test
    public void testHashTagRanking(){
        final HashTagRanking tagRanking = new HashTagRanking();
        final HashTag tag = createHashTag("health");
        tagRanking.setHashTag(tag);
        tagRanking.setAverage(25D);
        tagRanking.setRankingDate(new Date());
        getHashTagDao().saveOrUpdate(tagRanking);
    }
    
	/**
	 * Test {@link Hit} Domain.
	 */
	@Test
	public void testHit() {
		final Hit hit = new Hit(); 
		hit.setHashTag(this.initHashTag);
		hit.setHitCategory(hitCat);
		hit.setHitDate(new Date());
		hit.setIpAddress("");
		hit.setPoll(initPoll);
		hit.setSurvey(initSurvey);
		hit.setTweetPoll(initTweetPoll);
		hit.setUserAccount(initUser);
		getHashTagDao().saveOrUpdate(hit);
		assertNotNull(hit.getId());
	}
	
	/**
	 * Test {@link TweetPoll} Domain.
	 */
	@Test
	public void testTweetPoll() {
		final TweetPoll tweetPoll = new TweetPoll();

		final HashTag tag1 = createHashTag("education");
		final TweetPollFolder tpFolder = createTweetPollFolder("personal",
				initUser);

		tweetPoll.setAllowLiveResults(Boolean.TRUE);
		tweetPoll.setAllowRepatedVotes(Boolean.FALSE);
		tweetPoll.setCaptcha(Boolean.TRUE);
		tweetPoll.setCloseNotification(Boolean.FALSE);
		tweetPoll.setCompleted(Boolean.FALSE);
		tweetPoll.setCreateDate(new Date());
		tweetPoll.setDateLimit(Boolean.FALSE);
		tweetPoll.setDateLimited(null);
		tweetPoll.setDislikeVote(2L);
		tweetPoll.setEditorOwner(initUser);
		tweetPoll.setFavourites(Boolean.FALSE);
		tweetPoll.getHashTags().add(initHashTag);
		tweetPoll.getHashTags().add(tag1);
		tweetPoll.setHits(20L);
		tweetPoll.setLikeVote(18L);
		tweetPoll.setLimitVotes(90);
		tweetPoll.setLimitVotesEnabled(Boolean.TRUE);
		tweetPoll.setLocationLatitude(25F);
		tweetPoll.setMaxRepeatedVotes(10);
		tweetPoll.setLocationLongitude(50F);
		tweetPoll.setNumbervotes(10L);
		tweetPoll.setPublishTweetPoll(Boolean.TRUE);
		tweetPoll.setQuestion(initQuestion);
		tweetPoll.setRelevance(40L);
		tweetPoll.setResultNotification(Boolean.TRUE);
		tweetPoll.setResumeLiveResults(Boolean.FALSE);
		tweetPoll.setResumeTweetPollDashBoard(Boolean.FALSE);
		tweetPoll.setScheduleDate(new Date());
		tweetPoll.setScheduleTweetPoll(Boolean.FALSE);
		tweetPoll.setTweetOwner(initAccount);
		tweetPoll.setTweetPollFolder(tpFolder);
		tweetPoll.setUpdatedDate(new Date());
		getTweetPoll().saveOrUpdate(tweetPoll);
		assertNotNull(tweetPoll.getTweetPollId());
	}
}
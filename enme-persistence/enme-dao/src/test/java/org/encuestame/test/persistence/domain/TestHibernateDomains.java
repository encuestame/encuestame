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
import org.encuestame.persistence.domain.EmailSubscribe;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagRanking;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.question.CatQuestionCategory;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionColettion;
import org.encuestame.persistence.domain.question.QuestionDependenceSurvey;
import org.encuestame.persistence.domain.question.QuestionDependencies;
import org.encuestame.persistence.domain.question.QuestionPreferences;
import org.encuestame.persistence.domain.question.QuestionSection;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.HelpPage;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount.SocialSupport;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveyResult;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.survey.SurveyTemporalResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollRate;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.enums.GadgetType;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.LayoutEnum;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.TypeAuth;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Hibernate Domains.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 16/12/2009
 **/
@Category(DefaultTest.class)
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

    private SocialAccount socialAccount;

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
        socialAccount = createDefaultSettedSocialAccount(initUser);

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
        assertNotNull(user.getCreatedAccount());
        assertNotNull(user.getEnabled());
        
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
         surveys.setIsHidden(Boolean.TRUE);
         surveys.setIsPasswordProtected(Boolean.TRUE);
         surveys.setPassword("VdyGw");
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
		final GeoPointFolder geoLocationFolder = createDefaultGeoPointFolder(
				" Polygon folder", this.initAccount);
         geoPointFolder.setFolderType(GeoPointFolderType.GROUPING);
         geoPointFolder.setFolderName("test folder");
         geoPointFolder.setUsers(account);
         geoPointFolder.setCreatedAt(Calendar.getInstance().getTime());
         geoPointFolder.setCreatedBy(createUserAccount("juan carlos", account));
         geoPointFolder.setSubLocationFolder(geoLocationFolder);
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
         email.setSubscribed(Boolean.TRUE);
         email.setCreated_at(new Date());
         email.setEmailAccount("juan@encuestame.org");
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
         catListEmails.setDescripcionList("Favorite emails");
         catListEmails.setListState("Available");
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
        assertNotNull(tag.getHashTag());
        assertNotNull(tag.getHits());
        assertNotNull(tag.getSize());
        assertNotNull(tag.getTweetPoll());
     }

     /** Dashboard domain. **/
     @Test
     public void testDashboard(){
        final Dashboard board = new Dashboard();
        board.setPageBoardName("First dashboard");
        board.setDescription("My first dashboard");
        board.setFavorite(Boolean.TRUE);
        board.setFavoriteCounter(1);
        board.setPageLayout(LayoutEnum.BA_BLOCK_COLUMN);
        board.setBoardSequence(1);
        board.setUserBoard(createUserAccount("juan carlos", createAccount()));
        board.setSelectedByDefault(Boolean.TRUE);
        getDashboardDao().saveOrUpdate(board);
        
        assertNotNull(board.getBoardId());
        assertNotNull(board.getDescription());
        assertNotNull(board.getFavorite());
        assertNotNull(board.getFavoriteCounter());
        assertNotNull(board.getPageLayout());
        assertNotNull(board.getBoardSequence());
        assertNotNull(board.getUserBoard());
        assertNotNull(board.getSelectedByDefault());
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
        
        assertNotNull(gadget.getGadgetName());
        assertNotNull(gadget.getGadgetType());
        assertNotNull(gadget.getGadgetColumn());
        assertNotNull(gadget.getGadgetPosition());
        assertNotNull(gadget.getGadgetColor());
        assertNotNull(gadget.getGadgetId());
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
         
         assertNotNull(gadgetProp.getPropertyId());
         assertNotNull(gadgetProp.getGadgetPropName());
         assertNotNull(gadgetProp.getGadgetPropValue());
         assertNotNull(gadgetProp.getUserAccount());
         assertNotNull(gadgetProp.getGadget());
     }

     /** Test Comments **/
     @Test
     public void testComments(){
         final Comment comments = new Comment();
         final UserAccount user = createUserAccount("diana", createAccount());
         final Question question = createQuestion("Who I am?", "");
         final TweetPoll tpoll = createPublishedTweetPoll(user, question);
         comments.setComment("First comment");
         comments.setCreatedAt(new Date());
         comments.setLikeVote(1L);
         comments.setDislikeVote(2L);
         comments.setUser(user);
         comments.setTweetPoll(tpoll);
         comments.setIsSpam(Boolean.FALSE);
         comments.setIsPublished(Boolean.FALSE);
         getCommentsOperations().saveOrUpdate(comments);
         
         assertNotNull(comments.getCommentId());
         assertNotNull(comments.getCreatedAt());
         assertNotNull(comments.getLikeVote());
         assertNotNull(comments.getDislikeVote());
         assertNotNull(comments.getUser());
         assertNotNull(comments.getTweetPoll());
         assertNotNull(comments.getIsSpam());
         assertNotNull(comments.getIsPublished());
     }

     /** Test Poll. **/
     public void testPoll(){
         final Poll poll = new Poll();
         final Question question = createQuestion("Where do you live?", "");
         final UserAccount user = createUserAccount("diana", createAccount());
         final PollFolder pollFolder = createPollFolder("My polls", user);
         poll.setPollCompleted(Boolean.FALSE);
         poll.setCreateDate(Calendar.getInstance().getTime());
         poll.setEndDate(Calendar.getInstance().getTime());
         poll.setUpdatedDate(Calendar.getInstance().getTime());
         poll.setPollHash(RandomStringUtils.randomAlphanumeric(5));
         poll.setQuestion(question);
         poll.setEditorOwner(user);
         poll.setPublish(Boolean.TRUE);
         poll.setPollFolder(pollFolder); 
         poll.setIsHidden(Boolean.TRUE);
         poll.setIsPasswordProtected(Boolean.TRUE);
         poll.setPassword("EvXaD");
         getPollDao().saveOrUpdate(poll);
         
         assertNotNull(poll.getPollId());
         assertNotNull(poll.getPollCompleted());
         assertNotNull(poll.getCreateDate());
         assertNotNull(poll.getEndDate());
         assertNotNull(poll.getUpdatedDate());
         assertNotNull(poll.getPollHash());
         assertNotNull(poll.getQuestion());
         assertNotNull(poll.getEditorOwner());
         assertNotNull(poll.getPublish());
         assertNotNull(poll.getPollFolder());
         assertNotNull(poll.getPollFolder());
         assertNotNull(poll.getIsHidden());
         assertNotNull(poll.getIsPasswordProtected());
         assertNotNull(poll.getPassword()); 
         
     }

     /** Test item vote. **/
     @Test
     public void testRate(){
//         final Question question = createQuestion("Who will win the Champions League match today?", "");
//         final Account account = createAccount();
//         final UserAccount user = createUserAccount("carlos", account);
//         final TweetPoll tpoll = createPublishedTweetPoll(user, question);
//         final Comment comment = createDefaultTweetPollComment("my first comment", tpoll, user);
//         final AccessRate rateItem = new AccessRate();
//         rateItem.setRate(Boolean.TRUE);
//         rateItem.setUser(user);
//         rateItem.setTweetPoll(tpoll);
//         rateItem.setPoll(null);
//         rateItem.setSurvey(null);
//         rateItem.setUpdatedDate(Calendar.getInstance().getTime());
//         rateItem.setComments(comment);
//         getAccountDao().saveOrUpdate(rateItem);
         
//         assertNotNull(rateItem.getRateId());
//         assertNotNull(rateItem.getRate());
//         assertNotNull(rateItem.getUser());
//         assertNotNull(rateItem.getTweetPoll());
//         assertNotNull(rateItem.getPoll());
//         assertNotNull(rateItem.getSurvey());
//         assertNotNull(rateItem.getUpdatedDate());
//         assertNotNull(rateItem.getComments());

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
        
        assertNotNull(tagRanking.getRankId());
        assertNotNull(tagRanking.getHashTag());
        assertNotNull(tagRanking.getAverage());
        assertNotNull(tagRanking.getRankingDate());
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
        assertNotNull(hit.getHashTag());
        assertNotNull(hit.getHitCategory());
        assertNotNull(hit.getHitDate());
        assertNotNull(hit.getIpAddress());
        assertNotNull(hit.getPoll());
        assertNotNull(hit.getSurvey());
        assertNotNull(hit.getTweetPoll());
        assertNotNull(hit.getUserAccount());
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
        tweetPoll.setDateLimited(new Date());
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
        assertNotNull(tweetPoll.getAllowLiveResults());
        assertNotNull(tweetPoll.getAllowRepatedVotes());
        assertNotNull(tweetPoll.getCaptcha());
        assertNotNull(tweetPoll.getCloseNotification());
        assertNotNull(tweetPoll.getCompleted());
        assertNotNull(tweetPoll.getCreateDate());
        assertNotNull(tweetPoll.getDateLimit());
        assertNotNull(tweetPoll.getDateLimited());
        assertNotNull(tweetPoll.getDislikeVote());
        assertNotNull(tweetPoll.getEditorOwner());
        assertNotNull(tweetPoll.getFavourites());
        assertNotNull(tweetPoll.getHashTags());
        assertNotNull(tweetPoll.getHits());
        assertNotNull(tweetPoll.getLikeVote());
        assertNotNull(tweetPoll.getLimitVotes());
        assertNotNull(tweetPoll.getLimitVotesEnabled());
        assertNotNull(tweetPoll.getLocationLatitude());
        assertNotNull(tweetPoll.getNumbervotes());
        assertNotNull(tweetPoll.getPublishTweetPoll());
        assertNotNull(tweetPoll.getQuestion());
        assertNotNull(tweetPoll.getRelevance());
        assertNotNull(tweetPoll.getResultNotification());
        assertNotNull(tweetPoll.getResumeLiveResults());
        assertNotNull(tweetPoll.getResumeTweetPollDashBoard());
        assertNotNull(tweetPoll.getScheduleDate());
        assertNotNull(tweetPoll.getScheduleTweetPoll());
        assertNotNull(tweetPoll.getTweetOwner());
        assertNotNull(tweetPoll.getUpdatedDate());
    }

    /**
     * Test {@link QuestionPreferences} domain.
     */
    @Test
    public void testQuestionPreferences() {
        final QuestionPreferences preference = new QuestionPreferences();
        preference.setQuestion(this.initQuestion);
        preference.setPreference("Order");
        preference.setPreferenceValue("Desc");
        getQuestionDaoImp().saveOrUpdate(preference);
        assertNotNull(preference.getQuestion());
        assertNotNull(preference.getPreference());
        assertNotNull(preference.getPreferenceValue());
    }

    /**
     * Test {@link SurveyResult} domain.
     */
    @Test
    public void testSurveyResult() {
        final SurveyResult result = new SurveyResult();
        final QuestionAnswer qAnswers = createQuestionAnswer("Spain",
                this.initQuestion, "ORFQT29");
        result.setAnswer(qAnswers);
        result.setQuestion(this.initQuestion);
        result.setSurvey(this.initSurvey);
        result.setTxtResponse("20");
        getSurveyDaoImp().saveOrUpdate(result);
        assertNotNull(result.getAnswer());
        assertNotNull(result.getQuestion());
		assertNotNull(result.getTxtResponse());
		assertNotNull(result.getSurvey());
    }

    /**
     * Subscribe email.
     */
    @Test
	public void testEmailSubscribe() {
		final EmailSubscribe subscribe = new EmailSubscribe();
		final EmailList emailList = createListEmails(this.initAccount, "VIP Email List", new Date());
		final Email email = createEmails("juan@encuestame.org", emailList);
		subscribe.setHashCode("CKDpka8834s");
		//subscribe.setIdSubscribe(5L);
		subscribe.setList(emailList);
		subscribe.setEmail(email);
		getCatEmailDao().saveOrUpdate(subscribe);
		assertNotNull(subscribe.getHashCode());
		assertNotNull(subscribe.getList());
		assertNotNull(subscribe.getEmail());
		assertNotNull(subscribe.getIdSubscribe());
	}

	/**
	 * Create {@link TweetPollRate}
	 */
	@Ignore
	@Test
	public void TweetPollRate() {
		final TweetPollRate tpollRate = new TweetPollRate();
		tpollRate.setRate(Boolean.FALSE);
		tpollRate.setTweetPoll(this.initTweetPoll);
	//	tpollRate.setTweetPollRateId(2L);
		tpollRate.setUser(this.initUser);
		getTweetPoll().saveOrUpdate(tpollRate);
		assertNotNull(tpollRate.getRate());
		assertNotNull(tpollRate.getTweetPoll());
		assertNotNull(tpollRate.getTweetPollRateId());
		assertNotNull(tpollRate.getUser());
	}

    /**
     * Test {@link SurveyTemporalResult} domain.
     */
	@Test
    public void testSurveyTemporalResult() {
        final SurveyTemporalResult result = new SurveyTemporalResult();
        final QuestionAnswer qAnswers = createQuestionAnswer("Yes",
                this.initQuestion, "ORFQT31");
        result.setAnswer(qAnswers);
        result.setQuestion(this.initQuestion);
        result.setSurvey(this.initSurvey);
        result.setTxtResponse("3 Cups");
      //  result.setIdTempResult(1L);
        result.setHash("Jks193'5");
        getSurveyDaoImp().saveOrUpdate(result);
		assertNotNull(result.getAnswer());
		assertNotNull(result.getSurvey());
		assertNotNull(result.getTxtResponse());
		assertNotNull(result.getHash());
    }

	/**
	 * Test {@link TweetPollSavedPublishedStatus}.
	 */
	@Test
    public void testTweetPollSavedPublished() {
		final TweetPollSavedPublishedStatus tpollSaved = new TweetPollSavedPublishedStatus();
		tpollSaved.setApiType(SocialProvider.FACEBOOK);
		tpollSaved.setDescriptionStatus("my first tweetpoll published");
	//	tpollSaved.setId(2L);
		tpollSaved.setPoll(null);
		tpollSaved.setPublicationDateTweet(new Date());
		tpollSaved.setSocialAccount(this.socialAccount);
		tpollSaved.setStatus(Status.ACTIVE);
		tpollSaved.setSurvey(null);
		tpollSaved.setTweetContent("tweetcontent");
		tpollSaved.setTweetId("1L");
		tpollSaved.setTweetPoll(this.initTweetPoll);
		tpollSaved.toString();
		getSurveyDaoImp().saveOrUpdate(tpollSaved);
		assertNotNull(tpollSaved.getApiType());
		assertNotNull(tpollSaved.getDescriptionStatus());
		assertNotNull(tpollSaved.getId());
		assertNotNull(tpollSaved.getPublicationDateTweet());
		assertNotNull(tpollSaved.getSocialAccount());
		assertNotNull(tpollSaved.getStatus());
		assertNotNull(tpollSaved.getTweetContent());
		assertNotNull(tpollSaved.getTweetId());
		assertNotNull(tpollSaved.getTweetPoll());
	}


	/**
	 * Test {@link TweetPollSwitch}
	 */
	@Test
	public void testTweetPollSwitch() {
		final TweetPollSwitch tpollSwitch = new TweetPollSwitch();
		QuestionAnswer qAnswers = createQuestionAnswer("", this.initQuestion,
				"HxjG823Dm");
		tpollSwitch.setAnswers(qAnswers);
		tpollSwitch.setCodeTweet("cod9kd");
		tpollSwitch.setDateUpdated(new Date());
		tpollSwitch.setRelativeUrl("http://encuestame.org");
		tpollSwitch.setShortUrl("http://enme.gl");
		//tpollSwitch.setSwitchId(2L);
		tpollSwitch.setTweetPoll(this.initTweetPoll);
		getSurveyDaoImp().saveOrUpdate(tpollSwitch);
		
		assertNotNull(tpollSwitch.getAnswers());
		assertNotNull(tpollSwitch.getDateUpdated());
		assertNotNull(tpollSwitch.getCodeTweet());
		assertNotNull(tpollSwitch.getRelativeUrl());
		assertNotNull(tpollSwitch.getShortUrl());
		assertNotNull(tpollSwitch.getSwitchId());
		assertNotNull(tpollSwitch.getTweetPoll());
	}

	/**
	 * Test {@link QuestionSection}.
	 */
	@Ignore
	@Test
	@Deprecated
	public void testQuestionSection(){
		final QuestionSection qSection = new QuestionSection();
		final SurveySection section = createSurveySection("Personal Information");
		qSection.setQuestion(this.initQuestion);
		//qSection.setQuestionSectionId(1L);
		qSection.setSurveySection(section);
		getSurveyDaoImp().saveOrUpdate(qSection);
		assertNotNull(qSection.getQuestion());
		assertNotNull(qSection.getQuestionSectionId());
		assertNotNull(qSection.getSurveySection());
	}
	
	/**
	 * Test {@link SocialAccount} domain.
	 */
	@Test
	public void testSocialAccount(){
		final SocialAccount socialAccount = new SocialAccount(); 
		socialAccount.setAccessToken("accessTok");
		socialAccount.setAccount(this.initAccount);
		socialAccount.setAccounType(SocialProvider.FACEBOOK);
		socialAccount.setAddedAccount(new Date());
		socialAccount.setApplicationKey(3L);
		socialAccount.setDefaultSelected(Boolean.TRUE);
		socialAccount.setDescriptionProfile("Personal profile");
		socialAccount.setEmail("profile@encuestame.org");
		socialAccount.setExpires("exp");
		//socialAccount.setId(id);
		socialAccount.setPrictureUrl("www.encuestame.org/profile.jpg");
		socialAccount.setProfilePictureUrl("http://abs.twimg.com/sticky/default_profile_images");
		socialAccount.setProfileThumbnailPictureUrl("www.encuestame.org/profile-thumb.jpg");
		socialAccount.setProfileUrl("www.encuestame.org/profile");
		socialAccount.setPublicProfileUrl("http://www.twitter.com/profile");
		socialAccount.setRealName("Profile Muster");
		socialAccount.setRefreshToken("tokrefresh");
		socialAccount.setSecretToken("secToken");
		socialAccount.setSocialAccountName("Profile Nick");
		socialAccount.setSocialProfileId("socialId");
		socialAccount.setSupport(SocialSupport.PUBLISH_SUPPORT);
		socialAccount.setType(TypeAuth.OAUTH1);
		socialAccount.setUpgradedCredentials(new Date());
		socialAccount.setUserOwner(this.initUser);
		socialAccount.setVerfied(Boolean.TRUE); 
		getAccountDao().saveOrUpdate(socialAccount);
		
		////  
		assertNotNull(socialAccount.getAccessToken());
		assertNotNull(socialAccount.getAccount());
		assertNotNull(socialAccount.getAccounType());
		assertNotNull(socialAccount.getAddedAccount());
		assertNotNull(socialAccount.getApplicationKey());
		assertNotNull(socialAccount.getDefaultSelected());
		assertNotNull(socialAccount.getDescriptionProfile());
		assertNotNull(socialAccount.getEmail());
		assertNotNull(socialAccount.getExpires());
		assertNotNull(socialAccount.getPrictureUrl());
		assertNotNull(socialAccount.getProfilePictureUrl());
		assertNotNull(socialAccount.getProfileThumbnailPictureUrl());
		assertNotNull(socialAccount.getProfileUrl());
		assertNotNull(socialAccount.getPublicProfileUrl());
		assertNotNull(socialAccount.getRealName());
		assertNotNull(socialAccount.getRefreshToken());
		assertNotNull(socialAccount.getSecretToken());
		assertNotNull(socialAccount.getSocialAccountName());
		assertNotNull(socialAccount.getSocialProfileId());
		assertNotNull(socialAccount.getSupport());
		assertNotNull(socialAccount.getType());
		assertNotNull(socialAccount.getUpgradedCredentials());
		assertNotNull(socialAccount.getUserOwner());
		assertNotNull(socialAccount.getVerfied());  
	}
	
	/**
	 * Test {@link HelpPage} domain.
	 */
	@Test
	public void testHelpPage(){
		final HelpPage help = new HelpPage();
		//help.setHelpPageId(helpPageId);
		help.setPagePath("/user/dashboard");
		help.setUserAccount(this.initUser);
		getAccountDao().saveOrUpdate(help);
		
		assertNotNull(help.getHelpPageId());
		assertNotNull(help.getPagePath());
		assertNotNull(help.getUserAccount()); 
	}
	
	/**
	 * Test {@link Schedule} domain.
	 */
	public void testSchedule(){
		final Schedule schedule = new Schedule();
		schedule.setIdpub(null);
		schedule.setPoll(this.initPoll);
		schedule.setPublicationDate(new Date());
		schedule.setPublishAttempts(2);
		schedule.setScheduleDate(new Date());
		schedule.setSocialAccount(this.socialAccount);
		schedule.setStatus(Status.ACTIVE);
		schedule.setSurvey(this.initSurvey);
		schedule.setTpoll(this.initTweetPoll);
		schedule.setTpollSavedPublished(this.createPollSavedPublishedStatus(this.initPoll, "tweetId", this.socialAccount, "tweetText")); 
		schedule.setTweetText("TweetText");
		schedule.setTypeSearch(TypeSearchResult.POLL);
		getScheduleDao().saveOrUpdate(schedule);
		
		assertNotNull(schedule.getIdpub());
		assertNotNull(schedule.getPoll());
		assertNotNull(schedule.getPublicationDate());
		assertNotNull(schedule.getPublishAttempts());
		assertNotNull(schedule.getScheduleDate());
		assertNotNull(schedule.getSocialAccount());
		assertNotNull(schedule.getStatus());
		assertNotNull(schedule.getSurvey());
		assertNotNull(schedule.getTpoll());
		assertNotNull(schedule.getTpollSavedPublished());
		assertNotNull(schedule.getTweetText());
		assertNotNull(schedule.getTypeSearch()); 
	} 
	
	/**
	 * Test {@link Notification} domain.
	 */
	public void testNotification(){
		final Notification notification = new Notification();
		notification.setAccount(this.initAccount);
		notification.setAdditionalDescription("New Poll has been created");
		notification.setCreated(new Date());
		notification.setDescription(NotificationEnum.POLL_CREATED);
		notification.setGroup(Boolean.TRUE);
		notification.setReaded(Boolean.FALSE);
		notification.setUrlReference("https://twitter.com/demo3/status/"); 
		getNotification().saveOrUpdate(notification);
		
		assertNotNull(notification.getNotificationId());
		assertNotNull(notification.getAccount());
		assertNotNull(notification.getAdditionalDescription());
		assertNotNull(notification.getCreated());
		assertNotNull(notification.getDescription());
		assertNotNull(notification.getGroup());
		assertNotNull(notification.getReaded());
		assertNotNull(notification.getUrlReference()); 
	}
}
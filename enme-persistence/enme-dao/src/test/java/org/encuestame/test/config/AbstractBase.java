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
package org.encuestame.test.config;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.encuestame.persistence.dao.CommentsOperations;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.dao.IClientDao;
import org.encuestame.persistence.dao.IDashboardDao;
import org.encuestame.persistence.dao.IEmail;
import org.encuestame.persistence.dao.IFrontEndDao;
import org.encuestame.persistence.dao.IGeoPoint;
import org.encuestame.persistence.dao.IGeoPointTypeDao;
import org.encuestame.persistence.dao.IGroupDao;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.IPermissionDao;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.dao.IProjectDao;
import org.encuestame.persistence.dao.IQuestionDao;
import org.encuestame.persistence.dao.IScheduled;
import org.encuestame.persistence.dao.ISurvey;
import org.encuestame.persistence.dao.ISurveyFormatDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.dao.imp.ClientDao;
import org.encuestame.persistence.dao.imp.DashboardDao;
import org.encuestame.persistence.dao.imp.EmailDao;
import org.encuestame.persistence.dao.imp.FrontEndDao;
import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.dao.imp.PollDao;
import org.encuestame.persistence.dao.imp.ScheduleDao;
import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.AbstractSurvey.CustomFinalMessage;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.Attachment;
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
import org.encuestame.persistence.domain.Project.Priority;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionAnswer.AnswerType;
import org.encuestame.persistence.domain.question.QuestionColettion;
import org.encuestame.persistence.domain.question.QuestionPreferences;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Group.Type;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveyResult;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.PictureUtils;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.enums.GadgetType;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.LayoutEnum;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.stats.ItemStatDetail;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Base Class to Test Cases.
 * @author Picado, Juan juanATencuestame.org
 * @since October 15, 2009
 */

public abstract class AbstractBase extends AbstractConfigurationBase{

    /**
     * Hibernate Template.
     */
    @Autowired
    private HibernateTemplate hibernateTemplate;

     /** SurveyFormat  Dao.**/
    @Autowired
    private ISurveyFormatDao surveyformatDaoImp;

    /** User Security Dao.**/
    @Autowired
    private IAccountDao accountDao;

    /**Group Security Dao.**/
    @Autowired
    private IGroupDao groupDaoImp;

    /** Security Permissions Dao.**/
    @Autowired
    private IPermissionDao permissionDaoImp;

    /** Catalog Location Dao.**/
    @Autowired
    private IGeoPoint geoPointDao;

    /** Project Dao Imp.**/
    @Autowired
    private IProjectDao projectDaoImp;

    /** Survey Dao Imp.**/
    @Autowired
    private ISurvey surveyDaoImp;

    /** Question Dao Imp.**/
    @Autowired
    private IQuestionDao questionDaoImp;

    /** Catalog Location Type Dao.**/
    @Autowired
    private IGeoPointTypeDao geoPointTypeDao;

    /** {@link ClientDao}. **/
    @Autowired
    private IClientDao clientDao;

    /** {@link TweetPollDao}. **/
    @Autowired
    private ITweetPoll iTweetPoll;

    /** {@link PollDao}. **/
    @Autowired
    private IPoll pollDao;

    /** {@link EmailDao}. **/
    @Autowired
    private IEmail emailDao;

    /** {@link Notification}. **/
    @Autowired
    private INotification notificationDao;

    /** {@link CommentsOperations} **/
    @Autowired
    private CommentsOperations commentsOperations;

      /** Activate Notifications.**/
    private Boolean activateNotifications = false;

    /** Url Poll. **/
    public final String URLPOLL = "http://www.encuestame.org";

    /** {@link HashTagDao} **/
    @Autowired
    private IHashTagDao hashTagDao;

    /** {@link FrontEndDao} **/
    @Autowired
    private IFrontEndDao frontEndDao;

    /** {@link DashboardDao} **/
    @Autowired
    private IDashboardDao dashboardDao;

    /** {@link ScheduleDao **/
    @Autowired
    private IScheduled scheduleDao;

    /**
     * Generate a Long random value.
     * @return
     */
    private Long randomLongGenerator() {
         Random randomno = new Random();
         long value = randomno.nextLong();
         return value;
    }

    /**
     * Get Property.
     * @param property
     * @return
     */
    public String getProperty(final String property){
        Resource resource = new ClassPathResource("properties-test/test-config.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //log.debug("Property ["+property+"] value ["+props.getProperty(property)+"]");
        return props.getProperty(property);
    }


    /**
     * Getter.
     * @return the surveyFormatDaoImp
     */
    public ISurveyFormatDao getSurveyFormatDaoImp() {
        return surveyformatDaoImp;
    }

    /**
     * @param surveyformatDaoImp {@link ISurveyFormatDao}
     */
    public void setSurveyFormatDaoImp(final ISurveyFormatDao surveyformatDaoImp) {
        this.surveyformatDaoImp = surveyformatDaoImp;
    }

    /**
     * Force to push HIBERNATE domains saved to HIBERNATE SEARCH indexes.
     * This is useful to test full text session search on test cases.
     */
    public void flushIndexes(){
        final FullTextSession fullTextSession = Search.getFullTextSession(getHibernateTemplate().getSessionFactory().getCurrentSession());
        fullTextSession.flushToIndexes();
    }

    /**
     * @return the userDao
     */
    public IAccountDao getAccountDao() {
        return accountDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setAccountDao(final IAccountDao userDao) {
        this.accountDao = userDao;
    }

    /**
     * @return {@link IGroupDao}
     */
    public IGroupDao getGroup(){
        return groupDaoImp;
    }

    /**
     * @param GroupDaoImp  {@link IGroupDao}
     */
    public void setgroupDao(final IGroupDao GroupDaoImp){
        this.groupDaoImp = groupDaoImp;
    }

    /**
     * @return the permissionDaoImp
     */
    public IPermissionDao getPermissionDaoImp() {
        return permissionDaoImp;
    }

    /**
     * @param permissionDaoImp the permissionDaoImp to set
     */
    public void setPermissionDaoImp(final IPermissionDao permissionDaoImp) {
        this.permissionDaoImp = permissionDaoImp;
    }

    /**
     * @return the groupDaoImp
     */
    public IGroupDao getGroupDaoImp() {
        return groupDaoImp;
    }

    /**
     * @param secGroupDaoImp the secGroupDaoImp to set
     */
    public void setGroupDaoImp(final IGroupDao groupDaoImp) {
        this.groupDaoImp = groupDaoImp;
    }

    /**
     * @return the projectDaoImp
     */
    public IProjectDao getProjectDaoImp() {
        return projectDaoImp;
    }

    /**
     * @param projectDaoImp the projectDaoImp to set
     */
    public void setProjectDaoImp(final IProjectDao projectDaoImp) {
        this.projectDaoImp = projectDaoImp;
    }

    /**
     * @return the surveyDaoImp
     */
    public ISurvey getSurveyDaoImp() {
        return surveyDaoImp;
    }

    /**
     * @param surveyDaoImp the surveyDaoImp to set
     */
    public void setSurveyDaoImp(final ISurvey surveyDaoImp) {
        this.surveyDaoImp = surveyDaoImp;
    }

    /**
     * @return the questionDaoImp
     */
    public IQuestionDao getQuestionDaoImp() {
        return questionDaoImp;
    }

    /**
     * @param questionDaoImp the questionDaoImp to set
     */
    public void setQuestionDaoImp(final IQuestionDao questionDaoImp) {
        this.questionDaoImp = questionDaoImp;
    }


    /**
     * @return the surveyformatDaoImp
     */
    public ISurveyFormatDao getSurveyformatDaoImp() {
        return surveyformatDaoImp;
    }

    /**
     * @param surveyformatDaoImp the surveyformatDaoImp to set
     */
    public void setSurveyformatDaoImp(ISurveyFormatDao surveyformatDaoImp) {
        this.surveyformatDaoImp = surveyformatDaoImp;
    }

    /**
     * @return the geoPointTypeDao
     */
    public IGeoPointTypeDao getGeoPointTypeDao() {
        return geoPointTypeDao;
    }

    /**
     * @param geoPointTypeDao the geoPointTypeDao to set
     */
    public void setGeoPointTypeDao(IGeoPointTypeDao geoPointTypeDao) {
        this.geoPointTypeDao = geoPointTypeDao;
    }



    /**
     * @return geoPointDao
     */
    public IGeoPoint getGeoPointDao() {
        return geoPointDao;
    }

    /**
     * @param geoPointDao geoPointDao
     */
    public void setGeoPointDao(final IGeoPoint geoPointDao) {
        this.geoPointDao = geoPointDao;
    }

    /**
     * @return {@link GeoPoint}
     */
    public IGeoPoint getGeoPoint() {
        return geoPointDao;
    }

    /**
     * @param geoPoint {@link GeoPoint}
     */
    public void setGeoPoint(final IGeoPoint geoPoint) {
        this.geoPointDao = geoPoint;
    }

    /**
     * @return {@link Poll}
     */
    public IPoll getPollDao() {
        return pollDao;
    }

    /**
     * @param poll the iPoll to set
     */
    public void setPollDao(final IPoll poll) {
        this.pollDao = poll;
    }

    /**
     * @return the catEmailDao
     */
    public IEmail getCatEmailDao() {
        return emailDao;
    }


    /**
     * @param catEmailDao the catEmailDao to set
     */
    public void setCatEmailDao(IEmail catEmailDao) {
        this.emailDao = catEmailDao;
    }



    /**
     * Helper to create poll
     * @return poll
     */
    public Poll createPoll(final Date createdAt,
            final Question question,
            final UserAccount userAccount,
            final Boolean pollCompleted,
            final Boolean pollPublish
            ){
        final String pollHash = RandomStringUtils.randomAlphabetic(18);
        final Poll poll = new Poll();
        poll.setCreateDate(createdAt);
        poll.setQuestion(question);
        poll.setPollHash(pollHash);         //should be unique
        poll.setEditorOwner(userAccount);
        poll.setOwner(userAccount.getAccount());
        poll.setPasswordRestrictions(false);
        poll.setIsHidden(false);
        poll.setPollCompleted(pollCompleted);
        poll.setPublish(pollPublish);
        poll.setShowComments(CommentOptions.APPROVE);
        poll.setRelevance(1L);
        getPollDao().saveOrUpdate(poll);
        return poll;
    }

    /**
     * Helper create default poll.
     * @param question
     * @param userAccount
     * @return
     */
    public Poll createDefaultPoll(final Question question,
            final UserAccount userAccount) {
        return this.createPoll(new Date(), question, userAccount, Boolean.TRUE,
                Boolean.TRUE);
    }

    /**
     * Helper to create Default private {@link Poll}
     * @param question
     * @param userAccount
     * @param isHidden
     * @param isPasswordProtected
     * @param password
     * @return
     */
    public Poll createDefaultPrivatePoll(final Question question,
			final UserAccount userAccount, final Boolean isHidden,
			final Boolean isPasswordProtected) {
		final Poll poll = this.createPoll(new Date(), question, userAccount,
				Boolean.TRUE, Boolean.TRUE);
		poll.setIsHidden(isHidden);
		poll.setIsPasswordProtected(isPasswordProtected);
		poll.setPassword(this.generatePassword());
		getPollDao().saveOrUpdate(poll);
		return poll;
    }

    /**
     * Helper create default poll.
     * @param question
     * @param userAccount
     * @param createdAt
     * @return
     */
    public Poll createDefaultPoll(final Question question,
            final UserAccount userAccount, final Date createdAt) {
        return this.createPoll(createdAt, question, userAccount, Boolean.TRUE,
                Boolean.TRUE);
    }

    /**
     * Helper to create a default {@link Poll} with privacy properties.
     * @param question
     * @param userAccount
     * @param createdAt
     * @param isHidden
     * @param isPasswordProtected
     * @param password
     * @return
     */
    public Poll createDefaulPollWithPrivacy(final Question question,
			final UserAccount userAccount,
			final Date createdAt,
			final Boolean isHidden,
			final Boolean isPasswordProtected) {
    	final Poll poll = createPoll(createdAt, question, userAccount, Boolean.TRUE,
                Boolean.TRUE);
    	poll.setIsHidden(isHidden);
    	poll.setIsPasswordProtected(isPasswordProtected);
    	poll.setPassword(this.generatePassword());
        return poll;
    }

    /**
     * Helper create default poll.
     * @param question
     * @param userAccount
     * @param createdAt
     * @param likeVote
     * @param dislikeVote
     * @return
     */
    public Poll createDefaultPoll(final Question question,
            final UserAccount userAccount, final Date createdAt, final Long likeVote, final Long dislikeVote) {
        final Poll poll = createPoll(createdAt, question, userAccount, Boolean.TRUE,
                Boolean.TRUE);
        poll.setLikeVote(likeVote);
        poll.setDislikeVote(dislikeVote);
        getPollDao().saveOrUpdate(poll);
        return poll;
    }

    /**
      * Helper to create poll
      * @param createdDate
      * @param question
      * @param hash
      * @param currentUser
      * @param pollCompleted
      * @return
      */
    public Poll createPoll(
            final Date createdDate,
            final Question question,
            final String hash,
            final UserAccount userAccount,
            final Boolean pollCompleted,
            final Boolean published){
        final Poll poll = new Poll();
        poll.setCreateDate(createdDate);
        poll.setCloseAfterDate(true);
        poll.setAdditionalInfo("additional");
        poll.setClosedDate(new Date());
        poll.setClosedQuota(100);
        poll.setCustomFinalMessage(CustomFinalMessage.FINALMESSAGE);
        poll.setCustomMessage(true);
        poll.setDislikeVote(1L);
        poll.setLikeVote(1L);
        poll.setEndDate(new Date());
        poll.setFavourites(true);
        poll.setNumbervotes(600L);
        poll.setQuestion(question);
        poll.setPollHash(hash);
        poll.setEditorOwner(userAccount);
        poll.setOwner(userAccount.getAccount());
        poll.setPollCompleted(pollCompleted);
        poll.setPublish(published);
        poll.setShowComments(CommentOptions.APPROVE);
        poll.setIsHidden(false);
        poll.setIsPasswordProtected(false);
        getPollDao().saveOrUpdate(poll);
        return poll;

    }

     /**
     * Helper to create Poll Result.
     * @param questionAnswer {@link QuestionAnswer}
     * @param poll {@link Poll}
     * @return state
     */
    public PollResult createPollResults(final QuestionAnswer questionAnswer, final Poll poll){
        final PollResult pollRes = new PollResult();
        pollRes.setAnswer(questionAnswer);
        pollRes.setIpaddress("127.0.0."+RandomStringUtils.random(10));
        pollRes.setPoll(poll);
        pollRes.setVotationDate(new Date());
        getPollDao().saveOrUpdate(pollRes);
        return pollRes;

    }

    /**
     * Create Default {@link PollResult}.
     * @param questionAnswer
     * @param poll
     * @param ip
     * @return
     */
    public PollResult createDefaultPollResults(
            final QuestionAnswer questionAnswer, final Poll poll,
            final String ip) {
        final PollResult result = this.createPollResults(questionAnswer, poll);
        result.setIpaddress(ip);
        getPollDao().saveOrUpdate(result);
        return result;
    }

    /**
     * Create project.
     * @param name Project's name
     * @param descProject Project Description
     * @param infoProject Informations's Project
     * @param user user
     * @return {@link Project}
     */
    public Project createProject(
            final String name,
            final String descProject,
            final String infoProject,
            final Account user) {
          final Project project = new Project();
          final Calendar start = Calendar.getInstance();
          final Calendar end = Calendar.getInstance();
          end.add(Calendar.MONTH, 4);
          project.setProjectDateFinish(end.getTime());
          project.setProjectDateStart(start.getTime());
          project.setProjectInfo(infoProject);
          project.setProjectName(RandomStringUtils.randomAscii(4)+"_name");
          project.setLead(createUserAccount("tes-"+RandomStringUtils.randomAscii(4), createAccount()));
          project.setProjectDescription(descProject);
          project.setProjectStatus(Status.ACTIVE);
          project.setPriority(Priority.MEDIUM);
          project.setHideProject(Boolean.FALSE);
          project.setPublished(Boolean.TRUE);
          project.setUsers(user);
          getProjectDaoImp().saveOrUpdate(project);
          return project;
    }

   /**
    * Create Attachment.
    * @param filename
    * @param uploadDate
    * @param project
    * @return Attachment data.
    */
    public Attachment createAttachment(
            final String filename,
            final Date uploadDate,
            final Project project
            ){
        final Attachment attachmentInfo = new Attachment();
        attachmentInfo.setFilename(filename);
        attachmentInfo.setUploadDate(uploadDate);
        attachmentInfo.setProjectAttachment(project);
        getProjectDaoImp().saveOrUpdate(attachmentInfo);
        return attachmentInfo;
    }

    /**
     * Create {@link Client}.
     * @param name name
     * @param project {@link Project}
     * @return {@link Client}
     */
    public Client createClient(final String name, final Project project){
        final Client client = new Client();
        client.setClientName(name);
        client.setProject(project);
        client.setClientEmail("");
        client.setClientDescription("");
        client.setClientFax("");
        client.setClientTelephone("");
        client.setClientTwitter("");
        client.setClientUrl("");
        getClientDao().saveOrUpdate(client);
        return client;
    }


    /**
     * Helper to create Secondary User.
     * @param name user name
     * @param secUser {@link Account}
     * @return state
     */
    public UserAccount createUserAccount(
            final String name,
            final Account account){
        return createUserAccount(name, name.replace(" ", "")+"."+RandomStringUtils.randomNumeric(6)+"@users.com", account);
    }

    public UserAccount createSecondaryUserGroup(
            final String name,
            final Account secUser,
            final Group group){
        return createSecondaryUserGroup(name, name.replace(" ", "")+"."+RandomStringUtils.randomNumeric(6)+"@users.com", secUser, group);
    }

    public GadgetProperties createGadgetProperties(final String name, final String value,
            final Gadget gadget,
            final UserAccount user){
        final GadgetProperties properties = new GadgetProperties();
        properties.setGadgetPropName(name);
        properties.setGadgetPropValue(value);
        properties.setUserAccount(user);
        properties.setGadget(gadget);
        getDashboardDao().saveOrUpdate(properties);
        return properties;
    }

    /**
     * Create gadget default.
     * @return
     */
    public Gadget createGadgetDefault(final Dashboard board){
        return this.createGadget("default", board);
    }

    /**
     * Create gadget.
     * @param name
     * @param type
     * @return
     */
    public Gadget createGadget(final String name, final Dashboard board){
        final Gadget gadget = new Gadget();
        gadget.setGadgetName(name);
        gadget.setGadgetType(GadgetType.getGadgetType("stream"));
        gadget.setGadgetColumn(2);
        gadget.setGadgetColor("default");
        gadget.setGadgetPosition(0);
        gadget.setDashboard(board);
        getDashboardDao().saveOrUpdate(gadget);
        return gadget;
    }

    /**
     * Create dashboard.
     * @param boardName
     * @param favorite
     * @param userAcc
     * @return
     */
    public Dashboard createDashboard(final String boardName, final Boolean favorite, final UserAccount userAcc){
        final Dashboard board = new Dashboard();
        board.setPageBoardName(boardName);
          board.setDescription("");
          board.setFavorite(favorite);
          board.setFavoriteCounter(1);
          board.setPageLayout(LayoutEnum.BA_BLOCK_COLUMN);
          board.setBoardSequence(1);
          board.setUserBoard(userAcc);
          getDashboardDao().saveOrUpdate(board);
        return board;
    }

    /**
     * Create dashboard default.
     * @param userAcc
     * @return
     */
    public Dashboard createDashboardDefault(final UserAccount userAcc){
        return this.createDashboard("Board default", Boolean.TRUE, userAcc);
    }

    /**
     * Create Secondary User.
     * @param name
     * @param email
     * @param secUser
     * @return
     */
    public UserAccount createUserAccount(
            final String name,
            final String email,
            final Account secUser) {
        final UserAccount user= new UserAccount();
        user.setCompleteName(name);
        user.setUsername(name);
        user.setPassword("12345");
        user.setUserEmail(email.trim());
        user.setEnjoyDate(new Date());
        user.setInviteCode("xxxxxxx");
        user.setAccount(secUser);
        user.setUserStatus(true);
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Create Secondary User.
     * @param name
     * @param email
     * @param secUser
     * @return
     */
    public UserAccount createSecondaryUserGroup(
            final String name,
            final String email,
            final Account secUser,
            final Group group){
        final UserAccount user= new UserAccount();
        user.setCompleteName(name);
        user.setUsername(name);
        user.setPassword("12345");
        user.setUserEmail(email);
        user.setEnjoyDate(new Date());
        user.setInviteCode("xxxxxxx");
        user.setAccount(secUser);
        user.setUserStatus(true);
        user.setGroup(group);
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Create account.
     * @return {@link Account}
     */
    public Account createAccount(){
        Account user = new Account();
        user.setEnabled(Boolean.TRUE);
        user.setCreatedAccount(new Date());
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Create account with customized enabled.
     * @param enabled cuztomized enabled.
     * @return {@link Account}.
     */
    public Account createAccount(final Boolean enabled){
       final Account account = this.createAccount();
       account.setEnabled(enabled);
       getAccountDao().saveOrUpdate(account);
       return account;
    }

    /**
     * Create user account.
     * @param status
     * @param name
     * @param account
     * @return
     */
    public UserAccount createUserAccount(final Boolean status, final Date createdAt , final String name, final Account account){
        final UserAccount userAcc = this.createUserAccount(name, account);
        userAcc.setEnjoyDate(createdAt);
        userAcc.setUserStatus(status);
        getAccountDao().saveOrUpdate(userAcc);
        return userAcc;
     }

    /**
     * Create User.
     * @param twitterAccount account
     * @param twitterPassword password
     * @return {@link Account}
     */
    public Account createUser(final String twitterAccount, final String twitterPassword){
        Account user = new Account();
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Helper to create LocationType.
     * @param locationTypeName locationTypeName
     * @return locationType
     */

    public GeoPointType createGeoPointType(final String locationTypeName){
        final GeoPointType catLocatType = new GeoPointType();
        catLocatType.setLocationTypeDescription(locationTypeName);
        catLocatType.setLocationTypeLevel(1);
        catLocatType.setUsers(createAccount());
        getGeoPointTypeDao().saveOrUpdate(catLocatType);
        return catLocatType;
    }


    /**
     * Helper to create GeoPoint.
     * @param locDescription locDescription
     * @param locTypeName locTypeName
     * @param Level Level
     * @return location {@link GeoPointFolder}.
     */
    public GeoPoint createGeoPoint(
                       final String locDescription,
                       final String locTypeName,
                       final Integer Level,
                       final Account secUsers,
                       final GeoPointFolder geoPointFolder){
        final GeoPoint location = new GeoPoint();
        location.setLocationStatus(Status.ACTIVE);
        location.setLocationDescription(locDescription);
        location.setLocationLatitude(2F);
        location.setAccount(secUsers);
        location.setGeoPointFolder(geoPointFolder);
        location.setLocationLongitude(3F);
        location.setTidtype(createGeoPointType(locTypeName));
        getGeoPointDao().saveOrUpdate(location);
      return location;
    }

    /**
     * Create Default Location.
     * @param locDescription description.
     * @param locTypeName type
     * @param Level level
     * @param secUsers {@link Account}.
     * @return
     */
    public GeoPoint createGeoPoint(
            final String locDescription,
            final String locTypeName,
            final Integer Level,
            final Account secUsers){
    return this.createGeoPoint(locDescription, locTypeName, Level, secUsers, null);
    }


    /**
     * Helper to create Group.
     * @param groupname user name
     * @return state
     */
    public Group createGroups(final String groupname){
        return createGroups(groupname, this.createAccount());
    }

    public Group createGroups(final String groupname, final Account secUser){
        final Group group = new Group();
        group.setAccount(secUser);
        group.setGroupName(groupname);
        group.setIdState(1L);
        group.setGroupType(Type.SECURITY);
        group.setGroupDescriptionInfo("First Group");
        getGroup().saveOrUpdate(group);
        return group;
    }

    /**
     * Helper to create Permission.
     * @param permissionName name
     * @return Permission
     */
    public Permission createPermission(final String permissionName){
        final Permission permission = new Permission();
        permission.setPermissionDescription(permissionName);
        permission.setPermission(EnMePermission.getPermissionString(permissionName));
        getPermissionDaoImp().saveOrUpdate(permission);
        return permission;
    }

    /**
     * Helper to add permission to user.
     * @param user user
     * @param permission permission
     */
    public void addPermissionToUser(final Account user, final Permission permission){
       // final SecUserPermission userPerId = new SecUserPermission();
       // final SecUserPermissionId id = new SecUserPermissionId();
       /// id.setIdPermission(permission.getIdPermission());
       // id.setUid(user.getUid());
       // userPerId.setId(id);
        //userPerId.setState(true);
       // getSecUserDao().saveOrUpdate(userPerId);
    }

    /**
     * Helper to add user to group.
     * @param user user
     * @param group group
     */
    public void addGroupUser(
            final UserAccount user,
            final Group group)
    {
       /* final SecGroupUserId id = new SecGroupUserId();
        id.setGroupId(group.getGroupId());
        id.setUid(user.getUid());
        final SecGroupUser secGroupUser = new SecGroupUser();
        secGroupUser.setSecGroupUserId(id);
         secGroupUser.setSecUsers(user);
        secGroupUser.setSecGroups(group);*/
       // getSecUserDao().assingGroupToUser(secGroupUser);
    }

    /**
     * Helper permission to group.
     * @param permission permission
     * @param group group
     */
    public void addPermissionToGroup(
            final Permission permission,
            final Group group)
    {
      //  final SecGroupPermissionId groupPermissionId = new SecGroupPermissionId();
       //// groupPermissionId.setGroupId(group.getGroupId());
     //   groupPermissionId.setIdPermission(permission.getIdPermission());
       // final SecGroupPermission groupPermission = new SecGroupPermission();
       // groupPermission.setId(groupPermissionId);
       // groupPermission.setSecGroups(group);
       // groupPermission.setSecPermission(permission);
       // getSecGroup().saveOrUpdate(groupPermission);
    }

    /**
     * Create question.
     * @param question question
     * @param pattern pattern
     * @return {@link Question}
     */
    public Question createQuestion(
            final String question,
            final String pattern) {
        final Question questions = new Question();
        questions.setQidKey("1");
        questions.setQuestion(question);
        questions.setSlugQuestion(RestFullUtil.slugify(question));
        questions.setSharedQuestion(Boolean.TRUE);
        questions.setAccountQuestion(this.createAccount());
        getQuestionDaoImp().saveOrUpdate(questions);
        return questions;
    }

    public Question addQuestionSection(
            final String question,
            final SurveySection section,
            final Account account){
        final Question questions = new Question();
        questions.setQidKey("1");
        questions.setQuestion(question);
        questions.setSlugQuestion(question.replace(" ", "-"));
        questions.setSharedQuestion(Boolean.TRUE);
        questions.setAccountQuestion(account);
        questions.setSection(section);
        getQuestionDaoImp().saveOrUpdate(questions);
        return questions;
    }

    /**
     * Create Default Question.
     * @param questionName
     * @return
     */
    public Question createDefaultQuestion(final String questionName){
        return this.createQuestion(questionName, "radio");

    }

    /**
     * Create Question.
     * @param questionName
     * @param user
     * @return
     */
    public Question createQuestion(final String questionName, final Account user){
        final Question question =  this.createQuestion(questionName, "pattern");
        question.setAccountQuestion(user);
        getQuestionDaoImp().saveOrUpdate(question);
        return question;
    }

    /**
     * Create {@link Question}.
     * @param questionName
     * @param user
     * @param createDate
     * @param hits
     * @return
     */
    public Question createQuestion(
            final String questionName,
            final Account user,
            final Date createDate,
            final Long hits){
        final Question question =  this.createQuestion(questionName, "pattern");
        question.setAccountQuestion(user);
        question.setCreateDate(createDate);
        question.setHits(hits);
        getQuestionDaoImp().saveOrUpdate(question);
        return question;
    }

    /**
     * Create question.
     * @param question question
     * @param patron patron
     * @param user user
     * @return {@link Question}
     */
    public Question createQuestion(final String question, final String patron, final Account user){
        final Question questions = this.createQuestion(question, user);
        questions.setQidKey("1");
        questions.setHits(2L);
        questions.setCreateDate(new Date());
        getQuestionDaoImp().saveOrUpdate(questions);
        return questions;
    }

    /**
     * Create Question Answer.
     * @param answer answer
     * @param question question
     * @param hash hash
     * @return {@link QuestionAnswer}
     */
    public QuestionAnswer createQuestionAnswer(final String answer, final Question question, final String hash){
        final QuestionAnswer questionsAnswers = new QuestionAnswer();
        questionsAnswers.setAnswer(answer);
        questionsAnswers.setQuestions(question);
        questionsAnswers.setUniqueAnserHash(hash);
        questionsAnswers.setColor(PictureUtils.getRandomHexColor());
        questionsAnswers.setAnswerType(AnswerType.DEFAULT);
        getQuestionDaoImp().saveOrUpdate(questionsAnswers);
        //log.info("Q "+questionsAnswers.getQuestionAnswerId());
        return questionsAnswers;
    }

    /**
     * Create default {@link QuestionAnswer}
     * @param answer
     * @param question
     * @return
     */
    public QuestionAnswer createDefaultQuestionAnswer(final String answer, final Question question){
        final QuestionAnswer questionsAnswers = new QuestionAnswer();
        questionsAnswers.setAnswer(answer);
        questionsAnswers.setQuestions(question);
        questionsAnswers.setUniqueAnserHash(RandomStringUtils.randomAlphabetic(18));
        questionsAnswers.setColor(PictureUtils.getRandomHexColor());
        questionsAnswers.setAnswerType(AnswerType.DEFAULT);
        getQuestionDaoImp().saveOrUpdate(questionsAnswers);
        //log.info("Q "+questionsAnswers.getQuestionAnswerId());
        return questionsAnswers;
    }


    /**
     * Save survey responses.
     * @param answer
     * @param question
     * @param survey
     * @return
     */
    public SurveyResult createSurveyResult(final QuestionAnswer answer,
            final Question question, final Survey survey) {
        final SurveyResult result = new SurveyResult();
        result.setAnswer(answer);
        result.setQuestion(question);
        result.setSurvey(survey);
        getSurveyDaoImp().saveOrUpdate(result);
        return result;
    }

    /**
     *Helper to Create Survey Group.
     * @param surveyGroupName surveyGroupName
     * @return {@link SurveyGroup}
     *
     **/
    public SurveyGroup createSurveyGroup(String surveyGroupName){
        final SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setDateCreate(new Date());
        surveyGroup.setGroupName(surveyGroupName);
        getSurveyDaoImp().saveOrUpdate(surveyGroup);
        return surveyGroup;

    }

    /**
     *Helper to Create Question Collection.
     * @param desCollection Collection Description
     * @return {@link QuestionColettion}
     *
     **/
    public QuestionColettion createQuestionCollect(String desCollection){
        final QuestionColettion qCollection = new QuestionColettion();
        qCollection.setCreationDate(new Date());
        qCollection.setDesColeccion(desCollection);
        qCollection.setSecUsers(createAccount());
        getQuestionDaoImp().saveOrUpdate(qCollection);
        return qCollection;
    }
    /**
     * Helper to Create Surveys Format.
     * @return {@link SurveyFormat}
     * */
    public SurveyFormat createSurveyFormat(
            final String formatName,
            final Date createdDate
            ){
        final SurveyFormat sformat = new SurveyFormat();
        sformat.setDateCreated(createdDate);
        sformat.setSurveyFormatName(formatName);
        sformat.getSurveyGroups().add(createSurveyGroup("editors"));
        getSurveyformatDaoImp().saveOrUpdate(sformat);
        return sformat;
    }

    /**
     * Create Default Survey Format
     * @return
     */
    public SurveyFormat createDefaultSurveyFormat(){
          return this.createSurveyFormat("New", new Date());

    }

    //TODO: Create Helpers for Publicated and Non Publicated TweetPoll

    /**
     * Create TWeetPoll.
     * @param tweetId tweetId
     * @param closeNotification tweetId
     * @param resultNotification resultNotification
     * @param allowLiveResults allowLiveResults
     * @param publishTweetPoll publishTweetPoll
     * @param scheduleTweetPoll publishTweetPoll
     * @param scheduleDate scheduleDate
     * @param publicationDateTweet publicationDateTweet
     * @param completed completed
     * @param tweetOwner tweetOwner
     * @param question question
     * @return tweetPoll.
     */
    public TweetPoll createTweetPoll(
             Long tweetId,
             Boolean closeNotification,
             Boolean resultNotification,
             Boolean allowLiveResults,
             Boolean publishTweetPoll,
             Boolean scheduleTweetPoll,
             Date scheduleDate,
             Date creationDate,
             Boolean completed,
             UserAccount tweetOwner,
             Question question,
             final UserAccount userAccount){
        final TweetPoll tweetPoll = new TweetPoll();
        tweetPoll.setCloseNotification(closeNotification);
        tweetPoll.setResultNotification(resultNotification);
        tweetPoll.setAllowLiveResults(allowLiveResults);
        tweetPoll.setCompleted(completed);
        tweetPoll.setPublishTweetPoll(publishTweetPoll);
        tweetPoll.setQuestion(question);
        tweetPoll.setScheduleDate(scheduleDate);
        tweetPoll.setEditorOwner(userAccount);
        tweetPoll.setScheduleTweetPoll(scheduleTweetPoll);
        // The create date always have to be the date of creation
        tweetPoll.setCreateDate(creationDate);
        tweetPoll.setFavourites(Boolean.TRUE);
        tweetPoll.setTweetOwner(tweetOwner.getAccount());
        // The update date is the date when the tweetpoll has been updated, included the
        // publication date
        tweetPoll.setUpdatedDate(creationDate);
        tweetPoll.setEditorOwner(userAccount);
        tweetPoll.setRelevance(1L);
        getTweetPoll().saveOrUpdate(tweetPoll);
        return tweetPoll;
    }

    /**
     * Create Published {@link TweetPoll}.
     * @param tweetOwner tweet owner
     * @param question question
     * @return {@link TweetPoll}
     */
    public TweetPoll createPublishedTweetPoll(final UserAccount tweetOwner, final Question question){
       return createTweetPoll(randomLongGenerator(), false, false, false, true, true, new Date(), new Date(), false, tweetOwner, question, tweetOwner);
    }

    /**
     * Create published {@link TweetPoll}.
     * @param tweetOwner
     * @param question
     * @param dateTweet
     * @return
     */
    public TweetPoll createPublishedTweetPoll(final UserAccount tweetOwner, final Question question, final Date dateTweet){
        return createTweetPoll(randomLongGenerator(), false, false, false, true, true, new Date(), dateTweet, false, tweetOwner, question, tweetOwner);
     }

    /**
     * Create published {@link TweetPoll}.
     * @param question
     * @param user
     * @return
     */
    public TweetPoll createPublishedTweetPoll(final Question question, final UserAccount user) {
        return createTweetPoll(randomLongGenerator(), false, false, false, true, true, new Date(), new Date(), false, user, question, user);
     }

    /**
     * Create published {@link TweetPoll}.
     * @param id
     * @param question
     * @param user
     * @return
     */
    public TweetPoll createPublishedTweetPoll(final Long id, final Question question, final UserAccount user) {
        return createTweetPoll(id, false, false, false, true, true, new Date(), new Date(), false, user, question, user);
     }

    /**
     *
     * @param tweetOwner
     * @param question
     * @param isComplete
     * @param isPublished
     * @param isScheduled
     * @param creationDate
     * @return
     */
    public TweetPoll createAdvancedTweetPoll(final UserAccount tweetOwner,
            final Question question, final Boolean isPublished,
            final Boolean isComplete, final Boolean isScheduled,
            final Date creationDate) {
        return createTweetPoll(12345L, false, false, false, isPublished,
                isScheduled, new Date(), creationDate, isComplete, tweetOwner,
                question, null);
    }

    /**
     * Create Not Published {@link TweetPoll}.
     * @param tweetOwner tweet owner
     * @param question question
     * @return {@link TweetPoll}
     */
    public TweetPoll createNotPublishedTweetPoll(final UserAccount tweetOwner, final Question question){
       return createTweetPoll(null, false, false, false, false, false, new Date(), null, false, tweetOwner, question, null);
    }

    /**
     * Create {@link TweetPollSwitch}.
     * @param questionsAnswers  {@link QuestionAnswer}.
     * @param tweetPollDomain {@link TweetPoll}.
     * @return {@link TweetPollSwitch}.
     */
    public TweetPollSwitch createTweetPollSwitch(final QuestionAnswer questionsAnswers, final TweetPoll tweetPollDomain){
        final TweetPollSwitch tPollSwitch = new TweetPollSwitch();
        tPollSwitch.setAnswers(questionsAnswers);
        tPollSwitch.setTweetPoll(tweetPollDomain);
        tPollSwitch.setCodeTweet(questionsAnswers.getUniqueAnserHash());
        getTweetPoll().saveOrUpdate(tPollSwitch);
        return tPollSwitch;
    }

    /**
     * Create TweetPoll Result
     * @param tweetPollSwitch {@link TweetPollResult}
     * @param Ip ip address
     * @return {@link TweetPollResult}.
     */
    public TweetPollResult createTweetPollResult(final TweetPollSwitch tweetPollSwitch, final String Ip){
        final TweetPollResult tweetPollResult = new TweetPollResult();
        tweetPollResult.setIpVote(Ip);
        tweetPollResult.setTweetPollSwitch(tweetPollSwitch);
        tweetPollResult.setTweetResponseDate(new Date());
        getTweetPoll().saveOrUpdate(tweetPollResult);
        return tweetPollResult;
    }

    /**
     * Create tweetpoll result data with polling date.
     * @param tweetPollSwitch
     * @param Ip
     * @param pollingDate
     * @return
     */
    public TweetPollResult createTweetPollResultWithPollingDate(final TweetPollSwitch tweetPollSwitch, final String Ip, final Date pollingDate){
        final TweetPollResult tpResults = this.createTweetPollResult(tweetPollSwitch, Ip);
        tpResults.setTweetResponseDate(pollingDate);
        getTweetPoll().saveOrUpdate(tpResults);
        return tpResults;
    }

    /**
     * Create Fast TweetPoll Votes.
     * @return tweet poll
     */
    public TweetPoll createFastTweetPollVotes(){
        final UserAccount secondary = createUserAccount("jhon-"+RandomStringUtils.randomAscii(4), createAccount());
        final Question question = createQuestion("who I am?", "");
        final QuestionAnswer questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
        final QuestionAnswer questionsAnswers2 = createQuestionAnswer("no", question, "12346");
        final TweetPoll tweetPoll = createPublishedTweetPoll(secondary, question);
        final TweetPollSwitch pollSwitch1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
        final TweetPollSwitch pollSwitch2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
        createTweetPollResult(pollSwitch1, "192.168.0.1");
        createTweetPollResult(pollSwitch1, "192.168.0.2");
        createTweetPollResult(pollSwitch2, "192.168.0.3");
        createTweetPollResult(pollSwitch2, "192.168.0.4");
        //log.info("tw "+tweetPoll);
        return tweetPoll;
    }

    /**
     * Create {@link GeoPointFolder}.
     * @param type {@link GeoPointFolderType}.
     * @param locationFolderId folder Id
     * @param secUsers {@link Account}.
     * @param folderName name
     * @param locationFolder
     * @return {@link GeoPointFolder}.
     */
    public GeoPointFolder createGeoPointFolder(
            final GeoPointFolderType type,
            final Account secUsers,
            final String folderName,
            final GeoPointFolder locationFolder){
        final UserAccount userAcc = createUserAccount("Juan", secUsers);
        final GeoPointFolder geoPointFolder = new GeoPointFolder();
        geoPointFolder.setFolderType(type);
        geoPointFolder.setFolderName(folderName);
        geoPointFolder.setUsers(secUsers);
        geoPointFolder.setSubLocationFolder(locationFolder);
        geoPointFolder.setCreatedAt(Calendar.getInstance().getTime());
        geoPointFolder.setCreatedBy(userAcc);
        getGeoPointDao().saveOrUpdate(geoPointFolder);
        return geoPointFolder;
    }

    /**
     * Create Default {@link GeoPointFolder}
     * @param folderName
     * @param secUsers
     * @return
     */
    public GeoPointFolder createDefaultGeoPointFolder(final String folderName,
            final Account secUsers) {
        return this.createGeoPointFolder(GeoPointFolderType.POLYGON, secUsers,
                folderName, null);

    }

    /**
     * Helper Create Survey Section.
     * @param catState
     * @param descSection
     * @return
     */
    public SurveySection createSurveySection(
            final String descSection){
        final SurveySection surveySection = new SurveySection();
        surveySection.setDescSection(descSection);
      /*  surveySection.getQuestionSection().add(createDefaultQuestion("Why is your favourite movie"));
        surveySection.getQuestionSection().add(createDefaultQuestion("Where do you live"));
        surveySection.getQuestionSection().add(createDefaultQuestion("What do you do at home"));*/
        getSurveyDaoImp().saveOrUpdate(surveySection);
        return surveySection;
    }

    /**
     * Create default {@link SurveySection}.
     * @param name
     * @param survey
     * @return
     */
    public SurveySection createDefaultSection(final String name, final Survey survey){
        final SurveySection surveySection = new SurveySection();
        surveySection.setDescSection(name);
        surveySection.setSurvey(survey);
        surveySection.setSectionName(name);
        getSurveyDaoImp().saveOrUpdate(surveySection);
        return surveySection;
    }

    /**
     * Create Defaul Survey Pagination.
     * @param surveySection
     * @return
     */
    public SurveyPagination createDefaultSurveyPagination(final SurveySection surveySection){
        return this.createSurveyPagination(1, surveySection,this.createDefaultSurvey(this.createAccount()));
    }

    /**
     * Create Survey Pagination.
     * @param pageNumber
     * @param surveySection
     * @return
     */
    public SurveyPagination createSurveyPagination(
            final Integer pageNumber,
            final SurveySection surveySection,
            final Survey survey){
        final SurveyPagination surveyPag = new SurveyPagination();
        surveyPag.setPageNumber(pageNumber);
        surveyPag.setSurveySection(surveySection);
        surveyPag.setSurvey(survey);
        return surveyPag;
    }

    /**
     * Create Default Survey.
     * @param secUsers
     * @return
     */
    public Survey createDefaultSurvey(final Account secUsers ){
        return this.createSurvey("", new Date(), new Date(), secUsers,
                new Date(), createDefaultSurveyFormat(), "FirstSurvey", new Date());
     }

    /**
     *
     * @param secUsers
     * @param createdAt
     * @return
     */
    public Survey createDefaultSurvey(final Account secUsers, final String surveyName, final Date createdAt){
        return this.createSurvey("", new Date(), new Date(), secUsers,
                new Date(), createDefaultSurveyFormat(), surveyName, createdAt);
     }


    /**
     * Create {@link Survey}
     * @param complete
     * @param dateInterview
     * @param endDate
     * @param secUsers
     * @param startDate
     * @param surveyFormat
     * @return
     */
   public Survey createSurvey(
           final String complete,
           final Date dateInterview,
           final Date endDate,
           final Account secUsers,
           final Date startDate,
           final SurveyFormat surveyFormat,
           final String name,
           final Date createdAt
           ){
       final Survey survey = new Survey();
       survey.setName(name);
       survey.setComplete(complete);
       survey.setDateInterview(dateInterview);
       survey.setEndDate(endDate);
       survey.setOwner(secUsers);
       survey.setStartDate(startDate);
       survey.setTicket(3);
       survey.setCreateDate(createdAt);
       survey.setRelevance(1L);
       getSurveyDaoImp().saveOrUpdate(survey);
       return survey;
   }

    /**
     * Create Default List Email.
     * @param user
     * @param list
     * @return
     */
    public EmailList createDefaultListEmail(final Account user,final String list){
        return this.createListEmails(user, list, new Date());
    }

    /**
     * Create Default List Email.
     * @return
     */
    public EmailList createDefaultListEmail(){
        return this.createListEmails(createAccount(), "default", new Date());
    }

    /**
     * Create Default Email List.
     * @param list list Name
     * @return
     */

    public EmailList createDefaultListEmail(final String list){
        return this.createListEmails(createAccount(), list, new Date());
    }

    /**
     *Create Default Email List.
     * @param user
     * @return
     */
    public EmailList createDefaultListEmail(final Account user){
        return this.createListEmails(user, "default", new Date());
    }

    /**
     * Create Email List.
     * @return
     */
    public EmailList createListEmails(
                final Account users,
                final String listName,
                final Date createDate){
            final EmailList catListEmails = new EmailList();
            catListEmails.setCreatedAt(createDate);
            catListEmails.setListName(listName);
            catListEmails.setUsuarioEmail(users);
            getCatEmailDao().saveOrUpdate(catListEmails);
            return catListEmails;
    }

    /**
     * Create Default Emails.
     * @param email
     * @return
     */
    public Email createDefaultEmails(final String email){
        return this.createEmails(email, createDefaultListEmail());
    }

    /**
     * Create Default Emails.
     * @param email
     * @param listEmail
     * @return
     */
    public Email createDefaultEmails(final String email, final EmailList listEmail){
        return this.createEmails(email, listEmail);
    }
    /**
     * Create Emails.
     * @param email
     * @param list
     * @return
     */
    public Email createEmails(
                final String email,
                final EmailList list){
            final Email emails = new Email();
            emails.setEmail(email);
            emails.setIdListEmail(list);
            getCatEmailDao().saveOrUpdate(emails);
         return emails;
    }


    /**
     * Create {@link SocialAccount}.
     * @param consumerKey
     * @param consumerSecret
     * @param secretToken
     * @param userAccount
     * @param socialProfileUsername
     * @return
     */
    public SocialAccount createSocialAccount(
            final String token,
            final String secretToken,
            final UserAccount userAccount,
            final String socialProfileUsername,
            final Boolean verified,
            final SocialProvider provider) {
        final SocialAccount socialAccount = new SocialAccount();
        socialAccount.setAccessToken(token);
        socialAccount.setSecretToken(secretToken);
        socialAccount.setAccount(userAccount.getAccount());
        socialAccount.setUserOwner(userAccount);
        long randomNum = 100 + (int)(Math.random()* 4000);
        socialAccount.setSocialProfileId(String.valueOf(randomNum)+RandomStringUtils.randomAlphanumeric(10));
        socialAccount.setVerfied(verified);
        socialAccount.setUserOwner(userAccount);
        socialAccount.setAccounType(provider);
        socialAccount.setSocialAccountName(socialProfileUsername+RandomStringUtils.randomAlphanumeric(10));
        socialAccount.setUpgradedCredentials(new Date());
        socialAccount.setAddedAccount(new Date());
        socialAccount.setEmail("email"+String.valueOf(randomNum));
        socialAccount.setProfileUrl("url"+String.valueOf(randomNum));
        socialAccount.setRealName("real name"+String.valueOf(randomNum));
        socialAccount.setApplicationKey(RandomUtils.nextLong(new Random(50)));
        socialAccount.setRefreshToken("refresh_token_"+RandomStringUtils.randomAlphanumeric(10));
        socialAccount.setType(org.encuestame.utils.social.TypeAuth.OAUTH1);
        getAccountDao().saveOrUpdate(socialAccount);
        return socialAccount;
     }

    /**
     * Create Default Setted User.
     * @param account {@link Account}.
     * @return {@link SocialAccount}.
     */
    public SocialAccount createDefaultSettedSocialAccount(final UserAccount account){
        return this.createSocialAccount(
                getProperty("twitter.test.token"),
                getProperty("twitter.test.tokenSecret"),
                account,
                getProperty("twitter.test.account"), Boolean.TRUE, SocialProvider.TWITTER);
    }

    /**
     * Create {@link SocialAccount} with {@link SocialProvider}.
     * @param account {@link Account}
     * @param provider {@link SocialProvider}
     * @return {@link SocialAccount}.
     */
    public SocialAccount createSocialProviderAccount(final UserAccount account, final SocialProvider provider){
        return this.createSocialAccount(
                getProperty("twitter.test.token"),
                getProperty("twitter.test.tokenSecret"),
                account,
                getProperty("twitter.test.account"), Boolean.TRUE, provider);
    }


    /**
     * Create Default Setted Verified Twitter Account.
     * @param account
     * @return
     */
    public SocialAccount createDefaultSettedVerifiedSocialAccount(final UserAccount account){
        return this.createSocialAccount(
                getProperty("twitter.test.token"),
                getProperty("twitter.test.tokenSecret"),
                account,
                getProperty("twitter.test.account"),
                Boolean.TRUE, SocialProvider.TWITTER);
    }

  /**
   *
   * @param folderName
   * @param users
   * @return
   */
    public SurveyFolder createSurveyFolders(final String folderName, final UserAccount users){
        final SurveyFolder surveyFolders = new SurveyFolder();
        surveyFolders.setCreatedAt(new Date());
        surveyFolders.setFolderName(folderName);
        surveyFolders.setUsers(users.getAccount());
        surveyFolders.setStatus(Status.ACTIVE);
        surveyFolders.setCreatedBy(users);
        getSurveyDaoImp().saveOrUpdate(surveyFolders);
        return surveyFolders;
    }


    /**
     * Create {@link PollFolder}.
     * @param folderName folder name
     * @param users {@link Account}
     * @return {@link PollFolder}.
     */
    public PollFolder createPollFolder(final String folderName, final UserAccount users){
        final PollFolder folder = new PollFolder();
        folder.setCreatedAt(new Date());
        folder.setFolderName(folderName);
        folder.setUsers(users.getAccount());
        folder.setStatus(Status.ACTIVE);
        folder.setCreatedBy(users);
        getPollDao().saveOrUpdate(folder);
        return folder;
    }

    /**
     * Create TweetPoll Folder.
     * @param folderName
     * @param users
     * @return
     */
    public TweetPollFolder createTweetPollFolder(final String folderName, final UserAccount users){
        final TweetPollFolder folder = new TweetPollFolder();
        folder.setCreatedAt(new Date());
        folder.setFolderName(folderName);
        folder.setStatus(Status.ACTIVE);
        folder.setCreatedBy(users);
        folder.setUsers(users.getAccount());
        getTweetPoll().saveOrUpdate(folder);
        return folder;
    }

    /**
     * Add TweetPoll to Folder.
     * @param folderId
     * @param username
     * @param tweetPollId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public TweetPoll addTweetPollToFolder(final Long folderId, final Long userId, final Long tweetPollId) throws EnMeNoResultsFoundException{
        final TweetPollFolder tpfolder = getTweetPoll().getTweetPollFolderById(folderId);
        final TweetPoll tpoll = getTweetPoll().getTweetPollByIdandUserId(tweetPollId, userId);
        tpoll.setTweetPollFolder(tpfolder);
        getTweetPoll().saveOrUpdate(tpoll);
        return tpoll;
    }

    /**
     * Add Survey To Folder.
     * @param folderId
     * @param userId
     * @param surveyId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Survey addSurveyToFolder(final Long folderId, final Long userId, final Long surveyId) throws EnMeNoResultsFoundException{
        final SurveyFolder sfolder = getSurveyDaoImp().getSurveyFolderById(folderId);
        final Survey survey = getSurveyDaoImp().getSurveyByIdandUserId(surveyId, userId);
        survey.setSurveysfolder(sfolder);
        getSurveyDaoImp().saveOrUpdate(survey);
        return survey;
    }

    /**
     * Add Poll to Folder.
     * @param folderId
     * @param userId
     * @param pollId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Poll addPollToFolder(final Long folderId, final UserAccount userAccount, final Long pollId) throws EnMeNoResultsFoundException{
        final PollFolder pfolder = getPollDao().getPollFolderById(folderId);
        final Poll poll = getPollDao().getPollById(pollId, userAccount);
        poll.setPollFolder(pfolder);
        getPollDao().saveOrUpdate(poll);
        return poll;
    }

    /**
     * @return the activateNotifications
     */
    public Boolean getActivateNotifications() {
        return activateNotifications;
    }

    /**
     * @param activateNotifications uthe activateNotifications to set
     */
    public void setActivateNotifications(Boolean activateNotifications) {
        this.activateNotifications = activateNotifications;
    }

    /**
     * @return the clientDao
     */
    public IClientDao getClientDao() {
        return clientDao;
    }

    /**
     * @param clientDao the clientDao to set
     */
    public void setClientDao(final IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * @return the iTweetPoll
     */
    public ITweetPoll getTweetPoll() {
        return iTweetPoll;
    }

    /**
     * @param iTweetPoll the iTweetPoll to set
     */
    public void setTweetPoll(final ITweetPoll iTweetPoll) {
        this.iTweetPoll = iTweetPoll;
    }


    /**
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }


    /**
     * @param hibernateTemplate the hibernateTemplate to set
     */
    public void setHibernateTemplate(final HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * Create Notification.
     * @param message message
     * @param secUser {@link Account}.
     * @param description {@link NotificationEnum}.
     */
    public Notification createNotification(
            final String message,
            final Account secUser,
            final NotificationEnum description,
            final Boolean readed,
            final Date createdAt){
         final Notification notification = new Notification();
         notification.setAdditionalDescription(message);
         notification.setCreated(createdAt);
         notification.setDescription(description);
         notification.setReaded(readed);
         notification.setAccount(secUser);
         notification.setUrlReference("http://google.es");
         notification.setGroup(true);
         getNotification().saveOrUpdate(notification);
         return notification;
    }

    /**
     * Create Hash Tag.
     * @param hashTagName
     * @return
     */
    public HashTag createHashTag(final String hashTagName){
        final HashTag hashTag = new HashTag();
        hashTag.setHashTag(hashTagName.toLowerCase());
        hashTag.setHits(0L);
        hashTag.setUpdatedDate(new Date());
        hashTag.setSize(0L);
        getHashTagDao().saveOrUpdate(hashTag);
        return hashTag;
    }

    /**
     * Create hashtag with hits.
     * @param hashTagName name
     * @param hits total hits.
     * @return {@link HashTag}
     */
    public HashTag createHashTag(final String hashTagName, final Long hits){
        final HashTag hastag = this.createHashTag(hashTagName);
        hastag.setHits(hits);
        getHashTagDao().saveOrUpdate(hastag);
        return hastag;
    }

    /**
     *
     * @param hashTagName
     * @param hits
     * @return
     */
    public HashTag createHashTag(final String hashTagName, final Long hits, final Long size){
        final HashTag hastag = this.createHashTag(hashTagName);
        hastag.setHits(hits);
        hastag.setSize(size);
        hastag.setUpdatedDate(new Date());
        getHashTagDao().saveOrUpdate(hastag);
        return hastag;
    }

    /**
     * Helper.
     * Create hashTag ranking.
     * @param tag
     * @param rankingDate
     * @param average
     * @return
     */
    public HashTagRanking createHashTagRank(final HashTag tag, final Date rankingDate, final Double average){
        final HashTagRanking tagRank = new HashTagRanking();
        tagRank.setHashTag(tag);
        tagRank.setAverage(average);
        tagRank.setRankingDate(rankingDate);
        getHashTagDao().saveOrUpdate(tagRank);
        return tagRank;
    }

    /**
     * @return the notification
     */
    public INotification getNotification() {
        return notificationDao;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(final INotification notification) {
        this.notificationDao = notification;
    }

    /**
     * @return the hashTagDao
     */
    public IHashTagDao getHashTagDao() {
        return hashTagDao;
    }

    /**
     * @param hashTagDao the hashTagDao to set
     */
    public void setHashTagDao(IHashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }

    /**
    * @return the frontEndDao
    */
    public IFrontEndDao getFrontEndDao() {
        return frontEndDao;
    }

    /**
    * @param frontEndDao the frontEndDao to set
    */
    public void setFrontEndDao(IFrontEndDao frontEndDao) {
        this.frontEndDao = frontEndDao;
    }


    /**
     * Create fake questions.
     * @param user {@link Account};
     */
    public void createFakesQuestions(final Account user){
        createQuestion("Do you want soccer?",  user);
        createQuestion("Do you like apple's?",  user);
        createQuestion("Do you buy iPods?",  user);
        createQuestion("Do you like sky iPods Touch?",  user);
        createQuestion("Ipad VS Ipad2?",  user);
        createQuestion("How Often Do You Tweet? Survey Says Not That Often",  user);
        createQuestion("Is survey usseful on Twitter?",  user);
        createQuestion("Should be happy?",  user);
        createQuestion("Are you home alone?",  user);
    }

    /**
     * Create a list of fakes {@link TweetPoll}.
     * @param userAccount
     */
    public void createFakesTweetPoll(final UserAccount userAccount){
        final Question question = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        final Question question1 = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        final Question question2 = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        final Question question3 = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question1);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question2);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question3);
    }

    /**
     * Create {@link TweetPoll} published.
     * @param publishTweetPoll
     * @param completed
     * @param scheduleDate
     * @param tweetOwner
     * @param question
     * @return
     */
     public TweetPoll createTweetPollPublicated(
              final Boolean publishTweetPoll,
              final Boolean completed,
              final Date scheduleDate,
              final UserAccount tweetOwner,
              final Question question){
         final TweetPoll tweetPoll = new TweetPoll();
         tweetPoll.setPublishTweetPoll(publishTweetPoll);
         tweetPoll.setCompleted(completed);
         tweetPoll.setScheduleDate(scheduleDate);
         tweetPoll.setCreateDate(new Date());
         tweetPoll.setFavourites(false);
         tweetPoll.setQuestion(question);
         tweetPoll.setTweetOwner(tweetOwner.getAccount());
         tweetPoll.setEditorOwner(tweetOwner);
         getTweetPoll().saveOrUpdate(tweetPoll);
         return tweetPoll;
     }

     /**
      * Create Default {@link TweetPoll}
      * @param publishTweetPoll
      * @param completed
      * @param favourites
      * @param tweetOwner
      * @param question
      * @param creationDate
      * @return
      */
     public TweetPoll createDefaultTweetPollPublicated(
             final Boolean publishTweetPoll,
             final Boolean completed,
             final Boolean favourites,
             final UserAccount tweetOwner,
             final Question question,
             final Date creationDate ){
        final TweetPoll tweetPoll = new TweetPoll();
        tweetPoll.setPublishTweetPoll(publishTweetPoll);
        tweetPoll.setCompleted(completed);
        tweetPoll.setCreateDate(creationDate);
        tweetPoll.setFavourites(favourites);
        tweetPoll.setQuestion(question);
        tweetPoll.setTweetOwner(tweetOwner.getAccount());
        tweetPoll.setEditorOwner(tweetOwner);
        getTweetPoll().saveOrUpdate(tweetPoll);
        return tweetPoll;
    }

      /**
       *  Create TweetPoll social links.
       * @param tweetPoll
       * @param tweetId
       * @param socialAccount
       * @param tweetText
       * @return
       */
    public TweetPollSavedPublishedStatus createTweetPollSavedPublishedStatus(
            final TweetPoll tweetPoll, final String tweetId,
            final SocialAccount socialAccount, final String tweetText) {
        return this.createSocialLinkSavedPublishedStatus(
                tweetPoll,
                null,
                null,
                tweetId,
                socialAccount,
                tweetText);

    }

    /**
     * Create social network link.
     * @param tweetPoll
     * @param poll
     * @param survey
     * @param tweetId
     * @param socialAccount
     * @param tweetText
     * @return
     */
    public TweetPollSavedPublishedStatus createSocialLinkSavedPublishedStatus(
            final TweetPoll tweetPoll, final Poll poll, final Survey survey, final String tweetId,
            final SocialAccount socialAccount, final String tweetText) {
        final TweetPollSavedPublishedStatus publishedStatus = new TweetPollSavedPublishedStatus();
        publishedStatus.setTweetPoll(tweetPoll);
        publishedStatus.setStatus(org.encuestame.utils.enums.Status.SUCCESS);
        publishedStatus.setTweetContent(tweetText);
        publishedStatus.setSocialAccount(socialAccount);
        publishedStatus.setTweetId(RandomStringUtils.randomAlphabetic(18));
        publishedStatus.setPublicationDateTweet(Calendar.getInstance().getTime());
        publishedStatus.setPoll(poll);
        publishedStatus.setSurvey(survey);
        getTweetPoll().saveOrUpdate(publishedStatus);
        return publishedStatus;
    }

    /**
     * Create Poll social links.
     * @param poll
     * @param tweetId
     * @param socialAccount
     * @param tweetText
     * @return
     */
    public TweetPollSavedPublishedStatus createPollSavedPublishedStatus(
            final Poll poll, final String tweetId,
            final SocialAccount socialAccount, final String tweetText) {
         return this.createSocialLinkSavedPublishedStatus(null, poll, null, tweetId, socialAccount, tweetText);
    }

    /**
     * Create Survey social links.
     * @param survey
     * @param tweetId
     * @param socialAccount
     * @param tweetText
     * @return
     */
    public TweetPollSavedPublishedStatus createSurveySavedPublishedStatus(
            final Survey survey, final String tweetId,
            final SocialAccount socialAccount, final String tweetText) {
         return this.createSocialLinkSavedPublishedStatus(null, null, survey, tweetId, socialAccount, tweetText);
    }

    /**
     * Create hit new.
     * @param tweetPoll
     * @param poll
     * @param survey
     * @param ipAddress
     * @return
     */
    public Hit createHit(final TweetPoll tweetPoll, final Poll poll, final Survey survey, final HashTag hashTag,
            final String ipAddress){
        final Hit hit = new Hit();
        hit.setHitDate(Calendar.getInstance().getTime());
        hit.setIpAddress(ipAddress);
        hit.setPoll(poll);
        hit.setSurvey(survey);
        hit.setTweetPoll(tweetPoll);
        hit.setHashTag(hashTag);
        hit.setHitCategory(HitCategory.VISIT);
        getFrontEndDao().saveOrUpdate(hit);
        return hit;
    }

    /**
     * Create TweetPoll hit.
     * @param tweetPoll
     * @param ipAddress
     * @return
     */
    public Hit createTweetPollHit(final TweetPoll tweetPoll, final String ipAddress){
        return this.createHit(tweetPoll, null, null, null, ipAddress);
    }

    /**
     * Create Poll hit.
     * @param poll
     * @param ipAddress
     * @return
     */
    public Hit createPollHit(final Poll poll, final String ipAddress){
        return this.createHit(null, poll, null, null, ipAddress);
    }

    /**
     * Create survey hit.
     * @param survey
     * @param ipAddress
     * @return
     */
    public Hit createSurveyHit(final Survey survey, final String ipAddress){
        return this.createHit(null, null, survey, null, ipAddress);
    }

    /**
     * Create HashTag hit.
     * @param survey
     * @param ipAddress
     * @return
     */
    public Hit createHashTagHit(final HashTag tag, final String ipAddress){
        return this.createHit(null, null, null, tag, ipAddress);
    }

   /**
    * Create HashTag hit.
    * @param tag
    * @param ipAddress
    * @param hitDate
    * @return
    */
    public Hit createHashTagHit(final HashTag tag, final String ipAddress, final Date hitDate){
        final Hit visit = this.createHit(null, null, null, tag, ipAddress);
        visit.setHitDate(hitDate);
        return visit;
    }

    /**
    * @return the dashboardDao
    */
    public IDashboardDao getDashboardDao() {
        return dashboardDao;
    }

    /**
    * @param dashboardDao the dashboardDao to set
    */
    public void setDashboardDao(final IDashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }

    /**
     * @return the commentsOperationsDao
     */
    public CommentsOperations getCommentsOperations() {
        return commentsOperations;
    }

    /**
     * @param commentsOperationsDao the commentsOperationsDao to set
     */
    public void setCommentsOperations(final CommentsOperations commentsOperations) {
        this.commentsOperations = commentsOperations;
    }


    /**
     * @return the scheduleDao
     */
    public IScheduled getScheduleDao() {
        return scheduleDao;
    }


    /**
     * @param scheduleDao the scheduleDao to set
     */
    public void setScheduleDao(final IScheduled scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


    /**
     * Create comment.
     * @param comm
     * @param likeVote
     * @param tpoll
     * @param survey
     * @param poll
     * @return
     */
    public Comment createComment(
            final String comm,
            final Long likeVote,
            final TweetPoll tpoll,
            final Survey survey,
            final Poll poll,
            final UserAccount user,
            final Long dislikeVote,
            final Date createdAt){
           final Comment comment = new Comment();
           comment.setComment(comm);
           comment.setCreatedAt(createdAt);
           comment.setLikeVote(likeVote);
           comment.setDislikeVote(dislikeVote);
           comment.setPoll(poll);
           comment.setParentId(null);
           comment.setSurvey(survey);
           comment.setTweetPoll(tpoll);
           comment.setUser(user);
           getCommentsOperations().saveOrUpdate(comment);
           return comment;
        }

    /**
     * Create default tweetPoll comment.
     * @param tpoll
     * @return
     */
    public Comment createDefaultTweetPollComment(
            final String comment,
            final TweetPoll tpoll,
            final UserAccount userAcc){
        return this.createComment(comment, 0L, tpoll, null, null, userAcc, 0L , new Date());
    }

    /**
     *
     * @param comment
     * @param tpoll
     * @param userAcc
     * @param status
     * @return
     */
    public Comment createDefaultTweetPollCommentOptions(
            final String comment,
            final TweetPoll tpoll,
            final UserAccount userAcc, final CommentOptions status){
        return this.createComment(comment, 0L, tpoll, null, null, userAcc, 0L , new Date());
    }

    /**
     * Create a default {@link Comment} with status.
     * @param comment
     * @param tpoll
     * @param userAcc
     * @param commentOptions
     * @return
     */
    public Comment createDefaultTweetPollCommentWithStatus(
            final String comment,
            final TweetPoll tpoll,
            final UserAccount userAcc,
            final CommentOptions commentOptions,
            final Date creationDate){
        final Comment tweetPollComment = this.createComment(comment, 0L, tpoll, null, null, userAcc, 0L , creationDate);
        tweetPollComment.setCommentOptions(commentOptions);
        getCommentsOperations().saveOrUpdate(tweetPollComment);
        return tweetPollComment;
    }

    /**
     *
     * @param comment
     * @param tpoll
     * @param userAcc
     * @param likeVote
     * @param dislikeVote
     * @return
     */
    public Comment createDefaultTweetPollCommentVoted(
            final String comment,
            final TweetPoll tpoll,
            final UserAccount userAcc,
            final Long likeVote,
            final Long dislikeVote,
            final Date createdAt){
        return this.createComment(comment, likeVote, tpoll, null, null, userAcc, dislikeVote, createdAt);
    }

    /**
     * Create default poll comment.
     * @param poll
     * @return
     */
    public Comment createDefaultPollComment(
            final String comment,
            final Poll poll,
            final UserAccount userAcc){
        return this.createComment(comment, 0L, null, null, poll, userAcc, 0L , new Date());
    }

    /**
     * Create a default {@link Poll} {@link Comment} with status
     * @param comment
     * @param poll
     * @param userAcc
     * @param commentOptions
     * @return
     */
    public Comment createDefaultPollCommentWithStatus(final String comment,
            final Poll poll, final UserAccount userAcc,
            final CommentOptions commentOptions, final Date creationDate) {
        final Comment pollComment = this.createComment(comment, 0L, null, null,
                poll, userAcc, 0L, creationDate);
        pollComment.setCommentOptions(commentOptions);
        getCommentsOperations().saveOrUpdate(pollComment);
        return pollComment;
    }

    /**
     * Create default survey comment.
     * @param survey
     * @return
     */
    public Comment createDefaultSurveyComment(
            final String comment,
            final Survey survey,
            final UserAccount userAcc){
        return this.createComment(comment, 0L, null, survey, null, userAcc, 0L, new Date());
    }

    /**
     * Create survey comment with status.
     * @param comment
     * @param survey
     * @param userAcc
     * @param commentOptions
     * @return
     */
    public Comment createDefaultSurveyCommentWithStatus(final String comment,
            final Survey survey, final UserAccount userAcc,
            final CommentOptions commentOptions, final Date creationdate) {

        final Comment surveyComment = this.createComment(comment, 0L, null,
                survey, null, userAcc, 0L, creationdate);
        surveyComment.setCommentOptions(commentOptions);
        getCommentsOperations().saveOrUpdate(surveyComment);
        return surveyComment;
    }

    /**
     * Create access rate item.
     * @param rate
     * @param tpoll
     * @param survey
     * @param poll
     * @param user
     * @param ipAddress
     * @return
     */
    public AccessRate createAccessRateItem(final Boolean rate, final TweetPoll tpoll, final Survey survey, final Poll poll,
            final UserAccount user, final String ipAddress){
        final AccessRate vote = new AccessRate();
        vote.setRate(rate);
        vote.setTweetPoll(tpoll);
        vote.setPoll(poll);
        vote.setSurvey(survey);
        vote.setUser(user);
        vote.setIpAddress(ipAddress);
        vote.setUpdatedDate(Calendar.getInstance().getTime());
        getTweetPoll().saveOrUpdate(vote);
        return vote;
    }

    /**
     * Create tweetpoll access rate.
     * @param rate
     * @param tweetPoll
     * @param ipAddress
     * @return
     */
    public AccessRate createTweetPollRate(final Boolean rate, final TweetPoll tweetPoll, final String ipAddress){
        return this.createAccessRateItem(rate, tweetPoll, null, null, null, ipAddress);
    }

    /**
     * Create poll access rate.
     * @param rate
     * @param tweetPoll
     * @param ipAddress
     * @return
     */
    public AccessRate createPollRate(final Boolean rate, final Poll poll, final String ipAddress){
        return this.createAccessRateItem(rate, null, null, poll, null, ipAddress);
    }

    /**
     * Create survey rate.
     * @param rate
     * @param survey
     * @param ipAddress
     * @return
     */
    public AccessRate createSurveyRate(final Boolean rate, final Survey survey, final String ipAddress){
        return this.createAccessRateItem(rate, null, survey, null, null, ipAddress);
    }

    /**
     * Create question preference
     * @param preference
     * @param value preference value
     * @param question
     * @return
     */
    public QuestionPreferences createQuestionPreference(
            final String preference, final String value, final Question question) {
        final QuestionPreferences questionPreference = new QuestionPreferences();
        questionPreference.setPreference(preference);
        questionPreference.setQuestion(question);
        getQuestionDaoImp().saveOrUpdate(questionPreference);
        return questionPreference;
    }

    /**
     * Create random question for tweetpolls and polls.
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public Question createQuestionRandom() throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        final Question randomQuestion = createQuestion(
                "What is your favorite season? " + MD5Utils.md5(RandomStringUtils.randomAlphanumeric(15)),
                "");
        return randomQuestion;
    }


    /**
     *
     * @param randomDate
     * @param tweetOwner
     * @param isCompleted
     * @param isFavourites
     * @param isScheduled
     * @param isPublished
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public TweetPoll createTweetPollItems(final Date randomDate, final UserAccount tweetOwner,
            final Boolean isCompleted, final Boolean isFavourites,
            final Boolean isScheduled, final Boolean isPublished)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int j = 0;
        final String randomTweetContent = RandomStringUtils
                .randomAlphanumeric(6);
        final Question tpollQuestion = createQuestionRandom();
        final TweetPoll tweetPoll = createPublishedTweetPoll(tweetOwner,
                tpollQuestion);
        tweetPoll.setCompleted(isCompleted);
        tweetPoll.setFavourites(isFavourites);
        tweetPoll.setScheduleTweetPoll(isScheduled);
        tweetPoll.setPublishTweetPoll(isPublished);
        tweetPoll.setCreateDate(randomDate);
        getTweetPoll().saveOrUpdate(tweetPoll);
        return tweetPoll;
    }

    /**
    *
    * @param tpoll
    * @param socialAccount
    * @param provider
    */
    public void createTweetPollSavedPublishStatus(final TweetPoll tpoll,
            final SocialAccount socialAccount, final SocialProvider provider) {
        final String randomTweetContent = RandomStringUtils
                .randomAlphanumeric(6);

        final TweetPollSavedPublishedStatus tpSaved = this
                .createTweetPollSavedPublishedStatus(tpoll, " ", socialAccount,
                        randomTweetContent);

        tpSaved.setApiType(provider);
        tpSaved.setPublicationDateTweet(new Date());
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);

    }

    /**
     *
     * @param tpoll
     * @param socialAccount
     * @param provider
     */
    public void createTweetPollSavedPublishStatusMultiple(
            final TweetPoll tpoll, final List<SocialProvider> provider,
            final SocialAccount socialAccount) {
        final String randomTweetContent = RandomStringUtils
                .randomAlphanumeric(6);
        for (SocialProvider providerList : provider) {
            final TweetPollSavedPublishedStatus tpSaved = this.createTweetPollSavedPublishedStatus(tpoll, "", socialAccount, randomTweetContent);
            tpSaved.setApiType(providerList);
            tpSaved.setPublicationDateTweet(new Date());
            getTweetPoll().saveOrUpdate(tpSaved);
            assertNotNull(tpSaved);
        }
    }

    public ItemStatDetail createItemStatDetail(final Long itemId, final Date creationDate, final Long miliDate){
        final ItemStatDetail isd = new ItemStatDetail();
        isd.setDate(creationDate);
        isd.setItemId(itemId);
        //isd.setMilisecondsDate(miliDate);
        return isd;
    }

    /**
     *
     * @param tpoll
     * @param survey
     * @param poll
     * @param scheduleDate
     * @param socialAccount
     * @param status
     * @param attempts
     * @param tpollSaved
     * @param tweetText
     * @return
     */
    public Schedule createScheduledItem(final TweetPoll tpoll,
            final Survey survey, final Poll poll, final Date scheduleDate,
            final SocialAccount socialAccount, final Status status,
            final Integer attempts,
            final TweetPollSavedPublishedStatus tpollSaved, final String tweetText, final TypeSearchResult typeSearch) {
        final Schedule schedule = new Schedule();
        schedule.setPublishAttempts(attempts);
        schedule.setPoll(poll);
        schedule.setScheduleDate(scheduleDate);
        schedule.setSocialAccount(socialAccount);
        schedule.setStatus(status);
        schedule.setSurvey(survey);
        schedule.setTpoll(tpoll);
        schedule.setTpollSavedPublished(tpollSaved);
        schedule.setTweetText(tweetText);
        schedule.setTypeSearch(typeSearch);
        getScheduleDao().saveOrUpdate(schedule);
        return schedule;
    }

    /**
     * Create Schedule for {@link TweetPoll}
     * @param tpoll
     * @param scheduleDate
     * @param socialAccount
     * @param status
     * @return
     */
    public Schedule createTweetpollSchedule(final TweetPoll tpoll, final Date scheduleDate, final SocialAccount socialAccount, final Status status, final TypeSearchResult typeSearch){
        return this.createScheduledItem(tpoll, null, null, scheduleDate, socialAccount, status, 2 , null, "tweettext", typeSearch);
    }

    /**
     *
     * @param tpoll
     * @param scheduleDate
     * @param socialAccount
     * @param status
     * @param typeSearch
     * @return
     */
    public Schedule createTweetpollScheduleDefault(final TweetPoll tpoll,
            final Date scheduleDate, final SocialAccount socialAccount,
            final Status status, final TypeSearchResult typeSearch, final String tweetText) {
        return this.createScheduledItem(tpoll, null, null, scheduleDate,
                socialAccount, status, 2, null, tweetText, typeSearch);

    }

    /**
     *
     * @param tpoll
     * @param scheduleDate
     * @param socialAccount
     * @param status
     * @param typeSearch
     * @param attempts
     * @return
     */
    public Schedule createTweetpollScheduleDefault(final TweetPoll tpoll,
            final Date scheduleDate, final SocialAccount socialAccount,
            final Status status, final TypeSearchResult typeSearch, final Integer attempts) {
        return this.createScheduledItem(tpoll, null, null, scheduleDate,
                socialAccount, status, attempts, null, "tweettext", typeSearch);

    }

    /**
     * Helper to generate random password to {@link Poll} and {@link Survey}
     * @return
     */
	public String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}

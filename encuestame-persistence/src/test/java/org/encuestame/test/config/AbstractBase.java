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
package org.encuestame.test.config;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.domain.CatEmailLists;
import org.encuestame.persistence.domain.CatEmails;
import org.encuestame.persistence.domain.CatLocation;
import org.encuestame.persistence.domain.CatLocationFolder;
import org.encuestame.persistence.domain.CatLocationType;
import org.encuestame.persistence.domain.CatState;
import org.encuestame.persistence.domain.Client;
import org.encuestame.persistence.domain.LocationFolderType;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.Status;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.security.SecGroup;
import org.encuestame.persistence.domain.security.SecPermission;
import org.encuestame.persistence.domain.security.SecUser;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.persistence.domain.security.SecUserTwitterAccounts;
import org.encuestame.persistence.domain.security.SecGroup.Type;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.domain.survey.QuestionColettion;
import org.encuestame.persistence.domain.survey.QuestionPattern;
import org.encuestame.persistence.domain.survey.QuestionSection;
import org.encuestame.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.survey.Surveys;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.persistence.domain.survey.QuestionsAnswers.AnswerType;
import org.encuestame.persistence.dao.ICatEmail;
import org.encuestame.persistence.dao.ICatLocation;
import org.encuestame.persistence.dao.ICatLocationTypeDao;
import org.encuestame.persistence.dao.ICatState;
import org.encuestame.persistence.dao.IClientDao;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.dao.IProject;
import org.encuestame.persistence.dao.IQuestionDao;
import org.encuestame.persistence.dao.ISecGroups;
import org.encuestame.persistence.dao.ISecPermissionDao;
import org.encuestame.persistence.dao.ISecUserDao;
import org.encuestame.persistence.dao.ISurvey;
import org.encuestame.persistence.dao.ISurveyFormatDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.dao.imp.CatEmailDao;
import org.encuestame.persistence.dao.imp.ClientDao;
import org.encuestame.persistence.dao.imp.PollDao;
import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Base Class to Test Cases.
 * @author Picado, Juan juan@encuestame.org
 * @since October 15, 2009
 * @version $Id$
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

    /** State Catalog Dao.**/
    @Autowired
    private ICatState catStateDaoImp;

    /** User Security Dao.**/
    @Autowired
    private ISecUserDao secUserDao;

    /**Group Security Dao.**/
    @Autowired
    private ISecGroups secGroupDaoImp;

    /** Security Permissions Dao.**/
    @Autowired
    private ISecPermissionDao secPermissionDaoImp;

    /** Catalog Location Dao.**/
    @Autowired
    private ICatLocation catLocationDao;

    /** Project Dao Imp.**/
    @Autowired
    private IProject projectDaoImp;

    /** Survey Dao Imp.**/
    @Autowired
    private ISurvey surveyDaoImp;

    /** Question Dao Imp.**/
    @Autowired
    private IQuestionDao questionDaoImp;

    /** Catalog Location Type Dao.**/
    @Autowired
    private ICatLocationTypeDao catLocationTypeDao;

    /** {@link ClientDao}. **/
    @Autowired
    private IClientDao clientDao;

    /** {@link TweetPollDao}. **/
    @Autowired
    private ITweetPoll iTweetPoll;

    /** {@link PollDao}. **/
    @Autowired
    private IPoll iPoll;

    /** {@link CatEmailDao}. **/
    @Autowired
    private ICatEmail catEmailDao;

    /** {@link Notification}. **/
    @Autowired
    private INotification notification;

      /** Activate Notifications.**/
    private Boolean activateNotifications = false;

    /** Url Poll. **/
    public final String URLPOLL = "http://www.encuestame.org";

    /**
     * Get Property.
     * @param property
     * @return
     */
    public String getProperty(final String property){
        Resource resource = new FileSystemResource("src/main/resources/test-config.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("Property ["+property+"] value ["+props.getProperty(property)+"]");
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
     * Flush Indexes.
     */
    public void flushIndexes(){
        final FullTextSession fullTextSession = Search.getFullTextSession(getHibernateTemplate().getSessionFactory().getCurrentSession());
        fullTextSession.flushToIndexes();
    }

    /**
     * Getter.
     * @return the catStateDaoImp
     */
    public ICatState getCatStateDaoImp() {
        return catStateDaoImp;
    }

    /**
     * @param catStateDaoImp the catStateDaoImp to set
     */
    public void setCatStateDaoImp(final ICatState catStateDaoImp) {
        this.catStateDaoImp = catStateDaoImp;
    }

    /**
     * @return the userDao
     */
    public ISecUserDao getSecUserDao() {
        return secUserDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setSecUserDao(final ISecUserDao userDao) {
        this.secUserDao = userDao;
    }

    /**
     * @return {@link ISecGroups}
     */
    public ISecGroups getSecGroup(){
        return secGroupDaoImp;
    }

    /**
     * @param secGroupDaoImp  {@link ISecGroups}
     */
    public void setgroupDao(final ISecGroups secGroupDaoImp){
        this.secGroupDaoImp = secGroupDaoImp;
    }

    /**
     * @return the secPermissionDaoImp
     */
    public ISecPermissionDao getSecPermissionDaoImp() {
        return secPermissionDaoImp;
    }

    /**
     * @param secPermissionDaoImp the secPermissionDaoImp to set
     */
    public void setSecPermissionDaoImp(final ISecPermissionDao secPermissionDaoImp) {
        this.secPermissionDaoImp = secPermissionDaoImp;
    }

    /**
     * @return the secGroupDaoImp
     */
    public ISecGroups getSecGroupDaoImp() {
        return secGroupDaoImp;
    }

    /**
     * @param secGroupDaoImp the secGroupDaoImp to set
     */
    public void setSecGroupDaoImp(final ISecGroups secGroupDaoImp) {
        this.secGroupDaoImp = secGroupDaoImp;
    }

    /**
     * @return the projectDaoImp
     */
    public IProject getProjectDaoImp() {
        return projectDaoImp;
    }

    /**
     * @param projectDaoImp the projectDaoImp to set
     */
    public void setProjectDaoImp(final IProject projectDaoImp) {
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
     * @return the catLocationTypeDao
     */
    public ICatLocationTypeDao getCatLocationTypeDao() {
        return catLocationTypeDao;
    }

    /**
     * @param catLocationTypeDao the catLocationTypeDao to set
     */
    public void setCatLocationTypeDao(ICatLocationTypeDao catLocationTypeDao) {
        this.catLocationTypeDao = catLocationTypeDao;
    }



    /**
     * @return catLocationDao
     */
    public ICatLocation getCatLocationDao() {
        return catLocationDao;
    }

    /**
     * @param catLocationDao catLocationDao
     */
    public void setCatLocationDao(ICatLocation catLocationDao) {
        this.catLocationDao = catLocationDao;
    }

    /**
     * @return {@link CatLocation}
     */
    public ICatLocation getCatLocation() {
        return catLocationDao;
    }

    /**
     * @param catLocation {@link CatLocation}
     */
    public void setCatLocation(final ICatLocation catLocation) {
        this.catLocationDao = catLocation;
    }

    /**
     * @return {@link Poll}
     */
    public IPoll getiPoll() {
        return iPoll;
    }

    /**
     * @param poll the iPoll to set
     */
    public void setiPoll(final IPoll poll) {
        this.iPoll = poll;
    }

    /**
     * @return the catEmailDao
     */
    public ICatEmail getCatEmailDao() {
        return catEmailDao;
    }


    /**
     * @param catEmailDao the catEmailDao to set
     */
    public void setCatEmailDao(ICatEmail catEmailDao) {
        this.catEmailDao = catEmailDao;
    }


    /**
     * Helper to create poll
     * @return poll
     */
    public Poll createPoll(){
        final Poll poll = new Poll();
        poll.setCreatedAt(new Date());
        poll.setQuestion(createQuestion("Do you eat pizza", "yesNo"));
        //should be unique
        poll.setPollHash(RandomStringUtils.randomAlphabetic(18));
        poll.setPollOwner(createUser());
        poll.setPollCompleted(true);
        poll.setCreatedAt(new Date());
        getiPoll().saveOrUpdate(poll);
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
            final SecUser secUsers,
            final Boolean pollCompleted){
        final Poll poll = new Poll();
        poll.setCreatedAt(createdDate);
        poll.setQuestion(question);
        poll.setPollHash(hash);
        poll.setPollOwner(secUsers);
        poll.setPollCompleted(pollCompleted);
        getiPoll().saveOrUpdate(poll);
        return poll;

    }

     /**
     * Helper to create Poll Result.
     * @param questionAnswer {@link QuestionsAnswers}
     * @param poll {@link Poll}
     * @return state
     */
    public PollResult createPollResults(final QuestionsAnswers questionAnswer, final Poll poll){
        final PollResult pollRes = new PollResult();
        pollRes.setAnswer(questionAnswer);
        pollRes.setIpaddress("127.0.0.1");
        pollRes.setPoll(poll);
        pollRes.setVotationDate(new Date());
        getiPoll().saveOrUpdate(pollRes);
        return pollRes;

    }

    /**
     * Helper to create state.
     * @param name name of state
     * @return state
     */
    public CatState createState(final String name){
        final CatState state = new CatState();
        state.setDescState(name);
        state.setStateImage("image.jpg");
        getCatStateDaoImp().saveOrUpdate(state);
        return state;
    }

    /**
     * Helper to create Default State.
     * @return
     */
    public CatState createDefaultState(){
         return this.createState("Enabled");
     }


    /**
     * Create project.
     * @param name Project's name
     * @param descProject Project Description
     * @param infoProject Informations's Project
     * @param state Project's state
     * @param user user
     * @return {@link Project}
     */
    public Project createProject(
            final String name,
            final String descProject,
            final String infoProject,
            final SecUser user) {
          Project project = new Project();
          project.setProjectDateFinish(new Date());
          project.setProjectDateStart(new Date());
          project.setProjectInfo(infoProject);
          project.setProjectName("name");
          project.setLead(createSecondaryUser("test", createUser()));
          project.setProjectDescription(descProject);
          project.setUsers(user);
          getProjectDaoImp().saveOrUpdate(project);
          return project;
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
     * @param secUser {@link SecUser}
     * @return state
     */
    public SecUserSecondary createSecondaryUser(
            final String name,
            final SecUser secUser){
        return createSecondaryUser(name, name+"-"+RandomStringUtils.randomNumeric(6)+"@users.com", secUser);
    }

    /**
     * Create Secondary User.
     * @param name
     * @param email
     * @param secUser
     * @return
     */
    public SecUserSecondary createSecondaryUser(
            final String name,
            final String email,
            final SecUser secUser){
        final SecUserSecondary user= new SecUserSecondary();
        user.setCompleteName(name);
        user.setUsername(name);
        user.setPassword("12345");
        user.setUserEmail(email);
        user.setEnjoyDate(new Date());
        user.setInviteCode("xxxxxxx");
        user.setSecUser(secUser);
        user.setUserStatus(true);
        getSecUserDao().saveOrUpdate(user);
        return user;
    }


    /**
     * Create User.
     * @return {@link SecUser}
     */
    public SecUser createUser(){
        SecUser user = new SecUser();
        user.setTwitterAccount("testTWitterAccount");
        user.setTwitterPassword("testTwitterPwsd");
        getSecUserDao().saveOrUpdate(user);
        return user;
    }
    /**
     * Create User.
     * @param twitterAccount account
     * @param twitterPassword password
     * @return {@link SecUser}
     */
    public SecUser createUser(final String twitterAccount, final String twitterPassword){
        SecUser user = new SecUser();
        user.setTwitterAccount(twitterAccount);
        user.setTwitterPassword(twitterPassword);
        getSecUserDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Helper to create LocationType.
     * @param locationTypeName locationTypeName
     * @return locationType
     */

    public CatLocationType createCatLocationType(final String locationTypeName){
        final CatLocationType catLocatType = new CatLocationType();
        catLocatType.setLocationTypeDescription(locationTypeName);
        catLocatType.setLocationTypeLevel(1);
        catLocatType.setUsers(createUser());
        getCatLocationTypeDao().saveOrUpdate(catLocatType);
        return catLocatType;
    }


    /**
     * Helper to create CatLocation.
     * @param locDescription locDescription
     * @param locTypeName locTypeName
     * @param Level Level
     * @return location {@link CatLocationFolder}.
     */
    public CatLocation createCatLocation(
                       final String locDescription,
                       final String locTypeName,
                       final Integer Level,
                       final SecUser secUsers,
                       final CatLocationFolder catLocationFolder){
        final CatLocation location = new CatLocation();
        location.setLocationStatus(Status.ACTIVE);
        location.setLocationDescription(locDescription);
        location.setLocationLatitude(2F);
        location.setSecUsers(secUsers);
        location.setCatLocationFolder(catLocationFolder);
        location.setLocationLongitude(3F);
        location.setTidtype(createCatLocationType(locTypeName));
        getCatLocationDao().saveOrUpdate(location);
      return location;
    }

    /**
     * Create Default Location.
     * @param locDescription description.
     * @param locTypeName type
     * @param Level level
     * @param secUsers {@link SecUser}.
     * @return
     */
    public CatLocation createCatLocation(
            final String locDescription,
            final String locTypeName,
            final Integer Level,
            final SecUser secUsers){
    return this.createCatLocation(locDescription, locTypeName, Level, secUsers, null);
    }


    /**
     * Helper to create Group.
     * @param groupname user name
     * @return state
     */
    public SecGroup createGroups(final String groupname){
        return createGroups(groupname, createUser());
    }

    public SecGroup createGroups(final String groupname, final SecUser secUser){
        final SecGroup group = new SecGroup();
        group.setSecUsers(secUser);
        group.setGroupName(groupname);
        group.setIdState(1L);
        group.setGroupType(Type.SECURITY);
        group.setGroupDescriptionInfo("First Group");
        getSecGroup().saveOrUpdate(group);
        return group;
    }

    /**
     * Helper to create Permission.
     * @param permissionName name
     * @return Permission
     */
    public SecPermission createPermission(final String permissionName){
        final SecPermission permission = new SecPermission();
        permission.setPermissionDescription(permissionName);
        permission.setPermission(permissionName);
        getSecPermissionDaoImp().saveOrUpdate(permission);
        return permission;
    }

    /**
     * Helper to add permission to user.
     * @param user user
     * @param permission permission
     */
    public void addPermissionToUser(final SecUser user, final SecPermission permission){
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
            final SecUserSecondary user,
            final SecGroup group)
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
            final SecPermission permission,
            final SecGroup group)
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
     * @param patron patron
     * @return {@link Question}
     */
    public Question createQuestion(final String question, String patron){
        final Question questions = new Question();
        questions.setCatState(this.createState("active"));
        questions.setQidKey("1");
        questions.setQuestion(question);
        questions.setQuestionPattern(createQuestionPattern(patron));
        questions.setSecUsersQuestion(createUser());
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
    public Question createQuestion(final String questionName, final SecUser user){
        final Question question =  createQuestion(questionName, "patter");
        question.setSecUsersQuestion(user);
        getQuestionDaoImp().saveOrUpdate(question);
        //log.info("user assigned "+question.getSecUsersQuestion().getUid());
        return question;
    }

    /**
     * Create question.
     * @param question question
     * @param patron patron
     * @param user user
     * @return {@link Question}
     */
    public Question createQuestion(final String question, final String patron, final SecUser user){
        final Question questions = new Question();
        questions.setCatState(this.createState("active"));
        questions.setQidKey("1");
        questions.setSecUsersQuestion(user);
        questions.setQuestion(question);
        questions.setQuestionPattern(createQuestionPattern(patron));
        getQuestionDaoImp().saveOrUpdate(questions);
        return questions;
    }

    /**
     * Create Question Answer.
     * @param answer answer
     * @param question question
     * @param hash hash
     * @return {@link QuestionsAnswers}
     */
    public QuestionsAnswers createQuestionAnswer(final String answer, final Question question, final String hash){
        final QuestionsAnswers questionsAnswers = new QuestionsAnswers();
        questionsAnswers.setAnswer(answer);
        questionsAnswers.setQuestions(question);
        questionsAnswers.setUniqueAnserHash(hash);
        questionsAnswers.setAnswerType(AnswerType.DEFAULT);
        getQuestionDaoImp().saveOrUpdate(questionsAnswers);
        //log.info("Q "+questionsAnswers.getQuestionAnswerId());
        return questionsAnswers;
    }

    /**
     * Create Patron Domain.
     * @param typePatron name of patron
     * @return {@link QuestionPattern}
     */
    public QuestionPattern createQuestionPattern(final String typePatron){
        final QuestionPattern patron = new QuestionPattern();
        patron.setDesQid("patron Html");
        patron.setPatternTemplate("1");
        patron.setLabelQid("1");
        patron.setPatternType(typePatron);
        patron.setLevel(1);
        patron.setFinallity("save");
        //TODO: need patron dao to save this domain.
        getQuestionDaoImp().saveOrUpdate(patron);
        return patron;
    }

    /**
     *Helper to Create Survey Group.
     * @param surveyGroupName surveyGroupName
     * @return {@link SurveyGroup}
     *
     **/
    public SurveyGroup createSurveyGroup(String surveyGroupName){
        final SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setCatState(createState("editor"));
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
        qCollection.setSecUsers(createUser());
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
             Date publicationDateTweet,
             Boolean completed,
             SecUser tweetOwner,
             Question question){
        final TweetPoll tweetPoll = new TweetPoll();
        tweetPoll.setCloseNotification(closeNotification);
        tweetPoll.setResultNotification(resultNotification);
        tweetPoll.setAllowLiveResults(allowLiveResults);
        tweetPoll.setCompleted(completed);
        tweetPoll.setPublishTweetPoll(publishTweetPoll);
        tweetPoll.setQuestion(question);
        tweetPoll.setScheduleDate(scheduleDate);
        tweetPoll.setScheduleTweetPoll(scheduleTweetPoll);
        //tweetPoll.setTweetId(tweetId);
        tweetPoll.setTweetOwner(tweetOwner);
        getTweetPoll().saveOrUpdate(tweetPoll);
        return tweetPoll;
    }

    /**
     * Create Published {@link TweetPoll}.
     * @param tweetOwner tweet owner
     * @param question question
     * @return {@link TweetPoll}
     */
    public TweetPoll createPublishedTweetPoll(final SecUser tweetOwner, final Question question){
       return createTweetPoll(12345L, false, false, false, true, false, new Date(), new Date(), false, tweetOwner, question);
    }

    /**
     * Create Not Published {@link TweetPoll}.
     * @param tweetOwner tweet owner
     * @param question question
     * @return {@link TweetPoll}
     */
    public TweetPoll createNotPublishedTweetPoll(final SecUser tweetOwner, final Question question){
       return createTweetPoll(null, false, false, false, false, false, new Date(), null, false, tweetOwner, question);
    }

    /**
     * Create {@link TweetPollSwitch}.
     * @param questionsAnswers  {@link QuestionsAnswers}.
     * @param tweetPollDomain {@link TweetPoll}.
     * @return {@link TweetPollSwitch}.
     */
    public TweetPollSwitch createTweetPollSwitch(final QuestionsAnswers questionsAnswers, final TweetPoll tweetPollDomain){
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
     * Create Fast TweetPoll Votes.
     * @return tweet poll
     */
    public TweetPoll createFastTweetPollVotes(){
        final SecUserSecondary secondary = createSecondaryUser("jhon", createUser());
        final Question question = createQuestion("who I am?", "");
        final QuestionsAnswers questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
        final QuestionsAnswers questionsAnswers2 = createQuestionAnswer("no", question, "12346");
        final TweetPoll tweetPoll = createPublishedTweetPoll(secondary.getSecUser(), question);
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
     * Create {@link CatLocationFolder}.
     * @param type {@link LocationFolderType}.
     * @param locationFolderId folder Id
     * @param secUsers {@link SecUser}.
     * @param folderName name
     * @param locationFolder
     * @return {@link CatLocationFolder}.
     */
    public CatLocationFolder createCatLocationFolder(
            final LocationFolderType type,
            final SecUser secUsers,
            final String folderName,
            final CatLocationFolder locationFolder){
        final CatLocationFolder catLocationFolder = new CatLocationFolder();
        catLocationFolder.setFolderType(type);
        catLocationFolder.setLocationFolderName(folderName);
        catLocationFolder.setSecUsers(secUsers);
        catLocationFolder.setSubLocationFolder(locationFolder);
        getCatLocationDao().saveOrUpdate(catLocationFolder);
        return catLocationFolder;
    }

    /**
     * Helper Create Survey Section.
     * @param catState
     * @param descSection
     * @return
     */
    public SurveySection createSurveySection(
            final CatState catState,
            final String descSection){
        final SurveySection surveySection = new SurveySection();
        surveySection.setCatState(catState);
        surveySection.setDescSection(descSection);
        getSurveyDaoImp().saveOrUpdate(surveySection);
        surveySection.getQuestionSection().add(createDefaultQuestion("Why is your favourite movie"));
        surveySection.getQuestionSection().add(createDefaultQuestion("Where do you live"));
        surveySection.getQuestionSection().add(createDefaultQuestion("What do you do at home"));
        getSurveyDaoImp().saveOrUpdate(surveySection);
        return surveySection;
    }

    /**
     * Create Defaul Survey Pagination.
     * @param surveySection
     * @return
     */
    public SurveyPagination createDefaultSurveyPagination(final SurveySection surveySection){
        return this.createSurveyPagination(1, surveySection,this.createDefaultSurvey(this.createUser()));
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
            final Surveys survey){
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
    public Surveys createDefaultSurvey(final SecUser secUsers ){
        return this.createSurvey("", new Date(), new Date(), secUsers, new Date(), createDefaultSurveyFormat(),"FirstSurvey");
     }

    /**
     * Create {@link Surveys}
     * @param complete
     * @param dateInterview
     * @param endDate
     * @param secUsers
     * @param startDate
     * @param surveyFormat
     * @return
     */
   public Surveys createSurvey(
           final String complete,
           final Date dateInterview,
           final Date endDate,
           final SecUser secUsers,
           final Date startDate,
           final SurveyFormat surveyFormat,
           final String name

           ){
       final Surveys survey = new Surveys();
       survey.setName(name);
       survey.setComplete(complete);
       survey.setDateInterview(dateInterview);
       survey.setEndDate(endDate);
       survey.setSecUsers(secUsers);
       survey.setStartDate(startDate);
       survey.setSurveyFormat(surveyFormat);
       survey.setTicket(3);
       survey.setTicket(2);
       getSurveyDaoImp().saveOrUpdate(survey);
       return survey;
   }




    /**
     * Create Default List Email.
     * @param user
     * @param list
     * @return
     */
    public CatEmailLists createDefaultListEmail(final SecUser user,final String list){
        return this.createListEmails(user, list, new Date());
    }

    /**
     * Create Default List Email.
     * @return
     */
    public CatEmailLists createDefaultListEmail(){
        return this.createListEmails(createUser(), "default", new Date());
    }

    /**
     * Create Default Email List.
     * @param list list Name
     * @return
     */

    public CatEmailLists createDefaultListEmail(final String list){
        return this.createListEmails(createUser(), list, new Date());
    }

    /**
     *Create Default Email List.
     * @param user
     * @return
     */
    public CatEmailLists createDefaultListEmail(final SecUser user){
        return this.createListEmails(user, "default", new Date());
    }

    /**
     * Create Email List.
     * @return
     */
    public CatEmailLists createListEmails(
                final SecUser users,
                final String listName,
                final Date createDate){
            final CatEmailLists catListEmails = new CatEmailLists();
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
    public CatEmails createDefaultEmails(final String email){
        return this.createEmails(email, createDefaultListEmail());
    }

    /**
     * Create Default Emails.
     * @param email
     * @param listEmail
     * @return
     */
    public CatEmails createDefaultEmails(final String email, final CatEmailLists listEmail){
        return this.createEmails(email, listEmail);
    }
    /**
     * Create Emails.
     * @param email
     * @param list
     * @return
     */
    public CatEmails createEmails(
                final String email,
                final CatEmailLists list){
            final CatEmails emails = new CatEmails();
            emails.setEmail(email);
            emails.setIdListEmail(list);
            getCatEmailDao().saveOrUpdate(emails);
         return emails;
    }


    /**
     * Create {@link SecUserTwitterAccounts}.
     * @param consumerKey
     * @param consumerSecret
     * @param secretToken
     * @param secUsers
     * @param twitterAccount
     * @return
     */
    public SecUserTwitterAccounts createTwitterAccount(
            final String consumerKey,
            final String consumerSecret,
            final String token,
            final String secretToken,
            final Integer twitterPin,
            final SecUser secUsers,
            final String twitterAccount){
        final SecUserTwitterAccounts secUserTwitterAccounts = new SecUserTwitterAccounts();
        secUserTwitterAccounts.setConsumerKey(consumerKey);
        secUserTwitterAccounts.setConsumerSecret(consumerSecret);
        secUserTwitterAccounts.setToken(token);
        secUserTwitterAccounts.setSecretToken(secretToken);
        secUserTwitterAccounts.setSecUsers(secUsers);
        secUserTwitterAccounts.setTwitterPin(twitterPin);
        secUserTwitterAccounts.setVerfied(Boolean.FALSE);
        secUserTwitterAccounts.setTwitterAccount(twitterAccount);
        secUserTwitterAccounts.setTwitterPassword("not valid");
        getSecUserDao().saveOrUpdate(secUserTwitterAccounts);
        return secUserTwitterAccounts;
     }

    /**
     * Create Default Setted User.
     * @param secUsers {@link SecUser}.
     * @return {@link SecUserTwitterAccounts}.
     */
    public SecUserTwitterAccounts createDefaultSettedTwitterAccount(final SecUser secUsers){
        return this.createTwitterAccount(getProperty("twitter.test.consumerKey"),
                getProperty("twitter.test.consumerSecret"),
                getProperty("twitter.test.token"),
                getProperty("twitter.test.tokenSecret"),
                12345,
                secUsers,
                getProperty("twitter.test.account"));
    }

  /**
   *
   * @param folderName
   * @param users
   * @return
   */
    public SurveyFolder createSurveyFolders(final String folderName, final SecUser users){
        final SurveyFolder surveyFolders = new SurveyFolder();
        surveyFolders.setCreatedAt(new Date());
        surveyFolders.setFolderName(folderName);
        surveyFolders.setUsers(users);
        getSurveyDaoImp().saveOrUpdate(surveyFolders);
        return surveyFolders;
    }


    /**
     * Create {@link PollFolder}.
     * @param folderName folder name
     * @param users {@link SecUser}
     * @return {@link PollFolder}.
     */
    public PollFolder createPollFolder(final String folderName, final SecUser users){
        final PollFolder folder = new PollFolder();
        folder.setCreatedAt(new Date());
        folder.setFolderName(folderName);
        folder.setUsers(users);
        getiPoll().saveOrUpdate(folder);
        return folder;
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
     * @param secUser {@link SecUser}.
     * @param description {@link NotificationEnum}.
     */
    public Notification createNotification(final String message, final SecUser secUser, final NotificationEnum description){
         final Notification notification = new Notification();
         notification.setAdditionalDescription(message);
         notification.setCreated(new Date());
         notification.setDescription(description);
         notification.setReaded(Boolean.FALSE);
         notification.setSecUser(secUser);
         getNotification().saveOrUpdate(notification);
         return notification;
    }

    /**
     * @return the notification
     */
    public INotification getNotification() {
        return notification;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(final INotification notification) {
        this.notification = notification;
    }
}

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
package org.encuestame.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.ProfileUserAccount;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.QuestionPatternBean;
import org.encuestame.utils.json.ResumeResultTweetPoll;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollAnswerSwitchBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.mail.InvitationBean;
import org.encuestame.utils.mail.NotificationBean;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.oauth.StandardOAuthSession;
import org.encuestame.utils.security.ForgotPasswordBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.social.SocialNetworkBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.vote.UtilVoteCaptcha;
import org.encuestame.utils.web.CommentBean;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;
import org.encuestame.utils.web.GadgetPropertiesBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.encuestame.utils.web.TypeTreeNode;
import org.encuestame.utils.web.UnitCatStateBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitSessionUserBean;
import org.encuestame.utils.web.UnitSurveyFormat;
import org.encuestame.utils.web.UserAccountBean;
import org.encuestame.utils.web.UtilTreeNode;
import org.encuestame.utils.web.frontEnd.UnitSearchItem;
import org.encuestame.utils.web.notification.UtilNotification;
import org.junit.Before;
import org.junit.Test;

    /**
    * Test Unit Beans.
    *
    * @author Morales, Diana Paola paolaATencuestame.org
    * @since 13/03/2010 16:18:10
    * @version $Id: $
    **/
public class TestUnitBeans extends AbstractBaseUtils {

    /** {@link QuestionAnswerBean}. **/
    private QuestionAnswerBean questionAnswer;

    /** {@link TweetPollResultsBean}. **/
    private TweetPollResultsBean tpResultsBean;

    /** {@link TweetPollBean}. **/
    private TweetPollBean tpBean;

    /** {@link DashboardBean}.**/
    private DashboardBean myDashboardBean;

    /** {@link UserAccountBean}. **/
    private UserAccountBean myUserAccBean;

    @Before
    public void initService(){




       this.myDashboardBean = createDashboardBean("Store all surveys created",
                "Survey Dash", Boolean.TRUE, 8, "AAA", Boolean.TRUE, 2);
    }

    /**
    * Test Unit Answers Bean.
    **/
    @Test
    public void testUnitAnswerBean() {
        final QuestionAnswerBean unitAnswer = createUnitAnswerBean(1L, "yes",
                "HASH", 2L);
        assertNotNull(unitAnswer.getAnswerHash());
        assertNotNull(unitAnswer.getAnswers());
        assertNotNull(unitAnswer.getAnswerId());
        assertNotNull(unitAnswer.getUrl());
        assertNotNull(unitAnswer.getQuestionId());
    }

    @Test
    public void testUtilTreeNode(){
        final UtilTreeNode treeNode = new UtilTreeNode();
        treeNode.setId(1L);
        treeNode.setName("test node");
        treeNode.setNode(TypeTreeNode.FOLDER);
        assertNotNull(treeNode.getId());
        assertNotNull(treeNode.getName());
        assertNotNull(treeNode.getNode().name());
        treeNode.setNode(TypeTreeNode.ITEM);
    }

    /**
    * Test Unit Pattern Bean.
    */
    @Test
    public void testUnitPatternBean() {
        final QuestionPatternBean unitPattern = createUnitPatternBean("b", "", "",
                2L, "", "", "", 1, "");
        assertNotNull(unitPattern);
    }

    /**
     * Test for {@link UserAccountBean}.
     */
    @Test
    public void testUnitUserBean(){
        final UserAccountBean userBean = new UserAccountBean();
        userBean.setId(1L);
        userBean.setDateNew(new Date());
        userBean.setEmail("juanATencuestame.org");
        userBean.setInviteCode("code");
        userBean.setName("name");
        userBean.setPassword("password");
        userBean.setPrimaryUserId(1L);
        userBean.setStatus(Boolean.TRUE);
        userBean.setUsername("username");
        final Collection<UnitPermission> listPermission = new HashSet<UnitPermission>();
        userBean.setListPermission(listPermission);
        final Collection<UnitGroupBean> listGroups = new HashSet<UnitGroupBean>();
        userBean.setListGroups(listGroups);
        assertNotNull(userBean.getId());
        assertNotNull(userBean.getDateNew());
        assertNotNull(userBean.getEmail());
        assertNotNull(userBean.getInviteCode());
        assertNotNull(userBean.getName());
        assertNotNull(userBean.getPassword());
        assertNotNull(userBean.getPrimaryUserId());
        assertNotNull(userBean.getStatus());
        assertNotNull(userBean.getUsername());
        assertEquals(userBean.getListGroups().size(), 0);
        assertEquals(userBean.getListPermission().size(), 0);
    }

    /**
     * Unit Tweet Poll.
     */
    @Test
    public void testUnitTweetPoll(){
        final Date myDate = new Date();
        final TweetPollBean tweetPoll = new TweetPollBean();
        tweetPoll.setId(1L);
        tweetPoll.setAllowLiveResults(true);
        tweetPoll.setCloseNotification(true);
        tweetPoll.setPublishPoll(true);
        tweetPoll.setUserId(1L);
        tweetPoll.setQuestionBean(new QuestionBean());
        tweetPoll.setResultNotification(true);
        List<ResumeResultTweetPoll> results = new ArrayList<ResumeResultTweetPoll>();
        tweetPoll.setResults(results);
        tweetPoll.setSchedule(true);
        tweetPoll.setScheduleDate(myDate);
        tweetPoll.setCompleted(true);
        tweetPoll.setTweetUrl("http://www.encuestame.org");
      //  tweetPoll.setTwitterUserAccount("@encuestame");
        assertNotNull(tweetPoll.getCompleted());
    //    assertNotNull(tweetPoll.getTwitterUserAcoount());
    //    assertNotNull(tweetPoll.getTweetUrl());
    //    assertNotNull(tweetPoll.getTweetUrl());

        tweetPoll.setCaptcha(true);
        tweetPoll.setLimitVotes(12345);
        tweetPoll.setResumeLiveResults(true);
        tweetPoll.setCreateDate(myDate.toString());
        tweetPoll.setFavorite(Boolean.TRUE);
        assertNotNull(tweetPoll.getScheduleDate());
        assertNotNull(tweetPoll.getId());
        assertNotNull(tweetPoll.getAllowLiveResults());
        assertNotNull(tweetPoll.getCloseNotification());
        assertNotNull(tweetPoll.getPublishPoll());
        assertNotNull(tweetPoll.getQuestionBean());
        assertNotNull(tweetPoll.getResultNotification());
        assertNotNull(tweetPoll.getSchedule());
        assertNotNull(tweetPoll.getUserId());
        assertEquals(tweetPoll.getResults().size(), 0);
        assertNotNull(tweetPoll.getCaptcha());
        assertNotNull(tweetPoll.getLimitVotes());
        assertNotNull(tweetPoll.getResumeLiveResults());
    }

    /**
     * Test {@link SignUpBean}.
     */
    @Test
    public void testSignUpBean(){
        final SignUpBean singUpBean = new SignUpBean();
        singUpBean.setEmail("juanATencuestame.org");
        singUpBean.setFullName("Juan");
        singUpBean.setPassword("12345");
        singUpBean.setUsername("jotadeveloper");
        assertNotNull(singUpBean.getEmail());
        assertNotNull(singUpBean.getFullName());
        assertNotNull(singUpBean.getPassword());
        assertNotNull(singUpBean.getUsername());
    }

    /**
     * Test {@link UnitGroupBean}.
     */
    @Test
    public void testUnitGroupBean(){
        final UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(1L);
        groupBean.setGroupDescription("group description");
        groupBean.setGroupName("group name");
        groupBean.setStateId(1L);
        assertNotNull(groupBean.getId());
        assertNotNull(groupBean.getStateId());
        assertNotNull(groupBean.getGroupDescription());
        assertNotNull(groupBean.getGroupName());
        final UnitGroupBean groupBean2 = new UnitGroupBean(1L, "group", "description");
        assertNotNull(groupBean2);
        assertNotNull(groupBean2.toString());
    }

    /**
     * Test {@link UnitPermission}.
     */
    @Test
    public void testUnitPermission(){
        final UnitPermission permission = new UnitPermission(1L);
        permission.setId(1L);
        permission.setDescription("description");
        permission.setPermission("admin");
        assertNotNull(permission.getId());
        assertNotNull(permission.getDescription());
        assertNotNull(permission.getPermission());
        new UnitPermission();
    }

    /**
     * Test {@link PollBean}.
     */
    @Test
    public void testUnitPoll(){
        final PollBean poll = new PollBean();
        poll.setId(1L);
        poll.setCompletedPoll(true);
        poll.setCreateDate(DateUtil.DOJO_DATE_FORMAT.format(new Date()));
        poll.setQuestionBean(new QuestionBean());
        poll.setFinishDate(new Date());
        poll.setPublishPoll(true);
        poll.setCloseNotification(true);
        poll.setHashTags(new ArrayList<HashTagBean>());
        poll.setShowResultsPoll(true);
        assertNotNull(poll.getId());
        assertNotNull(poll.getHashTags());
        assertNotNull(poll.getQuestionBean());
        assertNotNull(poll.getCompletedPoll());
        //assertNotNull(poll.getCreationDate());
        assertNotNull(poll.getFinishDate());
        assertNotNull(poll.getPublishPoll());
        assertNotNull(poll.getCloseNotification());
        assertNotNull(poll.getShowResultsPoll());
    }

    /**
     * Test {@link PollBeanResult}.
     */
    @Test
    public void testUnitPollResult(){
        final PollBeanResult pollResult =  new PollBeanResult();
        pollResult.setAnswerBean(new QuestionAnswerBean());
        pollResult.setPoll( new PollBean());
        pollResult.setResult(1L);
        pollResult.setVotedDate(new Date());
        assertNotNull(pollResult.getAnswerBean());
        assertNotNull(pollResult.getPoll());
        assertNotNull(pollResult.getResult());
        assertNotNull(pollResult.getVotedDate());
    }

    /**
     * Test {@link UnitProjectBean}.
     */
    @Test
    public void testUnitProjectBean(){
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setId(1L);
        projectBean.setDateFinish(new Date());
        projectBean.setDateInit(new Date());
        projectBean.setHide(true);
        projectBean.setLeader(1L);
        projectBean.setName("name");
        projectBean.setNotify(true);
        projectBean.setPriority("HIGH");
        projectBean.setState("Good");
        projectBean.setStatus(1L);
        projectBean.setUserId(1L);
        projectBean.setDescription("description");
        assertNotNull(projectBean.getDateFinish());
        assertNotNull(projectBean.getDateInit());
        assertNotNull(projectBean.getHide());
        assertNotNull(projectBean.getLeader());
        assertNotNull(projectBean.getName());
        assertNotNull(projectBean.getNotify());
        assertNotNull(projectBean.getPriority());
        assertNotNull(projectBean.getState());
        assertNotNull(projectBean.getStatus());
        assertNotNull(projectBean.getUserId());
        assertNotNull(projectBean.toString());
    }
    /**
     * Test {@link org.encuestame.utils.web.UnitLocationBean}.
     */
    @Test
    public void testUnitLocationBean(){
        final org.encuestame.utils.web.UnitLocationBean locationBean = new org.encuestame.utils.web.UnitLocationBean();
        locationBean.setId(1L);
        locationBean.setLat(12.54F);
        locationBean.setLng(84.23F);
        locationBean.setLevel(1);
        locationBean.setName("location");
        locationBean.setStatus("ACTIVE");
        locationBean.setAddress("address");
        locationBean.setCountryCode("ESP");
        locationBean.setCountryName("spain");
        locationBean.setAccuracy(12345);
        locationBean.setTidtype(2L);
        assertNotNull(locationBean.getId());
        assertNotNull(locationBean.getLat());
        assertNotNull(locationBean.getLevel());
        assertNotNull(locationBean.getLng());
        assertNotNull(locationBean.getStatus());
        assertNotNull(locationBean.getName());
        assertNotNull(locationBean.getTidtype());
    }

    /**
     * Test {@link UnitLocationFolder}.
     */
    @Test
    public void testUnitLocationFormatBean(){
        final UnitLocationFolder locationFolder = new UnitLocationFolder();
        locationFolder.setId(1L);
        locationFolder.setName("folder");
        locationFolder.setType("type");
        locationFolder.setUnitUserBean(new UserAccountBean());
        assertNotNull(locationFolder.getId());
        assertNotNull(locationFolder.getName());
        assertNotNull(locationFolder.getUnitUserBean());
        assertNotNull(locationFolder.getType());
    }
    /**
     * Test {@link UnitSessionUserBean}.
     */
    @Test
    public void testUnitSessionUserBean(){
        final UnitSessionUserBean sessionUserBean = new UnitSessionUserBean();
        sessionUserBean.setConsumerTwitterKey("12345");
        sessionUserBean.setConsumerTwitterSecret("secret");
        sessionUserBean.setTwitterAccount("twitter");
        sessionUserBean.setTwitterPassword("password");
        sessionUserBean.setTwitterTwitterPing(12345);
        sessionUserBean.setUserSessionId(1L);
        assertNotNull(sessionUserBean.getConsumerTwitterKey());
        assertNotNull(sessionUserBean.getConsumerTwitterSecret());
        assertNotNull(sessionUserBean.getTwitterAccount());
        assertNotNull(sessionUserBean.getTwitterPassword());
        assertNotNull(sessionUserBean.getTwitterTwitterPing());
        assertNotNull(sessionUserBean.getUserSessionId());
    }

    /**
     * Test {@link ResumeResultTweetPoll}.
     */
    @Test
    public void testResumeResultTweetPoll(){
        final ResumeResultTweetPoll resultTweetPoll = new ResumeResultTweetPoll("label", 12345);
        resultTweetPoll.setLabel("label");
        resultTweetPoll.setResult(12345);
        assertNotNull(resultTweetPoll.getLabel());
        assertNotNull(resultTweetPoll.getResult());
    }

    /**
     * Test {@link UnitCatStateBean}.
     */
    @Test
    public void testUnitCatStateBean(){
        final UnitCatStateBean stateBean = new UnitCatStateBean();
        stateBean.setId(1L);
        stateBean.setState("ACTIVE");
        assertNotNull(stateBean.getState());
        assertNotNull(stateBean.getId());
    }

    /**
     * Test {@link UnitLocationTypeBean}.
     */
    @Test
    public void testUnitLocationTypeBean(){
        final UnitLocationTypeBean typeBean = new UnitLocationTypeBean();
        typeBean.setIdLocType(1L);
        typeBean.setLevel(1);
        typeBean.setLocTypeDesc("test");
        assertNotNull(typeBean.getLocTypeDesc());
        assertNotNull(typeBean.getLevel());
        assertNotNull(typeBean.getIdLocType());
    }

    /**
     * Test {@link QuestionBean}.
     */
    @Test
    public void testUnitQuestionBean(){
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setId(1L);
        questionBean.setListAnswers(new ArrayList<QuestionAnswerBean>());
        questionBean.setPattern(new QuestionPatternBean());
        questionBean.setQuestionName("Why sky is blue?");
        questionBean.setStateId(1L);
        questionBean.setUserId(1L);
        questionBean.setVersion("1.0");
        questionBean.setSlugName("Why-sky-is-blue-");
        questionBean.setHits(10L);
        assertNotNull(questionBean.getId());
        assertEquals(questionBean.getListAnswers().size(), 0);
        assertNotNull(questionBean.getPattern());
        assertNotNull(questionBean.getQuestionName());
        assertNotNull(questionBean.getStateId());
        assertNotNull(questionBean.getUserId());
        assertNotNull(questionBean.getVersion());
    }

    /**
     * Test {@link QuestionPatternBean}.
     */
    @Test
    public void testUnitPatterBean(){
        final QuestionPatternBean patternBean = new QuestionPatternBean();
        patternBean.setId(1L);
        patternBean.setClasspattern("class.class");
        patternBean.setDescripcion("description");
        patternBean.setFinallity("final");
        patternBean.setLabel("yes/no");
        patternBean.setLevelpattern("level");
        patternBean.setPatronType("option");
        patternBean.setTemplate("template");
        assertNotNull(patternBean.getId());
        assertNotNull(patternBean.getClasspattern());
        assertNotNull(patternBean.getDescripcion());
        assertNotNull(patternBean.getFinallity());
        assertNotNull(patternBean.getLabel());
        assertNotNull(patternBean.getLevelpattern());
        assertNotNull(patternBean.getPatronType());
        assertNotNull(patternBean.getTemplate());
    }

    /**
     * Test {@link UnitEmails}.
     */
    @Test
    public void testUnitEmails(){
        final UnitEmails email = new UnitEmails();
        email.setEmailName("juanATencuestame.org");
        email.setIdEmail(1L);
        email.setListsId(12345L);
        assertNotNull(email.getEmailName());
        assertNotNull(email.getListsId());
        assertNotNull(email.getListsId());
    }

    /**
     * Test.
     */
    @Test
    public void testUtilVoteCaptcha(){
        final UtilVoteCaptcha captcha = new UtilVoteCaptcha();
        captcha.setCaptcha("12345");
        captcha.setCodeVote("12345");
        assertNotNull(captcha.getCaptcha());
        assertNotNull(captcha.getCodeVote());
    }

    /**
     * Test.
     */
    @Test
    public void UtilNotification(){
        final org.encuestame.utils.web.notification.UtilNotification notification = new UtilNotification();
        notification.setAdditionalDescription("add");
        notification.setDate("12345");
        notification.setDescription("des");
        notification.setHour("1");
        notification.setIcon("icon");
        notification.setId(1L);
        notification.setType("type");
        assertNotNull(notification.getAdditionalDescription());
        assertNotNull(notification.getDate());
        assertNotNull(notification.getDescription());
        assertNotNull(notification.getHour());
        assertNotNull(notification.getIcon());
        assertNotNull(notification.getType());
    }

    /**
     * Test.
     */
    @Test
    public void testUnitSurveyFormat(){
        final UnitSurveyFormat format = new UnitSurveyFormat();
    }

    /**
     * Test.
     */
    @Test
    public void testUnitForgotPassword(){
        final ForgotPasswordBean password = new ForgotPasswordBean();
        password.setCaptcha("catpcha");
        password.setEmail("juanATencuestame.org");
        assertNotNull(password.getCaptcha());
        assertNotNull(password.getEmail());
    }

    /**
     * Test.
     */
    @Test
    public void testUnitHashTag(){
        final HashTagBean hashTag = new HashTagBean();
        hashTag.setHashTagName("nicaragua");
        hashTag.setId(1L);
        assertNotNull(hashTag.getHashTagName());
        assertNotNull(hashTag.getId());
    }

    /**
     * Test.
     */
    @Test
    public void testUnitTwitterAccountBean(){
        final SocialAccountBean accountBean = new SocialAccountBean();
        accountBean.setAccount("account");
        accountBean.setAccountId(1L);
        accountBean.setType("type");
        accountBean.setTypeAccount("typeAccount");
        accountBean.setDescriptionProfile("my description profile");
        accountBean.setEmail("jhonny@encuestame.org");
        accountBean.setDefaultSelected(Boolean.FALSE);
        accountBean.setAddedAccount(new Date());
        accountBean.setPrictureUrl("/encuestame/user");
        accountBean.setProfilePictureUrl("/encuestame/user/profile");
        accountBean.setRealName("Jhonny");
        accountBean.setSocialAccountName("Jhonny");
    }

    /**
     * Test.
     */
    @Test
    public void testInvitationBean(){
        final InvitationBean ib = new InvitationBean();
        ib.setCode("code");
        ib.setEmail("juan@encuestame.org");
        ib.setListName("list");
        ib.setUrlInvitation("url");
        assertNotNull(ib.getCode());
        assertNotNull(ib.getEmail());
        assertNotNull(ib.getListName());
        assertNotNull(ib.getUrlInvitation());
    }

    /**
     * Test.
     */
    @Test
    public void testNotificationBean(){
        final NotificationBean ib = new NotificationBean();
        ib.setCode("code");
        ib.setEmail("juan@encuestame.org");
        ib.setMessage("message");
        ib.setUrlUnsuscribe("unUrl");
        assertNotNull(ib.getCode());
        assertNotNull(ib.getEmail());
        assertNotNull(ib.getMessage());
        assertNotNull(ib.getUrlUnsuscribe());
    }

    /**
     * Test {@link UtilNotification}.
     */
    @Test
    public void testUtilNotification(){
        final UtilNotification nt = new UtilNotification();
        nt.setAdditionalDescription("additional des");
        nt.setDate("1 hour");
        nt.setDescription("description");
        nt.setHour("1");
        nt.setIcon("icon");
        nt.setId(1L);
        nt.setType("TWEETPOLL");
        assertNotNull(nt.getAdditionalDescription());
        assertNotNull(nt.getDate());
        assertNotNull(nt.getDescription());
        assertNotNull(nt.getHour());
        assertNotNull(nt.getIcon());
        assertNotNull(nt.getType());
    }

    /**
     * Test {@link UnitSearchItem}.
     */
    @Test
    public void testUnitSearchItem(){
        final UnitSearchItem si = new UnitSearchItem();
        si.setPolls(new ArrayList<PollBean>());
        si.setTweetPolls(new ArrayList<TweetPollBean>());
        assertNotNull(si.getPolls());
        assertNotNull(si.getTweetPolls());
    }

    /**
     * Test {@link AuthorizedRequestToken} and {@link OAuth1Token}.
     */
    @Test
    public void testAuthorizedRequestToken(){
        final OAuth1Token token = new OAuth1Token("value", "secret");
//        final AuthorizedRequestToken tk = new AuthorizedRequestToken(token, "veri");
//        assertNotNull(tk.getSecret());
//        assertNotNull(tk.getValue());
//        assertNotNull(tk.getVerifier());
//        assertNotNull(token.getSecret());
//        assertNotNull(token.getValue());
        final StandardOAuthSession oa = new StandardOAuthSession("api", "call", "token", "secret");
        oa.authorize(1235L, "veri");
        assertNotNull(oa.getApiKey());
        assertNotNull(oa.getCallbackUrl());
        assertNotNull(oa.getRequestToken());
        assertNotNull(oa.getSecret());
        assertNotNull(oa.getVerifier());
        assertNotNull(oa.getAuthorizingAccountId());
    }

    /**
     * Test {@link DashboardBean}
     */
    @Test
    public void testDashboardBean(){
        final DashboardBean boardBean = new DashboardBean();
        boardBean.setDashboardId(1L);
        boardBean.setDashboardName("My Dashboard");
        boardBean.setDashboardDesc("My First Dashboard");
        boardBean.setFavorite(Boolean.TRUE);
        boardBean.setLayout("AAA");
        boardBean.setSequence(1);
        boardBean.setFavoriteCounter(5);
        boardBean.setSelected(Boolean.TRUE);
        assertNotNull(boardBean);
        assertNotNull(boardBean.getDashboardId());
        assertNotNull(boardBean.getDashboardName());
        assertNotNull(boardBean.getDashboardDesc());
        assertNotNull(boardBean.getFavorite());
        assertNotNull(boardBean.getLayout());
        assertNotNull(boardBean.getSequence());
        assertNotNull(boardBean.getFavoriteCounter());
        assertNotNull(boardBean.getSelected());
    }

    /**
     * Test {@link ProfileUserAccount}.
     */
    @Test
    public void testProfileUserAccount(){
        final ProfileUserAccount profile = new ProfileUserAccount();
        profile.setEmail("jhonny@encuestame.org");
        profile.setLanguage("Sp");
        profile.setName("Jhonny English");
        profile.setPrivateProfile(Boolean.TRUE);
        profile.setUsername("jhonny");
        assertNotNull(profile);
        assertNotNull(profile.getEmail());
        assertNotNull(profile.getLanguage());
        assertNotNull(profile.getName());
        assertNotNull(profile.getPrivateProfile());
        assertNotNull(profile.getUsername());
    }

    /**
     * Test {@link FolderBean}
     */
    @Test
    public void testFolderBean(){
        FolderBean folderBean = new FolderBean();
        folderBean.setId(2L);
        folderBean.setCreateAt(new Date());
        folderBean.setFolderName("My folder");
        assertNotNull(folderBean);
        assertNotNull(folderBean.getId());
        assertNotNull(folderBean.getCreateAt());
        assertNotNull(folderBean.getFolderName());
    }

    /**
     * Test {@link LinksSocialBean}
     */
    @Test
    public void testLinkSocial() {
        LinksSocialBean linkSocial = new LinksSocialBean();
        linkSocial.setLink("https:/xxxx.xxxx.xxx");
        linkSocial.setProvider("TWITTER");
        assertNotNull(linkSocial);
        assertNotNull(linkSocial.getLink());
        assertNotNull(linkSocial.getProvider());
    }

    /**
     * Test {@link HomeBean}.
     */
    @Test
    public void testHomeBean(){
        final Date myDate = new Date();
        // QuestionPatternBean
        QuestionPatternBean qPatternBean = createUnitPatternBean(
                "TextBoxPattern", "TextBox", "", 1L, "txt", "", "txt", 1,
                "textBox");
        assertNotNull(qPatternBean);

        // QuestionAnswerBean

        final QuestionAnswerBean qAnswer2 = createUnitAnswerBean(2L, "No",
                "defg", null);

        // Add QuestionAnswerBean
        final List<QuestionAnswerBean> qAnswerBean = new ArrayList<QuestionAnswerBean>();
        qAnswerBean.add(this.questionAnswer);
        qAnswerBean.add(qAnswer2);

        // QuestionBean
        QuestionBean qBean = createUnitQuestionBean(1L, "First question", "1",
                qAnswerBean, qPatternBean);
        assertNotNull(qBean);

        // HashTagBeans
        HashTagBean tagBean1 = createUnitHashTag("Education", 1L);
        HashTagBean tagBean2 = createUnitHashTag("Health", 2L);
        HashTagBean tagBean3 = createUnitHashTag("Jobs", 3L);

        // Add HashtagBean
        final List<HashTagBean> hashTags = new ArrayList<HashTagBean>();
        hashTags.add(tagBean1);
        hashTags.add(tagBean2);
        hashTags.add(tagBean3);

        HomeBean homeBean = new HomeBean();
        homeBean.setCreateDate(myDate.toString());
        homeBean.setDislikeVote(150L);
        homeBean.setFavorite(Boolean.TRUE);
        homeBean.setHashTags(hashTags);
        homeBean.setHits(55L);
        homeBean.setItemType("TWEETPOLL");
        homeBean.setLikeVote(80L);
        homeBean.setOwnerUsername("jhonny");
        homeBean.setQuestionBean(qBean);
        homeBean.setRelativeTime("");
        homeBean.setRelevance(40L);
        homeBean.setTotalVotes(158L);
        homeBean.setUserId(1L);
        assertNotNull(homeBean);
        assertNotNull(homeBean.getCreateDate());
        assertNotNull(homeBean.getDislikeVote());
        assertNotNull(homeBean.getFavorite());
        assertNotNull(homeBean.getHashTags());
        assertNotNull(homeBean.getHits());
        assertNotNull(homeBean.getItemType());
        assertNotNull(homeBean.getLikeVote());
        assertNotNull(homeBean.getOwnerUsername());
        assertNotNull(homeBean.getQuestionBean());
        assertNotNull(homeBean.getRelativeTime());
        assertNotNull(homeBean.getTotalVotes());
        assertNotNull(homeBean.getUserId());
    }

    /**
     * Test {@link TweetPollAnswerSwitchBean}.
     */
    @Test
    public void testTweetPollAnswerSwitchBean(){
        TweetPollAnswerSwitchBean tpAnswerSwitch = new TweetPollAnswerSwitchBean();
        this.questionAnswer = createUnitAnswerBean(1L, "Yes", "abcd", null);
        this.tpResultsBean = createTweetPollResultsBean(
                questionAnswer.getAnswerId(), "Yes", 150L);
        this.tpBean = createTweetPollBean();
        tpAnswerSwitch.setId(2L);
        tpAnswerSwitch.setAnswerBean(this.questionAnswer);
        tpAnswerSwitch.setResultsBean(this.tpResultsBean);
        tpAnswerSwitch.setShortUrl("/twitter/mytweet/question");
        tpAnswerSwitch.setTweetPollBean(this.tpBean);
        tpAnswerSwitch.setTweetPollId(1L);
        assertNotNull(tpAnswerSwitch);
        assertNotNull(tpAnswerSwitch.getId());
        assertNotNull(tpAnswerSwitch.getAnswerBean());
        assertNotNull(tpAnswerSwitch.getResultsBean());
        assertNotNull(tpAnswerSwitch.getTweetPollBean());
        assertNotNull(tpAnswerSwitch.getShortUrl());
        assertNotNull(tpAnswerSwitch.getTweetPollId());
    }

    /**
     * Test Unit email list.
     */
    @Test
    public void testUnitLists(){
        final UnitLists emailLists = new UnitLists();
        emailLists.setCreatedAt(new Date());
        emailLists.setListName("my email Lists");
        emailLists.setUserId(1L);
        emailLists.setId(1L);
        assertNotNull(emailLists);
        assertNotNull(emailLists.getId());
        assertNotNull(emailLists.getListName());
        assertNotNull(emailLists.getCreatedAt());
        assertNotNull(emailLists.getUserId());
    }

    /**
     * Test comment bean.
     */
    @Test
    public void testCommentBean(){
        final CommentBean myCommentBean = new CommentBean();
        myCommentBean.setCommentId(1L);
        myCommentBean.setComment("My comment");
        myCommentBean.setCommentedBy("Jhonny");
        myCommentBean.setCommentedByUsername("jhonny");
        myCommentBean.setCreatedAt(new Date());
        myCommentBean.setDislikeVote(10L);
        myCommentBean.setLikeVote(50L);
        myCommentBean.setParentId(0L);
        myCommentBean.setType("TWEETPOLL");
        myCommentBean.setUserAccountId(1L);
        assertNotNull(myCommentBean);
        assertNotNull(myCommentBean.getCommentId());
        assertNotNull(myCommentBean.getComment());
        assertNotNull(myCommentBean.getCommentedBy());
        assertNotNull(myCommentBean.getCommentedByUsername());
        assertNotNull(myCommentBean.getCreatedAt());
        assertNotNull(myCommentBean.getDislikeVote());
        assertNotNull(myCommentBean.getLikeVote());
        assertNotNull(myCommentBean.getParentId());
        assertNotNull(myCommentBean.getType());
        assertNotNull(myCommentBean.getUserAccountId());
    }

    /**
     * Test gadget properties bean.
     */
    @Test
    public void testGadgetPropertiesBean(){
        final GadgetPropertiesBean gadgetProperties = new GadgetPropertiesBean();
        this.myUserAccBean = createUserAccountBean("Jhonny", "jhonny@encuestame.org");
        gadgetProperties.setPropertyId(1L);
        gadgetProperties.setGadgetPropName("my gadget");
        gadgetProperties.setGadgetPropValue("");
        gadgetProperties.setGadgetId(1L);
        gadgetProperties.setUserAccount(this.myUserAccBean);
        assertNotNull(gadgetProperties);
        assertNotNull(gadgetProperties.getPropertyId());
        assertNotNull(gadgetProperties.getGadgetPropName());
        assertNotNull(gadgetProperties.getGadgetPropValue());
        assertNotNull(gadgetProperties.getGadgetId());
        assertNotNull(gadgetProperties.getUserAccount());
    }

    /**
     * Test gadget bean.
     */
    @Test
    public void testGadgetBean(){
        final GadgetBean myGadgetBean = new GadgetBean();
        myGadgetBean.setGadgetId(1L);
        myGadgetBean.setStatus(Boolean.TRUE);
        myGadgetBean.setGadgetPosition(2);
        myGadgetBean.setGadgetName("Activity gadget");
        myGadgetBean.setGadgetColumn(2);
        myGadgetBean.setGadgetColor("#FFFF");
        myGadgetBean.getDashboard().add(this.myDashboardBean);
        assertNotNull(myGadgetBean);
        assertNotNull(myGadgetBean.getGadgetColor());
        assertNotNull(myGadgetBean.getGadgetName());
        assertNotNull(myGadgetBean.getDashboard());
        assertNotNull(myGadgetBean.getGadgetColumn());
        assertNotNull(myGadgetBean.getGadgetId());
        assertNotNull(myGadgetBean.getGadgetPosition());
        assertNotNull(myGadgetBean.getStatus());
    }

    /**
     * Test social user profile.
     */
    @Test
    public void testSocialUserProfile(){
        final SocialUserProfile socialUserProfile = new SocialUserProfile();
        socialUserProfile.setContributorsEnabled(Boolean.TRUE);
        socialUserProfile.setCreatedAt(new Date());
        socialUserProfile.setDescription("user profile description");
        socialUserProfile.setEmail("jhonnyATencuestame.org");
        socialUserProfile.setFavouritesCount(5);
        socialUserProfile.setFirstName("Jhonny");
        socialUserProfile.setFollowersCount(5);
        socialUserProfile.setFollowRequestSent(Boolean.TRUE);
        socialUserProfile.setFriendsCount(2);
        socialUserProfile.setGeoEnabled(Boolean.FALSE);
        socialUserProfile.setHeadline("headline");
        socialUserProfile.setId("1");
        socialUserProfile.setIndustry("encuestame,Inc");
        socialUserProfile.setLang("");
        socialUserProfile.setLastName("English");
        socialUserProfile.setListedCount(1);
        socialUserProfile.setLocation("spain");
        socialUserProfile.setName("Jhonny");
        socialUserProfile.setProfileBackgroundColor("#393CCA");
        socialUserProfile.setProfileBackgroundImageUrl("/images/test.jpg");
        socialUserProfile.setProfileBackgroundTiled(Boolean.FALSE);
        socialUserProfile.setProfileImageUrl("/images/profile.jpg");
        socialUserProfile.setProfileLinkColor("#8B3232");
        socialUserProfile.setProfileSidebarBorderColor("#39CA51");
        socialUserProfile.setProfileSidebarFillColor("F0B615");
        socialUserProfile.setProfileTextColor("060606");
        socialUserProfile.setProfileUrl("/profile/jhonny");
        socialUserProfile.setProfileUseBackgroundImage(Boolean.FALSE);
        socialUserProfile.setProtected(Boolean.TRUE);
        socialUserProfile.setScreenName("myprofile");
        socialUserProfile.setShowAllInlineMedia(Boolean.FALSE);
        socialUserProfile.setStatusesCount(1);
        socialUserProfile.setTimeZone("GMT");
        socialUserProfile.setTranslator(Boolean.FALSE);
        socialUserProfile.setUrl("www.encuestame/profile/jhonny");
        socialUserProfile.setUsername("jhonny");
        socialUserProfile.setUtcOffset(0);
        socialUserProfile.setVerified(Boolean.FALSE);

        assertNotNull(socialUserProfile.isContributorsEnabled());
        assertNotNull(socialUserProfile.getCreatedAt());
        assertNotNull(socialUserProfile.getDescription());
        assertNotNull(socialUserProfile.getEmail());
        assertNotNull(socialUserProfile.getFavouritesCount());
        assertNotNull(socialUserProfile.getFirstName());
        assertNotNull(socialUserProfile.getFollowersCount());
        assertNotNull(socialUserProfile.isFollowRequestSent());
        assertNotNull(socialUserProfile.getFriendsCount());
        assertNotNull(socialUserProfile.isGeoEnabled());
        assertNotNull(socialUserProfile.getHeadline());
        assertNotNull(socialUserProfile.getId());
        assertNotNull(socialUserProfile.getIndustry());
        assertNotNull(socialUserProfile.getLang());
        assertNotNull(socialUserProfile.getLastName());
        assertNotNull(socialUserProfile.getListedCount());
        assertNotNull(socialUserProfile.getLocation());
        assertNotNull(socialUserProfile.getName());
        assertNotNull(socialUserProfile.getProfileBackgroundColor());
        assertNotNull(socialUserProfile.getProfileBackgroundImageUrl());
        assertNotNull(socialUserProfile.isProfileBackgroundTiled());
        assertNotNull(socialUserProfile.getProfileImageUrl());
        assertNotNull(socialUserProfile.getProfileLinkColor());
        assertNotNull(socialUserProfile.getProfileSidebarBorderColor());
        assertNotNull(socialUserProfile.getProfileSidebarFillColor());
        assertNotNull(socialUserProfile.getProfileTextColor());
        assertNotNull(socialUserProfile.getProfileUrl());
        assertNotNull(socialUserProfile.isProfileUseBackgroundImage());
        assertNotNull(socialUserProfile.isProtected());
        assertNotNull(socialUserProfile.getScreenName());
        assertNotNull(socialUserProfile.isShowAllInlineMedia());
        assertNotNull(socialUserProfile.getStatusesCount());
        assertNotNull(socialUserProfile.getTimeZone());
        assertNotNull(socialUserProfile.isTranslator());
        assertNotNull(socialUserProfile.getUrl());
        assertNotNull(socialUserProfile.getUsername());
        assertNotNull(socialUserProfile.getUtcOffset());
        assertNotNull(socialUserProfile.isVerified());
    }

    /**
     * Test social network bean.
     */
    @Test
    public void testSocialNetworkBean(){
        final SocialNetworkBean socialNetwork = new SocialNetworkBean();
        socialNetwork.setApiId("dada");
        socialNetwork.setConsumerKey("consumerKey");
        socialNetwork.setConsumerSecret("consumerSecret");
        socialNetwork.setSocialNetworkName("Twitter");
        socialNetwork.setSocialProvider(SocialProvider.TWITTER);
        socialNetwork.setUrlAccessToken("urlAccessToken");
        socialNetwork.setUrlRequestToken("urlRequestToken");
        assertNotNull(socialNetwork);
        assertNotNull(socialNetwork.getApiId());
        assertNotNull(socialNetwork.getConsumerKey());
        assertNotNull(socialNetwork.getConsumerSecret());
        assertNotNull(socialNetwork.getSocialNetworkName());
        assertNotNull(socialNetwork.getSocialProvider());
        assertNotNull(socialNetwork.getUrlAccessToken());
        assertNotNull(socialNetwork.getUrlRequestToken());
    }
}

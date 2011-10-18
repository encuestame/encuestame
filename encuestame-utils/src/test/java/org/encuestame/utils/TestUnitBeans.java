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
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.ResumeResultTweetPoll;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.json.QuestionPatternBean;
import org.encuestame.utils.mail.InvitationBean;
import org.encuestame.utils.mail.NotificationBean;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.oauth.StandardOAuthSession;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.ForgotPasswordBean;
import org.encuestame.utils.vote.UtilVoteCaptcha;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.TypeTreeNode;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitCatStateBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitSessionUserBean;
import org.encuestame.utils.web.UnitSurveyFormat;
import org.encuestame.utils.web.UserAccountBean;
import org.encuestame.utils.web.UtilTreeNode;
import org.encuestame.utils.web.frontEnd.UnitSearchItem;
import org.encuestame.utils.web.notification.UtilNotification;
import org.junit.Test;

    /**
    * Test Unit Beans.
    *
    * @author Morales, Diana Paola paolaATencuestame.org
    * @since 13/03/2010 16:18:10
    * @version $Id: $
    **/
    @SuppressWarnings("deprecation")
    public class TestUnitBeans extends AbstractBaseUtils {

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
        tweetPoll.setScheduleDate(new Date());
        tweetPoll.setCompleted(true);
        tweetPoll.setTweetUrl("http://www.encuestame.org");
      //  tweetPoll.setTwitterUserAccount("@encuestame");
        assertNotNull(tweetPoll.getCompleted());
    //    assertNotNull(tweetPoll.getTwitterUserAcoount());
    //    assertNotNull(tweetPoll.getTweetUrl());
    //    assertNotNull(tweetPoll.getTweetUrl());
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
        tweetPoll.setCaptcha(true);
        tweetPoll.setLimitVotes(12345);
        tweetPoll.setResumeLiveResults(true);
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
        boardBean.setDashboardName("My Dashboard");
        boardBean.setDashboardDesc("My First Dashboard");
        boardBean.setFavorite(Boolean.TRUE);
        boardBean.setLayout("AAA");
        boardBean.setSequence(1);
        boardBean.setFavoriteCounter(5);
        //boardBean.setSecUser(create)
         assertNotNull(boardBean.getDashboardName());
         assertNotNull(boardBean.getDashboardDesc());
         assertNotNull(boardBean.getFavorite());
         assertNotNull(boardBean.getLayout());
         assertNotNull(boardBean.getSequence());
    }
}
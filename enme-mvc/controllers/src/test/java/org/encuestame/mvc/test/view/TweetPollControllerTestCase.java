/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */
package org.encuestame.mvc.test.view;

import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.mvc.page.TweetPollController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

@Category(DefaultTest.class)
public class TweetPollControllerTestCase extends AbstractMvcUnitBeans{

    /**
     * 
     */
    @Autowired
    private TweetPollController tweetPollController;

    /**
     *
     */
    @Autowired
    private ITweetPollService tweetPollService;

    UserAccount user;

    /**
     *
     */
    @Before
     public void initMVc() {
        this.user = createUserAccount("jota", createAccount());
     }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testnewTweetPollControllerWithOutSocial() throws Exception {
        this.quickLogin();
        logPrint(SecurityContextHolder.getContext().getAuthentication());
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/tweetpoll/new");
        final ModelAndView mav = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav, "tweetpoll/social");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testnewTweetPollController() throws Exception {
        UserAccount user = this.quickLogin();
        createSocialAccount("test", "test", user, "testUser", true, SocialProvider.FACEBOOK);
        logPrint(SecurityContextHolder.getContext().getAuthentication());
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/tweetpoll/new");
        final ModelAndView mav = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav, "tweetpoll/new");
        logPrint(mav.isEmpty());
        logPrint(mav.getModelMap().size());
        logPrint(mav.getModelMap().toString());
        Assert.assertNotNull(mav.getModelMap().get("i18n"));
        HashMap<String, String> i18n = (HashMap<String, String>) mav.getModelMap().get("i18n");
        Assert.assertNotNull(i18n.get("leave_mesage"));
        Assert.assertNotNull(i18n.get("common_show"));
        Assert.assertNotNull(i18n.get("tp_write_questions"));
        Assert.assertNotNull(i18n.get("tp_add_answer"));
        Assert.assertNotNull(i18n.get("tp_add_hashtag"));
        Assert.assertNotNull(i18n.get("tp_scheduled"));
        Assert.assertNotNull(i18n.get("tp_customize"));
        Assert.assertNotNull(i18n.get("tp_select_publish"));
        Assert.assertNotNull(i18n.get("tp_options_chart"));
        Assert.assertNotNull(i18n.get("tp_options_spam"));
        Assert.assertNotNull(i18n.get("tp_options_report"));
        Assert.assertNotNull(i18n.get("tp_options_scheduled_this_tweetpoll"));
        Assert.assertNotNull(i18n.get("tp_options_allow_results"));
        Assert.assertNotNull(i18n.get("tp_options_allow_repeated_votes"));
        Assert.assertNotNull(i18n.get("tp_options_limit_votes"));
        Assert.assertNotNull(i18n.get("tp_options_resume_live_results"));
        Assert.assertNotNull(i18n.get("button_add"));
        Assert.assertNotNull(i18n.get("button_remove"));
        Assert.assertNotNull(i18n.get("button_close"));
        Assert.assertNotNull(i18n.get("button_finish"));
        Assert.assertNotNull(i18n.get("button_publish"));
        Assert.assertNotNull(i18n.get("button_try_again"));
        Assert.assertNotNull(i18n.get("button_ignore"));
        Assert.assertNotNull(i18n.get("button_try_later"));
        Assert.assertNotNull(i18n.get("commons_captcha"));
        Assert.assertNotNull(i18n.get("tp_publish_error"));
        Assert.assertNotNull(i18n.get("pubication_failure_status"));
        Assert.assertNotNull(i18n.get("pubication_success_status"));
        Assert.assertNotNull(i18n.get("pubication_inprocess_status"));
        Assert.assertNotNull(i18n.get("e_020"));
        Assert.assertNotNull(i18n.get("e_021"));
        Assert.assertNotNull(i18n.get("e_024"));
        Assert.assertNotNull(i18n.get("commons_success"));
        Assert.assertNotNull(i18n.get("commons_failure"));
        Assert.assertNotNull(i18n.get("commons_tab_to_save"));
        Assert.assertNotNull(i18n.get("button_scheduled"));
        Assert.assertNotNull(i18n.get("tp_help_question"));
        Assert.assertNotNull(i18n.get("tp_help_1"));
        Assert.assertNotNull(i18n.get("tp_help_2"));
        Assert.assertNotNull(i18n.get("tp_help_3"));
        Assert.assertNotNull(i18n.get("tp_help_4"));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testdetailTweetPollController() throws Exception {
        UserAccount d = this.quickLogin();
        createFakesTweetPoll(d);
        List<TweetPoll> tweetpolls = getTweetPoll().getTweetPollByUsername(10, d);
        for (TweetPoll tweetpoll : tweetpolls) {
            tweetpoll.setPublishTweetPoll(true);
            getTweetPoll().saveOrUpdate(tweetpoll);
            logPrint(tweetpoll.getTweetPollId());
            String path = "/tweetpoll/{id}/{slug}";
            Question q = tweetpoll.getQuestion();
            path = path.replace("{id}", new String(tweetpoll.getTweetPollId().toString()));
            path = path.replace("{slug}", q.getSlugQuestion());
            logPrint(path);
            request = new MockHttpServletRequest(MethodJson.GET.toString(), path.toString());
            final ModelAndView mav = handlerAdapter.handle(request, response, tweetPollController);
            assertViewName(mav, "tweetpoll/detail");
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testdetailTweetPollControllerNonPublished() throws Exception {
        UserAccount d = this.quickLogin();
        createFakesTweetPoll(d);
        List<TweetPoll> tweetpolls = getTweetPoll().getTweetPollByUsername(10, d);
        for (TweetPoll tweetpoll : tweetpolls) {
            tweetpoll.setPublishTweetPoll(false);
            getTweetPoll().saveOrUpdate(tweetpoll);
            logPrint(tweetpoll.getTweetPollId());
            String path = "/tweetpoll/{id}/{slug}";
            Question q = tweetpoll.getQuestion();
            path = path.replace("{id}", new String(tweetpoll.getTweetPollId().toString()));
            path = path.replace("{slug}", q.getSlugQuestion());
            logPrint(path);
            request = new MockHttpServletRequest(MethodJson.GET.toString(), path.toString());
            final ModelAndView mav = handlerAdapter.handle(request, response, tweetPollController);
            assertViewName(mav, "404");
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testdetailTweetPollController404() throws Exception {
            UserAccount user = this.quickLogin();
            String path = "/tweetpoll/3/fake";
            request = new MockHttpServletRequest(MethodJson.GET.toString(), path.toString());
            final ModelAndView mav = handlerAdapter.handle(request, response, tweetPollController);
            assertViewName(mav, "404");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testnewTweetPollVote() throws Exception {
        //login
        UserAccount user = this.quickLogin();
        // create the tweetpoll to vote
        SocialAccount social = createSocialAccount("test", "test", user, "testUser", true, SocialProvider.TWITTER);
        Question question = createQuestion("Why the sky is blue?", "html");
        createQuestionAnswer("Yes", question, "SSSA");
        List<QuestionAnswerBean>answers = new ArrayList<QuestionAnswerBean>();
        answers.add(createAnswersBean("2DFAAS", "Yes", question.getQid()));
        answers.add(createAnswersBean("4DSWGK", "No", question.getQid()));
        QuestionBean questionBean = createUnitQuestionBean("questionName", 1L, this.user.getUid(), answers);
        final TweetPollBean myTpBean = createUnitTweetPoll(Boolean.TRUE, "tweetPollUrl", user.getUid(), questionBean);
        final TweetPoll myTweetPoll = tweetPollService.createTweetPoll(
                myTpBean, "What is your favourite city?",user, null);
        //define tp
            myTweetPoll.setAllowRepatedVotes(false);
            myTweetPoll.setLimitVotesEnabled(false);
            myTweetPoll.setPublishTweetPoll(true);
            myTweetPoll.setDateLimit(false);
            getPollDao().saveOrUpdate(myTweetPoll);
        ////
        final Question myQuestion = createQuestion("What is your favourite city", "pattern");
        final QuestionAnswerBean qAnswerBean = createAnswersBean("26354","Yes", myQuestion.getQid());
        final QuestionAnswerBean qAnswerBean2 = createAnswersBean("26355","No", myQuestion.getQid());
        final TweetPollSwitch pollSwitch = tweetPollService .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);
        final TweetPollSwitch pollSwitch2 = tweetPollService.createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);
        // first vote
        String path = "/tweetpoll/vote/{tweetId}";
        path = path.replace("{tweetId}", pollSwitch.getCodeTweet());
        logPrint(path);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        request.setRemoteAddr("192.168.1.1");
        final ModelAndView mav = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav, "tweetVoted");
        // second vote
        String path2 = "/tweetpoll/vote/{tweetId}";
        path2 = path2.replace("{tweetId}", pollSwitch2.getCodeTweet());
        logPrint(path2);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path2);
        request.setRemoteAddr("192.168.1.2");
        final ModelAndView mav2 = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav2, "tweetVoted");
        // second vote
        String path3 = "/tweetpoll/vote/{tweetId}";
        path3 = path3.replace("{tweetId}", pollSwitch2.getCodeTweet());
        logPrint(path3);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path3);
        request.setRemoteAddr("192.168.1.2");
        final ModelAndView mav3 = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav3, "repeatedTweetVote");
        //step 3 complete
        myTweetPoll.setCompleted(true);
        getPollDao().saveOrUpdate(myTweetPoll);
        String path4 = "/tweetpoll/vote/{tweetId}";
        path4 = path4.replace("{tweetId}", pollSwitch2.getCodeTweet());
        logPrint(path4);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path4);
        request.setRemoteAddr("192.168.1.2");
        final ModelAndView mav4 = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav4, "completeTweetVote");
        // answer no found
        //step 4 not found
        myTweetPoll.setPublishTweetPoll(false);
        getPollDao().saveOrUpdate(myTweetPoll);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path4);
        request.setRemoteAddr("192.168.1.6");
        final ModelAndView mav5 = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav5, "404");
        // step 5 limit votes
        myTweetPoll.setPublishTweetPoll(true);
        myTweetPoll.setCompleted(false);
        myTweetPoll.setLimitVotes(1);
        myTweetPoll.setLimitVotesEnabled(true);
        myTweetPoll.setAllowRepatedVotes(true);
        myTweetPoll.setMaxRepeatedVotes(1);
        getPollDao().saveOrUpdate(myTweetPoll);
        int f = 0;
        logPrint(this.getTweetPoll().getTotalVotesByTweetPollId(myTweetPoll.getTweetPollId()));
        for(f=1;f<=40;f++) {
            this.tweetPollService.tweetPollVote(pollSwitch2, "192.4.4.4.4", new Date());
        }
        for(f=1;f<=40;f++) {
            this.tweetPollService.tweetPollVote(pollSwitch, "192.4.4.4.4", new Date());
        }
        logPrint(this.getTweetPoll().getTotalVotesByTweetPollId(myTweetPoll.getTweetPollId()));
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path4);
        request.setRemoteAddr("192.168.1.4");
        final ModelAndView mav6 = handlerAdapter.handle(request, response, tweetPollController);
        assertViewName(mav6, "LimitTweetVote");

    }
}

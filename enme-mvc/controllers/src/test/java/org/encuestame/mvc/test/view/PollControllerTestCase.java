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

import org.encuestame.core.service.IPollService;
import org.encuestame.core.service.ITweetPollService;
import org.encuestame.mvc.page.PollController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.util.exception.EnMeException;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.CreatePollBean;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Poll Controller TestCase.
 */
@Category(DefaultTest.class)
public class PollControllerTestCase extends AbstractMvcUnitBeans {

    /**
     * 
     */
    @Autowired
    private PollController pollController;

    /**
     *
     */
    @Autowired
    private ITweetPollService tweetPollService;

    /**
     *
     */
    @Autowired
    private IPollService iPollService;

    /**
     *
     */
    UserAccount user;

    /**
     *
     */
    @Before
     public void initMVc() {
        this.user = createUserAccount("jota", createAccount());
     }

    /**
     * Test {@link org.encuestame.mvc.page.SignInController}.
     * @throws Exception exception.
     */
    @Test
    public void testPollController() throws Exception {
        final Poll poll = createPoll(new Date(), createQuestion("question 1", "Si"),
                createUserAccount("diana", createAccount()), true, true);
        //"/user/signin
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/poll/"+poll.getPollId()+"/"+poll.getQuestion().getSlugQuestion());
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "poll/detail");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testPollControllerNotFound() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/poll/44343/xx");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "404");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testPollControllerVoted() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/poll/voted");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "poll/voted");
    }


    @Test
    public void testPollControllerRedirect() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/poll");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "redirect:/user/poll/list");
    }

    /**
     *
     * @param question
     * @return
     * @throws EnMeException
     */
    private Poll createQuickPoll(String question)  throws EnMeException {
        final String[] answer = {"a", "b"};
        final String[] hashtag = {"hastag1", "hastag2"};
        final CreatePollBean cb = createPollBean(
                question,
                answer,
                hashtag,
                "MODERATE",
                "ALL",
                true,
                null,
                null);
        cb.setRepeatedVotes(5);
        cb.setMultiple(false);
        cb.setLimitVote(10);
        return this.iPollService.createPoll(cb);
    }

    @Test
    public void testpollVoteController() throws Exception {
        UserAccount user = this.quickLogin();

        String path = "/poll/vote/{id}/{slug}";
        Question question = createQuestion("question 1", "Si");
        final Poll p1 = createPoll(new Date(), question, user, false, true);
        flushIndexes();
        path = path.replace("{id}", p1.getPollId().toString());
        path = path.replace("{slug}", p1.getQuestion().getSlugQuestion());
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "poll/vote");
        Assert.assertNotNull(mav.getModelMap().get("answers"));
    }

    @Test
    public void testpollVoteController404() throws Exception {
        String path = "/poll/vote/{id}/{slug}";
        flushIndexes();
        path = path.replace("{id}", "32423482934323");
        path = path.replace("{slug}", "dsdsadsa");
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "404");
        Assert.assertNotNull(mav.getModelMap().get("message"));
    }

    @Test
    public void pollsubmit() throws Exception {
        UserAccount user = this.quickLogin();
        Question question = createQuestion("question 1", "Si");
        createQuestionAnswer("1", question, "23132143");
        QuestionAnswer q2 = createQuestionAnswer("2", question, "23132143");
        final Poll p1 = createPoll(new Date(), question, user, false, true);
        p1.setPublish(true);
        p1.setMultipleResponse(AbstractSurvey.MultipleResponse.SINGLE);
        getPollDao().saveOrUpdate(p1);
        flushIndexes();

        // simple vote
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.2");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "poll/voted");
        Assert.assertNotNull(mav.getModelMap().get("message"));

        // simple embebed vote
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "embedded");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.13.2");
        final ModelAndView mavembedded = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav, "poll/voted");
        Assert.assertNotNull(mavembedded.getModelMap().get("message"));

        //FUTURE: Pending multiple votes test
        //FUTURE: Pending multiple votes test
        //FUTURE: Pending multiple votes test
        // Just here !!
        //FUTURE: Pending multiple votes test
        //FUTURE: Pending multiple votes test
        //FUTURE: Pending multiple votes test
        //FUTURE: Pending multiple votes test


        // double vote
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.2");
        final ModelAndView mav2 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav2, "poll/repeated");

        //bad answer
        // double vote
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", "12345");
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.2");
        final ModelAndView mav3 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav3, "poll/bad");

        //
        // double vote
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.54");
        final ModelAndView mav4 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav4, "poll/voted");


        // complete poll
        p1.setPollCompleted(true);
        getPollDao().saveOrUpdate(p1);
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.124");
        final ModelAndView mav5 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav5, "poll/completed");

        // complete poll
        p1.setPollCompleted(true);
        getPollDao().saveOrUpdate(p1);
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.124");
        final ModelAndView mav11 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav11, "poll/completed");

        // no quota
        p1.setPollCompleted(false);
        p1.setAllowRepeatedVotes(true);
        p1.setLimitVotesEnabled(true);
        p1.setLimitVotes(1);
        p1.setClosedQuota(1);
        getPollDao().saveOrUpdate(p1);
        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/poll/vote/post");
        request.setParameter("itemId", p1.getPollId().toString());
        request.setParameter("poll", q2.getQuestionAnswerId().toString());
        request.setParameter("type_form", "normal");
        request.setParameter("slugName", p1.getQuestion().getSlugQuestion());
        request.setRemoteAddr("192.168.1.54");
        final ModelAndView mav6 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav6, "poll/completed");
        final ModelAndView mav7 = handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav7, "poll/completed");

        // no quota by ip
        DateTime dateTime = new DateTime();
        dateTime.minusWeeks(3);
        p1.setClosedDate(dateTime.toDate());
        p1.setCloseAfterDate(false);
        p1.setClosedQuota(null);
        getPollDao().saveOrUpdate(p1);
        final ModelAndView mav18= handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav18, "poll/completed");

        // no quota by ip
        p1.setAllowRepeatedVotes(true);
        p1.setLimitVotesEnabled(false);
        p1.setClosedQuota(1);
        getPollDao().saveOrUpdate(p1);
        final ModelAndView mav8= handlerAdapter.handle(request, response,
                pollController);
        assertViewName(mav8, "poll/completed");

        // no published
        p1.setPublish(false);
        getPollDao().saveOrUpdate(p1);
        final ModelAndView mav9= handlerAdapter.handle(request, response, pollController);
        assertViewName(mav9, "404");
    }


    @Test
    public void testListPollController() throws Exception {
        UserAccount user = this.quickLogin();
        createSocialAccount("test", "test", user, "testUser", true, SocialProvider.FACEBOOK);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/poll/list");
        final ModelAndView mav = handlerAdapter.handle(request, response, pollController);
        assertViewName(mav, "poll/list");
        logPrint(mav.isEmpty());
        logPrint(mav.getModelMap().size());
        logPrint(mav.getModelMap().toString());
        Assert.assertNotNull(mav.getModelMap().get("i18n"));
        HashMap<String, String> i18n = (HashMap<String, String>) mav.getModelMap().get("i18n");
        Assert.assertNotNull(i18n.get("commons_no_results"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_options"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_answers"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_edit"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_preview"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_publish_options"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_embebed"));
        Assert.assertNotNull(i18n.get("commons_success"));
        Assert.assertNotNull(i18n.get("detail_manage_today"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_votes"));
        Assert.assertNotNull(i18n.get("detail_manage_poll_title"));
        Assert.assertNotNull(i18n.get("detail_manage_filters"));
        Assert.assertNotNull(i18n.get("detail_manage_poll_dropdown_title"));
        Assert.assertNotNull(i18n.get("poll_admon_poll_new"));
        Assert.assertNotNull(i18n.get("commons_remove"));
        Assert.assertNotNull(i18n.get("poll_options_close"));
        Assert.assertNotNull(i18n.get("poll_options_quota"));
        Assert.assertNotNull(i18n.get("poll_options_ip"));
        Assert.assertNotNull(i18n.get("poll_options_password"));
        Assert.assertNotNull(i18n.get("poll_options_info"));
        Assert.assertNotNull(i18n.get("poll_options_public"));
        Assert.assertNotNull(i18n.get("poll_options_notifications"));
        Assert.assertNotNull(i18n.get("commons_confirm"));
        Assert.assertNotNull(i18n.get("commons_no"));
        Assert.assertNotNull(i18n.get("commons_yes"));
        Assert.assertNotNull(i18n.get("detail_manage_today"));
        Assert.assertNotNull(i18n.get("publish_social"));
        Assert.assertNotNull(i18n.get("loading_message"));
        Assert.assertNotNull(i18n.get("counter_zero"));
        Assert.assertNotNull(i18n.get("pubication_failure_status"));
        Assert.assertNotNull(i18n.get("button_try_later"));
        Assert.assertNotNull(i18n.get("button_ignore"));
        Assert.assertNotNull(i18n.get("button_try_again"));
    }

    @Test
    public void testnewPollController() throws Exception {
        UserAccount user = this.quickLogin();
        createSocialAccount("test", "test", user, "testUser", true, SocialProvider.FACEBOOK);
        logPrint(SecurityContextHolder.getContext().getAuthentication());
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/poll/new");
        final ModelAndView mav = handlerAdapter.handle(request, response, pollController);
        assertViewName(mav, "poll/new");
        logPrint(mav.isEmpty());
        logPrint(mav.getModelMap().size());
        logPrint(mav.getModelMap().toString());
        Assert.assertNotNull(mav.getModelMap().get("i18n"));
        HashMap<String, String> i18n = (HashMap<String, String>) mav.getModelMap().get("i18n");
        Assert.assertNotNull(i18n.get("leave_mesage"));
        Assert.assertNotNull(i18n.get("tp_add_hashtag"));
        Assert.assertNotNull(i18n.get("poll_create_question_title"));
        Assert.assertNotNull(i18n.get("poll_create_build_answers"));

        Assert.assertNotNull(i18n.get("poll_create_add_new_answer"));
        Assert.assertNotNull(i18n.get("poll_create_allow_multiple_selection"));
        Assert.assertNotNull(i18n.get("poll_create_allow_new_responses"));
        Assert.assertNotNull(i18n.get("poll_create_limits"));
        Assert.assertNotNull(i18n.get("poll_create_poll_options"));
        Assert.assertNotNull(i18n.get("poll_create_comments"));
        Assert.assertNotNull(i18n.get("poll_create_results"));
        Assert.assertNotNull(i18n.get("poll_create_button_create"));
        Assert.assertNotNull(i18n.get("widget_folder_select_label"));
        Assert.assertNotNull(i18n.get("pattern_question_single"));
        Assert.assertNotNull(i18n.get("m_025"));
        Assert.assertNotNull(i18n.get("m_026"));
        Assert.assertNotNull(i18n.get("widget_repated_votes"));
        Assert.assertNotNull(i18n.get("widget_limit_votes"));
        Assert.assertNotNull(i18n.get("widget_date_to_close"));
        Assert.assertNotNull(i18n.get("widget_comments_allow"));
        Assert.assertNotNull(i18n.get("widget_comments_moderated"));
        Assert.assertNotNull(i18n.get("widget_comments_no_comments"));
        Assert.assertNotNull(i18n.get("widget_results_options"));
        Assert.assertNotNull(i18n.get("widget_results_only_percents"));
        Assert.assertNotNull(i18n.get("widget_results_all_data"));
        Assert.assertNotNull(i18n.get("widget_question_type"));
        Assert.assertNotNull(i18n.get("commons_cancel"));
        Assert.assertNotNull(i18n.get("social_picker_filter_selected"));
        Assert.assertNotNull(i18n.get("publish_social"));
        Assert.assertNotNull(i18n.get("loading_message"));
        Assert.assertNotNull(i18n.get("counter_zero"));
        Assert.assertNotNull(i18n.get("pubication_failure_status"));
        Assert.assertNotNull(i18n.get("button_try_again"));
        Assert.assertNotNull(i18n.get("button_ignore"));
        Assert.assertNotNull(i18n.get("button_try_later"));

    }
//

}

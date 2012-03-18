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
package org.encuestame.mvc.test.view;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.Date;

import junit.framework.Assert;

import org.encuestame.mvc.controller.AdmonController;
import org.encuestame.mvc.controller.DashBoardController;
import org.encuestame.mvc.controller.HomeController;
import org.encuestame.mvc.controller.PollController;
import org.encuestame.mvc.controller.SignInController;
import org.encuestame.mvc.controller.SignUpController;
import org.encuestame.mvc.controller.SurveyController;
import org.encuestame.mvc.controller.TweetPollController;
import org.encuestame.mvc.controller.UserProfileController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
public class ViewControllerTestCase extends AbstractMvcUnitBeans{

        @Autowired
        private TweetPollController tweetPollController;

        @Autowired
        private PollController pollController2;

        @Autowired
        private DashBoardController dashBoardController;

        @Autowired
        private SignUpController signupController;

        @Autowired
        private UserProfileController profileController;

        private TweetPollSwitch tpswitch;

        @Before
        public void initMVc() {
            final UserAccount userAccount = createUserAccount("jota", createAccount());
            createFakesTweetPoll(userAccount);
            final TweetPoll tp1 = (TweetPoll) getHibernateTemplate().find("from TweetPoll").get(0);
            final Question q1 = tp1.getQuestion();
            final QuestionAnswer a1 = createQuestionAnswer("yes", q1, "12345");
            final QuestionAnswer a2 = createQuestionAnswer("no", q1, "12346");
            this.tpswitch = createTweetPollSwitch(a1, tp1);
            final TweetPollSwitch tps2 = createTweetPollSwitch(a2, tp1);
        }

        /**
         * home view.
         * @throws Exception exception
         */
        @Test
        public void testHome() throws Exception {
            final HomeController controller = new HomeController();
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/home");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "redirect:/user/signin");
        }

        /**
         * Index view.
         * @throws Exception exception
         */
        @Test
        public void testIndexView() throws Exception {
            HomeController controller = new HomeController();
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "redirect:/home");
        }

        /**
         * Dashboard view.
         * @throws Exception
         */
        @Test
        public void testDashBoardController() throws Exception {
            DashBoardController controller = this.dashBoardController;
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/dashboard");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "dashboard");
        }

        /**
         * Test {@link AdmonController}.
         * @throws Exception
         */
        @Test
        public void testAdmonController() throws Exception {
            final AdmonController controller = new AdmonController();
            //location
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/location");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "location");

            //members
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/members");
            final ModelAndView mav2 = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav2, "members");
            //project
            //members
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/project");
            final ModelAndView mav3 = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav3, "project");
        }

        /**
         * Test {@link SignInController}.
         * @throws Exception exception.
         */
        @Test
        public void testLoginController() throws Exception {
            final SignInController controller = new SignInController();
            //"/user/signin
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/signin");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "signin");
            // forgot view. TODO: refactored this path.
            //request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/forgot");
            //        final ModelAndView mav2 = handlerAdapter.handle(request, response,
            //            controller);
            //        assertViewName(mav2, "forgot");
        }

        /**
         * Test {@link SignInController}.
         * @throws Exception exception.
         */
        @Test
        public void testPollController() throws Exception {
            final Poll poll = createPoll(new Date(), createQuestion("question 1", "Si"),
                    createUserAccount("diana", createAccount()), true, true);
            //"/user/signin
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/poll/"+poll.getPollId()+"/"+poll.getQuestion().getSlugQuestion());
            final ModelAndView mav = handlerAdapter.handle(request, response,
                pollController2);
            assertViewName(mav, "poll/detail");
        }

    /**
     * Test {@link SurveyController}
     * @throws Exception
     */
    @Test
    public void testSurveyController() throws Exception {
        final SurveyController controller = new SurveyController();
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/user/survey");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
        assertViewName(mav, "user/survey");
    }

    /**
     * Test
     * @throws Exception
     */
    @Test
    public void testSurveyPublicController() throws Exception {
        SurveyController controller = new SurveyController();
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/survey/1/slug/");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
        assertViewName(mav, "survey");
    }

    /**
     * Test.
     * @throws Exception
     */
    @Test
    public void testAddHandlerSignUpController() throws Exception {
        //SignUpController controller = new SignUpController();
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/user/signup");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                signupController);
        assertViewName(mav, "signup");
    }

/*
 * 	TODO: fix testProcessSubmit(org.encuestame.mvc.test.view.ViewControllerTestCase): Required String parameter 'realName' is not present
    @Test
    public void testProcessSubmit() throws Exception {
        final String realName = "Jhon Smith";
        final String password = "smith1234";
        final String username = "jhonsmith";
        final String email = "jhonsmith@encuestame.org";
        request.setParameter("realName", realName);
        request.setParameter("password", password);
        request.setParameter("username", username);
        request.setParameter("email", email);
        SignUpController controller = new SignUpController();
        request = new MockHttpServletRequest(MethodJson.POST.toString(),
                "/user/signup/create");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
        assertViewName(mav, "redirect:/user/dashboard");
    }*/

    /**
     * Test confirmation account
     * @throws Exception
     */
    @Test
    public void testConfirmAccountController() throws Exception {
        final UserAccount userAcc = createUserAccount("jhonsmith", createAccount());
        userAcc.setInviteCode("15D27P495M");
        getAccountDao().saveOrUpdate(userAcc);
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/user/confirm/email/"+userAcc.getInviteCode());
        final ModelAndView mav = handlerAdapter.handle(request, response,
                signupController);
        assertViewName(mav, "user/confirm/");
    }

    /**
     * Test confirm account with bad invitation code.
     * @throws Exception
     */
    @Test
    public void testAccountWithBadInvitationCodeController() throws Exception {
        final UserAccount userAcc = createUserAccount("jhonsmith", createAccount());
        userAcc.setInviteCode("15D27P495M34U");
        getAccountDao().saveOrUpdate(userAcc);
        final String badInvitationCode = "2905JP";
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/user/confirm/email/"+ badInvitationCode);
        final ModelAndView mav = handlerAdapter.handle(request, response,
                signupController);
        assertViewName(mav, "signin");
    }

    /**
     * Test {@link UserProfileController}.
     * @throws Exception
     */
    @Test
    public void testUserProfileController() throws Exception {
        final UserAccount userAcc = createUserAccount("jhonsmith", createAccount());
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/profile/"+ userAcc.getUsername());
        final ModelAndView mav = handlerAdapter.handle(request, response,
                profileController);
        assertViewName(mav, "profile/view");

        // Username  not found.
        final String badUsername = "badUser";
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/profile/"+ badUsername );
        final ModelAndView mavBadUser = handlerAdapter.handle(request, response,
                profileController);
        assertViewName(mavBadUser, "404");
    }

    /**
     *
     */
    @Test
    public void testDetailTweetPollController() {

    }

    public void testNewTweetPollController() {

    }





    /**
     * Test {@link TweetPollController}.
     *
     * @throws Exception
     *             exception.
     */
    @Test
    public void testTweetPollController() throws Exception {
        Assert.assertNotNull(this.tweetPollController);

        // bad vote view.
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/tweetpoll/vote/1");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                tweetPollController);
        // System.out.println(mav);
        assertViewName(mav, "badTweetVote");

        // vote view.
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/tweetpoll/vote/" + this.tpswitch.getCodeTweet());
        final ModelAndView mavVote = handlerAdapter.handle(request, response,
                tweetPollController);
        assertViewName(mavVote, "badTweetVote");

        // repeated vote view.
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/tweetpoll/vote/" + this.tpswitch.getCodeTweet());
        final ModelAndView mavVote1 = handlerAdapter.handle(request, response,
                tweetPollController);
        assertViewName(mavVote1, "badTweetVote");

        // /user/tweetpoll/list
        // request = new MockHttpServletRequest(MethodJson.GET.toString(),
        // "/user/tweetpoll/list");
        // final ModelAndView mav2 = handlerAdapter.handle(request, response,
        // tweetPollController);
        // assertViewName(mav2, "badTweetVote");

        // /user/tweetpoll/list
        // request = new MockHttpServletRequest(MethodJson.GET.toString(),
        // "/user/tweetpoll/new");
        // final ModelAndView mav3 = handlerAdapter.handle(request, response,
        // tweetPollController);
        // assertViewName(mav3, "tweetpoll/new");
    }

    /**
     *
     * @throws Exception
     */
    // @Test
    public void testRedirecttweetPollController() throws Exception {
        final TweetPollController controller = this.tweetPollController;
        // /user/tweetpoll/list
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/user/tweetpoll");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
        assertViewName(mav, "redirect:/user/tweetpoll/list");
    }

    // @Test
    public void testtweetPollController() throws Exception {
        final TweetPollController controller = this.tweetPollController;
        // /user/tweetpoll/list
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/user/tweetpoll/list");
        final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
        assertViewName(mav, "tweetpoll");
    }

    /**
     * Test TweetPoll controller banned ip
     *
     * @throws Exception
     */
    public void testTweetPollControllerBanned() throws Exception {
        // Banned Ip vote.
        // TODO: ENCUESTAME-179
        request = new MockHttpServletRequest(MethodJson.GET.toString(),
                "/tweetpoll/vote/" + this.tpswitch.getCodeTweet());
        final ModelAndView mavVote2 = handlerAdapter.handle(request, response,
                this.tweetPollController);
        assertViewName(mavVote2, "banned");
    }

    /**
     * @param pollController
     *            the pollController to set
     */
    public void setPollController(TweetPollController pollController) {
        this.tweetPollController = pollController;
    }

    /**
     * @param signupController the signupController to set
     */
    public void setSignupController(SignUpController signupController) {
        this.signupController = signupController;
    }

    /**
     * @param profileController the profileController to set
     */
    public void setProfileController(UserProfileController profileController) {
        this.profileController = profileController;
    }
}

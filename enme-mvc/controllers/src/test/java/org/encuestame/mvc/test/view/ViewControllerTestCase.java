///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//package org.encuestame.mvc.test.view;
//
//import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
//
//import org.encuestame.mvc.page.HomeController;
//import org.encuestame.mvc.page.SignInController;
//import org.encuestame.mvc.page.SignUpController;
//import org.encuestame.mvc.page.SurveyController;
//import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
//import org.encuestame.persistence.domain.question.Question;
//import org.encuestame.persistence.domain.question.QuestionAnswer;
//import org.encuestame.persistence.domain.security.UserAccount;
//import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
//import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
//import org.encuestame.utils.categories.test.DefaultTest;
//import org.encuestame.utils.enums.MethodJson;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// * Description.
// * @author Picado, Juan juanATencuestame.org
// * @since Apr 10, 2011
// */
//@Category(DefaultTest.class)
//public class ViewControllerTestCase extends AbstractMvcUnitBeans {
//
//        @Autowired
//        private SignUpController signupController;
//
//        @Autowired
//        private HomeController homeController;
//
//        private TweetPollSwitch tpswitch;
//
//        @Before
//        public void initMVc() {
//            final UserAccount userAccount = createUserAccount("jota", createAccount());
//            createFakesTweetPoll(userAccount);
//            final TweetPoll tp1 = (TweetPoll) getHibernateTemplate().find("from TweetPoll").get(0);
//            final Question q1 = tp1.getQuestion();
//            final QuestionAnswer a1 = createQuestionAnswer("yes", q1, "12345");
//            final QuestionAnswer a2 = createQuestionAnswer("no", q1, "12346");
//            this.tpswitch = createTweetPollSwitch(a1, tp1);
//            final TweetPollSwitch tps2 = createTweetPollSwitch(a2, tp1);
//        }
//
//        /**
//         * Index view.
//         * @throws Exception exception
//         */
//        @Test
//        public void testIndexView() throws Exception {
//            HomeController controller = new HomeController();
//            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/");
//            final ModelAndView mav = handlerAdapter.handle(request, response,
//                controller);
//            assertViewName(mav, "redirect:/home");
//        }
//
//        /**
//         * Test {@link SignInController}.
//         * @throws Exception exception.
//         */
//        @Test
//        public void testLoginController() throws Exception {
//            final SignInController controller = new SignInController();
//            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/signin");
//            final ModelAndView mav = handlerAdapter.handle(request, response,
//                controller);
//            assertViewName(mav, "signin");
//            // forgot view. TODO: refactored this path.
//            //request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/forgot");
//            //        final ModelAndView mav2 = handlerAdapter.handle(request, response,
//            //            controller);
//            //        assertViewName(mav2, "forgot");
//        }
//
//
//    /**
//     * Test {@link SurveyController}
//     * @throws Exception
//     */
//    @Test
//    public void testSurveyController() throws Exception {
//        final SurveyController controller = new SurveyController();
//        request = new MockHttpServletRequest(MethodJson.GET.toString(),
//                "/user/survey");
//        final ModelAndView mav = handlerAdapter.handle(request, response,
//                controller);
//        assertViewName(mav, "user/survey");
//    }
//
//    /**
//     * Test
//     * @throws Exception
//     */
//    @Test
//    public void testSurveyPublicController() throws Exception {
//        SurveyController controller = new SurveyController();
//        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/survey/1/slug/");
//        final ModelAndView mav = handlerAdapter.handle(request, response,
//                controller);
//        assertViewName(mav, "survey");
//    }
//
//    /**
//     * Test.
//     * @throws Exception
//     */
//    @Test
//    public void testAddHandlerSignUpController() throws Exception {
//        //SignUpController controller = new SignUpController();
//        request = new MockHttpServletRequest(MethodJson.GET.toString(),
//                "/user/signup");
//        final ModelAndView mav = handlerAdapter.handle(request, response,
//                signupController);
//        assertViewName(mav, "signup");
//    }
//
//    /**
//     * Sign up valid
//     * @throws Exception
//     */
//    @Test
//    public void testProcessSubmit() throws Exception {
//        final String realName = "Jhon Smith";
//        final String password = "smith1234";
//        final String username = "jhonsmith";
//        final String email = "jhonsmith@encuestame.org";
//        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/user/signup/create");
//        request.setParameter("realName", realName);
//        request.setParameter("password", password);
//        request.setParameter("username", username);
//        request.setParameter("email", email);
//        final ModelAndView mav = handlerAdapter.handle(request, response, signupController);
//        assertViewName(mav, "/user/created");
//    }
//
//    /**
//     * Sign up not valid
//     * @throws Exception
//     */
//    @Test
//    public void testProcessSubmitNonValid() throws Exception {
//        final String realName = "Jhon Smith";
//        final String password = "1";
//        final String username = "jhonsmith";
//        final String email = "jhonsmithALTencuestame.org";
//        request = new MockHttpServletRequest(MethodJson.POST.toString(), "/user/signup/create");
//        request.setParameter("realName", realName);
//        request.setParameter("password", password);
//        request.setParameter("username", username);
//        request.setParameter("email", email);
//        final ModelAndView mav = handlerAdapter.handle(request, response, signupController);
//        assertViewName(mav, "redirect:/user/signup");
//    }
//
//    /**
//     * Test confirmation account
//     * @throws Exception
//     */
//    @Test
//    public void testConfirmAccountController() throws Exception {
//        final UserAccount userAcc = createUserAccount("jhonsmith", createAccount());
//        userAcc.setInviteCode("15D27P495M");
//        getAccountDao().saveOrUpdate(userAcc);
//        request = new MockHttpServletRequest(MethodJson.GET.toString(),
//                "/user/confirm/email/"+userAcc.getInviteCode());
//        final ModelAndView mav = handlerAdapter.handle(request, response,
//                signupController);
//        assertViewName(mav, "user/confirm/");
//    }
//
//    /**
//     * Test confirm account with bad invitation code.
//     * @throws Exception
//     */
//    @Test
//    public void testAccountWithBadInvitationCodeController() throws Exception {
//        final UserAccount userAcc = createUserAccount("jhonsmith", createAccount());
//        userAcc.setInviteCode("15D27P495M34U");
//        getAccountDao().saveOrUpdate(userAcc);
//        final String badInvitationCode = "2905JP";
//        request = new MockHttpServletRequest(MethodJson.GET.toString(),
//                "/user/confirm/email/"+ badInvitationCode);
//        final ModelAndView mav = handlerAdapter.handle(request, response,
//                signupController);
//        assertViewName(mav, "redirect:/user/signin");
//    }
//
//    /**
//     * Test {@link HomeController}.
//     * @throws Exception
//     */
//    @Test
//    public void testUserProfileController() throws Exception {
//        final UserAccount userAcc = createUserAccount("jhonsmith", createAccount());
//        request = new MockHttpServletRequest(MethodJson.GET.toString(),
//                "/profile/"+ userAcc.getUsername());
//        final ModelAndView mav = handlerAdapter.handle(request, response, homeController);
//        assertViewName(mav, "profile/view");
//
//        // Username  not found.
//        final String badUsername = "badUser";
//        request = new MockHttpServletRequest(MethodJson.GET.toString(),
//                "/profile/"+ badUsername );
//        final ModelAndView mavBadUser = handlerAdapter.handle(request, response, homeController);
//        assertViewName(mavBadUser, "404");
//    }
//
//
//    @Test
//    @Ignore
//    //No matching handler method found for servlet request: path '/user/confirm/email/refresh/code', method 'GET', parameters map[[empty]]
//    public void testrefreshCodeController() throws Exception {
//        this.quickLogin();
//        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/confirm/email/refresh/code");
//        final ModelAndView mav = handlerAdapter.handle(request, response, homeController);
//        assertViewName(mav, "redirect:/user/tweetpoll/list");
//    }
//
//    /**
//     * @param signupController the signupController to set
//     */
//    public void setSignupController(SignUpController signupController) {
//        this.signupController = signupController;
//    }
//
//    /**
//     *
//     * @param homeController
//     */
//    public void setHomeController(HomeController homeController) {
//        this.homeController = homeController;
//    }
//}

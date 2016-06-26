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

import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.mvc.page.HomeController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Created by jpicado on 04/12/14.
 */
@Category(DefaultTest.class)
public class HomeControllerTestCase extends AbstractMvcUnitBeans {


    @Autowired
    public HomeController homeController;



    @Test
    public void testHomePrivate() throws Exception {
        EnMePlaceHolderConfigurer.setProperty("application.private", "true");
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/home");
        final ModelAndView mav = handlerAdapter.handle(request, response, homeController);
        assertViewName(mav, "redirect:/user/signin");
    }

    /**
     *
     * @param path
     * @param mp
     * @throws Exception
     */
    private void testPath(String path, HashMap<String, String> mp)  throws Exception {
        EnMePlaceHolderConfigurer.setProperty("application.private", "false");
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            logPrint(pairs.getKey() + " = " + pairs.getValue());
            it.remove();
            request.setParameter(pairs.getKey().toString(), pairs.getValue().toString());
        }
        ModelAndView mav = handlerAdapter.handle(request, response, homeController);
        List items = (List) mav.getModelMap().get("items");
        assertViewName(mav, "home");
        Assert.assertNotNull(items);

    }

    @Test
    public void testHome() throws Exception {
        EnMePlaceHolderConfigurer.setProperty("application.private", "false");
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/home");
        final ModelAndView mav = handlerAdapter.handle(request, response, homeController);
        assertViewName(mav, "home");
    }

    @Test
    public void testHomeTweeetpoll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "tweetpoll");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeTweeetpoll24() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "tweetpoll");
        hashMap.put("period", "24");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeTweeetpol7() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "tweetpoll");
        hashMap.put("period", "7");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeTweeetpoll30() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "tweetpoll");
        hashMap.put("period", "30");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeTweeetpollAll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "tweetpoll");
        hashMap.put("period", "all");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomePoll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "poll");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomePoll24() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "poll");
        hashMap.put("period", "24");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomePoll7() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "poll");
        hashMap.put("period", "7");
        testPath("/home", hashMap);
    }


    @Test
    public void testHomePoll30() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "poll");
        hashMap.put("period", "30");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomePollAll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "poll");
        hashMap.put("period", "all");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeSurvey() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "survey");
        testPath("/home", hashMap);
    }


    @Test
    public void testHomeSurvey24() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "survey");
        hashMap.put("period", "24");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeSurvey7() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "survey");
        hashMap.put("period", "7");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeSurvey30() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "survey");
        hashMap.put("period", "30");
        testPath("/home", hashMap);
    }

    @Test
    public void testHomeSurveyAll() throws Exception {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("view", "survey");
        hashMap.put("period", "all");
        testPath("/home", hashMap);
    }


    @Test
    public void testquestionController() throws Exception {
        EnMePlaceHolderConfigurer.setProperty("application.private", "true");
        UserAccount user = this.quickLogin();
        Question q = createQuestion("test", user.getAccount());
        String path = "/question/detail/{id}/{slug}";
        path = path.replace("{id}", q.getQid().toString());
        path = path.replace("{id}", q.getSlugQuestion());
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        final ModelAndView mav = handlerAdapter.handle(request, response, homeController);
        assertViewName(mav, "question/detail");
    }

    @Test
    public void testquestionController404() throws Exception {
        EnMePlaceHolderConfigurer.setProperty("application.private", "true");
        String path = "/question/detail/33333/test";
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        final ModelAndView mav = handlerAdapter.handle(request, response, homeController);
        assertViewName(mav, "404");
    }
}

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

package org.encuestame.test.business.csv.cron;

import org.apache.commons.collections.ListUtils;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.cron.IndexRebuilder;
import org.encuestame.core.cron.ReIndexJob;
import org.encuestame.core.service.IFrontEndService;
import org.encuestame.core.service.IPollService;
import org.encuestame.core.service.ITweetPollService;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.test.business.config.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.SearchPeriods;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by jpicado on 21/12/14.
 */
//@Category(DefaultTest.class)
public class TestCron extends AbstractSpringSecurityContext {

    @Autowired
    public CalculateRelevance calculateRelevance;

    @Autowired
    public CalculateHashTagSize calculateHashTagSize;

    @Autowired
    public IFrontEndService frontEndService;

    @Autowired
    public ITweetPollService tweetPollService;

    @Autowired
    public IPollService pollService;

    @Autowired
    public ReIndexJob reIndexJob;

    @Autowired
    public IndexRebuilder indexRebuilder;

    /**
     * /
     */
    UserAccount userAccount;

    /**
     *
     */
    @Before
    public void initService() {
        this.userAccount = getSpringSecurityLoggedUserAccount();
        final Question question = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        final Question question1 = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        final Question question2 = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        final Question question3 = createQuestion("Real Madrid or Barcelona", userAccount.getAccount());
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question1);
        createTweetPollPublicated(Boolean.TRUE, Boolean.FALSE, new Date(), userAccount, question2);
        createTweetPollPublicated(Boolean.FALSE, Boolean.TRUE, new Date(), userAccount, question3);
        DateTime dateTime = new DateTime();
        dateTime.minusWeeks(4);
        Poll poll1 = createPoll(
                dateTime.toDate(),
                createQuestion("question 2", "Si"),
                userAccount,
                true,
                true);
        Poll poll2 = createPoll(
                dateTime.toDate(),
                createQuestion("question 1", "Si"),
                userAccount,
                true,
                true);
        createHashTag("demo1");
        createHashTag("demo2");
        createHashTag("demo3");
        createHashTag("demo4");
        createHashTag("demo5");
        createHashTag("demo6");
        createHashTag("demo7");
        //force
        EnMePlaceHolderConfigurer.setSystemInitialized(true);
    }

    @After
    public void endTest(){
        EnMePlaceHolderConfigurer.setSystemInitialized(false);
    }

    /**
     *
     */
    @Test
    public void testgetTweetPollsbyRange() {
        this.login(userAccount);
        final List<TweetPoll> tweetPolls = this.tweetPollService.getTweetPollsbyRange(10, 0, null);
        log.debug("debug::==>::" + tweetPolls.size());
        Assert.assertEquals(tweetPolls.size(), 3);
        final List<TweetPoll> tweetPolls2 = this.tweetPollService.getTweetPollsbyRange(2, 0, null);
        log.debug("debug::==>::" + tweetPolls2.size());
        Assert.assertEquals(tweetPolls2.size(), 2);
    }

    /**
     *
     */
    @Test
    public void testgetPollsByRange() {
        final List<Poll> poll = this.pollService.getPollsByRange(10, 0, null);
        Assert.assertEquals(poll.size(), 2);
    }

    /**
     *
     */
    @Test
    public void testProcessItemstoCalculateRelevance(){
        createFakesTweetPoll(getSpringSecurityLoggedUserAccount());
        final List<TweetPoll> tweetPolls = this.tweetPollService.getTweetPollsbyRange(10, 0, null);
        this.frontEndService.processItemstoCalculateRelevance(tweetPolls, ListUtils.EMPTY_LIST, ListUtils.EMPTY_LIST, SearchPeriods.ALLTIME);
    }

    @Test
    public void testcalculate() {
        //FUTURE: this test needs more test in deep
        this.calculateRelevance.calculate();
    }

    @Test
    public void testCalculateHashTagSizeMcalcalculate() {
        //FUTURE: this test needs more test in deep
        this.calculateHashTagSize.calculate();
    }

    @Test
    public void testreIndexJob(){
        this.reIndexJob.reindex();
    }

    @Test
    public void testIndexRebuilder() throws Exception{
        this.indexRebuilder.reindexEntities();
    }

}

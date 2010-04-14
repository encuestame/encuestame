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
package org.encuestame.core.test.service;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.AbstractSurveyService;
import org.encuestame.core.test.service.config.AbstractBase;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

import twitter4j.Status;

/**
 * Test of {@link AbstractSurveyService}
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 15:04:56
 * @version $Id$
 */

public class TestSurveyService  extends AbstractBase{

    /** {@link AbstractSurveyService} */
    @Autowired
    private ISurveyService surveyService;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    /** {@link Questions} */
    private Questions question;

    /** {@link QuestionPattern} **/
    private QuestionPattern pattern;

    private SecUsers user;
    /**
     * Before.
     */
    @Before
    public void setterBeans(){
        surveyService.setServiceMail(mailServiceImpl);
        this.user = createUser("testEncuesta", "testEncuesta123");
    }
    /**
     *
     */
    @Before
    public void serviceInit(){
            this.question = createQuestion("Why the sky is blue?","html");
            this.pattern = createQuestionPattern("html");
    }
    /**
     * Test Load All Questions without questions.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllQuestionsSizeZero() throws EnMeExpcetion{
        final List<UnitQuestionBean> alist = surveyService.loadAllQuestions();
        assertEquals("Should be equals",1, alist.size());
    }

    /**
     * Test Load All Questions.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllQuestions() throws EnMeExpcetion{
      //  this.serviceInit();
        final List<UnitQuestionBean> alist = surveyService.loadAllQuestions();
        assertEquals("Should be equals",1, alist.size());
    }

    /**
     * Load Patter Info Null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testloadPatternInfoNull() throws EnMeExpcetion {
        surveyService.loadPatternInfo(null);
    }

    /**
     * Load Patter Info.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadPatternInfo() throws EnMeExpcetion {
      //  this.serviceInit();
        UnitPatternBean patternBean = new UnitPatternBean(this.pattern.getPatternId(),"descPattern","label",
                "patronType", "template","classpattern","levelpattern","finallity");
    //    patternBean.setId(createQuestionPattern("html").getPatternId());
        patternBean = surveyService.loadPatternInfo(patternBean);
       // assertNotNull(patternBean);
       assertEquals("Should be equals",patternBean.getPatronType(), getPattern().getPatternType());
    }

    /**
     * Load All Patterns.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllPatrons() throws EnMeExpcetion {
       // this.serviceInit();
        final Collection<UnitPatternBean> patternList = surveyService.loadAllPatrons();
       // assertNotNull(patternList);
        assertEquals("Should be equals",2, patternList.size());
    }

    /**
     * Load All Patterns Zero Results.
     * @throws EnMeExpcetion exception
     */
  //  @Test
    public void testloadAllPatronsZeroResults() throws EnMeExpcetion {
        final Collection<UnitPatternBean> patternList = surveyService.loadAllPatrons();
        assertNotNull(patternList);
        assertEquals("Should be equals",0, patternList.size());
    }

    /**
     * Test Create Tweet Poll.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testCreateTweetPoll() throws EnMeExpcetion{
       final Questions question = createQuestion("why the sky is blue?", "yes/no", this.user);
       createQuestionAnswer("yes", question, "12345");
       createQuestionAnswer("no", question, "12346");
       final UnitTweetPoll tweetPollBean = new UnitTweetPoll();
       final UnitQuestionBean questionBean = new UnitQuestionBean();
       questionBean.setId(question.getQid());
       tweetPollBean.setQuestionBean(questionBean);
       tweetPollBean.setPublishPoll(true);
       tweetPollBean.setScheduleDate(new Date());
       tweetPollBean.setCompleted(false);
       tweetPollBean.setUserId(this.user.getUid());
       this.surveyService.createTweetPoll(tweetPollBean);
       final String s = this.surveyService.generateTweetPollText(tweetPollBean,  RandomStringUtils.randomAlphabetic(15));
       final Status status = this.surveyService.publicTweetPoll(s, this.user.getTwitterAccount(), this.user.getTwitterPassword());
       assertNotNull(status.getId());
    }

    /**
     * Service to retrieve Results TweetPoll  Id.
     */
    @Test
    public void testGetResultsByTweetPollId(){
       final TweetPoll tweetPoll = createFastTweetPollVotes();
       final List<UnitTweetPollResult> results = this.surveyService.getResultsByTweetPollId(tweetPoll.getTweetPollId());
       assertEquals("Should be equals", 2 , results.size());
    }

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(final ISurveyService surveyService) {
        this.surveyService = surveyService;
    }
    /**
     * @return the question
     */
    public Questions getQuestion() {
        return question;
    }
    /**
     * @return the pattern
     */
    public QuestionPattern getPattern() {
        return pattern;
    }

    /**
     * @param mailServiceImpl the mailServiceImpl to set
     */
    public void setMailServiceImpl(final MailServiceImpl mailServiceImpl) {
        this.mailServiceImpl = mailServiceImpl;
    }
}

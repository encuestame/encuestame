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
package org.encuestame.mvc.test.stats;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.statistics.HashTagStatsJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link HashTagStatsJsonController} Test Case.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 09, 2012.
 */
public class HashTagsJsonStatsTestCase extends AbstractJsonMvcUnitBeans {

    /**
     *
     */
    private UserAccount userAcc = getSpringSecurityLoggedUserAccount();

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    //@Test
    //TODO: Need a review.
    public void testGetHashTagButtonStats() throws ServletException, IOException{
        //1-  Create Tag, Question and TweetPoll
        final Date creationDate = new Date();

        /* HashTag **/
        final HashTag hashtag1 = createHashTag("romantic");

        /* Question **/
        final Question question = createQuestion("What is your favorite type of movies?", "");

        /* TweetPoll **/
        final TweetPoll myTweetPoll = createPublishedTweetPoll(question, userAcc);

        /* Poll **/
        final Poll myPoll = createPoll(creationDate, question, userAcc, Boolean.TRUE, Boolean.TRUE);

        /* Survey **/
        final Survey mySurvey = createDefaultSurvey(userAcc.getAccount(), "My First Encuestame Survey", creationDate);



        // 2- Add HashTag to TweetPoll, Poll or Survey
        myTweetPoll.getHashTags().add(hashtag1);
        getTweetPoll().saveOrUpdate(myTweetPoll);

        myPoll.getHashTags().add(hashtag1);
        getPollDao().saveOrUpdate(myPoll);

        mySurvey.getHashTags().add(hashtag1);
        getSurveyDaoImp().saveOrUpdate(mySurvey);

        // 2- Create TweetPollSavedPublishedStatus -- Create Social network link.


        final SocialAccount socialAccount = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                myTweetPoll, " ", socialAccount, tweetContent);

        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);

        //Total Usage by Social Networks
        // Total Usaged by HashTag (TweetPoll, Poll or Survey)

        // Total Usage by HashTag(Hits)
            createHashTagHit(hashtag1, "192.1.1.1");
            createHashTagHit(hashtag1, "192.1.1.2");
            createHashTagHit(hashtag1, "192.1.1.3");
            createHashTagHit(hashtag1, "192.1.1.4");
            createHashTagHit(hashtag1, "192.1.1.5");

            final Long totalHitsbyTag = getFrontEndDao().getTotalHitsbyType(hashtag1.getHashTagId(),
                    TypeSearchResult.HASHTAG);
             Assert.assertEquals("Should be equals ", totalHitsbyTag.intValue(), 5);


        // 2- Call Json service
        initService("/api/common/hashtags/stats/button.json", MethodJson.GET);
        setParameter("tagName", "");
        setParameter("filter", "");
        setParameter("limit", "10");

        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONObject buttonStats = (JSONObject) success.get("hashTagButtonStats");
        log.debug("Showing stats usage" +  buttonStats.get("usageByItem").toString());
        log.debug("Showing stats usage by social network links" +  buttonStats.get("totalUsageBySocialNetwork").toString());
        log.debug("Showing stats usage by item(HashTag)" +  buttonStats.get("totalHits").toString());

    }

    @Test
    public void testGetHashTagRankingStats() throws ServletException, IOException{
        final Date myDate = new Date();
        final HashTag tag = createHashTag("America", 20L);
        final HashTag tag1 = createHashTag("Europa", 20L);
        final HashTag tag2 = createHashTag("Asia", 20L);
        final HashTag tag3 = createHashTag("Oceania", 20L);
        final HashTag tag4 = createHashTag("Africa", 20L);

        createHashTagRank(tag3, myDate, (double) 90); // Oceania -- 0
        createHashTagRank(tag4, myDate, (double) 70); // Africa -- 1
        createHashTagRank(tag2, myDate, (double) 30); // Asia -- 2
        createHashTagRank(tag, myDate, (double) 20); // America -- 3
        createHashTagRank(tag1, myDate, (double) 10); // Europa --4

        // Call json service.
        initService("/api/common/hashtags/stats/ranking.json", MethodJson.GET);
        setParameter("tagName", "Asia");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray hashTagsRanking2 = (JSONArray) success.get("hashTagRankingStats");
        System.out.println("Size HashTag ranking json --->" + hashTagsRanking2.size());
    }



}

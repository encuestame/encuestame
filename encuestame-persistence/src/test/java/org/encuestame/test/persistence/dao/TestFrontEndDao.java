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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.FrontEndDao;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.enums.TypeSearchResult;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link FrontEndDao}..
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since January 06, 2011
 * @version $Id: $
 */
public class TestFrontEndDao extends AbstractBase {

    /** {@link HashTag} **/
    private HashTag hashTag;

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** {@link Hit} **/
    private Hit hit;

    final String ipAddress = "192.168.1.1";

    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        this.hashTag = createHashTag("software");
        final String ipAddress2 = "192.168.1.2";
        final String ipAddress3 = "192.168.1.3";
        this.hit = createHashTagHit(hashTag, ipAddress);
        createHashTagHit(hashTag, ipAddress2);
        createHashTagHit(hashTag, ipAddress3);
    }

    /** Test Get hash tags by ip.**/
   @Test
    public void testGetHashTagsHitByIp() {
        assertNotNull(this.hit);
        flushIndexes();
        final List<Hit> hitsbyIp = getFrontEndDao().getHitsByIpAndType(
                this.ipAddress, this.hashTag.getHashTagId(),
                TypeSearchResult.HASHTAG);
        assertNotNull(hitsbyIp);
        assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(),
                this.ipAddress);
        assertEquals("Should be equals", hitsbyIp.size(), 1);
    }

   @Test
   public void testGetHitsByIpandType(){
       assertNotNull(this.hashTag);
       flushIndexes();
       final List<Hit> hitsbyIp = getFrontEndDao().getHitsByIpAndType(
               ipAddress,
               this.hashTag.getHashTagId(),
               TypeSearchResult.HASHTAG);
       assertNotNull(hitsbyIp);
       assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(), this.ipAddress);
       final Long totalHits = getFrontEndDao().getTotalHitsbyType(hashTag.getHashTagId(), TypeSearchResult.HASHTAG);
       assertEquals("total hits should be equals", 3, totalHits.intValue());
   }

    /**
     * Test get access rateby item.
     */
    @Test
    public void testGetAccessRatebyItem() {
        this.secondary = createUserAccount("jhon", createAccount());
        final Question question = createQuestion("question1",
                secondary.getAccount());
        final TweetPoll tweet = createPublishedTweetPoll(
                secondary.getAccount(), question, Calendar.getInstance()
                        .getTime());
        final String ipAddress = "192.168.1.19";
        createTweetPollRate(Boolean.TRUE, tweet, ipAddress);
        flushIndexes();
        final List<AccessRate> tpRate = getFrontEndDao().getAccessRatebyItem(
                ipAddress, tweet.getTweetPollId(), TypeSearchResult.TWEETPOLL);
        assertNotNull(tpRate);
        assertEquals("Should be equals", 1, tpRate.size());
    }

    /**
     *
     */
    @Test
    public void testGetLinksByHomeItem() {
        final TweetPoll tp1 = createTweetPoll(12345L, true, true, true, true, false, null, new Date(),
                true, this.secondary.getAccount(),
                createQuestion("test tp1", this.secondary.getAccount()),
                this.secondary);
        final TweetPoll tp2 = createTweetPoll(162345L, true, true, true, true, false, null, new Date(),
                true, this.secondary.getAccount(),
                createQuestion("test tp2", this.secondary.getAccount()),
                this.secondary);
        tp1.getHashTags().add(this.hashTag);
        getTweetPoll().saveOrUpdate(tp1);
        getTweetPoll().saveOrUpdate(tp2);
        createTweetPollSavedPublishedStatus(tp1, "432432532", null, "test tweettxt dad");
        createTweetPollSavedPublishedStatus(tp2, "43243sa2532", null, "test tweettxt fdsc");
        createTweetPollSavedPublishedStatus(tp1, "4324a1232532", null, "test tweettxt cz xc");
        createTweetPollSavedPublishedStatus(tp2, "432d123432532", null, "test tweettxt c cxz");
        List<TweetPollSavedPublishedStatus> links = getFrontEndDao()
                .getLinksByHomeItem(this.hashTag, null, null, null, null,
                        TypeSearchResult.TypeSearchResult.HASHTAG);
        assertEquals("Should be equals", 2, links.size());
    }
}

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
import java.util.List;

import org.encuestame.persistence.dao.imp.FrontEndDao;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.test.config.AbstractBase;
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

    /** {@link HashTagHits} **/
    private HashTagHits hashTagHit;

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
        //this.hashTagHit = createHashTagHit(hashTag, ipAddress, this.secondary);
        this.hit = createHashTagHit(hashTag, ipAddress);
        createHashTagHit(hashTag, ipAddress2);
        createHashTagHit(hashTag, ipAddress3);
    }

    /** Test Get hash tags by ip.**/
   @Test
    public void testGetHashTagsHitByIp(){
       System.out.println("----------------");
        //assertNotNull(this.hashTagHit);
        //flushIndexes();
        //final List<HashTagHits> hitsbyIp = getFrontEndDao().getHashTagsHitByIp(this.ipAddress);
       // System.out.print("SIZE HASHTAG hit---> "+ hitsbyIp.size());
        //assertNotNull(hitsbyIp);
        //assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(), this.ipAddress);
        //assertEquals("Should be equals", hitsbyIp.size(),1);
    }

   //@Test
   public void testGetHitsByIpandType(){
       assertNotNull(this.hashTag);
       flushIndexes();
       final List<Hit> hitsbyIp = getFrontEndDao().getHitsByIpAndType(ipAddress, this.hashTag.getHashTagId(), "hashTag");
       //System.out.print("size hashTag hit---> "+ hitsbyIp.size());
       assertNotNull(hitsbyIp);
       assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(), this.ipAddress);
       final Long totalHits = getFrontEndDao().getTotalHitsbyType(hashTag.getHashTagId(), null);
       assertEquals("total hits should be equals", 3, totalHits.intValue());
       //System.out.print("total hit count by hash tag---> "+ totalHits);
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
                ipAddress, tweet.getTweetPollId(), "tweetPoll");
        assertNotNull(tpRate);
        assertEquals("Should be equals", 1, tpRate.size());
    }
}

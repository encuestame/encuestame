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

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.statistics.HashTagJsonStats;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.social.SocialProvider;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * {@link HashTagJsonStats} Test Case.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 09, 2012.
 */
public class HashTagsJsonStatsTestCase extends AbstractJsonMvcUnitBeans {
	
	@Test
	public void testGetHashTagButtonStats() throws ServletException, IOException{
		// Create Tag, Question and TweetPoll
		final HashTag hashtag1 = createHashTag("romantic");
    	final Question question = createQuestion("What is your favorite type of movies?", "");
    	final TweetPoll tp = createPublishedTweetPoll(question, getSpringSecurityLoggedUserAccount());
    	// Add 
    	tp.getHashTags().add(hashtag1);
    	getTweetPoll().saveOrUpdate(tp);
    	
    	final SocialAccount socialAccount = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tp, " ", socialAccount, tweetContent);
       
        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);
		
		
		// 2- Call Json service
		initService("/api/common/hashtags/stats/button.json", MethodJson.GET);
		setParameter("tagName", "");
		setParameter("filter", "");
		setParameter("limit", "10");
		
		final JSONObject response = callJsonService();
		final JSONObject success = getSucess(response);
		final JSONObject buttonStats = (JSONObject) success.get("hashTagButtonStats");
		System.out.println("Showing stats usage" +  buttonStats.get("usageByItem").toString()); 
		 
	}
	
	

}

 /*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.test.json;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.v1.GeoLocationJsonController;
import org.encuestame.mvc.test.config.AbstractJsonV1MvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.social.SocialProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * {@link GeoLocationJsonController} Test Case.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 01, 2012.
 */
@Category(DefaultTest.class)
public class GeoLocationJsonControllerTestCase extends AbstractJsonV1MvcUnitBeans{
	
	/** {@link UserAccount}**/
	private UserAccount userAcc;
	
	/** {@link HashTag} **/
	private HashTag tag ;
	
	/** {@link TweetPoll}. **/
	private TweetPoll initTweetPoll;
	
	@Before
	public void initData(){
		this.userAcc = getSpringSecurityLoggedUserAccount();
		this.tag = createHashTag("real madrid"); 
		final Calendar myCalendarDate = Calendar.getInstance(); 
		this.initTweetPoll = createPublishedTweetPoll(
				this.userAcc,
				createQuestion("What is your favorite futboll team?",
						this.userAcc.getAccount()), myCalendarDate.getTime());

		initTweetPoll.setLocationLatitude(40.4167F);
		this.initTweetPoll.setLocationLongitude(-3.70325F);

		this.initTweetPoll.getHashTags().add(this.tag);

		getTweetPoll().saveOrUpdate(this.initTweetPoll);
		assertNotNull(this.initTweetPoll);
 
		 
	}
	
	/**
	 * Set json parameters.
	 * @param path
	 * @param responseBeanName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private JSONArray setJsonParameters(final String path, final String responseBeanName) throws ServletException, IOException{
		    initService(path, MethodJson.GET);
	        setParameter("range", "510");
	        setParameter("maxItem", "20");
	        setParameter("type", "all");
	        setParameter("longitude", "2.16991870F");
	        setParameter("latitude", "41.3879169");
	        setParameter("period", "all");
	        setParameter("tag", "real madrid");

	        final JSONObject response = callJsonService();
	        final JSONObject success = getSucess(response);
	        final JSONArray geoItems = (JSONArray) success.get(responseBeanName);
		return geoItems;
	}

	/**
	 * Test retrieve all items around a geolocation point.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void testGetItemsbyGeoLocationRange() throws ServletException,
			IOException {
		final JSONArray geoUsage = this.setJsonParameters(
				"/api/common/geolocation/search/all.json", "itemsByGeo");
		Assert.assertEquals("Should be", 1, geoUsage.size());
		 
	}
	
	/**
	 *  Test retrieve hashtag used around geolocation point.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void testRetrieveHashTagUsebyGeoLoc() throws ServletException,
			IOException {
		final TweetPoll tp2 = this.createPublishedTweetPoll(
				this.userAcc,
				createQuestion("What is your favorite television show?",
						this.userAcc.getAccount()), new Date());

		tp2.setLocationLatitude(40.4167F);
		tp2.setLocationLongitude(-3.70325F);
		tp2.getHashTags().add(this.tag);
		getTweetPoll().saveOrUpdate(tp2);

		assertNotNull(tp2);

		final JSONArray geoHashtagItems = this.setJsonParameters(
				"/api/common/geolocation/search/hashtag.json",
				"hashtagUsebyGeo");

		Assert.assertEquals("Should be", 2, geoHashtagItems.size());
	}

	/**
	 * Test retrieve social items published around geolocation data.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void testRetrieveSocialNetworksPublicationsbyGeoLocation()
			throws ServletException, IOException {
		final TweetPoll tp2 = this.createPublishedTweetPoll(
				this.userAcc,
				createQuestion("What is your favorite television show?",
						this.userAcc.getAccount()), new Date());

		tp2.setLocationLatitude(40.4167F);
		tp2.setLocationLongitude(-3.70325F);
		tp2.getHashTags().add(this.tag);
		getTweetPoll().saveOrUpdate(tp2);

		assertNotNull(tp2);

		final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.userAcc);
		assertNotNull(socialAccount);
		final String tweetContent = "Tweet content text";

		final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
				tp2, " ", socialAccount, tweetContent);
		tpSaved2.setApiType(SocialProvider.TWITTER);
		getTweetPoll().saveOrUpdate(tpSaved2);
		assertNotNull(tpSaved2);

		final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", socialAccount, tweetContent);
		tpSaved.setApiType(SocialProvider.TWITTER);
		getTweetPoll().saveOrUpdate(tpSaved);
		assertNotNull(tpSaved);
		final JSONArray geoSocial = this.setJsonParameters(
				"/api/common/geolocation/search/socialnetwork.json",
				"socialGeo");
		Assert.assertEquals("Should be", 2, geoSocial.size());   

	}


}

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
package org.encuestame.test.business.service;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.business.service.GeoLocationService;
import org.encuestame.core.service.imp.GeoLocationSupport;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.dao.IGeoPoint;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.geo.ItemGeoLocationBean;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link GeoLocationService}.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 10, 2010 11:37:52 AM
 */
@Category(DefaultTest.class)
public class TestLocationServices extends AbstractSpringSecurityContext{

    /**
     * Location Service.
     */
    @Autowired
    private GeoLocationSupport locationService;

    /**
     * Cat Location Dao.
     */
    @Autowired
    private IGeoPoint geoPoint;

    private UserAccount secondary;
    
    /** **/
    private HashTag initHashTag;
    
    /** **/
    private DateTime initDate;
    
    /** **/
    private TweetPoll initTweetPoll;

    @Before
    public void init(){
        this.secondary = createUserAccount("jota", createAccount());
        this.initHashTag = createHashTag("developer");
        this.initDate = new DateTime();
        this.initTweetPoll = createPublishedTweetPoll(
				this.secondary.getAccount(),
				createQuestion("What is your favorite futboll team?",
						secondary.getAccount()), this.initDate.toDate());
    }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
    public void testupdateGeoPoint() throws EnMeExpcetion{
        final GeoPoint locationBean = createGeoPoint("test", "test", 0, getSpringSecurityLoggedUserAccount().getAccount());
        final UnitLocationBean bean = ConvertDomainBean.convertLocationToBean(locationBean);
        bean.setName("test2");
        this.locationService.updateGeoPoint(bean);
        Assert.assertEquals(locationBean.getLocationDescription(), new String("test2"));
    }

    /**
     * test for createGeoPoint.
     * @throws EnMeExpcetion
     */
    //@Test
    public void testCreateGeoPoint() throws EnMeExpcetion{
        final UnitLocationBean locationBean = createUnitLocationBean("pozuelo");
        this.locationService.createGeoPoint(locationBean);
        Assert.assertNotNull(locationBean.getId());
    }

    /**
     * Test createLocationFolder.
     * @throws EnMeExpcetion
     */
    //@Test
    public void testcreateLocationFolder() throws EnMeExpcetion{
        final UnitLocationFolder folder = createUnitLocationFolder("folder");
        this.locationService.createGeoPointFolder(folder);
        Assert.assertNotNull(folder.getId());
    }

    /**
     * @throws EnMeNoResultsFoundException
     *
     */
    //@Test
    public void testretrieveLocationFolderByUser() throws EnMeNoResultsFoundException{
         final UnitLocationFolder folder1 = createUnitLocationFolder("folder 1");
         this.locationService.createGeoPointFolder(folder1);
         final UnitLocationFolder folder2 = createUnitLocationFolder("folder2 ");
         this.locationService.createGeoPointFolder(folder2);
         final List<UnitLocationFolder> list = this.locationService.retrieveLocationFolderByUser(getSpringSecurityLoggedUserAccount().getUsername());
         Assert.assertEquals(list.size(), 2);
    }

    /**
     * test retrieveLocationSubFolderByUser.
     * @throws Exception
     */
    //@Test
    public void testretrieveLocationSubFolderByUser() throws Exception{
        final GeoPointFolder locationFolder = createGeoPointFolder(GeoPointFolderType.GROUPING, this.secondary.getAccount(), "folder", null);
        createGeoPointFolder(GeoPointFolderType.GROUPING, this.secondary.getAccount(), "folder sub", locationFolder);
        final List<UnitLocationFolder> list = this.locationService.retrieveLocationSubFolderByUser(locationFolder.getLocationFolderId(), this.secondary.getUsername());
        Assert.assertEquals(list.size(), 1);
    }

    /**
     *
     * @throws Exception
     */
    //@Test
    public void testupdateLocationMap() throws Exception{
        final GeoPointFolder locationFolder = createGeoPointFolder(GeoPointFolderType.GROUPING, this.secondary.getAccount(), "folder", null);
        //final CatLocation location = createCatLocation("Managua", locTypeName, Level, secUsers)
    }

    /**
     * Test Retrieve items by geolocation and a distance max.
     */
	@Test 
	public void testRetrieveItemsByGeo() {
		final Calendar myCalendarDate = Calendar.getInstance();
		final TweetPoll tweetPoll = createPublishedTweetPoll(
				this.secondary.getAccount(),
				createQuestion("What is your favorite futboll team?",
						secondary.getAccount()), myCalendarDate.getTime());

		tweetPoll.setLocationLatitude(40.4167F);
		tweetPoll.setLocationLongitude(-3.70325F);
		getTweetPoll().saveOrUpdate(tweetPoll);
		assertNotNull(tweetPoll);
		
		// Create Poll
		final Poll myPoll = createPoll(
				myCalendarDate.getTime(),
				createQuestion("What is your favorite futboll team?",
						secondary.getAccount()), this.secondary, Boolean.TRUE,
				Boolean.TRUE);

		myPoll.setLocationLatitude(40.4167F);
		myPoll.setLocationLongitude(-3.70325F);
		getPollDao().saveOrUpdate(myPoll);
		assertNotNull(myPoll);

		final Survey mySurvey = createDefaultSurvey(this.secondary.getAccount());
		mySurvey.setLocationLatitude(40.4167F);
		mySurvey.setLocationLongitude(-3.70325F);
		getSurveyDaoImp().saveOrUpdate(mySurvey);
		assertNotNull(mySurvey);

		final List<ItemGeoLocationBean> distanceFromOrigin = getLocationService()
				.retrieveItemsByGeo(510d, 30, TypeSearchResult.ALL,
						2.16991870F, 41.3879169F, SearchPeriods.SEVENDAYS);
		Assert.assertEquals(distanceFromOrigin.size(), 3);
		Assert.assertEquals(distanceFromOrigin.get(0).getLatitude(), tweetPoll.getLocationLatitude());
		Assert.assertEquals(distanceFromOrigin.get(1).getLatitude(), myPoll.getLocationLatitude());
	}
	
	/**
	 * Test retrieve {@link HashTag} use by geoLocation.
	 * @throws EnMeSearchException
	 */
	@Test
	public void testRetrieveHashTagsByGeo() throws EnMeSearchException {

		final HashTag hashtag3 = createHashTag("ana");

		this.initTweetPoll.setLocationLatitude(40.4167F);
		this.initTweetPoll.setLocationLongitude(-3.70325F);
		this.initTweetPoll.getHashTags().add(this.initHashTag);
		this.initTweetPoll.getHashTags().add(hashtag3);
		getTweetPoll().saveOrUpdate(this.initTweetPoll);
		assertNotNull(this.initTweetPoll);

		final TweetPoll tp2 = this.createTweetPollTest(
				"What is your favorite television show?",
				this.initDate.toDate()); 

		tp2.setLocationLatitude(40.4167F);
		tp2.setLocationLongitude(-3.70325F);
		tp2.getHashTags().add(hashtag3);
		getTweetPoll().saveOrUpdate(tp2);

		assertNotNull(tp2);
		
		final List<ItemGeoLocationBean> items = locationService
				.retreiveHashTagUsebyGeoLo(510d, 30,
						TypeSearchResult.ALL, 2.16991870F, 41.3879169F,
						hashtag3.getHashTag(), SearchPeriods.ALLTIME);  
		Assert.assertEquals(items.size(), 2); 
	}

	/**
	 * Test retrieve items published on social networks.
	 * @throws EnMeSearchException
	 * @throws EnMeNoResultsFoundException
	 */
	@Test
	public void testRetreiveSocialNetworksPublicationsbyGeoLocation()
			throws EnMeSearchException, EnMeNoResultsFoundException {
		this.initTweetPoll.setLocationLatitude(40.4167F);
		this.initTweetPoll.setLocationLongitude(-3.70325F);
		getTweetPoll().saveOrUpdate(this.initTweetPoll);

		final TweetPoll tp2 = this.createTweetPollTest(
				"What is your favorite rock band?",
				this.initDate.toDate());

		tp2.setLocationLatitude(40.4167F);
		tp2.setLocationLongitude(-3.70325F);

		getTweetPoll().saveOrUpdate(tp2);

		assertNotNull(tp2);

		final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
		assertNotNull(socialAccount);
		final String tweetContent = "Tweet content text";

		final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", socialAccount, tweetContent);
		tpSaved.setApiType(SocialProvider.TWITTER);
		getTweetPoll().saveOrUpdate(tpSaved);
		assertNotNull(tpSaved);

		final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", socialAccount, tweetContent);
		tpSaved2.setApiType(SocialProvider.FACEBOOK);
		getTweetPoll().saveOrUpdate(tpSaved2);
		assertNotNull(tpSaved2);
		
		final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
				tp2, " ", socialAccount, tweetContent);
		tpSaved3.setApiType(SocialProvider.FACEBOOK);
		getTweetPoll().saveOrUpdate(tpSaved3);
		assertNotNull(tpSaved3);


		 final List<TweetPollSavedPublishedStatus> tpsavedPublished = getTweetPoll()
				.getLinksByTweetPoll(this.initTweetPoll, null, null,
						TypeSearchResult.TWEETPOLL);
		Assert.assertEquals("Should be", 2, tpsavedPublished.size()); 
		

		final List<ItemGeoLocationBean> socialLinks = locationService
				.retrieveSocialNetworksPublicationsbyGeoLocation(510d, 30,
						TypeSearchResult.ALL, 2.16991870F, 41.3879169F,
						SearchPeriods.ALLTIME);
		Assert.assertEquals("Should be", 3, socialLinks.size());   
	}

	/**
	 * 
	 * @param questionName
	 * @param creationDate
	 * @return
	 */
	private TweetPoll createTweetPollTest(final String questionName,
			final Date creationDate) {
		final Question question = createQuestion(
				"What is your favorite futboll team22?", this.secondary.getAccount());
		final TweetPoll tweetPoll = createPublishedTweetPoll(
				this.secondary.getAccount(), question, creationDate);
		return tweetPoll;
	}
    
    /**
     * @return the locationService
     */
    public GeoLocationSupport getLocationService() {
        return locationService;
    }

    /**
     * @param locationService the locationService to set
     */
    public void setLocationService(GeoLocationSupport locationService) {
        this.locationService = locationService;
    }

    /**
     * @return the geoPoint.
     */
    public IGeoPoint getGeoPoint() {
        return geoPoint;
    }

    /**
     * @param geoPoint the catLocation to set
     */
    public void setGeoPoint(IGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}

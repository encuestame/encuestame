/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ITwitterService;
import org.encuestame.core.service.TwitterService;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * {@link TwitterService} test case.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 5:05:12 PM
 * @version $Id$
 */
public class TestTwitterService extends AbstractBase {

    /** {@link TwitterService}.  */
    @Autowired
    public ITwitterService twitterService;

    /** {@link SecUsers}. **/
    private SecUsers user;

    /**
     * Before.
     */
    @Before
    public void before(){
        this.user = createUser("testEncuesta", "testEncuesta123");
    }

    /**
     * Test Tiny Url.
     * @throws IOException io exception
     * @throws HttpException http exception
     */
    @Test
    public void testTinyUrl() throws HttpException, IOException{
       final String tinyUrl = twitterService.getTinyUrl("http://www.google.es");
       assertNotNull(tinyUrl);
    }

    /**
     * Test Public Tweet.
     * @throws TwitterException exception
     */
    @Test
    public void testPublicTweet() throws TwitterException{
        final String testTweet = RandomStringUtils.randomAlphabetic(5);
        final Status tweet = twitterService.publicTweet(this.user.getTwitterAccount(),
                                                        this.user.getTwitterPassword(), testTweet);
        assertNotNull(tweet.getId());
    }

    /**
     * @throws TwitterException ex
     *
     */
    public void test() throws TwitterException{
        Twitter twitter = new Twitter();
        twitter.setOAuthConsumer("nFboU4T1Zhv8cqMC4cP0ug", "GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0");
        RequestToken twitterRequestToken
                         = twitter.getOAuthRequestToken();
        String token = twitterRequestToken.getToken();
        String tokenSecret = twitterRequestToken.getTokenSecret();
       String authorizationUrl = twitterRequestToken.
       getAuthorizationURL();
    }

    /**
     * @return the twitterService
     */
    public ITwitterService getTwitterService() {
        return twitterService;
    }

    /**
     * @param twitterService the twitterService to set
     */
    public void setTwitterService(ITwitterService twitterService) {
        this.twitterService = twitterService;
    }
}

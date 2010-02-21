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

package org.encuestame.core.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.runner.Request;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * Twitter Service.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 4:07:03 PM
 * @version $Id$
 */
public class TwitterService extends Service implements ITwitterService {


    private String tinyApi;

    /**
     * Constructor.
     */
    public TwitterService() {
    }

    /**
     * Get Tiny Url.
     * @param url survey url
     * @return tiny url
     * @throws IOException IOException
     * @throws HttpException HttpExceptio
     */
    public String getTinyUrl(final String url) throws HttpException, IOException{
        final HttpClient httpclient = new HttpClient();
        final HttpMethod method = new GetMethod(tinyApi);
        method.setQueryString(new NameValuePair[]{new NameValuePair("url",url)});
        httpclient.executeMethod(method);
        final String tinyUrl = method.getResponseBodyAsString();
        method.releaseConnection();
        return tinyUrl;
    }

    /**
     * Public Tweet Poll.
     * @param username twitter username
     * @param password twitter password
     * @param tweet text to tweet
     * @return {@link Status}
     * @throws TwitterException twitter exception
     */
    public Status publicTweet(final String username, final String password, final String tweet) throws TwitterException{
        final Twitter twitter = new Twitter(username, password);
        return twitter.updateStatus(tweet);
    }

    /**
     * Get Twitter Ping.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @return {@link RequestToken}
     * @throws TwitterException exception
     */
    public RequestToken getTwitterPing(String consumerKey, String consumerSecret)
            throws TwitterException {
        if (consumerKey == null) {
            throw new IllegalArgumentException("Consumer key is missing");
        }
        if (consumerSecret == null) {
            throw new IllegalArgumentException("Consumer secret is missing");
        }
        final Twitter twitter = new Twitter();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        return twitter.getOAuthRequestToken();
    }

    /**
     * @return the tinyApi
     */
    public String getTinyApi() {
        return tinyApi;
    }

    /**
     * @param tinyApi the tinyApi to set
     */
    public void setTinyApi(final String tinyApi) {
        this.tinyApi = tinyApi;
    }
}

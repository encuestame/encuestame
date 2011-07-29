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
package org.encuestame.social;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.encuestame.utils.TweetPublishedMetadata;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract template API layer.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 19, 2011
 */
public abstract class AbstractSocialAPISupport {

    private RestTemplate restTemplate;

    protected DateFormat searchDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    protected DateFormat timelineDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);

    /**
     *
     * @param dateString
     * @param dateFormat
     * @return
     */
    protected Date toDate(String dateString, DateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public TweetPublishedMetadata createStatus(final String tweetText){
        final TweetPublishedMetadata published = new TweetPublishedMetadata();
        published.setDatePublished(Calendar.getInstance().getTime()); //TODO: should be offial time of tweet.
        published.setTextTweeted(tweetText);
        return published;
    }

    /**
     * @return the restTemplate
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * @param restTemplate the restTemplate to set
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}

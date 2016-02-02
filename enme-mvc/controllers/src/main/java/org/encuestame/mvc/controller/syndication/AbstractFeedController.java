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
package org.encuestame.mvc.controller.syndication;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.rss.Item;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.core.util.FeedUtils;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.search.TweetPollSearchBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

 

/**
 * Abstract Feed Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 3:29:57 AM
 * @version $Id:$
 */
public abstract class AbstractFeedController extends AbstractBaseOperations{


   /**
    *
    */
   @Autowired
   private VelocityEngine velocityEngine;

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

            /**
             * Get TweetPolls.
             * @param username
             * @return
             * @throws EnMeNoResultsFoundException
             */

    public List<TweetPollBean> getTweetPolls(final String username, final HttpServletRequest httpServletRequest) throws EnMeNoResultsFoundException{
        return getTweetPollService().getTweetsPollsByUserName(username, httpServletRequest, new TweetPollSearchBean());
    }

    /**
     * Get polls.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<PollBean> getPolls(final String username) throws EnMeNoResultsFoundException{
        return getPollService().getPollsByUserName(username, null, null);
    }


    /**
     * Get RSS item tweet poll.
     * @param username
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    public List<Item> getItemRssFeed(
            final String username,
            final HttpServletRequest request,
            final TypeSearchResult itemType,
            final Integer limits) throws EnMeNoResultsFoundException, UnsupportedEncodingException{
        List<Item> item = new ArrayList<Item>();
        log.debug("getItemRssFeed username "+username);
        log.debug("getItemRssFeed itemType "+itemType);
        log.debug("getItemRssFeed limits "+limits);
        if (itemType.equals(TypeSearchResult.TWEETPOLL)) {
            item = FeedUtils.convertTweetPollBeanToItemRSS(
                    getTweetPolls(username, request),
                    InternetUtils.getDomain(request),
                    velocityEngine);
        } else if (itemType.equals(TypeSearchResult.POLL)) {
            item = FeedUtils.convertPollBeanToItemRSS(getPolls(username),
                    InternetUtils.getDomain(request),
                    velocityEngine);
        } else if (itemType.equals(TypeSearchResult.SURVEY)) {
            item = ListUtils.EMPTY_LIST;
        } else if (itemType.equals(TypeSearchResult.PROFILE)) {
            item = FeedUtils.convertHomeBeanToItemRSS(getFrontService()
                .getLastItemsPublishedFromUserAccount(username, limits, false,
                        request),
                        InternetUtils.getDomain(request),
                        velocityEngine);
//        } else if (itemType.equals("projects")) {
//            item = ListUtils.EMPTY_LIST;
        } else if (itemType.equals(TypeSearchResult.ALL)) {
            try {
                item = FeedUtils.convertHomeBeanToItemRSS(
                        //FUTURE: migrate to SearchPeriods Enum
                        getFrontService().getFrontEndItems(SearchPeriods.ALLTIME.toString(), EnMeUtils.DEFAULT_START, limits, true, request),
                        InternetUtils.getDomain(request),
                        velocityEngine);
            } catch (EnMeSearchException e) {
                log.error("Error on retrieve RSS home items ", e);
                item = ListUtils.EMPTY_LIST;
            }
        }
        log.debug("getItemRssFeed item "+item.size());
        return item;
    }

    /**
     * Get atom entry tweet poll.
     * @param username
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws UnsupportedEncodingException
     * @throws EnMeSearchException
     */
    public List<Entry> getEntryAtomFeed(
            final String username,
            final HttpServletRequest request,
            final TypeSearchResult entryType,
            final Integer limits) throws EnMeNoResultsFoundException, UnsupportedEncodingException, EnMeSearchException{
        List<Entry> entry = new ArrayList<Entry>();
        if (entryType.equals(TypeSearchResult.TWEETPOLL)) {
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                    getTweetPolls(username, request),
                    InternetUtils.getDomain(request), velocityEngine);
        }else if(entryType.equals(TypeSearchResult.POLL)) {
            entry = FeedUtils.convertPollBeanToEntryAtom(
                      getPolls(username),
                      InternetUtils.getDomain(request), velocityEngine);
        }else if(entryType.equals(TypeSearchResult.SURVEY)) {
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                     getTweetPolls(username, request),
                     InternetUtils.getDomain(request), velocityEngine);
        } else if(entryType.equals(TypeSearchResult.PROFILE)) {
            entry = FeedUtils.convertHomeBeanToItemATOM(
                    getFrontService().getLastItemsPublishedFromUserAccount(username, limits, false,
                            request),
                            InternetUtils.getDomain(request),
                            velocityEngine);
        }
//        else if(entryType.equals("projects")){
//            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
//                     getTweetPolls(username, request),
//                     InternetUtils.getDomain(request));
//        }
        else if(entryType.equals("frontend")){
            entry = FeedUtils.convertHomeBeanToItemATOM(
                    getFrontService().getFrontEndItems(SearchPeriods.ALLTIME.toString(),
                            EnMeUtils.DEFAULT_START, limits, request),
                    InternetUtils.getDomain(request), velocityEngine);
        }
        return entry;
    }


    /**
     * @return the velocityEngine
     */
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
     * @param velocityEngine the velocityEngine to set
     */
    @Autowired
    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}

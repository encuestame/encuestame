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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.core.util.FeedUtils;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.PollBean;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.rss.Item;

/**
 * Abstract Feed Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 3:29:57 AM
 * @version $Id:$
 */
public abstract class AbstractFeedController extends AbstractBaseOperations{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Get TweetPolls.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<TweetPollBean> getTweetPolls(final String username, final HttpServletRequest httpServletRequest) throws EnMeNoResultsFoundException{
        return getTweetPollService().getTweetsPollsByUserName(username, httpServletRequest, null);
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
     */
    @SuppressWarnings("unchecked")
    public List<Item> getItemRssFeed(
            final String username,
            final HttpServletRequest request,
            final String itemType,
            final Integer limits) throws EnMeNoResultsFoundException{
        List<Item> item = new ArrayList<Item>();
        log.debug("getItemRssFeed username "+username);
        log.debug("getItemRssFeed itemType "+itemType);
        log.debug("getItemRssFeed limits "+limits);
        if (itemType.equals("tweetPolls")) {
            item = FeedUtils.convertTweetPollBeanToItemRSS(
                    getTweetPolls(username, request), InternetUtils.getDomain(request));
        } else if (itemType.equals("polls")) {
            item = FeedUtils.convertPollBeanToItemRSS(getPolls(username),
                    InternetUtils.getDomain(request));

        } else if (itemType.equals("surveys")) {
            item = ListUtils.EMPTY_LIST;
        } else if (itemType.equals("profiles")) {
            item = FeedUtils.convertHomeBeanToItemRSS(getFrontService()
                .getLastItemsPublishedFromUserAccount(username, limits, false,
                        request),
                        InternetUtils.getDomain(request));
        } else if (itemType.equals("projects")) {
            item = ListUtils.EMPTY_LIST;
        } else if (itemType.equals("frontend")) {
            try {
                item = FeedUtils.convertHomeBeanToItemRSS(
                        getFrontService().getFrontEndItems("all",
                                EnMeUtils.DEFAULT_START, limits, request),
                        InternetUtils.getDomain(request));
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
     */
    public List<Entry> getEntryAtomFeed(
            final String username,
            final HttpServletRequest request,
            final String entryType,
            final Integer limits) throws EnMeNoResultsFoundException{
        List<Entry> entry = new ArrayList<Entry>();
        if (entryType.equals("tweetPolls")){
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                    getTweetPolls(username, request),
                    InternetUtils.getDomain(request));
        }else if(entryType.equals("polls")){
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                      getTweetPolls(username, request),
                      InternetUtils.getDomain(request));

        }else if(entryType.equals("surveys")){
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                     getTweetPolls(username, request),
                     InternetUtils.getDomain(request));
        }
        else if(entryType.equals("profiles")){
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                     getTweetPolls(username, request),
                     InternetUtils.getDomain(request));
        }
        else if(entryType.equals("projects")){
            entry = FeedUtils.convertTweetPollBeanToEntryAtom(
                     getTweetPolls(username, request),
                     InternetUtils.getDomain(request));
        }
        else if(entryType.equals("frontend")){
//            entry = FeedUtils.convertHomeBeanToItemRSS(
//					getFrontService().getFrontEndItems("all",
//							EnMeUtils.DEFAULT_START, limits, request),
//					InternetUtils.getDomain(request));
        }
        return entry;
    }
}

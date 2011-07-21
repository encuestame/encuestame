/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.encuestame.utils.web.TweetPollBean;
import org.encuestame.utils.web.UnitPoll;

import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.feed.rss.Item;

/**
 * Feed Utils.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 10:19:37 AM
 * @version $Id:$
 */
public class FeedUtils {


    /**
     * Create Feed Link.
     * @param url
     * @param title
     * @return
     */
    public final static Link createLink(final String url, final String title){
        Link link = new Link();
        link.setHref(url);
        link.setTitle(title);
        return link;
    }

    /**
    * Create url syndicate.
    * @param url
    * @param code
    * @param slug
    * @return
    */
    public final static String createUrlTweetPoll(final String url, final String code, final TweetPollBean tweetPoll){
        StringBuffer urlString = new StringBuffer(url);
        urlString.append(code);
        urlString.append(tweetPoll.getId());
        urlString.append("/");
        urlString.append(tweetPoll.getQuestionBean().getSlugName());
        return urlString.toString();
    }

    /**
     * Format date.
     * @param format
     * @param tweetPollDate
     * @return
     */
    public final static String formattedDate(final String format, final Date tweetPollDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateToFormat = formatDate.format(tweetPollDate);
        return dateToFormat;
    }

    /**
     * Convert tweetPoll bean to item.
     * @param tpBean
     * @param domain
     * @return
     */
    public final static List<Item> convertTweetPollBeanToItemRSS(final List<TweetPollBean> tpBean, final String domain){
        List<Item> entries = new ArrayList<Item>(tpBean.size());
        for (TweetPollBean content : tpBean) {
            final Item item = new Item();
            final String createdAt = FeedUtils.formattedDate("yyyy-MM-dd", content.getCreatedDateAt());
            item.setTitle(String.format("On %s, %s publish", createdAt, content.getQuestionBean().getQuestionName()));
            String urlTweet = FeedUtils.createUrlTweetPoll(domain, "/tweetpoll/", content);
            item.setPubDate(content.getUpdateDate());
            item.setLink(urlTweet);
            entries.add(item);
        }
        return entries;
    }

    /**
     * Convert tweetPollBean to entry atom.
     * @param tpBean
     * @param domain
     * @return
     */
    public final static List<Entry> convertTweetPollBeanToEntryAtom(final List<TweetPollBean> tpBean, final String domain){
        List<Entry> entries = new ArrayList<Entry>(tpBean.size());
        for (TweetPollBean content : tpBean) {
            final Entry entry = new Entry();
            final String createdAt = FeedUtils.formattedDate("yyyy-MM-dd", content.getCreatedDateAt());
            entry.setId(content.getQuestionBean().getQuestionName());
            entry.setTitle(String.format("On %s, %s publish", createdAt, content.getQuestionBean().getQuestionName()));
            entry.setUpdated(content.getUpdateDate());
            String urlTweet = FeedUtils.createUrlTweetPoll(domain, "/tweetpoll/", content);
            final List<Link> links = new ArrayList<Link>();
            links.add(FeedUtils.createLink(urlTweet,"Tweet Polls"));
            entry.setAlternateLinks(links);
            entries.add(entry);
        }
       return entries;
    }

    /**
     * Convert pollBean to entry item.
     * @param pollBean
     * @param domain
     * @return
     */
    public final static List<Item> convertPollBeanToItemRSS(final List<UnitPoll> pollBean, final String domain){
        List<Item> entries = new ArrayList<Item>(pollBean.size());
        for (UnitPoll content : pollBean) {
            final Item item = new Item();
            final String createdAt = FeedUtils.formattedDate("yyyy-MM-dd", content.getCreationDate());
            item.setTitle(String.format("On %s, %s publish", createdAt, content.getQuestionBean().getQuestionName()));
         //   String urlTweet = FeedUtils.createUrlTweetPoll(domain, "/tweetpoll/", content);
            item.setPubDate(null);
            item.setLink(null);
            entries.add(item);
        }
        return entries;
    }
}

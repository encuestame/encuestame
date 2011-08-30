/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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

import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.PollBean;

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
     * @param id
     * @param slugName
     * @return
     */
    public final static String createUrlFeed(final String url, final String code, final Long id, final String slugName){
        StringBuffer urlString = new StringBuffer(url);
        urlString.append(code);
        urlString.append(id);
        urlString.append("/");
        urlString.append(slugName);
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
            String urlTweetPoll = FeedUtils.createUrlFeed(domain, "/tweetpoll/", content.getId(),
                    content.getQuestionBean().getSlugName());
            final Item item = convertBeanToRSSItem(content.getCreatedDateAt(), content.getQuestionBean().getQuestionName(),
                    content.getUpdateDate(), urlTweetPoll);
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
            String urlTweet = FeedUtils.createUrlFeed(domain, "/tweetpoll/", content.getId(),
                    content.getQuestionBean().getSlugName());
            final Entry entry = convertBeanToAtomEntry(content.getCreatedDateAt(),
                    content.getQuestionBean().getQuestionName(), content.getUpdateDate(), urlTweet);
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
    public final static List<Item> convertPollBeanToItemRSS(final List<PollBean> pollBean, final String domain){
        List<Item> entries = new ArrayList<Item>(pollBean.size());
        for (PollBean content : pollBean) {
            String urlPoll = FeedUtils.createUrlFeed(domain, "/poll/", content.getId(),
                    content.getQuestionBean().getSlugName());
            final Item item = convertBeanToRSSItem(content.getCreationDate(), content.getQuestionBean().getQuestionName(),
                    content.getUpdatedDate(), urlPoll);
            entries.add(item);
        }
        return entries;
    }

    /**
     * Convert bean to RSS Item.
     * @param createdAt
     * @param questionName
     * @param pubDate
     * @param urlLink
     * @return
     */
    public final static Item convertBeanToRSSItem(final Date createdAt,
            final String questionName, final Date pubDate , final String url){
        final Item item = new Item();
        final String formatDate = FeedUtils.formattedDate("yyyy-MM-dd", createdAt);
        item.setTitle(String.format("On %s, %s publish", formatDate, questionName));
        item.setPubDate(pubDate);
        item.setLink(url);
        return item;
    }

    /**
     * Convert bean to Atom entry.
     * @param createdAt
     * @param questionName
     * @param pubDate
     * @param urlLink
     * @return
     */
    public final static Entry convertBeanToAtomEntry(final Date createdAt,
            final String questionName, final Date pubDate, final String urlLink){
        final Entry entry = new Entry();
        final String formatDate = FeedUtils.formattedDate("yyyy-MM-dd", createdAt);
        entry.setId(questionName);
        entry.setTitle(String.format("On %s, %s publish", formatDate, questionName));
        entry.setUpdated(pubDate);
        final List<Link> links = new ArrayList<Link>();
        links.add(FeedUtils.createLink(urlLink,"Tweet Polls"));
        entry.setAlternateLinks(links);
        return entry;
    }
}

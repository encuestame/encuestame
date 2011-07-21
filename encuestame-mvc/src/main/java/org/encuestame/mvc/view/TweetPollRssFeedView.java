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
package org.encuestame.mvc.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encuestame.core.rss.AbstractBaseRssFeedView;
import org.encuestame.core.util.FeedUtils;
import org.encuestame.utils.web.TweetPollBean;
import org.jfree.util.Log;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;

/**
 * TweetPoll Published RSS Feed View.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 10:42:10 AM
 */
public class TweetPollRssFeedView extends AbstractBaseRssFeedView{

    @Override
    protected Channel newFeed() {
        final Channel channel = new Channel("rss_2.0");
        channel.setPubDate(new Date());
        channel.setDescription("RSS Description");
        channel.setTitle("TITLE");
        channel.setLink("link");
        channel.setCopyright("2010");
        channel.setPubDate(new Date());
        return  channel;
    }

    /**
     * Build Feed Item Body.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TweetPollBean> contentList = (List<TweetPollBean>) model.get("tweetPolls");
        List<Item> entries = new ArrayList<Item>(contentList.size());
        final String urlDomain = model.get("url").toString();
        for (TweetPollBean content : contentList) {
            final Item item = new Item();
            final String createdAt = FeedUtils.formattedDate("yyyy-MM-dd", content.getCreatedDateAt());
            //final String updatedAt = FeedUtils.formattedDate("MM-dd-yyyy HH:mm:ss", content.getUpdateDate());
            //String date = String.format("%1$tY-%1$tm-%1$td", new Date());
            item.setTitle(String.format("On %s, %s publish", createdAt, content.getQuestionBean().getQuestionName()));
            String urlTweet = FeedUtils.createUrlTweetPoll(urlDomain, "/tweetpoll/", content);
            item.setPubDate(content.getUpdateDate());
            item.setLink(urlTweet);
            entries.add(item);
        }
        return entries;
    }
}

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
package org.encuestame.core.util;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.rss.*;
import org.apache.velocity.app.VelocityEngine;
import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

 
/**
 * Feed Utils.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 10:19:37 AM
 * @version $Id:$
 */
public class FeedUtils {

    private final static String CODE_TEMPLATES = "/org/encuestame/mvc/controller/syndication/templates/";

    /**
     * Create Feed Link.
     *
     * @param url
     * @param title
     * @return
     */
    public static final Link createLink(final String url, final String title) {
        Link link = new Link();
        link.setHref(url);
        link.setTitle(title);
        return link;
    }

    /**
     * Create url syndicate.
     *
     * @param url
     * @param code
     * @param id
     * @param slugName
     * @return
     */
    public static final String createUrlFeed(final String url,
            final String code, final Long id, final String slugName) {
        StringBuffer urlString = new StringBuffer(url);
        urlString.append(code);
        urlString.append(id);
        urlString.append("/");
        urlString.append(slugName);
        return urlString.toString();
    }

    /**
     * Format date.
     *
     * @param format
     * @param tweetPollDate
     * @return
     */
    public static final String formattedDate(final String format,
            final Date tweetPollDate) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateToFormat = formatDate.format(tweetPollDate);
        return dateToFormat;
    }

    /**
     * Convert tweetPoll bean to RSS item.
     * @param tpBean
     * @param domain
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final List<Item> convertTweetPollBeanToItemRSS(
            final List<TweetPollBean> tpBean,
            final String domain,
            final VelocityEngine velocityEngine) throws UnsupportedEncodingException {
        List<Item> entries = new ArrayList<Item>(tpBean.size());
        for (TweetPollBean tweetPoll : tpBean) {
            final String urlTweetPoll = FeedUtils.createUrlFeed(domain,
                    "/tweetpoll/", tweetPoll.getId(), tweetPoll.getQuestionBean()
                            .getSlugName());
             final Item item = new Item();
             final Map model = new HashMap();
             model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.base64"));
             model.put("domain", domain);
             model.put("type",TypeSearchResult.getUrlPrefix(TypeSearchResult.TWEETPOLLRESULT));
             model.put("type_id", tweetPoll.getId());
             model.put("question", tweetPoll.getQuestionBean().getQuestionName());
             model.put("anwers", tweetPoll.getAnswerSwitchBeans());
             item.setTitle(tweetPoll.getQuestionBean().getQuestionName());
             item.setPubDate(tweetPoll.getUpdateDate());
             item.setLink(urlTweetPoll);
             item.setAuthor(tweetPoll.getOwnerUsername());
             final List<Category> categories = new ArrayList<Category>();
             for (HashTagBean iterable_element : tweetPoll.getHashTags()) {
                 Category ll = new Category();
                 ll.setValue(iterable_element.getHashTagName());
                 categories.add(ll);
             }
             item.setCategories(categories);
             final Guid d = new Guid();
             d.setPermaLink(true);
             d.setValue(urlTweetPoll);
             item.setGuid(d);
             final Content c = new Content();
             final String text = VelocityEngineUtils.mergeTemplateIntoString(
                     velocityEngine, CODE_TEMPLATES  + "rss_item.vm", "utf-8", model);
             final String string = new String(text.getBytes("UTF-8"));
             c.setValue(string);
             c.setType("text");
             final Description dd = new Description();
             dd.setValue(string);
             dd.setType("html");
             item.setDescription(dd);
             item.setContent(c);
            entries.add(item);
        }
        return entries;
    }

    /**
     * Convert tweetPollBean to entry ATOM.
     * @param tpBean
     * @param domain
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final List<Entry> convertTweetPollBeanToEntryAtom(
            final List<TweetPollBean> tpBean,
            final String domain,
            final VelocityEngine velocityEngine) throws UnsupportedEncodingException {
        List<Entry> entries = new ArrayList<Entry>(tpBean.size());
        for (TweetPollBean tweetPoll : tpBean) {
            //JUAN
            String urlPoll = FeedUtils.createUrlFeed(domain, "/poll/",
                    tweetPoll.getId(), tweetPoll.getQuestionBean().getSlugName());
            final Entry item = new Entry();
            //replace
            final Map model = new HashMap();
            model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.base64"));
            model.put("domain", domain);
            model.put("type",TypeSearchResult.getUrlPrefix(TypeSearchResult.TWEETPOLLRESULT));
            model.put("type_id", tweetPoll.getId());
            model.put("question", tweetPoll.getQuestionBean().getQuestionName());
            model.put("anwers", tweetPoll.getAnswerSwitchBeans());
            item.setTitle(tweetPoll.getQuestionBean().getQuestionName());
            item.setModified(tweetPoll.getUpdateDate());
            item.setCreated(tweetPoll.getUpdateDate());
            final List<Link> links = new ArrayList<Link>();
            links.add(FeedUtils.createLink(urlPoll, tweetPoll.getQuestionBean().getQuestionName()));
            item.setAlternateLinks(links);
            final List<Person> authors = new ArrayList<Person>();
            final Person person = new Person();
            person.setName(tweetPoll.getOwnerUsername());
            //TODO: set url profile link
            authors.add(person);
            //TODO:MIGRATION 
            //item.setAuthors(authors); Check
            final List<com.rometools.rome.feed.atom.Category> categories = new ArrayList<com.rometools.rome.feed.atom.Category>();
            for (HashTagBean iterable_element : tweetPoll.getHashTags()) {
                com.rometools.rome.feed.atom.Category ll = new com.rometools.rome.feed.atom.Category();
                ll.setLabel(iterable_element.getHashTagName());
                categories.add(ll);
            }
            item.setCategories(categories);
            final Guid d = new Guid();
            d.setPermaLink(true);
            d.setValue(urlPoll);
            final Content c = new Content();
            final String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, CODE_TEMPLATES  + "rss_item.vm", "utf-8", model);
            final String string = new String(text.getBytes("UTF-8"));
            c.setValue(string);
            c.setType("text");
            final Description dd = new Description();
            dd.setValue(string);
            dd.setType("html");
            final com.rometools.rome.feed.atom.Content content = new com.rometools.rome.feed.atom.Content();
            content.setSrc(urlPoll);
            content.setType("html");
            content.setValue(text);
            item.setSummary(content);
            entries.add(item);
        }
        return entries;
    }

    public static final List<Entry> convertPollBeanToEntryAtom(
            final List<PollBean> tpBean,
            final String domain,
            final VelocityEngine velocityEngine) throws UnsupportedEncodingException {
        List<Entry> entries = new ArrayList<Entry>(tpBean.size());
        for (PollBean tweetPoll : tpBean) {
            //JUAN
            String urlPoll = FeedUtils.createUrlFeed(domain, "/poll/",
                    tweetPoll.getId(), tweetPoll.getQuestionBean().getSlugName());
            final Entry item = new Entry();
            //replace
            final Map model = new HashMap();
            model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.base64"));
            model.put("domain", domain);
            model.put("type",TypeSearchResult.getUrlPrefix(TypeSearchResult.POLLRESULT));
            model.put("type_id", tweetPoll.getId());
            model.put("question", tweetPoll.getQuestionBean().getQuestionName());
            model.put("anwers", tweetPoll.getResultsBean());
            item.setTitle(tweetPoll.getQuestionBean().getQuestionName());
            item.setModified(tweetPoll.getUpdatedDate());
            item.setCreated(tweetPoll.getUpdatedDate());
            final List<Link> links = new ArrayList<Link>();
            links.add(FeedUtils.createLink(urlPoll, tweetPoll.getQuestionBean().getQuestionName()));
            item.setAlternateLinks(links);
            final List<Person> authors = new ArrayList<Person>();
            final Person person = new Person();
            person.setName(tweetPoll.getOwnerUsername());
            //TODO: set url profile link
            authors.add(person);
            //TODO: MIGRATION
            //item.setAuthors(authors); Check authors List on Entry(Rome) 
            final List<com.rometools.rome.feed.atom.Category> categories = new ArrayList<com.rometools.rome.feed.atom.Category>();
            for (HashTagBean iterable_element : tweetPoll.getHashTags()) {
                com.rometools.rome.feed.atom.Category ll = new com.rometools.rome.feed.atom.Category();
                ll.setLabel(iterable_element.getHashTagName());
                categories.add(ll);
            }
            item.setCategories(categories);
            final Guid d = new Guid();
            d.setPermaLink(true);
            d.setValue(urlPoll);
            final Content c = new Content();
            final String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, CODE_TEMPLATES  + "rss_poll_item.vm", "utf-8", model);
            final String string = new String(text.getBytes("UTF-8"));
            c.setValue(string);
            c.setType("text");
            final Description dd = new Description();
            dd.setValue(string);
            dd.setType("html");
            final com.rometools.rome.feed.atom.Content content = new com.rometools.rome.feed.atom.Content();
            content.setSrc(urlPoll);
            content.setType("html");
            content.setValue(text);
            item.setSummary(content);
            entries.add(item);
        }
        return entries;
    }

    /**
     * Convert pollBean to entry item.
     *
     * @param pollBean
     * @param domain
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final List<Item> convertPollBeanToItemRSS(
            final List<PollBean> pollBean,
            final String domain,
            final VelocityEngine velocityEngine) throws UnsupportedEncodingException {
        List<Item> entries = new ArrayList<Item>(pollBean.size());
        for (PollBean poll : pollBean) {
            String urlPoll = FeedUtils.createUrlFeed(domain, "/poll/",
                    poll.getId(), poll.getQuestionBean().getSlugName());
            final Item item = new Item();
            final Map model = new HashMap();
            model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.base64"));
            model.put("domain", domain);
            model.put("type",TypeSearchResult.getUrlPrefix(TypeSearchResult.POLLRESULT));
            model.put("type_id", poll.getId());
            model.put("question", poll.getQuestionBean().getQuestionName());
            model.put("anwers", poll.getResultsBean());
            item.setTitle(poll.getQuestionBean().getQuestionName());
            item.setPubDate(poll.getUpdatedDate());
            item.setLink(urlPoll);
            item.setAuthor(poll.getOwnerUsername());
            final List<Category> categories = new ArrayList<Category>();
            for (HashTagBean iterable_element : poll.getHashTags()) {
                Category ll = new Category();
                ll.setValue(iterable_element.getHashTagName());
                categories.add(ll);
            }
            item.setCategories(categories);
            final Guid d = new Guid();
            d.setPermaLink(true);
            d.setValue(urlPoll);
            item.setGuid(d);
            final Content c = new Content();
            final String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, CODE_TEMPLATES  + "rss_poll_item.vm", "utf-8", model);
            final String string = new String(text.getBytes("UTF-8"));
            c.setValue(string);
            c.setType("text");
            final Description dd = new Description();
            dd.setValue(string);
            dd.setType("html");
            item.setDescription(dd);
            item.setContent(c);
            entries.add(item);
        }
        return entries;
    }

    public static final List<Entry> convertHomeBeanToItemATOM(
            final List<HomeBean> homeBean,
            final String domain,
            final VelocityEngine velocityEngine) throws UnsupportedEncodingException {
        return null;
    }

    /**
     * Convert {@link HomeBean} to RSS Item.
     * @param homeBean
     * @param domain
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final List<Item> convertHomeBeanToItemRSS(
            final List<HomeBean> homeBean,
            final String domain,
            final VelocityEngine velocityEngine) throws UnsupportedEncodingException {
        List<Item> entries = new ArrayList<Item>(homeBean.size());
        for (HomeBean poll : homeBean) {
            String urlPoll = FeedUtils.createUrlFeed(domain, "/"+poll.getItemType()+"/",
                    poll.getId(), poll.getQuestionBean().getSlugName());
                    final Item item = new Item();
                    final Map model = new HashMap();
                    model.put("mailLogo", EnMePlaceHolderConfigurer.getProperty("application.mail.logo.base64"));
                    model.put("domain", domain);
                    model.put("type", poll.getTypeSearchResult().toString());
                    model.put("type_id", poll.getId());
                    model.put("question", poll.getQuestionBean().getQuestionName());
                    model.put("anwers", poll.getResultsBean());
                    item.setTitle(poll.getQuestionBean().getQuestionName());
                    item.setPubDate(poll.getCreateDateComparable());
                    item.setLink(urlPoll);
                    item.setAuthor(poll.getOwnerUsername());
                    final List<Category> categories = new ArrayList<Category>();
                    for (HashTagBean iterable_element : poll.getHashTags()) {
                        Category ll = new Category();
                        ll.setValue(iterable_element.getHashTagName());
                        categories.add(ll);
                    }
                    item.setCategories(categories);
                    final Guid d = new Guid();
                    d.setPermaLink(true);
                    d.setValue(urlPoll);
                    item.setGuid(d);
                    final Content c = new Content();
                    final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, CODE_TEMPLATES  + "rss_home_item.vm", "utf-8", model);
                    final String string = new String(text.getBytes("UTF-8"));
                    c.setValue(string);
                    c.setType("text");
                    final Description dd = new Description();
                    dd.setValue(string);
                    dd.setType("html");
                    item.setDescription(dd);
                    item.setContent(c);
            entries.add(item);
        }
        return entries;
    }
}

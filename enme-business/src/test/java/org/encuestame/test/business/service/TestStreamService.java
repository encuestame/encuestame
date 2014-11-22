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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.encuestame.business.service.StreamService;
import org.encuestame.core.service.imp.StreamOperations;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.DateClasificatedEnum;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.web.notification.UtilNotification;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;


/**
 * Stream service test case.
 * @author Picado, Juan juanATencuestame.org
 */
@Category(DefaultTest.class)
public class TestStreamService extends AbstractSpringSecurityContext {

    /**
     * {@link StreamService}.
     */
    @Autowired
    private StreamOperations streamService;

    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;
    private Question question;

     /**
     * Run before each test.
     */
    @Before
    public void beforeMock(){

        request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.ENGLISH);
        this.question = createDefaultQuestion("Default Question ");

        final TweetPoll tp = createDefaultTweetPollPublicated(Boolean.TRUE,
                Boolean.TRUE, Boolean.FALSE,
                getSpringSecurityLoggedUserAccount(), question,
                DateUtil.getCalendarDate().getTime());

        createNotification2(tp.getQuestion().getQuestion(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.TWEETPOL_CREATED, false,
                DateUtil.getCalendarDate().getTime());

        DateUtil.getCalendarDate().add(Calendar.HOUR, -3);
        final TweetPoll tp1 = createDefaultTweetPollPublicated(Boolean.TRUE,
                Boolean.TRUE, Boolean.FALSE,
                getSpringSecurityLoggedUserAccount(), question, DateUtil
                        .getCalendarDate().getTime());

        createNotification2(tp1.getQuestion().getQuestion(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.TWEETPOL_CREATED, false,
                DateUtil.getCurrentCalendarDate());

        DateUtil.getCalendarDate().add(Calendar.DATE, -1);
         final Poll poll1 = createDefaultPoll(question, getSpringSecurityLoggedUserAccount());
        createNotification2(poll1.getQuestion().getQuestion(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.POLL_CREATED, false,
                DateUtil.getCurrentCalendarDate());

          DateUtil.getCalendarDate().add(Calendar.DATE, -8);
          final Poll poll2 = createDefaultPoll(question, getSpringSecurityLoggedUserAccount());
        createNotification2(poll2.getQuestion().getQuestion(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.POLL_CREATED, false,
                DateUtil.getCurrentCalendarDate());
    }

    /**
     *
     */
    @Test
    public void testretrieveLastNotifications() throws Exception{
        final List<UtilNotification> list = this.streamService.retrieveLastNotifications(10, this.request);

        Assert.assertEquals(4, list.size());
        // Parameter onlyReaded is false.. so return all notifications(readed or not).
        final List<UtilNotification> list2 = this.streamService.retrieveLastNotifications(10, false, this.request);
        //TODO: review, this method should retrieve 0 items.
        Assert.assertEquals(list2.size(), 4);
        final TweetPoll tp4 = createDefaultTweetPollPublicated(Boolean.TRUE,
                Boolean.TRUE, Boolean.FALSE,
                getSpringSecurityLoggedUserAccount(), question, DateUtil
                        .getCalendarDate().getTime());

        createNotification2(tp4.getQuestion().getQuestion(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.TWEETPOL_CREATED, true, DateUtil
                        .getCalendarDate().getTime());

        final List<UtilNotification> list3 = this.streamService.retrieveLastNotifications(10, true, this.request);
        // if parameter onlyReaded is true, so return all notifications with property readed = false
        Assert.assertEquals(4, list3.size());
    }

    /**
     * test loadNotificationByUserAndLimit method.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testloadNotificationByUserAndLimit() throws EnMeNoResultsFoundException{
        final List<UtilNotification> loadNotificationByUserAndLimit = this.streamService
                .loadNotificationByUserAndLimit(10, 0, false, this.request);
        Assert.assertEquals(4, loadNotificationByUserAndLimit.size());
    }

    /**
     * @throws EnMeNoResultsFoundException
     *
     */
    @Test
    public void testclassifyNotificationList() throws EnMeNoResultsFoundException{
        final List<UtilNotification> list = this.streamService
                .retrieveLastNotifications(10, this.request);
        final HashMap<DateClasificatedEnum, List<UtilNotification>> classify = this.streamService.classifyNotificationList(list, this.request);
        Assert.assertNotNull(classify.get(DateClasificatedEnum.FEW_MONTHS_AGO));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.TODAY));
        Assert.assertEquals(classify.get(DateClasificatedEnum.TODAY).size(), 4);
        Assert.assertNotNull(classify.get(DateClasificatedEnum.LAST_MONTH));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.LAST_YEAR));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.LONG_TIME_AGO));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.THIS_MONTH));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.THIS_WEEK));
    }

    public Notification createNotification2(
            final String message,
            final Account secUser,
            final NotificationEnum description,
            final Boolean readed,
            final Date createdAt){
         final Notification notification = new Notification();
         notification.setAdditionalDescription(message);
         notification.setCreated(createdAt);
         notification.setDescription(description);
         notification.setReaded(readed);
         notification.setAccount(secUser);
         notification.setUrlReference("http://google.es");
         notification.setGroup(true);
         getNotification().saveOrUpdate(notification);
         return notification;
    }
}

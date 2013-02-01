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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.encuestame.business.service.StreamService;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
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
    private StreamService streamService;

    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;

    /**
     * Run before each test.
     */
    @Before
    public void beforeMock(){
        request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.ENGLISH);
        createNotification("not1", getSpringSecurityLoggedUserAccount().getAccount(), NotificationEnum.TWEETPOL_CREATED, false);
    }



    /**
     *
     */
    @Test
    public void testretrieveLastNotifications() throws Exception{
        final List<UtilNotification> list = this.streamService.retrieveLastNotifications(10, this.request);
        Assert.assertEquals(list.size(), 1);
        final List<UtilNotification> list2 = this.streamService.retrieveLastNotifications(10, true, this.request);
        //TODO: review, this method should retrieve 0 items.
        Assert.assertEquals(list2.size(), 1);
        createNotification("not3", getSpringSecurityLoggedUserAccount().getAccount(), NotificationEnum.TWEETPOL_CREATED, true);
        final List<UtilNotification> list3 = this.streamService.retrieveLastNotifications(10, true, this.request);
        Assert.assertEquals(list3.size(), 1);
    }

    /**
     * test loadNotificationByUserAndLimit method.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testloadNotificationByUserAndLimit() throws EnMeNoResultsFoundException{
        final List<UtilNotification> loadNotificationByUserAndLimit = this.streamService
                .loadNotificationByUserAndLimit(10, 0, false, this.request);
        Assert.assertEquals(loadNotificationByUserAndLimit.size(), 1);
    }

    /**
     * @throws EnMeNoResultsFoundException
     *
     */
    @Test
    public void testclassifyNotificationList() throws EnMeNoResultsFoundException{
        final List<UtilNotification> list = this.streamService
                .retrieveLastNotifications(10, this.request);
        final HashMap<DateClasificatedEnum, List<UtilNotification>> classify = this.streamService
                .classifyNotificationList(list);
        Assert.assertNotNull(classify.get(DateClasificatedEnum.FEW_MONTHS_AGO));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.TODAY));
        Assert.assertEquals(classify.get(DateClasificatedEnum.TODAY).size(), 1);
        Assert.assertNotNull(classify.get(DateClasificatedEnum.LAST_MONTH));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.LAST_YEAR));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.LONG_TIME_AGO));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.THIS_MONTH));
        Assert.assertNotNull(classify.get(DateClasificatedEnum.THIS_WEEK));
    }
}

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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.NotificationEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test for {@link NotificationDao}.
 * @author Picado, Juan juanATencuestame.org
 * @since April 10, 2011
 */
@Category(DefaultTest.class)
public class TestNotificationDao extends AbstractBase{


    /** {@link Account} **/
    private Account account;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    private Calendar calDate = Calendar.getInstance();

    /**
     * Before start.
     */
    @Before
    public void initService(){
        this.account = createAccount();
        this.userAccount = createUserAccount("user 1", this.account);
                for (int i = 0; i < 10; i++) {
            createNotification(RandomStringUtils.randomAscii(100), account, NotificationEnum.TWEETPOL_CREATED, false, calDate.getTime());
        }
  		calDate.add(Calendar.HOUR, 3);
  		for (int i = 0; i < 10; i++) {
            createNotification(RandomStringUtils.randomAscii(100), account, NotificationEnum.TWEETPOL_CREATED, true, calDate.getTime());
        }
    }


    /**
     * test loadNotificationByUserAndLimit.
     */
    @Test
    public void testloadNotification(){
        final List<Notification> list = getNotification().loadNotificationByUserAndLimit(this.account, 5, 0, true);
        final Notification not1 = list.get(0);
        assertEquals("Should be equals", 5, list.size());
        final List<Notification> list2 = getNotification().loadNotificationByUserAndLimit(this.account, 20, 0, true);
        assertEquals("Should be equals", 10, list2.size());
        final Long list3 = getNotification().retrieveTotalNotificationStatus(this.account);
        assertEquals("Should be equals", 20L, list3.longValue());
        final Long list4 = getNotification().retrieveTotalNotReadedNotificationStatus(this.account);
        assertEquals("Should be equals", 10L, list4.longValue());
        final Notification expected = getNotification().retrieveNotificationById(not1.getNotificationId());
        Assert.assertNotNull(expected);
    }
}

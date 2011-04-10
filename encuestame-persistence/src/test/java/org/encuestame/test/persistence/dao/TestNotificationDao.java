package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link NotificationDao}.
 * @author Picado, Juan juan@encuestame.org
 * @since April 10, 2011
 */
public class TestNotificationDao extends AbstractBase{


    /** {@link Account} **/
    private Account account;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /**
     * Before start.
     */
    @Before
    public void initService(){
        this.account = createAccount();
        this.userAccount = createUserAccount("user 1", this.account);
        for (int i = 0; i < 10; i++) {
            createNotification(RandomStringUtils.randomAscii(100), account, NotificationEnum.TWEETPOL_CREATED, false);
        }
        for (int i = 0; i < 10; i++) {
            createNotification(RandomStringUtils.randomAscii(100), account, NotificationEnum.TWEETPOL_CREATED, true);
        }
    }


    /**
     * test loadNotificationByUserAndLimit.
     */
    @Test
    public void testloadNotification(){
        final List<Notification> list = getNotification().loadNotificationByUserAndLimit(this.account, 5);
        final Notification not1 = list.get(0);
        assertEquals("Should be equals", 5, list.size());
        final List<Notification> list2 = getNotification().loadNotificationByUserAndLimit(this.account, 20);
        assertEquals("Should be equals", 10, list2.size());
        final Long list3 = getNotification().retrieveTotalNotificationStatus(this.account);
        assertEquals("Should be equals", 20L, list3.longValue());
        final Long list4 = getNotification().retrieveTotalNotReadedNotificationStatus(this.account);
        assertEquals("Should be equals", 10L, list4.longValue());
        final Notification expected = getNotification().retrieveNotificationById(not1.getNotificationId());
        Assert.assertNotNull(expected);
    }
}

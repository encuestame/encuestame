
package org.encuestame.comet.services;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Notification comet service.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 4, 2011
 */
@Named
@Singleton
@Service("notificationService")
public class NotificationCometService extends AbstractCometService {

    /** {@link NotificationDao}. **/
    @Autowired
    private INotification notificationDao;

    /**
     * Notificiaton process.
     * @param remote
     * @param message
     */
    @Listener("/service/notification")
    public void processNotification(final ServerSession remote, final ServerMessage.Mutable message) {
        final Map<String, Object> input = message.getDataAsMap();
        log.debug("Notification Input "+input);
        String name = (String) input.get("name");
        final Map<String, Object> output = new HashMap<String, Object>();
        final UserAccount userAccount = getByUsername(getUserPrincipalUsername());
        if (userAccount != null) {
            final Long totalNot = getNotificationDao().retrieveTotalNotificationStatus(userAccount.getAccount());
            log.debug("totalNot "+totalNot);
            final Long totalNewNot = getNotificationDao().retrieveTotalNotReadedNotificationStatus(userAccount.getAccount());
            log.debug("totalNewNot "+totalNewNot);
            output.put("totalNot", totalNot);
            output.put("totalNot", totalNewNot);
        } else {
            log.error("Error username ");
        }
        output.put("greeting", "Hello, " + name);
        remote.deliver(serverSession, "/not", output, null);
    }

    /**
     * @return the notificationDao
     */
    public INotification getNotificationDao() {
        return notificationDao;
    }

    /**
     * @param notificationDao the notificationDao to set
     */
    public void setNotificationDao(INotification notificationDao) {
        this.notificationDao = notificationDao;
    }
}

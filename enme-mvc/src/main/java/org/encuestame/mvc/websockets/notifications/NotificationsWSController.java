/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.encuestame.mvc.websockets.notifications;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.imp.NotificationDao;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.json.NotificationResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


@Controller
public class NotificationsWSController extends AbstractBaseOperations {

	/*
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /** {@link NotificationDao}. **/
    @Autowired
    private INotification notificationDao;


    @MessageMapping("/notifications-ws")
	public NotificationResume getPositions() throws Exception {
		log.debug("/notifications-ws");	
		final NotificationResume notificationResume = new NotificationResume();
		try {
            final String username = getUserPrincipalUsername();
            
            log.debug("notifications-ws User get by getUserPrincipalUsername ---> " + getUserPrincipalUsername());
            UserAccount userAccount;
            if (!username.isEmpty()) {
                userAccount = getByUsername(username);
                if (userAccount != null) {
                    final Long totalNot = getNotificationDao().retrieveTotalNotificationStatus(userAccount.getAccount());
                    log.debug("totalNot "+totalNot);
                    final Long totalNewNot = getNotificationDao().retrieveTotalNotReadedNotificationStatus(userAccount.getAccount());
                    log.debug("totalNewNot "+totalNewNot);
                    notificationResume.setTotalNot(totalNot);
                    notificationResume.setTotalNewNot(totalNewNot);
                    log.debug(totalNewNot + " NEW of "+totalNot+" total not");
                } else {
                	notificationResume.setTotalNot(0L);
                    notificationResume.setTotalNewNot(0L);
                }
            } else {
                throw new EnMeExpcetion("Auth object is not valid");
            }
        } catch (EnMeExpcetion e) {
        	notificationResume.setTotalNot(0L);
            notificationResume.setTotalNewNot(0L);
             log.fatal("cometd: username invalid");
        }
		return notificationResume;
	}

	@MessageMapping("/push-notification-ws")
	public void executeTrade(Principal principal) {
		//		trade.setUsername(principal.getName());
		//		log.debug("Trade: " + trade);
		//this.tradeService.executeTrade(trade);
	}

	@MessageExceptionHandler
	@SendToUser("/notifications-ws/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}
	
    /**
     * @return the notificationDao
     */
    public INotification getNotificationDao() {
        return notificationDao;
    }

    /**
     * @param notificationDao
     *            the notificationDao to set
     */
    public void setNotificationDao(final INotification notificationDao) {
        this.notificationDao = notificationDao;
    }

}

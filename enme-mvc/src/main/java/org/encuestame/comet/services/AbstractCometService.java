///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//package org.encuestame.comet.services;
//
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//import javax.inject.Inject;
//
//import org.apache.log4j.Logger;
//import org.cometd.annotation.Session;
//import org.cometd.bayeux.server.BayeuxServer;
//import org.cometd.bayeux.server.ServerMessage;
//import org.cometd.bayeux.server.ServerSession;
//import org.encuestame.mvc.controller.AbstractBaseOperations;
//import org.encuestame.persistence.dao.INotification;
//import org.encuestame.persistence.dao.imp.NotificationDao;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * Base Case to all comet service.
// *
// * @author Picado, Juan juanATencuestame.org
// * @since Mar 4, 2011
// */
//public abstract class AbstractCometService extends AbstractBaseOperations {
//
//    /**
//     * Log.
//     */
//    private Logger log = Logger.getLogger(this.getClass());
//
//    /** {@link NotificationDao}. **/
//    @Autowired
//    private INotification notificationDao;
//
//    @Inject
//    private BayeuxServer bayeux;
//
//    @Session
//    private ServerSession serverSession;
//
//    @PostConstruct
//    public void init() {
//    }
//
//    /**
//     * Get comet parameter.
//     * @param message {@link ServerMessage.Mutable}
//     * @param parameter parameter name.
//     * @return value of parameter.
//     */
//    public String getParameter(final ServerMessage.Mutable message, final String parameter){
//        final Map<String, Object> input = message.getDataAsMap();
//        log.debug("Messages content:{"+input.toString());
//        final String name = (String) input.get(parameter);
//        return name;
//    }
//
//    /**
//     * @return the bayeux
//     */
//    public BayeuxServer getBayeux() {
//        return bayeux;
//    }
//
//    /**
//     * @param bayeux
//     *            the bayeux to set
//     */
//    public void setBayeux(BayeuxServer bayeux) {
//        this.bayeux = bayeux;
//    }
//
//    /**
//     * @return the serverSession
//     */
//    public ServerSession getServerSession() {
//        return serverSession;
//    }
//
//    /**
//     * @param serverSession
//     *            the serverSession to set
//     */
//    public void setServerSession(final ServerSession serverSession) {
//        this.serverSession = serverSession;
//    }
//
//    /**
//     * @return the notificationDao
//     */
//    public INotification getNotificationDao() {
//        return notificationDao;
//    }
//
//    /**
//     * @param notificationDao
//     *            the notificationDao to set
//     */
//    public void setNotificationDao(final INotification notificationDao) {
//        this.notificationDao = notificationDao;
//    }
//}

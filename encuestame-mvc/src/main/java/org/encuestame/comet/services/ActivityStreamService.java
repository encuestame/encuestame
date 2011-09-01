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
package org.encuestame.comet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.encuestame.core.util.JSONUtils;
import org.encuestame.utils.web.notification.UtilNotification;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since 12/08/2011
 */
@Named // Tells Spring that this is a bean
@Singleton // Tells Spring that this is a singleton
@Service("streamService")
public class ActivityStreamService extends AbstractCometService {

    /*
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     */
    @Listener("/service/stream/get")
    public void processStream(final ServerSession remote, final ServerMessage.Mutable message) {
        //log.debug("Notification Input "+input);
        final Map<String, Object> output = new HashMap<String, Object>();
        try {
            log.debug("ActivityStreamService............");		            //TODO: null pointer with request null;
            final List<UtilNotification> activities = getStreamOperations().retrieveLastNotifications(20, false, null);
            log.debug("not stream SIZE...."+activities.size());
            output.put("stream", JSONUtils.convertObjectToJsonString(activities));
            //TODO: temp, awaiting cometd 1.4.0 with json jackson plug in.
        } catch (Exception e) {
             log.fatal("cometd error: "+e.getMessage());
             output.put("stream", ListUtils.EMPTY_LIST);
        }
        remote.deliver(getServerSession(), message.getChannel(), output, null);
    }

}

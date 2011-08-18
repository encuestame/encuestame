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
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.CommentBean;

/**
 * Comments service.
 * @author Picado, Juan juanATencuestame.org
 * @since 12/08/2011
 */
@Named // Tells Spring that this is a bean
@Singleton // Tells Spring that this is a singleton
@Service("commentCometService")
public class CommentsStreamService extends AbstractCometService {

    /*
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     */
    @Listener("/service/comment/get")
    public void processStream(final ServerSession remote, final ServerMessage.Mutable message) {
        //log.debug("Notification Input "+input);
        final Map<String, Object> output = new HashMap<String, Object>();
        try {
            log.debug("CommentsStreamService............");
            List<CommentBean> comments = getCommentService().getCommentsbyUser(20, 0);
            log.debug("CommentsStreamService.comments size .."+comments.size());
            output.put("comments", JSONUtils.convertObjectToJsonString(comments));
        } catch (Exception e) {
             e.printStackTrace();
             output.put("comments", ListUtils.EMPTY_LIST);
             log.fatal("cometd: username invalid "+e);
        }
        remote.deliver(getServerSession(), message.getChannel(), output, null);
    }

}

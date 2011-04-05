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

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.TweetPollBean;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Tweetpoll comet service.
 * @author Picado, Juan juanATencuestame.org
 * @since Ap 4, 2011
 */
@Named
@Singleton
@Service("notificationService")
public class TweetPollCometService extends AbstractCometService {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

   /**
     *
     * @param remote
     * @param message
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @Listener("/service/tweetpoll/autosave")
    public void processAutoSave(final ServerSession remote, final ServerMessage.Mutable message) {
        final Map<String, Object> inputMessage = message.getDataAsMap();
        Map<String, Object> outPutMessage = new HashedMap();
        log.debug("Messages content:{"+inputMessage.toString());
        log.debug("Messages content JSON:{"+message.getJSON());
        final Map<String, Object> tweetPollJson = (Map<String, Object>) inputMessage.get("tweetPoll");
        log.debug("tweetPoll content:{"+tweetPollJson.toString());
        log.debug("tweetPoll content:{"+tweetPollJson.get("question"));
        log.debug("tweetPoll anwers:{"+tweetPollJson.get("anwers"));
        log.debug("tweetPoll hashtags:{"+tweetPollJson.get("hashtags"));
        String[] a = {"hashtaga1", "hashtag2"};
        String[] b = {"asnwer1", "answers2"};
        final String[] hashtags =  (String[]) tweetPollJson.get("hashtags");
        final String[] answers =  (String[]) tweetPollJson.get("anwers");
        log.debug("tweetPoll map hashtags:{"+hashtags);
        log.debug("tweetPoll map answers:{"+answers);
        final String question = filterValue(tweetPollJson.get("question").toString());
        try {
            final UserAccount user = getUserAccount();
            final Long tweetPollId =  tweetPollJson.get("tweetPollId") == null
                  ? null : Long.valueOf(tweetPollJson.get("tweetPollId").toString());
            if(tweetPollId == null){
                TweetPoll tweetPoll;

                    tweetPoll = createTweetPoll(question, a, b, user);
                outPutMessage.put("tweetPollId", tweetPoll.getTweetPollId());
                log.debug("tweet poll created.");
            } else {
                log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
                TweetPoll tweetPoll = getTweetPollService().getTweetPollById(tweetPollId, user);
                //update tweetPoll
                tweetPoll = updateTweetPoll(tweetPoll, question, a, b, user);
                outPutMessage = inputMessage;
                log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
            }
        } catch (EnMeExpcetion e) {
            log.error(e);
            e.printStackTrace();
        }
        log.debug("tweetPoll content:{"+outPutMessage);
        remote.deliver(getServerSession(), message.getChannel(), outPutMessage, null);
    }

    /**
     *
     * @param remote
     * @param message
     */
    @Listener("/service/tweetpoll/publish")
    public void processPublish(final ServerSession remote, final ServerMessage.Mutable message) {
        final Map<String, Object> input = message.getDataAsMap();
    }
}
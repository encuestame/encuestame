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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
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
    @SuppressWarnings("unchecked")
    public void processAutoSave(final ServerSession remote, final ServerMessage.Mutable message) {
        log.debug("--------- TweetPoll COMMET AUTOSAVE ----------");
        final Map<String, Object> inputMessage = message.getDataAsMap();
        Map<String, Object> outPutMessage = new HashedMap();
        if (log.isDebugEnabled()) {
            log.debug("Messages content:{"+inputMessage.toString());
            log.debug("Messages content JSON:{"+message.getJSON());
            log.debug("Messages content TweetPoll:{"+inputMessage.get("tweetPoll"));
        }
        final Map<String, Object> tweetPollJson = (Map<String, Object>) inputMessage.get("tweetPoll");
        List<String> hastagsArray = new ArrayList<String>();
        List<Long> answerArray = new ArrayList<Long>();
        final Object[] hashtags =  (Object[]) tweetPollJson.get("hashtags");
        if (log.isDebugEnabled()) {
            log.debug("Array of hashtags: ---->"+tweetPollJson.get("hashtags"));
            log.debug("Array of hashtags: ---->"+hashtags);
            log.debug("Array of hashtags: ---->"+hashtags.length);
        }
        //{"hashtags":[{"id":null,"newValue":true,"label":"nicaragua"}
        for (int i = 0; i < hashtags.length; i++) {
            HashMap<String, String> hashtagsMap = (HashMap<String, String>) hashtags[i];
            if (log.isDebugEnabled()) {
                log.debug("Hashtag: ---->"+hashtagsMap.get("label"));
                log.debug(hashtagsMap.get("newValue"));
            }
            if (hashtagsMap.get("label") != null) {
                hastagsArray.add(hashtagsMap.get("label"));
            }
        }
        final Object[] answers =  (Object[]) tweetPollJson.get("answers");
        if (log.isDebugEnabled()) {
            log.debug("Array of Answer: ---->"+tweetPollJson.get("answers"));
            log.debug("Array of Answer: ---->"+answers.length);
        }
        for (int i = 0; i < answers.length; i++) {
            Long answersMap = (Long) answers[i];
            //log.debug("Answer: ---->"+answersMap.get("value"));
            if (answersMap != null) {
                answerArray.add(Long.valueOf(answersMap));
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("review answerArray: "+answerArray.size());
            log.debug("review hastagsArray: "+hastagsArray.size());
        }

        final HashMap<String, String> questionMap = (HashMap<String, String>) tweetPollJson.get("question");
        final String question = filterValue(questionMap.get("value") == null ? "" : questionMap.get("value"));
        try {
            //get user account from session.
            final UserAccount user = getUserAccount();
            if (user != null) {
                final Long tweetPollId =  tweetPollJson.get("tweetPollId") == null
                      ? null : Long.valueOf(tweetPollJson.get("tweetPollId").toString());
                if (tweetPollId == null) {
                    final TweetPoll tweetPoll = createTweetPoll(question, hastagsArray.toArray(new String[]{}), user);
                    outPutMessage.put("tweetPollId", tweetPoll.getTweetPollId());
                    //retrieve answers stored.
                    log.debug("tweet poll created.");
                } else {
                    log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
                    //update tweetPoll
                    final TweetPoll tweetPoll = updateTweetPoll(tweetPollId, question, hastagsArray.toArray(new String[]{}),
                            answerArray.toArray(new Long[]{}));
                    outPutMessage = inputMessage;
                    log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
                }
            } else {
                log.warn("forbiden access");
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
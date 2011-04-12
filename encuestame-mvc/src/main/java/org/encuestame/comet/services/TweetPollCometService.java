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
        final Map<String, Object> inputMessage = message.getDataAsMap();
        Map<String, Object> outPutMessage = new HashedMap();
        log.debug("Messages content:{"+inputMessage.toString());
        log.debug("Messages content JSON:{"+message.getJSON());
        final Map<String, Object> tweetPollJson = (Map<String, Object>) inputMessage.get("tweetPoll");
        String[] hastagsArray = new String[15];
        String[] answerArray = new String[5];
        final Object[] hashtags =  (Object[]) tweetPollJson.get("hashtags");
        for (int i = 0; i < hashtags.length; i++) {
            HashMap<String, String> hashtagsMap = (HashMap<String, String>) hashtags[i];
            log.debug("Hashtags: ---->"+hashtagsMap.get("value"));
            //if (hashtagsMap.get("value") != null) {
                hastagsArray[i] = hashtagsMap.get("value");
            //}
        }
        final Object[] answers =  (Object[]) tweetPollJson.get("anwers");
        for (int i = 0; i < answers.length; i++) {
            HashMap<String, String> answersMap = (HashMap<String, String>) answers[i];
            log.debug("Answer: ---->"+answersMap.get("value"));
            //if (answersMap.get("value") != null) {
                answerArray[i] = answersMap.get("value");
            //}
        }
        log.debug("review answerArray: "+answerArray);
        log.debug("review hastagsArray: "+hastagsArray);
        final HashMap<String, String> questionMap = (HashMap<String, String>) tweetPollJson.get("question");
        final String question = filterValue(questionMap.get("value") == null ? "" : questionMap.get("value"));
        try {
            final UserAccount user = getUserAccount();
            if (user != null) {
                final Long tweetPollId =  tweetPollJson.get("tweetPollId") == null
                      ? null : Long.valueOf(tweetPollJson.get("tweetPollId").toString());
                if (tweetPollId == null) {
                    final TweetPoll tweetPoll = createTweetPoll(question, hastagsArray, answerArray, user);
                    outPutMessage.put("tweetPollId", tweetPoll.getTweetPollId());
                    //retrieve answers stored.
                    log.debug("tweet poll created.");
                } else {
                    log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
                    //update tweetPoll
                    final TweetPoll tweetPoll = updateTweetPoll(tweetPollId, question, hastagsArray, answerArray);
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
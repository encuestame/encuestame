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
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Named;
//import javax.inject.Singleton;
//
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.log4j.Logger;
//import org.cometd.annotation.Listener;
//import org.cometd.annotation.Service;
//import org.cometd.bayeux.server.ServerMessage;
//import org.cometd.bayeux.server.ServerSession;
//import org.encuestame.persistence.domain.security.UserAccount;
//import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
//import org.encuestame.persistence.exception.EnMeExpcetion;
//import org.encuestame.utils.DateUtil;
//import org.encuestame.utils.json.QuestionBean;
//import org.encuestame.utils.json.TweetPollBean;
//import org.springframework.security.access.prepost.PreAuthorize;
//
///**
// * Tweetpoll comet service.
// * @author Picado, Juan juanATencuestame.org
// * @since Ap 4, 2011
// */
//@Named
//@Singleton
////@Service("tweetPollCometService")
//@Deprecated
//public class TweetPollCometService extends AbstractCometService {
//
//    /**
//     * Log.
//     */
//    private Logger log = Logger.getLogger(this.getClass());
//
//   /**
//     * Autosave process.
//     * @param remote
//     * @param message
//     */
//    @PreAuthorize("hasRole('ENCUESTAME_USER')")
//    @Listener("/service/tweetpoll/autosave")
//    @SuppressWarnings("unchecked")
//    @Deprecated
//    public void processAutoSave(final ServerSession remote, final ServerMessage.Mutable message) {;
//        log.debug("--------- TweetPoll COMMET AUTOSAVE ----------");
//        final Map<String, Object> inputMessage = message.getDataAsMap();
//        Map<String, Object> outPutMessage = new HashedMap();
//        if (log.isDebugEnabled()) {
//            log.debug("Messages content:{"+inputMessage.toString());
//            log.debug("Messages content JSON:{"+message.getJSON());
//            log.debug("Messages content TweetPoll:{"+inputMessage.get("tweetPoll"));
//        }
//        final Map<String, Object> tweetPollJson = (Map<String, Object>) inputMessage.get("tweetPoll");
//        List<String> hastagsArray = new ArrayList<String>();
//        List<Long> answerArray = new ArrayList<Long>();
//        final Object[] hashtags =  (Object[]) tweetPollJson.get("hashtags");
//        if (log.isDebugEnabled()) {
//            log.debug("Array of hashtags: ---->"+tweetPollJson.get("hashtags"));
//            log.debug("Array of hashtags: ---->"+hashtags);
//            log.debug("Array of hashtags: ---->"+hashtags.length);
//        }
//        //{"hashtags":[{"id":null,"newValue":true,"label":"nicaragua"}
//        for (int i = 0; i < hashtags.length; i++) {
//            HashMap<String, String> hashtagsMap = (HashMap<String, String>) hashtags[i];
//            if (log.isDebugEnabled()) {
//                log.debug("Hashtag: ---->"+hashtagsMap.get("label"));
//                log.debug(hashtagsMap.get("newValue"));
//            }
//            if (hashtagsMap.get("label") != null) {
//                hastagsArray.add(hashtagsMap.get("label"));
//            }
//        }
//        final Object[] answers =  (Object[]) tweetPollJson.get("answers");
//        if (log.isDebugEnabled()) {
//            log.debug("Array of Answer: ---->"+tweetPollJson.get("answers"));
//            log.debug("Array of Answer: ---->"+answers.length);
//        }
//        for (int i = 0; i < answers.length; i++) {
//            Long answersMap = (Long) answers[i];
//            //log.debug("Answer: ---->"+answersMap.get("value"));
//            if (answersMap != null) {
//                answerArray.add(Long.valueOf(answersMap));
//            }
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("review answerArray: "+answerArray.size());
//            log.debug("review hastagsArray: "+hastagsArray.size());
//        }
//        final HashMap<String, String> questionMap = (HashMap<String, String>) tweetPollJson.get("question");
//        final String question = filterValue(questionMap.get("value") == null ? "" : questionMap.get("value"));
//
//        //Options
//        final Options options = new Options((tweetPollJson.get("options") == null
//                ? new HashedMap() : (Map<String, Object>)tweetPollJson.get("options")));
//        if (log.isDebugEnabled()) {
//            log.debug("review options: "+options.toString());
//        }
//
//        try {
//            //get user account from session.
//            final UserAccount user = getUserAccount();
//            if (user != null) {
//                final Long tweetPollId =  tweetPollJson.get("tweetPollId") == null
//                      ? null : Long.valueOf(tweetPollJson.get("tweetPollId").toString());
//                if (tweetPollId == null) {
//                    final TweetPollBean tweetPollBean = this.fillTweetPoll(options, question, user, hastagsArray, null);
//                    //new tweetpoll domain.
//                    final TweetPoll tweetPoll = createTweetPoll(tweetPollBean);
//                    outPutMessage.put("tweetPollId", tweetPoll.getTweetPollId());
//                    //retrieve answers stored.
//                    log.debug("tweet poll created.");
//                } else {
//                    log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
//                    //update tweetPoll
//                    final TweetPollBean tweetPollBean = this.fillTweetPoll(options, question, user, hastagsArray, tweetPollId);
//                    //final TweetPoll tweetPoll = updateTweetPoll(tweetPollId, question, hastagsArray.toArray(new String[]{}),
//                     //       answerArray.toArray(new Long[]{}));
//                    updateTweetPoll(tweetPollBean);
//                    outPutMessage = inputMessage;
//                    log.debug("updated tweetPoll:{"+tweetPollJson.get("tweetPollId"));
//                }
//            } else {
//                log.warn("forbiden access");
//            }
//        } catch (EnMeExpcetion e) {
//            log.error(e);
//        } catch (ParseException e) {
//             log.error(e);
//        }
//        log.debug("tweetPoll content:{"+outPutMessage);
//        remote.deliver(getServerSession(), message.getChannel(), outPutMessage, null);
//    }
//
//    /**
//     *
//     * @param options
//     * @param question
//     * @param user
//     * @param hastagsArray
//     * @return
//     * @throws ParseException
//     */
//    private TweetPollBean fillTweetPoll(
//            final Options options,
//            final String question,
//            final UserAccount user,
//            final  List<String> hastagsArray,
//            final Long tweetPollId) throws ParseException{
//        final TweetPollBean tweetPollBean = new TweetPollBean();
//        if (tweetPollId != null) {
//            tweetPollBean.setId(tweetPollId);
//        }
//        tweetPollBean.getHashTags().addAll(fillListOfHashTagsBean(hastagsArray.toArray(new String[]{})));
//        // save create tweet poll
//        tweetPollBean.setUserId(user.getAccount().getUid());
//        //defined values.
//        tweetPollBean.setCloseNotification(Boolean.TRUE); //TOOD: ????
//        tweetPollBean.setResultNotification(Boolean.TRUE);
//        //resume live results.
//        tweetPollBean.setResumeLiveResults(options.getResumeLiveResults());
//        //follow on dashboard.
//        tweetPollBean.setResumeTweetPollDashBoard(options.getFollowDashBoard());
//        //captcha.
//        tweetPollBean.setCaptcha(options.getCaptcha());
//        //live results
//        tweetPollBean.setAllowLiveResults(options.getLiveResults());
//        //repeated votes
//        tweetPollBean.setAllowRepeatedVotes(options.getRepeatedVotes());
//        if (options.getRepeatedVotes()) {
//            tweetPollBean.setMaxRepeatedVotes(options.getMaxRepeatedVotes());
//        }
//        //scheduled
//        tweetPollBean.setSchedule(options.getScheduled());
//        if (options.getScheduled()) {
//            //eg. format 5/25/11 10:45:00
//            final StringBuilder builder = new StringBuilder(options.getScheduledDate());
//            builder.append(" ");
//            builder.append(options.getScheduledTime());
//            tweetPollBean.setScheduleDate(DateUtil.parseDate(builder.toString(), DateUtil.COMPLETE_FORMAT_TIME));
//        }
//        //limit votes
//        tweetPollBean.setLimitVotesEnabled(options.getLimitVotes());
//        if (options.getLimitVotes()) {
//            tweetPollBean.setLimitVotes(options.getMaxLimitVotes());
//        }
//        //question
//        tweetPollBean.setQuestionBean(new QuestionBean(question));
//        log.debug("fillTweetPoll: "+tweetPollBean);
//        return tweetPollBean;
//    }
//
//    /**
//     *
//     * @param remote
//     * @param message
//     */
//    @Listener("/service/tweetpoll/publish")
//    public void processPublish(final ServerSession remote, final ServerMessage.Mutable message) {
//        final Map<String, Object> input = message.getDataAsMap();
//    }
//
//    /**
//     * Options.
//     * options={repeatedVotes=false, scheduledDate=null,
//       resumeLiveResults=true, maxRepeatedVotes=5, scheduled=false, limitVotes=true,
//       followDashBoard=true, captcha=true, scheduledTime=null, liveResults=false, maxLimitVotes=24}}}
//     */
//    public class Options {
//        private Boolean repeatedVotes;
//        private Boolean resumeLiveResults;
//        private Boolean scheduled;
//        private Boolean limitVotes;
//        private Boolean followDashBoard;
//        private Boolean captcha;
//        private Boolean liveResults;
//
//        private Integer maxRepeatedVotes;
//        private Integer maxLimitVotes;
//        private String scheduledDate;
//        private String scheduledTime;
//
//        public Options(final Map<String, Object> options) {
//            this.repeatedVotes = options.get("repeatedVotes") == null ? false
//                    : new Boolean(options.get("repeatedVotes").toString());
//            this.resumeLiveResults = options.get("resumeLiveResults") == null ? false
//                    : new Boolean(options.get("resumeLiveResults").toString());
//            this.scheduled = options.get("scheduled") == null ? false
//                    : new Boolean(options.get("scheduled").toString());
//            this.followDashBoard = options.get("followDashBoard") == null ? false
//                    : new Boolean(options.get("followDashBoard").toString());
//            this.limitVotes = options.get("limitVotes") == null ? false
//                    : new Boolean(options.get("limitVotes").toString());
//            this.captcha = options.get("captcha") == null ? false
//                    : new Boolean(options.get("captcha").toString());
//            this.liveResults = options.get("liveResults") == null ? false
//                    : new Boolean(options.get("liveResults").toString());
//            this.maxRepeatedVotes = options.get("maxRepeatedVotes") == null ? 0
//                    : new Integer(options.get("maxRepeatedVotes").toString());
//            this.maxLimitVotes = options.get("maxLimitVotes") == null ? 1
//                    : new Integer(options.get("maxLimitVotes").toString());
//            this.scheduledDate = options.get("scheduledDate") == null ? null
//                    : options.get("scheduledDate").toString();
//            this.scheduledTime = options.get("scheduledTime") == null ? null
//                    :options.get("scheduledTime").toString();
//        }
//
//        /**
//         * @return the repeatedVotes
//         */
//        public Boolean getRepeatedVotes() {
//            return repeatedVotes;
//        }
//
//        /**
//         * @return the resumeLiveResults
//         */
//        public Boolean getResumeLiveResults() {
//            return resumeLiveResults;
//        }
//
//        /**
//         * @return the scheduled
//         */
//        public Boolean getScheduled() {
//            return scheduled;
//        }
//
//        /**
//         * @return the limitVotes
//         */
//        public Boolean getLimitVotes() {
//            return limitVotes;
//        }
//
//        /**
//         * @return the followDashBoard
//         */
//        public Boolean getFollowDashBoard() {
//            return followDashBoard;
//        }
//
//        /**
//         * @return the captcha
//         */
//        public Boolean getCaptcha() {
//            return captcha;
//        }
//
//        /**
//         * @return the liveResults
//         */
//        public Boolean getLiveResults() {
//            return liveResults;
//        }
//
//        /**
//         * @return the maxRepeatedVotes
//         */
//        public Integer getMaxRepeatedVotes() {
//            return maxRepeatedVotes;
//        }
//
//        /**
//         * @return the maxLimitVotes
//         */
//        public Integer getMaxLimitVotes() {
//            return maxLimitVotes;
//        }
//
//        /**
//         * @return the scheduledDate
//         */
//        public String getScheduledDate() {
//            return scheduledDate;
//        }
//
//        /**
//         * @return the scheduledTime
//         */
//        public String getScheduledTime() {
//            return scheduledTime;
//        }
//
//        /* (non-Javadoc)
//         * @see java.lang.Object#toString()
//         */
//        @Override
//        public String toString() {
//            return "Options [repeatedVotes=" + repeatedVotes
//                    + ", resumeLiveResults=" + resumeLiveResults
//                    + ", scheduled=" + scheduled + ", limitVotes=" + limitVotes
//                    + ", followDashBoard=" + followDashBoard + ", captcha="
//                    + captcha + ", liveResults=" + liveResults
//                    + ", maxRepeatedVotes=" + maxRepeatedVotes
//                    + ", maxLimitVotes=" + maxLimitVotes + ", scheduledDate="
//                    + scheduledDate + ", scheduledTime=" + scheduledTime + "]";
//        }
//    }
//}
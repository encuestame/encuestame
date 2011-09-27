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
package org.encuestame.core.cron;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.dao.IFrontEndDao;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.dao.ISurvey;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Calculate relevance.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */
public class CalculateRelevance {

     /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private ITweetPoll tweetPollDao;

    @Autowired
    private IPoll pollDao;

    @Autowired
    private ISurvey surveyDao;
    /**
     * Calculate relevance.
     */
    public void calculate() {
        log.info("************ Start calculate relevance item **************");
        final Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DATE, -5);
        final Calendar datebefore = Calendar.getInstance();
        datebefore.add(Calendar.DATE, -5);
        final Calendar todayDate = Calendar.getInstance();

        final List<TweetPoll> tweetPolls = getTweetPollDao().getTweetPolls(50,
                0, dateFrom.getTime());
        log.info("Total tweetpolls -------------" + tweetPolls.size());

        final List<Poll> polls = getPollDao().getPolls(50,
                0, dateFrom.getTime());
        this.processItems(tweetPolls, polls, null, datebefore, todayDate);
    }

    /**
     * Process items
     * @param tweetPollList
     * @param pollList
     * @param surveyList
     * @param datebefore
     * @param todayDate
     */
    private void processItems(final List<TweetPoll> tweetPollList, final List<Poll> pollList, final List<Survey> surveyList,
            final Calendar datebefore, final Calendar todayDate) {
        long likeVote;
        long dislikeVote;
        long hits;
        long maxVotebyUser;
        long relevance;
        for (TweetPoll tweetPoll : tweetPollList) {
            likeVote = tweetPoll.getLikeVote() == null ? 0 : tweetPoll
                    .getLikeVote();
            dislikeVote = tweetPoll.getDislikeVote() == null ? 0
                    : tweetPoll.getDislikeVote();
            hits = tweetPoll.getHits() == null ? 0 : tweetPoll.getHits();
            maxVotebyUser = getTweetPollDao()
                    .getMaxTweetPollLikeVotesbyUser(
                            tweetPoll.getEditorOwner().getUid(),
                            datebefore.getTime(), todayDate.getTime());
            relevance = EnMeUtils.calculateRelevance(likeVote,
                    dislikeVote, maxVotebyUser, hits);
            log.info("*******************************");
            log.info("******* Resume of Process *****");
            log.info("-------------------------------");
            log.info("|  Total like votes : " + likeVote + "            |");
            log.info("|  Total dislike votes : " + dislikeVote
                    + "            |");
            log.info("|  Total hits : " + hits + "            |");
            log.info("|  Max like vote by user : " + maxVotebyUser
                    + "           |");
            log.info("|  Relevance : " + relevance + "       |");
            log.info("-------------------------------");
            log.info("*******************************");
            log.info("************ Finished Start Relevance calculate job **************");
            tweetPoll.setRelevance(relevance);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }

        for (Poll poll : pollList) {
            likeVote = poll.getLikeVote() == null ? 0 : poll
                    .getLikeVote();
            dislikeVote = poll.getDislikeVote() == null ? 0
                    : poll.getDislikeVote();
            hits = poll.getHits() == null ? 0 : poll.getHits();
            maxVotebyUser = getPollDao()
                    .getMaxPollLikeVotesbyUser(
                            poll.getEditorOwner().getUid(),
                            datebefore.getTime(), todayDate.getTime());
            relevance = EnMeUtils.calculateRelevance(likeVote,
                    dislikeVote, maxVotebyUser, hits);
            log.info("*******************************");
            log.info("******* Resume of Process *****");
            log.info("-------------------------------");
            log.info("|  Total like votes : " + likeVote + "            |");
            log.info("|  Total dislike votes : " + dislikeVote
                    + "            |");
            log.info("|  Total hits : " + hits + "            |");
            log.info("|  Max like vote by user : " + maxVotebyUser
                    + "           |");
            log.info("|  Relevance : " + relevance + "       |");
            log.info("-------------------------------");
            log.info("*******************************");
            log.info("************ Finished Start Relevance calculate job **************");
            poll.setRelevance(relevance);
            getTweetPollDao().saveOrUpdate(poll);
        }
    }

    /**
     * @return the tweetPollDao
     */
    public ITweetPoll getTweetPollDao() {
        return tweetPollDao;
    }


    /**
     * @param tweetPollDao the tweetPollDao to set
     */
    public void setTweetPollDao(final ITweetPoll tweetPollDao) {
        this.tweetPollDao = tweetPollDao;
    }

    /**
     * @return the pollDao
     */
    public IPoll getPollDao() {
        return pollDao;
    }

    /**
     * @param pollDao the pollDao to set
     */
    public void setPollDao(final IPoll pollDao) {
        this.pollDao = pollDao;
    }

    /**
     * @return the surveyDao
     */
    public ISurvey getSurveyDao() {
        return surveyDao;
    }

    /**
     * @param surveyDao the surveyDao to set
     */
    public void setSurveyDao(final ISurvey surveyDao) {
        this.surveyDao = surveyDao;
    }
}

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

import org.apache.log4j.Logger;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Calculate relevance.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */
public class CalculateRelevance {

     /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * {@link IFrontEndService}.
     */
    @Autowired
    private IFrontEndService frontEndService;

    /**
     *  {@link ITweetPollService}.
     */
    @Autowired
    private ITweetPollService tweetPollService;

    /**
     *  {@link IPollService}.
     */
    @Autowired
    private IPollService pollService;

    /**
     * The max of results.
     * **/
    private Integer MAX_RESULTS = 50;

    /**
     * Start results.
     *  **/
    private Integer START_RESULTS = 0;

    /**
     * Calculate relevance.
     * @throws EnMePollNotFoundException
     * @throws EnMeTweetPollNotFoundException
     */
    public void calculate() {
        log.info("************ Start calculate relevance item **************");
        // Unused code to search items by date range.
        /*
         * final Calendar dateFrom = Calendar.getInstance();
         * dateFrom.add(Calendar.DATE, -5); final Calendar datebefore =
         * Calendar.getInstance(); datebefore.add(Calendar.DATE, -5); final
         * Calendar todayDate = Calendar.getInstance();
         */
        getFrontEndService().processItemstoCalculateRelevance(
                getTweetPollService().getTweetPollsbyRange(MAX_RESULTS, START_RESULTS,
                        null),
                getPollService().getPollsByRange(null, START_RESULTS, null),
                null, null, null);
    }

    /**
     * @return the frontEndService
     */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
     * @param frontEndService the frontEndService to set
     */
    public void setFrontEndService(final IFrontEndService frontEndService) {
        this.frontEndService = frontEndService;
    }

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService the tweetPollService to set
     */
    public void setTweetPollService(final ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }

    /**
     * @return the pollService
     */
    public IPollService getPollService() {
        return pollService;
    }

    /**
     * @param pollService the pollService to set
     */
    public void setPollService(final IPollService pollService) {
        this.pollService = pollService;
    }
}
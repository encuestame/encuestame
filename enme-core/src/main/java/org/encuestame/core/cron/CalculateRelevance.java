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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.IFrontEndService;
import org.encuestame.core.service.IPollService;
import org.encuestame.core.service.ITweetPollService;
import org.encuestame.utils.enums.SearchPeriods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Calculate relevance.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */
public class CalculateRelevance {

     /** Log. **/
    private static final Log log = LogFactory.getLog(CalculateRelevance.class);

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
     */
    @Scheduled(cron = "${cron.calculateRelevance}")
    public void calculate() {
        if (EnMePlaceHolderConfigurer.getSystemInitialized()) {
            log.info("Starting calculate of relevance..");
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
                    getPollService().getPollsByRange(MAX_RESULTS, START_RESULTS, null),
                    null, SearchPeriods.ALLTIME);
            log.info("calculated relevance finished");
        }
    }

    /**
     * @return the frontEndService
     */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @return the pollService
     */
    public IPollService getPollService() {
        return pollService;
    }


}
/*
 ************************************************************************************
 * Copyright (C) 2001-2013 encuestame: system online surveys Copyright (C) 2013
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.cron;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.persistence.dao.IScheduled;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This job calculate every day the size of each hashgtag in the database.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since October 31, 2013
 */
public class PublishScheduled {

	/** Log. **/
	private Logger log = Logger.getLogger(this.getClass());

	/** **/
	@Autowired
	private IScheduled scheduled;

	/** {@link ITweetPollService} **/
	@Autowired
	private ITweetPollService tpollService;

	/**
	 * publish {@link TweetPoll} {@link Poll} or {@link Survey} scheduled to be
	 * published later.
	 */
	public void publish(){
		log.info("************ Start publish scheduled items **************");
		// I include in the search for the minimum date that have the maximum attempts
		final Date minimumDate = getScheduled().retrieveMinimumScheduledDate(
				Status.ACTIVE);
		final Date currentDate = DateUtil.getCurrentCalendarDate();
		// Check if minimun Date exists and its before to current date
		if ((minimumDate != null) && (minimumDate.before(currentDate))) {
			// Add parameter to evaulate item date
			// Is necesarry update TweetpollSavedPublished once it is published?
			getTpollService().publishScheduledItems(Status.ACTIVE);
		}
		else {
			log.debug("*** The minimum date is greater than the current ****");
		}

 	}

	/**
	 *
	 */
	public void remove(){
		// 1. Remove all scheduled with status published and counter attempts equals CONSTANT
		final List<Schedule> scheduleItems = getScheduled().retrieveScheduled(Status.FAILED);
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(Logger log) {
		this.log = log;
	}

	/**
	 * @return the scheduled
	 */
	public IScheduled getScheduled() {
		return scheduled;
	}

	/**
	 * @param scheduled the scheduled to set
	 */
	public void setScheduled(IScheduled scheduled) {
		this.scheduled = scheduled;
	}

	/**
	 * @return the tpollService
	 */
	public ITweetPollService getTpollService() {
		return tpollService;
	}

	/**
	 * @param tpollService the tpollService to set
	 */
	public void setTpollService(ITweetPollService tpollService) {
		this.tpollService = tpollService;
	}

}

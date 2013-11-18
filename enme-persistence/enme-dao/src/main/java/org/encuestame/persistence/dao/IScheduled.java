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
package org.encuestame.persistence.dao;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.ScheduleDao;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.utils.enums.Status;

/**
 * Interface to {@link ScheduleDao}.
 * @author Morales Urbina, Diana  paolaATencuestame.org
 * @since October 18, 2013
 */
public interface IScheduled  extends IBaseDao{

	/**
	 *
	 * @param status
	 * @return
	 */
	Date retrieveMinimumScheduledDate(final Status status);

	/**
	 *
	 * @param status
	 * @param minimumDate
	 * @return
	 */
	List<Schedule> retrieveScheduled(final Status status, final Date minimumDate);


}

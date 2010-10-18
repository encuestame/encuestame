/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.util.List;

import org.encuestame.core.exception.EnMeSearchException;
import org.encuestame.core.persistence.domain.survey.Poll;
import org.encuestame.core.persistence.domain.survey.Surveys;
import org.encuestame.core.persistence.domain.survey.TweetPoll;
import org.encuestame.core.search.SearchPeriods;
import org.encuestame.core.search.SearchSurveyPollTweetItem;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.utils.web.frontEnd.UnitSearchItem;

/**
 * Front End Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:29:38 AM
 * @version $Id:$
 */
public class FrontEndService extends AbstractBaseService implements IFrontEndService {


    /**
     * Search Items By Keyword.
     * @param keyword keyword.
     * @return result of the search.
     * @throws EnMeSearchException
     */
    public List<UnitSearchItem> searchItemsByKeyword(final String keyword, final String period) throws EnMeSearchException{
        final List<TweetPoll> items;
        final List<Poll> polls;
        final List<Surveys> surveys;
        if(period == null || keyword == null){
            throw new EnMeSearchException("search params required.");
        } else {
            if(period.equals(SearchPeriods.getPeriodString(period))){
                //final List<TweetPoll> items = getFrontEndDao().
            }
        }
        return null;
    }
}

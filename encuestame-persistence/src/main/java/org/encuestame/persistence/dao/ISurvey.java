/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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

import java.util.List;

import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.survey.Surveys;

/**
 * Interface to implement Survey Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since  01/06/2009 13:08:40
 * @version $Id$
 */
public interface ISurvey extends IBaseDao{

    /**
     * @param searchString
     * @param userId
     * @return
     */
      public List<Surveys> searchSurveyByUserId(final String searchString, final Long userId);

      /**
       *
       * @param userId
       * @return
       */
      public List<SurveyFolder> retrieveFolderByUserId(final Long userId);

      /**
       *
       * @param secId
       * @return
       */
      public List<SurveySection> retrieveQuestionsBySurveySection(final Long secId);

      /**
       *
       * @param sectionId
       * @return
       */
      public SurveySection retrieveSurveySectionById(Long sectionId);

      /**
       *
       * @param pagId
       * @return
       */
      public List<SurveyPagination> retrieveSectionByPagination(final Integer pagId);
}

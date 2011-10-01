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
package org.encuestame.persistence.dao;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.hibernate.HibernateException;

/**
 * SurveyFormat Interface.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since November 10, 2009
 * @version $Id$
 */
public interface ISurveyFormatDao extends IBaseDao {

    /**
     * Get User By Id.
     * @param idSidFormat idSidFormat
     * @return SurveyFormat
     * @throws HibernateException hibernate exception
     */
     SurveyFormat getSurveyFormatById(final int idSidFormat);
     /**
      * Get User By Name.
      * @param name name
      * @return SurveyFormat
      * @throws HibernateException hibernate exception
      */
     SurveyFormat getSurveyFormatbyname(final String name);

     /**
      * Get SurveyFormat By Date.
      * @param startDate startDate
      * @param endDate endDate
      * @return SurveyFormat
      * @throws HibernateException hibernate exception
      */
     List<SurveyFormat> getSurveyFormatbyDate(final Date startDate, final Date endDate);


}

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
package org.encuestame.web.beans.survey;

import org.encuestame.web.beans.MasterBean;
/**
 * Unit Answers Bean.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 01/06/2009 15:24:16
 * @version $Id$
 */
public class UnitAnswersBean extends MasterBean {

    public Integer id;
    public String answers;
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the answers
     */
    public String getAnswers() {
        return answers;
    }
    /**
     * @param answers the answers to set
     */
    public void setAnswers(String answers) {
        this.answers = answers;
    }



}

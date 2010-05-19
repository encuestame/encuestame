/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.Date;

/**
 * Unit Poll Bean.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since  March 15, 2009
 * @version $Id: $
 */
public class UnitPoll implements Serializable{

    /**
     * Serial
     */
    private static final long serialVersionUID = 7022698996782621900L;

    /***/
    private Long id;

    /***/
    private Boolean completedPoll;

    /***/
    private Date creationDate;

    /***/
    private UnitQuestionBean questionBean = new UnitQuestionBean();

    @Deprecated
    private String pollUser;


    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(final Long id) {
        this.id = id;
    }
    /**
     * @return the completedPoll
     */
    public final Boolean getCompletedPoll() {
        return completedPoll;
    }
    /**
     * @param completedPoll the completedPoll to set
     */
    public final void setCompletedPoll(final Boolean completedPoll) {
        this.completedPoll = completedPoll;
    }
    /**
     * @return the creationDate
     */
    public final Date getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate the creationDate to set
     */
    public final void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * @return the questionBean
     */
    public final UnitQuestionBean getQuestionBean() {
        return questionBean;
    }
    /**
     * @param questionBean the questionBean to set
     */
    public final void setQuestionBean(final UnitQuestionBean questionBean) {
        this.questionBean = questionBean;
    }



}

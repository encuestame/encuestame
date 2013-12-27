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
package org.encuestame.utils.json;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.utils.web.AbstractUnitSurvey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Home Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 06, 2011
 */
public class HomeBean extends AbstractUnitSurvey implements Serializable, Comparable<Object>{

    /** Serial. **/
    private static final long serialVersionUID = 2543644253906482885L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "question")
    private QuestionBean questionBean = new QuestionBean();

    @JsonProperty(value = "userId")
    private Long userId;

    /** Log **/
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the id
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the questionBean
     */
    @JsonIgnore
    public QuestionBean getQuestionBean() {
        return questionBean;
    }

    /**
     * @param questionBean the questionBean to set
     */
    public void setQuestionBean(final QuestionBean questionBean) {
        this.questionBean = questionBean;
    }

    /**
     * @return the userId
     */
    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * Compare home Bean items.
     */
    public int compareTo(Object o) {
        HomeBean home = (HomeBean) o;
        //log.debug("Home Bean Value: " + home.getRelevance());
        //log.debug("This home bean Value: " + this.getRelevance());
        int CompareToValue = Float.compare(home.getRelevance() == null ? 0
                : home.getRelevance(),
                this.getRelevance() == null ? 0 : this.getRelevance());
        if (CompareToValue == 0) {
            return this.getCreateDate().compareTo(home.getCreateDate());
        } else {
            //log.debug(" Result Home Bean compare: " + CompareToValue);
            return CompareToValue;
        }
    }
}

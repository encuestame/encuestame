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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.encuestame.utils.web.AbstractUnitSurvey;

/**
 * Home Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 06, 2011
 */
public class HomeBean extends AbstractUnitSurvey implements Serializable {

    /** Serial. **/
    private static final long serialVersionUID = 2543644253906482885L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "question")
    private QuestionBean questionBean = new QuestionBean();

    @JsonProperty(value = "userId")
    private Long userId;

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
}

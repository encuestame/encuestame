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
package org.encuestame.utils.web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * TweetPoll results bean.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetPollResultsBean extends AbstractResultBean implements Serializable {

    @JsonIgnore
     private QuestionAnswerBean answerBean;

    /**
     *
     */
    private static final long serialVersionUID = -8477551175621750552L;

    @Override
    @JsonIgnore
    public QuestionAnswerBean getAnswerBean() {
        return answerBean;
    }

    @Override
    public void setAnswerBean(QuestionAnswerBean answerBean) {
        this.answerBean = answerBean;
    }


}

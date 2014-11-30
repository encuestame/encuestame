package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HomeResultBean  extends AbstractResultBean implements Serializable {

    /**
    *
    */
   @JsonIgnore
   private QuestionAnswerBean answerBean;

    /**
     * @return the answerBean
     */
   @JsonIgnore
    public QuestionAnswerBean getAnswerBean() {
        return answerBean;
    }

    /**
     * @param answerBean the answerBean to set
     */
   @JsonIgnore
    public void setAnswerBean(QuestionAnswerBean answerBean) {
        this.answerBean = answerBean;
    }

}

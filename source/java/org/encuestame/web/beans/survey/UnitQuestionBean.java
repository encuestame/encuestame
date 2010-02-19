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

import java.util.ArrayList;
import java.util.List;

import org.encuestame.web.beans.MasterBean;

/**
 * Unit Question Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 01/06/2009 15:22:10
 * @version $Id$
 **/
public class UnitQuestionBean extends MasterBean {

    private String questionName;

    /**
     *
     */
    private String version;

    /**
     *
     */
    private UnitPatternBean pattern = new UnitPatternBean();

    /**
     *
     */
    private Long stateId;

    /**
     *
     */
    private Long id;

    /**
     * User Id.
     */
    private Long userId;

    /**
     *
     */
    private List<UnitAnswersBean> listAnswers = new ArrayList<UnitAnswersBean>();

    /**
     * Constructor.
     */
    public UnitQuestionBean() {
    }

    /**
     * @return the questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @param questionName
     *            the questionName to set
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the pattern
     */
    public UnitPatternBean getPattern() {
        return pattern;
    }

    /**
     * @param pattern
     *            the pattern to set
     */
    public void setPattern(UnitPatternBean pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the listAnswers
     */
    public List<UnitAnswersBean> getListAnswers() {
        return listAnswers;
    }

    /**
     * @param listAnswers
     *            the listAnswers to set
     */
    public void setListAnswers(final List<UnitAnswersBean> listAnswers) {
        this.listAnswers = listAnswers;
    }

    /**
     * @return the stateId
     */
    public Long getStateId() {
        return stateId;
    }

    /**
     * @param stateId
     *            the stateId to set
     */
    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

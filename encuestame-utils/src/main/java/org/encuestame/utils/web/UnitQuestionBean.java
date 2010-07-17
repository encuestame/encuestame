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
package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit Question Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since 01/06/2009 15:22:10
 * @version $Id$
 **/
public class UnitQuestionBean implements Serializable {



    /**
     * Serial.
     */
    private static final long serialVersionUID = -3106607865655197340L;

    private String questionName = new String("");

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
    public final String getQuestionName() {
        return questionName;
    }

    /**
     * @param questionName
     *            the questionName to set
     */
    public final void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * @return the version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the pattern
     */
    public final UnitPatternBean getPattern() {
        return pattern;
    }

    /**
     * @param pattern
     *            the pattern to set
     */
    public final void setPattern(UnitPatternBean pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the listAnswers
     */
    public final List<UnitAnswersBean> getListAnswers() {
        return listAnswers;
    }

    /**
     * @param listAnswers
     *            the listAnswers to set
     */
    public final void setListAnswers(final List<UnitAnswersBean> listAnswers) {
        this.listAnswers = listAnswers;
    }

    /**
     * @return the stateId
     */
    public final Long getStateId() {
        return stateId;
    }

    /**
     * @param stateId
     *            the stateId to set
     */
    public final void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    /**
     * @return the userId
     */
    public final Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public final void setUserId(Long userId) {
        this.userId = userId;
    }

}

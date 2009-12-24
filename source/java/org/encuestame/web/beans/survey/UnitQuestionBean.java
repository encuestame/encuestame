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

import java.util.Collection;
import java.util.LinkedList;

import org.encuestame.web.beans.MasterBean;
/**
 * Unit Question Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 01/06/2009 15:22:10
 * @version $Id$
 **/
public class UnitQuestionBean extends MasterBean {


    public String questionName;
    /**
     *
     */
    public String version;
    /**
     *
     */
    public UnitPatternBean pattern;
    /**
     *
     */
    public Long idState;
    /**
     *
     */
    public Integer id;
    /**
     *
     */
    public Collection<UnitAnswersBean> listAnswers = new LinkedList<UnitAnswersBean>();

    /**
     *
     */

    public UnitQuestionBean() {
        pattern = new UnitPatternBean();
    }

    /**
     *
     * @param questionName questionName
     * @param version version
     * @param pattern pattern
     * @param idState idState
     * @param id id
     * @param listAnswers listAnswers
     */
    public UnitQuestionBean(String questionName, String version,
            UnitPatternBean pattern, Long idState, Integer id,
            Collection<UnitAnswersBean> listAnswers) {
        super();
        this.questionName = questionName;
        this.version = version;
        this.pattern = pattern;
        this.idState = idState;
        this.id = id;
        this.listAnswers = listAnswers;
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



    public UnitPatternBean getPattern() {
        return pattern;
    }

    public void setPattern(UnitPatternBean pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the idState
     */
    public Long getIdState() {
        return idState;
    }

    /**
     * @param idState
     *            the idState to set
     */
    public void setIdState(Long idState) {
        this.idState = idState;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the listAnswers
     */
    public Collection<UnitAnswersBean> getListAnswers() {
        return listAnswers;
    }

    /**
     * @param listAnswers
     *            the listAnswers to set
     */
    public void setListAnswers(Collection<UnitAnswersBean> listAnswers) {
        this.listAnswers = listAnswers;
    }

}

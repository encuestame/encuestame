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
package org.encuestame.test.config;

import java.util.Date;
import java.util.List;

import org.encuestame.utils.web.LocationBean;
import org.encuestame.utils.web.LocationTypeBean;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitQuestionBean;

/**
 * Abstract class for beans.
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 11:16:31
 * @version $Id$
 */
public abstract class AbstractBeanBaseTest extends AbstractBaseTest{

    /**
     * Create Project Bean.
     * @param projectName project bean.
     * @param leadId user leader id
     * @param userId user owner id
     * @return {@link UnitProjectBean}
     */
    public UnitProjectBean createProjectBean(final String projectName, final Long leadId, final Long userId){
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setDateFinish(new Date());
        projectBean.setDateInit(new Date());
        projectBean.setLeader(leadId);
        projectBean.setUserId(userId);
        projectBean.setDescription("description");
        projectBean.setState(createState("active").getIdState());
        projectBean.setName(projectName);
        return projectBean;
    }

    /**
     * Create Location Type Bean.
     * @param locationName name
     * @param level level
     * @return {@link LocationTypeBean}
     */
    public LocationTypeBean createLocationTypeBean(
            final String locationName,
            final Integer level){
        final LocationTypeBean locationTypeBean = new  LocationTypeBean();
      //  locationTypeBean.setDescription(locationName);
       // locationTypeBean.setLevel(level);
        return locationTypeBean;
    }


    /**
     * Create Location Bean.
     * @param active active
     * @param description description
     * @param lng longitud
     * @param lat latitud
     * @param level level
     * @param desc desc
     * @return {@link LocationBean}
     */
    public LocationBean createLocationBean(
            final String active,
            final String description,
            final Float lng,
            final Float lat,
            final Integer level,
            final String desc){
        final LocationBean locationBean = new LocationBean();
        locationBean.setActive(active);
        locationBean.setDescription(description);
        locationBean.setLat(lat);
        locationBean.setLng(lng);
        locationBean.setLevel(level);
        locationBean.setTidtype(createCatLocationType(desc).getLocationTypeId());
        return locationBean;
    }

    /**
     * Create Unit Answer Bean.
     * @param answerId answerId
     * @param answers answers
     * @param answerHash answerHash
     * @param questionId questionId
     * @return {@link UnitAnswersBean}
     */

    public UnitAnswersBean createUnitAnswerBean(
            final Long answerId,
            final String answers,
            final String answerHash,
            final Long questionId){

       final UnitAnswersBean unitAnswerBean = new UnitAnswersBean();
       unitAnswerBean.setAnswerHash(answerHash);
       unitAnswerBean.setAnswerId(answerId);
       unitAnswerBean.setAnswers(answers);
       unitAnswerBean.setQuestionId(questionId);
       return unitAnswerBean;
    }

    /**
     * Create Unit Question Bean.
     * @param questionId questionId
     * @param questionName questionName
     * @param version version
     * @param unitAnswer unitAnswer
     * @return {@link UnitQuestionBean}
     */


    public UnitQuestionBean createUnitQuestionBean(
    final Long questionId,
    final String questionName,
    final String version,
    final List unitAnswer,
    final UnitPatternBean pattern

    ){
        final UnitQuestionBean unitQuestionBean = new UnitQuestionBean();
        unitQuestionBean.setId(questionId);

        unitQuestionBean.setQuestionName(questionName);
        unitQuestionBean.setVersion(version);
        unitQuestionBean.setUserId(1L);
        unitQuestionBean.setStateId(1L);
        return unitQuestionBean;
    }


    /**
     * Create Unit Pattern Bean.
     * @param classpattern classpattern
     * @param descripcion descripcion
     * @param finallity finallity
     * @param patternId patternId
     * @param label label
     * @param levelpattern levelpattern
     * @param patronType patronType
     * @param shortNumberString shortNumberString
     * @return {@link UnitPatternBean}
     */

    public UnitPatternBean createUnitPatternBean(
    final String classpattern,
    final String descripcion,
    final String finallity,
    final Long patternId,
    final String label,
    final String levelpattern,
    final String patronType,
    final Integer shortNumberString,
    final String template
    )
    {
        final UnitPatternBean unitPatternBean = new UnitPatternBean();
        unitPatternBean.setClasspattern(classpattern);
        unitPatternBean.setDescripcion(descripcion);
        unitPatternBean.setFinallity(finallity);
        unitPatternBean.setId(patternId);
        unitPatternBean.setLabel(label);
        unitPatternBean.setLevelpattern(levelpattern);
        unitPatternBean.setPatronType(patronType);
       // unitPatternBean.setServicemanagerBean(servicemanagerBean)
        unitPatternBean.setShortNumberString(shortNumberString);
        unitPatternBean.setTemplate(template);

        return unitPatternBean;

    }
}

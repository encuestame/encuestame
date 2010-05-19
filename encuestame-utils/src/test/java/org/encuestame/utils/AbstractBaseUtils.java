package org.encuestame.utils;

import java.util.Date;
import java.util.List;
import org.encuestame.utils.web.LocationTypeBean;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UnitQuestionBean;

import junit.framework.TestCase;

public abstract class AbstractBaseUtils extends TestCase{
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
        projectBean.setState(1L);
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
    public UnitLocationBean createLocationBean(
        final String active,
        final String description,
        final Float lng,
        final Float lat,
        final Integer level,
        final String desc){
        final UnitLocationBean locationBean = new UnitLocationBean();
        //locationBean.se
       // locationBean.setDescription(description);
       // locationBean.setLat(lat);
       // locationBean.setLng(lng);
       // locationBean.setLevel(level);
        //locationBean.setTidtype(1L);
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
       // unitPatternBean.setShortNumberString(shortNumberString);
        unitPatternBean.setTemplate(template);

        return unitPatternBean;

    }

    /**
     * Create Unit Poll.
     * @param completedPoll completedPoll
     * @param creationDate creationDate
     * @param id idUnitPoll
     * @param questionBean UnitQuestionBean
     * @return
     */
    public UnitPoll createUnitPoll(
            final Boolean completedPoll,
            final Date creationDate,
            final Long id,
            final UnitQuestionBean questionBean){
        final UnitPoll unitPoll = new UnitPoll();
        unitPoll.setCompletedPoll(completedPoll);
        unitPoll.setCreationDate(creationDate);
        unitPoll.setId(id);
        unitPoll.setQuestionBean(questionBean);
        return unitPoll;


    }
}

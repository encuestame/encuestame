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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.SurveyFormat;
import org.encuestame.utils.web.UnitSurveySection;
import org.encuestame.web.beans.MasterBean;
/**
 * Survey Format Bean.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 31/05/2009 22:43:57
 * @version $Id$
 */
public class SurveyFormatBean extends MasterBean {

    Collection<SurveyFormat> lista = new ArrayList<SurveyFormat>();
    Collection<UnitSurveySection> sections = new LinkedList<UnitSurveySection>();
    private String surveyName;
    private Log log = LogFactory.getLog(this.getClass());
    private Boolean listSuggest = false;

    /**
     *
     * @return
     */
    public final List<SurveyFormat> suggestSurveysNames() {
        ArrayList<SurveyFormat> result = new ArrayList<SurveyFormat>();
        try {
            String pref = getSurveyName();
            loadSurveySuggestion();
            log.info("before loadSurveySuggestion->");
            Iterator<SurveyFormat> iterator = lista.iterator();
            while (iterator.hasNext()) {
                SurveyFormat elem = ((SurveyFormat) iterator.next());
                if ((elem.getSurveyFormatName() != null && elem.getSurveyFormatName().toLowerCase()
                        .indexOf(pref.toLowerCase()) == 0)
                        || "".equals(pref)) {
                    result.add(elem);
                }
            }

            log.info("suggest bolean->" + getListSuggest());

        } catch (Exception e) {
            log.info("Exception->" + e);
            addErrorMessage("Error autocomplentar->" + e.getMessage(), e
                    .getMessage());
        }
        return result;
    }

    /**
     *
     */
    private void loadSurveySuggestion() {
        log.info("loadSurveySuggestion-->" + getSurveyName());
        if (getSurveyName() != null && !getSurveyName().trim().isEmpty()) {
            lista = getServicemanager()
            .getApplicationServices().getSecurityService()
            .getSurveyService()
                    .getSurveyDaoImp().searchSurveyByName(
                            getSurveyName().trim());
            if (lista.size() > 0)
                setListSuggest(true);
            else
                setListSuggest(false);
        } else {
            setListSuggest(false);
            log.info("getSurveyName empty->" + getSurveyName());
        }
    }

    /**
     * @return the surveyName
     */
    public final String getSurveyName() {
        return surveyName;
    }

    /**
     * @param surveyName
     *            the surveyName to set
     */
    public final void setSurveyName(final String surveyName) {
        this.surveyName = surveyName.trim();
    }

    /**
     * @return the lista
     */
    public final Collection<SurveyFormat> getLista() {
        log.info("lista->" + lista.size());
        return lista;
    }

    /**
     * @param lista
     *            the lista to set
     */
    public final void setLista(final Collection<SurveyFormat> lista) {
        this.lista = lista;
    }

    /**
     * @return the listSuggest
     */
    public final Boolean getListSuggest() {
        return listSuggest;
    }

    /**
     * @param listSuggest
     *            the listSuggest to set
     */
    public final void setListSuggest(final Boolean listSuggest) {
        this.listSuggest = listSuggest;
    }

}

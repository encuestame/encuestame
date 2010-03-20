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

import java.util.Collection;

import org.encuestame.web.beans.MasterBean;

/**
 * Unit Pattern Bean.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 01/06/2009 15:25:459
 * @version $Id$
 */
public class UnitPatternBean extends MasterBean {

    private Long id;
    private String descripcion;
    private String label;
    private String patronType;
    private String template;
    private String classpattern;
    private String levelpattern;
    private String finallity;

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the patronType
     */
    public String getPatronType() {
        return patronType;
    }

    /**
     * @param patronType
     *            the patronType to set
     */
    public void setPatronType(String patronType) {
        this.patronType = patronType;
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
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template
     *            template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return classpattern classpattern
     */
    public String getClasspattern() {
        return classpattern;
    }

    /**
     * @param classpattern classpattern
     */
    public void setClasspattern(String classpattern) {
        this.classpattern = classpattern;
    }

    /**
     * @return levelpattern
     */
    public String getLevelpattern() {
        return levelpattern;
    }

    /**
     * @param levelpattern levelpattern
     */
    public void setLevelpattern(String levelpattern) {
        this.levelpattern = levelpattern;
    }

    /**
     * @return finallity
     */
    public String getFinallity() {
        return finallity;
    }

    /**
     * @param finallity finallity
     */
    public void setFinallity(String finallity) {
        this.finallity = finallity;
    }





      /**
     * @param id  id
     * @param descPattern descPattern
     * @param label label
     * @param patronType patronType
     * @param template template
     * @param classpattern classpattern
     * @param levelpattern levelpattern
     * @param finallity finallity
     */
    public UnitPatternBean (Long id, String descPattern, String label,
            String patronType, String template, String classpattern,
            String levelpattern, String finallity) {
        super();
        this.descripcion = descPattern;
        this.label = label;
        this.patronType = patronType;
        this.template = template;
        this.id = id;
        this.classpattern = classpattern;
        this.levelpattern = levelpattern;
        this.finallity = finallity;
    }

    /**
     *
     */
    public UnitPatternBean(){

    }

}

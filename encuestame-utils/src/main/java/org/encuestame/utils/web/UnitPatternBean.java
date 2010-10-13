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


/**
 * Unit Pattern Bean.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 01/06/2009 15:25:459
 * @version $Id$
 */
public class UnitPatternBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 504237606410573311L;
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
    public final String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public final void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the label
     */
    public final String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public final void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the patronType
     */
    public final String getPatronType() {
        return patronType;
    }

    /**
     * @param patronType
     *            the patronType to set
     */
    public final void setPatronType(String patronType) {
        this.patronType = patronType;
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
    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * @return template
     */
    public final String getTemplate() {
        return template;
    }

    /**
     * @param template
     *            template
     */
    public final void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return classpattern classpattern
     */
    public final String getClasspattern() {
        return classpattern;
    }

    /**
     * @param classpattern classpattern
     */
    public final void setClasspattern(String classpattern) {
        this.classpattern = classpattern;
    }

    /**
     * @return levelpattern
     */
    public final String getLevelpattern() {
        return levelpattern;
    }

    /**
     * @param levelpattern levelpattern
     */
    public final void setLevelpattern(String levelpattern) {
        this.levelpattern = levelpattern;
    }

    /**
     * @return finallity
     */
    public final String getFinallity() {
        return finallity;
    }

    /**
     * @param finallity finallity
     */
    public final void setFinallity(String finallity) {
        this.finallity = finallity;
    }

    /**
     *
     */
    public UnitPatternBean(){}

}

/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */
package org.encuestame.web.beans.survey;

import java.util.Collection;

import org.encuestame.web.beans.MasterBean;

/**
 * Unit Pattern Bean.
 *
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 01/06/2009 15:25:459
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

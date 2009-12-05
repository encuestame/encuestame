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
}

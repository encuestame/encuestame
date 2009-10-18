/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.pojo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SurveyTime.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_time")
public class SurveyTime implements java.io.Serializable {

    private SurveyTimeId id;
    private Surveys surveys;
    private int elapsedTime;
    private int quitflag;

    public SurveyTime() {
    }

    public SurveyTime(SurveyTimeId id, Surveys surveys, int elapsedTime,
            int quitflag) {
        this.id = id;
        this.surveys = surveys;
        this.elapsedTime = elapsedTime;
        this.quitflag = quitflag;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "sequence", column = @Column(name = "sequence", nullable = false)),
            @AttributeOverride(name = "sid", column = @Column(name = "sid", nullable = false)) })
    public SurveyTimeId getId() {
        return this.id;
    }

    public void setId(SurveyTimeId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid", nullable = false, insertable = false, updatable = false)
    public Surveys getSurveys() {
        return this.surveys;
    }

    public void setSurveys(Surveys surveys) {
        this.surveys = surveys;
    }

    @Column(name = "elapsed_time", nullable = false)
    public int getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Column(name = "quitflag", nullable = false)
    public int getQuitflag() {
        return this.quitflag;
    }

    public void setQuitflag(int quitflag) {
        this.quitflag = quitflag;
    }

}

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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SurveyResult.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_result")
public class SurveyResult implements java.io.Serializable {

    private Long rid;
    private Long qid;
    private Long sid;
    private String resp;
    private Set<SurveyResultMod> surveyResultMods = new HashSet<SurveyResultMod>(
            0);

    public SurveyResult() {
    }

    public SurveyResult(Long qid, Long sid, String resp) {
        this.qid = qid;
        this.sid = sid;
        this.resp = resp;
    }

    public SurveyResult(Long qid, Long sid, String resp,
            Set<SurveyResultMod> surveyResultMods) {
        this.qid = qid;
        this.sid = sid;
        this.resp = resp;
        this.surveyResultMods = surveyResultMods;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rid", unique = true, nullable = false)
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    @Column(name = "qid", nullable = false)
    public Long getQid() {
        return this.qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    @Column(name = "sid", nullable = false)
    public Long getSid() {
        return this.sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    @Column(name = "resp", nullable = false)
    public String getResp() {
        return this.resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "surveyResult")
    public Set<SurveyResultMod> getSurveyResultMods() {
        return this.surveyResultMods;
    }

    public void setSurveyResultMods(Set<SurveyResultMod> surveyResultMods) {
        this.surveyResultMods = surveyResultMods;
    }

}

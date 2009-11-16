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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Surveys.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "surveys")
public class Surveys implements java.io.Serializable {

    private Long sid;
    private SecUsers secUsers;
    private Integer ticket;
    private Date startDate;
    private Date endDate;
    private Date dateInterview;
    private String complete;
    private Integer idSidFormat;
    private Set<SurveyTime> surveyTimes = new HashSet<SurveyTime>(0);

    @Id
    //TODO: need add autoincrement
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sid", unique = true, nullable = false)
    public long getSid() {
        return this.sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUsers getSecUsers() {
        return this.secUsers;
    }

    public void setSecUsers(SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    @Column(name = "ticket", nullable = false)
    public int getTicket() {
        return this.ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, length = 0)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false, length = 0)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_interview", length = 0)
    public Date getDateInterview() {
        return this.dateInterview;
    }

    public void setDateInterview(Date dateInterview) {
        this.dateInterview = dateInterview;
    }

    @Column(name = "complete", length = 2)
    public String getComplete() {
        return this.complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    @Column(name = "id_sid_format")
    public Integer getIdSidFormat() {
        return this.idSidFormat;
    }

    public void setIdSidFormat(Integer idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "surveys")
    public Set<SurveyTime> getSurveyTimes() {
        return this.surveyTimes;
    }

    public void setSurveyTimes(Set<SurveyTime> surveyTimes) {
        this.surveyTimes = surveyTimes;
    }

}

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
package org.encuestame.core.persistence.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QuestionColettion.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "question_collection")
public class QuestionColettion {

    private Long idQColection;
    private SecUser secUsers;
    private String desColeccion;
    private Date creationDate;

    /**
     * @return idQColection
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_q_colection", unique = true, nullable = false)
    public Long getIdQColection() {
        return this.idQColection;
    }

    /**
     * @param idQColection idQColection
     */
    public void setIdQColection(final Long idQColection) {
        this.idQColection = idQColection;
    }

    /**
     * @return {@link SecUser}
     */
    @ManyToOne()
    @JoinColumn(name = "uid", nullable = false)
    public SecUser getSecUsers() {
        return this.secUsers;
    }

    /**
     * @param secUsers {@link SecUser}
     */
    public void setSecUsers(final SecUser secUsers) {
        this.secUsers = secUsers;
    }

    /**
     * @return desColeccion
     */
    @Column(name = "des_coleccion", nullable = false)
    public String getDesColeccion() {
        return this.desColeccion;
    }

    /**
     * @param desColeccion desColeccion
     */
    public void setDesColeccion(final String desColeccion) {
        this.desColeccion = desColeccion;
    }

    /**
     * @return creationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, length = 0)
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * @param creationDate creationDate
     */
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }
}

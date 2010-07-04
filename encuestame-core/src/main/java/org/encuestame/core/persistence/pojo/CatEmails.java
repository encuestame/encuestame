/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Emails Catalog.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "cat_emails",
	    uniqueConstraints = {@UniqueConstraint(columnNames={"email"})})
public class CatEmails {

	private Long idEmail;
 	private String email;
 	private CatListEmails idListEmail;

 	/**
	 * @return the idEmail
	 */
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "email_id", unique = true, nullable = false)
	public Long getIdEmail() {
		return idEmail;
	}

	/**
	 * @param idEmail the idEmail to set
	 */
	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
	}

	/**
	 * @return the email
	 */
 	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the idListEmail
	 */
	 @ManyToOne(cascade = CascadeType.MERGE)
     @JoinColumn(name = "id_list", nullable = false)
	public CatListEmails getIdListEmail() {
		return idListEmail;
	}

	/**
	 * @param idListEmail the idListEmail to set
	 */
	public void setIdListEmail(CatListEmails idListEmail) {
		this.idListEmail = idListEmail;
	}



}

/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.persistence.domain.security;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

/**
 * Created by jpicado on 01/11/14.
 */
@Entity
@Table(name = "helpPage")
@Indexed(index="help_page")
public class HelpPage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9143752254367442213L;

    private Long helpPageId;
    private UserAccount userAccount;
    private String pagePath;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "help_page_id", unique = true, nullable = false)
    public Long getHelpPageId() {
        return helpPageId;
    }

    public void setHelpPageId(Long helpPageId) {
        this.helpPageId = helpPageId;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "help_user_id")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Column(name = "url_path")
    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}

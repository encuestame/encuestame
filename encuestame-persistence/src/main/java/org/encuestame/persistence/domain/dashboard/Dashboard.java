/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.encuestame.persistence.domain.security.UserAccount;

/**
 * Dashboard domain.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 27, 2011
 */
@Entity
@Table(name = "dashboard")
public class Dashboard {

    /** Dashboard Id. **/
    private Long boardId;

    /** Dashboard name. **/
    private String pageBoardName;

    /** Dashboard description. **/
    private String description;

    /** Favorite. **/
    private Boolean favorite;

    /** Page Layout. **/
    private LayoutEnum pageLayout = LayoutEnum.AAA_COLUMNS;

    /** Dashboard sequence. **/
    private Integer boardSequence;

    /** Favorite counter. **/
    private Integer favoriteCounter;

    /** {@link UserAccount} **/
    private UserAccount userBoard;

    /**
    * @return the boardId
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dashboardId", unique = true, nullable = true)
    public Long getBoardId() {
        return boardId;
    }

    /**
    * @param boardId the boardId to set
    */
    public void setBoardId(final Long boardId) {
        this.boardId = boardId;
    }

    /**
    * @return the pageBoardName
    */
    @Column(name = "dashboardName", nullable = false)
    public String getPageBoardName() {
        return pageBoardName;
    }

    /**
    * @param pageBoardName the pageBoardName to set
    */
    public void setPageBoardName(final String pageBoardName) {
        this.pageBoardName = pageBoardName;
    }

    /**
    * @return the description
    */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
    * @param description the description to set
    */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
    * @return the favorite
    */
    @Column(name = "favorite")
    public Boolean getFavorite() {
        return favorite;
    }

    /**
    * @param favorite the favorite to set
    */
    public void setFavorite(final Boolean favorite) {
        this.favorite = favorite;
    }

    /**
    * @return the pageLayout
    */
    @Column(name = "dashboad_layout")
    @Enumerated(EnumType.ORDINAL)
    public LayoutEnum getPageLayout() {
        return pageLayout;
    }

    /**
    * @param pageLayout the pageLayout to set
    */
    public void setPageLayout(final LayoutEnum pageLayout) {
        this.pageLayout = pageLayout;
    }

    /**
    * @return the boardSequence
    */
    @Column(name = "sequence")
    public Integer getBoardSequence() {
        return boardSequence;
    }

    /**
    * @param boardSequence the boardSequence to set
    */
    public void setBoardSequence(final Integer boardSequence) {
        this.boardSequence = boardSequence;
    }

    /**
    * @return the favoriteCounter
    */
    @Column(name = "favorite_counter")
    public Integer getFavoriteCounter() {
        return favoriteCounter;
    }

    /**
    * @param favoriteCounter the favoriteCounter to set
    */
    public void setFavoriteCounter(final Integer favoriteCounter) {
        this.favoriteCounter = favoriteCounter;
    }

    /**
    * @return the userBoard
    */
    @ManyToOne()
    public UserAccount getUserBoard() {
        return userBoard;
    }

    /**
    * @param userBoard the userBoard to set
    */
    public void setUserBoard(final UserAccount userBoard) {
        this.userBoard = userBoard;
    }
}

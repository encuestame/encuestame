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

import org.apache.log4j.Logger;
/**
 * Menu Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since  27/05/2009 12:49:52
 * @version $Id$
 **/
public class MenuBean {

    private String currentSelected = "current";
    private String menuSelected;
    private String admonCss;
    private String pollCss;
    private String indexCss;
    private String surveyCss;
    private String statsCss;
    private static Logger log = Logger.getLogger(MenuBean.class);

    /**
     * Constructor.
     */
    public MenuBean() {
        setIndexCss(getCurrentSelected());
    }

    /**
     * @return the currentSelected
     */
    public String getCurrentSelected() {
        return currentSelected;
    }

    /**
     * @param currentSelected
     *            the currentSelected to set
     */
    public void setCurrentSelected(String currentSelected) {
        this.currentSelected = currentSelected;
    }

    /**
     *
     */
    private void changeCurrentMenu() {
        resetMenus();
        log.info("changeCurrentMenu->"+getCurrentSelected());
        if (getMenuSelected().compareTo("index") == 0) {
            setIndexCss(getCurrentSelected());
        } else if (getMenuSelected().compareTo("admon") == 0) {
            setAdmonCss(getCurrentSelected());
        } else if (getMenuSelected().compareTo("survey") == 0) {
            setSurveyCss(getCurrentSelected());
        } else if (getMenuSelected().compareTo("stats") == 0) {
            setStatsCss(getCurrentSelected());
        } else if (getMenuSelected().compareTo("poll") == 0) {
            setPollCss(getCurrentSelected());
        } else {
            setIndexCss(getCurrentSelected());
        }
    }

    private void resetMenus() {
        setIndexCss("");
        setAdmonCss("");
        setStatsCss("");
        setPollCss("");
        setSurveyCss("");
    }

    /**
     * @return the menuSelected
     */
    public String getMenuSelected() {

        return menuSelected;
    }

    /**
     * @param menuSelected
     *            the menuSelected to set
     */
    public void setMenuSelected(String menuSelected) {
        log.info("menuSelected->"+menuSelected);
        this.menuSelected = menuSelected;
        changeCurrentMenu();
    }

    /**
     * @return the admonCss
     */
    public String getAdmonCss() {
        return admonCss;
    }

    /**
     * @param admonCss
     *            the admonCss to set
     */
    public void setAdmonCss(String admonCss) {
        this.admonCss = admonCss;
    }

    /**
     * @return the indexCss
     */
    public String getIndexCss() {
        return indexCss;
    }

    /**
     * @param indexCss
     *            the indexCss to set
     */
    public void setIndexCss(String indexCss) {
        this.indexCss = indexCss;
    }

    /**
     * @return the surveyCss
     */
    public String getSurveyCss() {
        return surveyCss;
    }

    /**
     * @param surveyCss
     *            the surveyCss to set
     */
    public void setSurveyCss(String surveyCss) {
        this.surveyCss = surveyCss;
    }

    /**
     * @return the statsCss
     */
    public String getStatsCss() {
        return statsCss;
    }

    /**
     * @param statsCss
     *            the statsCss to set
     */
    public void setStatsCss(String statsCss) {
        this.statsCss = statsCss;
    }

    /**
     * @return pollCss.
     */
    public String getPollCss() {
        return pollCss;
    }
    /**
     *
     * @param pollCss pollCss
     */
    public void setPollCss(String pollCss) {
        this.pollCss = pollCss;
    }

}

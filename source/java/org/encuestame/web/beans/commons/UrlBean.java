package org.encuestame.web.beans.commons;

import org.encuestame.core.service.ApplicationServices;

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
 *
 * Id: UrlBean.java Date: 13/05/2009 15:40:14
 *
 * @author juanpicado package: org.encuestame.web.beans.commons
 * @version 1.0
 */
public class UrlBean {

    ApplicationServices applicationService;
    public String img;
    public String googleKey;

    public ApplicationServices getApplicationService() {
        return applicationService;
    }

    public void setApplicationService(ApplicationServices applicationService) {
        this.applicationService = applicationService;
    }

    public String getImg() {
        img = getApplicationService().getUrlImg().trim();
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return the googleKey
     */
    public String getGoogleKey() {
        googleKey = getApplicationService().getApiKeygoogle();
        if (googleKey == null) {
            googleKey = "";
        }
        return googleKey;
    }

    /**
     * @param googleKey
     *            the googleKey to set
     */
    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }

}

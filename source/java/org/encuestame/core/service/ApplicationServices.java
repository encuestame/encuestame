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
package org.encuestame.core.service;

import org.encuestame.web.beans.commons.UrlBean;

/**
 * Application Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 11:35:01
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public class ApplicationServices extends Service implements IApplicationServices {

    private String name;
    private String urlImg;
    private String encoding;
    private String apiKeygoogle;
    private UrlBean url;

    private ISecurityService securityService;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the apiKeygoogle
     */
    public String getApiKeygoogle() {
        return apiKeygoogle;
    }

    /**
     * @param apiKeygoogle the apiKeygoogle to set
     */
    public void setApiKeygoogle(String apiKeygoogle) {
        this.apiKeygoogle = apiKeygoogle;
    }

    /**
     * @return the securityService
     */
    public ISecurityService getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(ISecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @return the url
     */
    public UrlBean getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(UrlBean url) {
        this.url = url;
    }

}

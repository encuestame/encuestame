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
package org.encuestame.core.service.util;

import org.encuestame.core.service.ApplicationServices;

/**
 * Url Bean.
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since 13/05/2009 15:40:14
 * @version $Id$
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

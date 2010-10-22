/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans.admon;

import org.encuestame.business.service.imp.IProjectService;
import org.encuestame.web.beans.MasterBean;

/**
 * Admon Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 23, 2010 6:53:46 PM
 * @version $Id:$
 */
public abstract class AdmonBean extends MasterBean{


    private IProjectService projectService;

    /**
     * @return the projectService
     */
    public IProjectService getProjectService() {
        return projectService = getServicemanager().getApplicationServices().getProjectService();
    }

    /**
     * @param projectService the projectService to set
     */
    public void setProjectService(IProjectService projectService) {
        this.projectService = projectService;
    }
}

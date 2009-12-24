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
package org.encuestame.web.beans.location;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.web.beans.MasterBean;

/**
 * Location Type Bean.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since 16/11/2009 21:25:07
 * @version $Id$
 **/
public class LocationTypeBean extends MasterBean {
    /**Unit Location Type.*/
    UnitLocationTypeBean locationTypeBean;

    /**
     * Location Type Bean Constructor.
     */
    public LocationTypeBean() {

    }

    /**
     *Create Location Type.
     */
    public void createLocationType(){
        try {
            getServicemanager().getDataEnMeSource().createCatLocationType(this.locationTypeBean);
        } catch (EnMeExpcetion e) {
            addErrorMessage(e.getMessage(),e.getMessage());
        }

    }

    /**
     * @param locationTypeBean the locationTypeBean to set
     */
    public void setLocationTypeBean(UnitLocationTypeBean locationTypeBean) {
        this.locationTypeBean = locationTypeBean;
    }
}

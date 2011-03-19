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
package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Unit Survey.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 31, 2010 8:19:51 AM
 * @version $Id:$
 */
public abstract class AbstractUnitSurvey implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -2162917977567543044L;


    /** List of HashTags. **/
    private List<HashTagBean> hashTags = new ArrayList<HashTagBean>();

    /**
     * @return the hashTags
     */
    public final List<HashTagBean> getHashTags() {
        return hashTags;
    }

    /**
     * @param hashTags the hashTags to set
     */
    public final void setHashTags(List<HashTagBean> hashTags) {
        this.hashTags = hashTags;
    }
}

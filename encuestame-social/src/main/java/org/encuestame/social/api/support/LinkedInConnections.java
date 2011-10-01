/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.social.api.support;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  A model class containing a list of a user's connections on LinkedIn.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
@XmlRootElement(name = "connections")
public class LinkedInConnections {
    @XmlElement(name = "person")
    List<LinkedInProfile> connections;

    /**
     * Retrieves the list of connected profiles.
     *
     * @return a list of connected profiles
     */
    public List<LinkedInProfile> getConnections() {
        return connections;
    }
}

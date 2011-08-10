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

/**
 * Gadget Types.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public enum GadgetType {

    /** Gadget type for polls**/
    POLL,

    /** Gadget type for surveys**/
    SURVEYS,

    /** Gadget type for tweetPolls**/
    TWEETPOLLS,

    GadgetType(){

    };

    /**
     * Get gadget type by string
     * @param gadgetType
     * @return
     */
    public static GadgetType getGadgetType(final String gadgetType) {
        if (null == gadgetType) { return null; }
        else if (gadgetType.equalsIgnoreCase("TWEETPOLLS")) { return TWEETPOLLS; }
        else if (gadgetType.equalsIgnoreCase("SURVEYS")) { return SURVEYS; }
        else if (gadgetType.equalsIgnoreCase("POLL")) { return POLL; }
        else return null;
    }

}

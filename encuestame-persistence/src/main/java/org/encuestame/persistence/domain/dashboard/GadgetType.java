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
package org.encuestame.persistence.domain.dashboard;

/**
 * Gadget Types.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public enum GadgetType {

    /** **/
    ACTIVITY_STREAM,

    /** Gadget type for surveys**/
    COMMENTS,

    /** Gadget type for tweetPolls**/
    TWEETPOLLS_VOTES,

    /**
     * Constructor.
     */
    GadgetType(){

    };

    /**
     * Get gadget type by string
     * @param gadgetType
     * @return
     */
    public static GadgetType getGadgetType(final String gadgetType) {
        if (null == gadgetType) { return null; }
        else if (gadgetType.equalsIgnoreCase("STREAM")) { return ACTIVITY_STREAM; }
        else if (gadgetType.equalsIgnoreCase("COMMENTS")) { return COMMENTS; }
        else if (gadgetType.equalsIgnoreCase("TWEETPOLLSVOTES")) { return TWEETPOLLS_VOTES; }
        else return null;
    }

    /**
     * To String.
     */
    public String toString() {
        String gadget = "STREAM";
        if (this == ACTIVITY_STREAM) { gadget = "STREAM".toLowerCase(); }
        else if (this == COMMENTS) { gadget = "COMMENTS".toLowerCase(); }
        else if (this == TWEETPOLLS_VOTES) { gadget = "TWEETPOLLSVOTES".toLowerCase(); }
        return gadget;
    }
}

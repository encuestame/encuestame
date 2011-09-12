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
package org.encuestame.core.search;

/**
 * Type search result.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 23, 2011
 */
public enum TypeSearchResult {
    TWEETPOLL, PROFILE, POLL, SURVEY, ATTACHMENT, QUESTION, HASHTAG,

    TypeSearchResult(){

    };

    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == TWEETPOLL) { type = "TWEETPOLL"; }
        else if (this == PROFILE) { type = "PROFILE"; }
        else if (this == POLL) { type = "POLL"; }
        else if (this == SURVEY) { type = "SURVEY"; }
        else if (this == ATTACHMENT) { type = "ATTACHMENT"; }
        else if (this == QUESTION) { type = "QUESTION"; }
        else if (this == HASHTAG) { type = "HASHTAG"; }
        return type;
    }

    /**
     *
     * @param layout
     * @return
     */
    public static TypeSearchResult getTypeSearchResult(final String type) {
        if (null == type) { return null; }
        else if (type.equalsIgnoreCase("TWEETPOLL")) { return TWEETPOLL; }
        else if (type.equalsIgnoreCase("PROFILE")) { return PROFILE; }
        else if (type.equalsIgnoreCase("POLL")) { return POLL; }
        else if (type.equalsIgnoreCase("SURVEY")) { return SURVEY; }
        else if (type.equalsIgnoreCase("ATTACHMENT")) { return ATTACHMENT; }
        else if (type.equalsIgnoreCase("QUESTION")) { return QUESTION; }
        else if (type.equalsIgnoreCase("HASHTAG")) { return HASHTAG; }
        else return null;
    }
}

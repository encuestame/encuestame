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
package org.encuestame.utils.enums;

/**
 * Type search result.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 23, 2011
 */
public enum TypeSearchResult {
    TWEETPOLL, PROFILE, POLL, SURVEY, ATTACHMENT, QUESTION, HASHTAG, HASHTAGRATED, SOCIALNETWORK, HITS, VOTES, ALL, COMMENT, TWEETPOLLRESULT, POLLRESULT,

    TypeSearchResult(){

    };

    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == TWEETPOLL) { type = "TWEETPOLL"; }
        else if (this == PROFILE) { type = "PROFILE"; }
        else if (this == COMMENT) { type = "COMMENT"; }
        else if (this == POLL) { type = "POLL"; }
        else if (this == SURVEY) { type = "SURVEY"; }
        else if (this == ATTACHMENT) { type = "ATTACHMENT"; }
        else if (this == QUESTION) { type = "QUESTION"; }
        else if (this == HASHTAG) { type = "HASHTAG"; }
        else if (this == HASHTAGRATED) { type = "HASHTAGRATED"; }
        else if (this == SOCIALNETWORK) { type = "SOCIALNETWORK"; }
        else if (this == HITS) { type = "HITS"; }
        else if (this == VOTES) { type = "VOTES"; }
        else if (this == TWEETPOLLRESULT) { type = "TWEETPOLLRESULT"; }
        else if (this == POLLRESULT) { type = "POLLRESULT"; }
        else if (this == ALL) { type = "ALL"; }
        return type;
    }

    /**
     * Return the url prefix to make url to acces to public url.
     * @param type
     * @return
     */
    public static String getUrlPrefix(final TypeSearchResult type) {
        if (null == type) { return null; }
        else if (type.equals(TWEETPOLL)) { return "tweetpoll"; }
        else if (type.equals(PROFILE)) { return "profile"; }
        else if (type.equals(COMMENT)) { return "comment"; }
        else if (type.equals(POLL)) { return "poll"; }
        else if (type.equals(SURVEY)) { return "survey"; }
        else if (type.equals(HASHTAG)) { return "tag"; }
        else if (type.equals(HASHTAGRATED)) { return "hashtagRated"; }
        else if (type.equals(SOCIALNETWORK)) { return "socialnetwork"; }
        else if (type.equals(HITS)) { return "hits"; }
        else if (type.equals(VOTES)) { return "votes"; }
        else if (type.equals(TWEETPOLLRESULT)) { return "tweetpollresult"; }
        else if (type.equals(POLLRESULT)) { return "pollresult"; }
        else if (type.equals(ALL)) { return "all"; }
        else return null;
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
        else if (type.equalsIgnoreCase("COMMENT")) { return COMMENT; }
        else if (type.equalsIgnoreCase("HASHTAGRATED")) { return HASHTAGRATED; }
        else if (type.equalsIgnoreCase("SOCIALNETWORK")) { return SOCIALNETWORK; }
        else if (type.equalsIgnoreCase("HITS")) { return HITS; }
        else if (type.equalsIgnoreCase("VOTES")) { return VOTES; }
        else if (type.equalsIgnoreCase("TWEETPOLLRESULT")) { return TWEETPOLLRESULT; }
        else if (type.equalsIgnoreCase("POLLRESULT")) { return POLLRESULT; }
        else if (type.equalsIgnoreCase("ALL")) { return ALL; }
        else return null;
    }
}

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
    TWEETPOLL("TWEETPOLL"),
    PROFILE("PROFILE"),
    POLL("POLL"),
    SURVEY("SURVEY"),
    QUESTION("QUESTION"),
    HASHTAG("HASHTAG"),
    HASHTAGRATED("HASHTAGRATED"),
    SOCIALNETWORK("SOCIALNETWORK"),
    HITS("HITS"),
    VOTES("VOTES"),
    ALL("ALL"),
    COMMENT("COMMENT"),
    TWEETPOLLRESULT("TWEETPOLLRESULT"),
    POLLRESULT("POLLRESULT");

    /** **/
    private String typeSearchAsString;

    /**
     * Constructor
     * @param optionAsString
     */
    TypeSearchResult(String optionAsString){
        this.typeSearchAsString = optionAsString;
    }

    /**
     *
     * @return
     */
    public String toWidget() {
        String type = "";
        if (this == TWEETPOLL) { type = "tweetpoll"; }
        else if (this == PROFILE) { type = "profile"; }
        else if (this == POLL) { type = "poll"; }
        else if (this == SURVEY) { type = "survey"; }
        else if (this == HASHTAG) { type = "hashtag"; }
        else if (this == HASHTAGRATED) { type = "hashtagrated"; }
        else if (this == SOCIALNETWORK) { type = "socialnetwork"; }
        else if (this == TWEETPOLLRESULT) { type = "tweetpollresult"; }
        else if (this == POLLRESULT) { type = "pollresult"; }
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
        else if (type.equals(HASHTAGRATED)) { return "hashtagrated"; }
        else if (type.equals(SOCIALNETWORK)) { return "socialnetwork"; }
        else if (type.equals(HITS)) { return "hits"; }
        else if (type.equals(VOTES)) { return "votes"; }
        else if (type.equals(TWEETPOLLRESULT)) { return "tweetpollresult"; }
        else if (type.equals(POLLRESULT)) { return "pollresult"; }
        else if (type.equals(ALL)) { return "all"; }
        else return null;
    }

    /**
     * The css class to be appended into the embed code.
      * @param type
     * @return
     */
    public static String getCSSClass(final TypeSearchResult type ){
        if (null == type) { return null; }
        else if (type.equals(POLL)) { return "enme-poll-form"; }
        else if (type.equals(POLLRESULT)) { return "enme-poll-vote"; }
        else if (type.equals(TWEETPOLLRESULT)) { return "enme-tp-vote"; }
        else if (type.equals(TWEETPOLL)) { return "enme-tp-form"; }
        else if (type.equals(PROFILE)) { return "enme-profile"; }
        else if (type.equals(HASHTAG)) { return "enme-hashtag"; }
        else return null;
    }

}

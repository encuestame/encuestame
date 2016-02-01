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
    TWEETPOLL("TWEETPOLL", "enme-tp-form"),
    PROFILE("PROFILE","enme-profile "),
    POLL("POLL", "enme-poll-form"),
    SURVEY("SURVEY"),
    QUESTION("QUESTION"),
    HASHTAG("HASHTAG", "enme-hashtag"),
    HASHTAGRATED("HASHTAGRATED"),
    SOCIALNETWORK("SOCIALNETWORK"),
    HITS("HITS"),
    VOTES("VOTES"),
    ALL("ALL"),
    COMMENT("COMMENT"),
    TWEETPOLLRESULT("TWEETPOLLRESULT","enme-tp-vote"),
    POLLRESULT("POLLRESULT", "enme-poll-vote");

    /**
     *
     */
    private String typeSearchAsString;


    /**
     *
     */
    private String cssClass;

    /**
     *
     * Constructor
     * @param optionAsString
     */
    TypeSearchResult(String optionAsString){
        this.typeSearchAsString = optionAsString;
    }

    /**
     *
     * @param optionAsString
     * @param cssClass
     */
    TypeSearchResult(String optionAsString,  final String cssClass){
        this.typeSearchAsString = optionAsString;
        this.cssClass = cssClass;

    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return this.typeSearchAsString;
    }

    /**
     *
     * @return
     */
    public String toWidget() {
        return this.typeSearchAsString.toLowerCase();
    }

    /**
     * Return the url prefix to make url to acces to public url.
     * @return
     */
    public String  getUrlPrefix() {
        return this.typeSearchAsString.toLowerCase();
    }

    /**
     * The css class to be appended into the embed code.
     * @return
     */
    public String getCSSClass(){
         return this.cssClass;
    }
}

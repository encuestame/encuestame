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
 * Comments Options Enumeration
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 22, 2011
 */
public enum CommentOptions {

    /** 
     * Define the status of the comment as restricted. 
     **/
    RESTRICT,

    /** 
     * Approve Comments 
     **/
    APPROVE,

    /**
     *  Define the comment status as moderated.
     **/
    MODERATE,
    	
    /**
     * Define the asset as published
     */
    PUBLISHED,

    /**
     * Define the asset as a SPAM
     */
    SPAM,

    /**
     * Allow all
     */
    ALL,

    showComments(){};

    /**
     * To String.
     */
    public String toString() {
        String comment = "";
        if (this == RESTRICT) { comment = "RESTRICT"; }
        else if (this == APPROVE) { comment = "APPROVE"; }
        else if (this == MODERATE) { comment = "MODERATE"; }
        else if (this == PUBLISHED) { comment = "PUBLISHED"; }
        else if (this == SPAM) { comment = "SPAM"; }
        else if (this == ALL) { comment = "ALL"; }
        return comment;
    }

    /**
     * Get comment option enum.
     * @param option
     * @return
     */
    public static CommentOptions getCommentOption(final String option) {
        if (null == option) { return null; }
        else if (option.equalsIgnoreCase("RESTRICT")) { return RESTRICT; }
        else if (option.equalsIgnoreCase("APPROVE")) { return APPROVE; }
        else if (option.equalsIgnoreCase("MODERATE")) { return MODERATE; }
        else if (option.equalsIgnoreCase("PUBLISHED")) { return PUBLISHED; }
        else if (option.equalsIgnoreCase("SPAM")) { return SPAM; }
        else if (option.equalsIgnoreCase("ALL")) { return ALL; }
        else return null;
    }
    
    /**
     * Get comment option enum.
     * @param option
     * @return
     */
    public static CommentOptions getCommentStatus(final String option) {
        if (null == option) { return null; }
        else if (option.equalsIgnoreCase("RESTRICT")) { return RESTRICT; }
        else if (option.equalsIgnoreCase("APPROVE")) { return APPROVE; }
        else if (option.equalsIgnoreCase("MODERATE")) { return MODERATE; }
        else if (option.equalsIgnoreCase("PUBLISHED")) { return PUBLISHED; }
        else if (option.equalsIgnoreCase("SPAM")) { return SPAM; }
        else if (option.equalsIgnoreCase("ALL")) { return ALL; }
        else return null;
    }    
}

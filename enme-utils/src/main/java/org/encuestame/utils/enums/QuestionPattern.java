/*
 ************************************************************************************
 * Copyright (C) 2008-2012 encuestame: system online surveys Copyright (C) 2012
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
 * Question Patterns Enum.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2012
 */
public enum QuestionPattern {

    /**
     * A simple link to vote.
     */
    LINKS("LINKS"),

    /**
     * The user could select single or multiple based on previous configuration.
     */
    CUSTOMIZABLE_SELECTION("CUSTOMIZABLE_SELECTION"),

    /**
     * Single response selection.
     */
    SINGLE_SELECTION("SINGLE_SELECTION"),
    /**
     * Multiples response selection.
     */
    MULTIPLE_SELECTION("MULTIPLE_SELECTION");

    private String optionAsString;

    QuestionPattern(final String optionAsString) {
        this.optionAsString = optionAsString;

    };

    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == LINKS) {
            type = "LINKS";
        } else if (this == SINGLE_SELECTION) {
            type = "SINGLE_SELECTION";
        } else if (this == MULTIPLE_SELECTION) {
            type = "MULTIPLE_SELECTION";
        } else if (this == CUSTOMIZABLE_SELECTION) {
            type = "CUSTOMIZABLE_SELECTION";
        }
        return type;
    }

    /**
     * Get dojo widget.
     * @return
     */
    public String getWidget() {
        String type = "";
        if (this == LINKS) {
            type = "LINKS";
        } else if (this == SINGLE_SELECTION) {
            type = "me/web/widget/questions/patterns/SingleOptionResponse";
        } else if (this == MULTIPLE_SELECTION) {
            type = "me/web/widget/MultipleOptionResponse";
        } else if (this == CUSTOMIZABLE_SELECTION) {
            type = "me/web/widget/CustomizableSelectionOptionResponse";
        }
        return type;
    }

    /**
     *
     * @param layout
     * @return
     */
    public static QuestionPattern getQuestionPattern(final String type) {
        if (null == type) {
            return null;
        } else if (type.equalsIgnoreCase("LINKS")) {
            return LINKS;
        } else if (type.equalsIgnoreCase("SINGLE_SELECTION")) {
            return SINGLE_SELECTION;
        } else if (type.equalsIgnoreCase("MULTIPLE_SELECTION")) {
            return MULTIPLE_SELECTION;
        } else if (type.equalsIgnoreCase("CUSTOMIZABLE_SELECTION")) {
            return CUSTOMIZABLE_SELECTION;
        } else
            return null;
    }
}

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
package org.encuestame.utils.web.frontEnd;

import java.io.Serializable;

/**
 * WebMessage.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public final class WebMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -7442678263773715275L;

    /**
     * {@link WebInfoType}
     */
    private final WebInfoType infoType;

    /**
     * The error message.
     */
    private final String message;

    /**
     *
     */
    private String description = "";

    /**
     * Define the error level, useful to control the size of error to display.
     */
    private Integer errorLevel;

    /**
     * Define if display the link to bug traking in the ui.
     */
    private Boolean displayErrorBugTracking;

    /**
     *
     * @param type
     * @param text
     */
    public WebMessage(WebInfoType type, String text) {
        this.infoType = type;
        this.message = text;
    }

    /**
     *
     * @param infoType
     * @param message
     * @param description
     * @param errorLevel
     */
    public WebMessage(WebInfoType infoType, String message, String description,
            Integer errorLevel) {
        super();
        this.infoType = infoType;
        this.message = message;
        this.description = description;
        this.errorLevel = errorLevel;
    }


    /**
     * @param infoType
     * @param message
     * @param description
     */
    public WebMessage(WebInfoType infoType, String message, String description) {
        super();
        this.infoType = infoType;
        this.message = message;
        this.description = description;
    }

    /**
     *
     * @param infoType
     * @param message
     * @param description
     * @param errorLevel
     * @param displayErrorBugTracking
     */
    public WebMessage(WebInfoType infoType, String message, String description,
            Integer errorLevel, Boolean displayErrorBugTracking) {
        super();
        this.infoType = infoType;
        this.message = message;
        this.description = description;
        this.errorLevel = errorLevel;
        this.displayErrorBugTracking = displayErrorBugTracking;
    }

    /**
     * @return the infoType
     */
    public WebInfoType getInfoType() {
        return infoType;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WebMessage [infoType=" + infoType + ", message=" + message
                + ", description=" + description + ", errorLevel=" + errorLevel
                + ", displayErrorBugTracking=" + displayErrorBugTracking + "]";
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the errorLevel
     */
    public Integer getErrorLevel() {
        return errorLevel;
    }

    /**
     * @param errorLevel the errorLevel to set
     */
    public void setErrorLevel(Integer errorLevel) {
        this.errorLevel = errorLevel;
    }

    /**
     * @return the displayErrorBugTracking
     */
    public Boolean getDisplayErrorBugTracking() {
        return displayErrorBugTracking;
    }

    /**
     * @param displayErrorBugTracking the displayErrorBugTracking to set
     */
    public void setDisplayErrorBugTracking(Boolean displayErrorBugTracking) {
        this.displayErrorBugTracking = displayErrorBugTracking;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }



    public enum WebInfoType {
        WARNING, ERROR, INFO, SUCCESS;
        private final String css;

        private WebInfoType() {
            css = name().toLowerCase();
        }

        public String getCss() {
            return css;
        }
    }

}
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

/**
 * WebMessage.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public final class WebMessage {

    private final WebInfoType infoType;

    private final String message;

    private String description = "";

    public WebMessage(WebInfoType type, String text) {
        this.infoType = type;
        this.message = text;
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

    public String toString() {
        return infoType + ": " + message;
    }



    /**
     * @return the description
     */
    public String getDescription() {
        return description;
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
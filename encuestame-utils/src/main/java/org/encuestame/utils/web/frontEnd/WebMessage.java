package org.encuestame.utils.web.frontEnd;

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
package org.encuestame.utils.web.frontEnd;

public final class WebMessage {

    private final WebInfoType infoType;

    private final String message;

    public WebMessage(WebInfoType type, String text) {
        this.infoType = type;
        this.message = text;
    }

    public WebInfoType getType() {
        return infoType;
    }

    /**
     * The info text.
     */
    public String getText() {
        return message;
    }

    public String toString() {
        return infoType + ": " + message;
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
package org.encuestame.utils.enums;

public enum EmbeddedType {
    SCRIPT,
    IFRAME,
    WORDPRESS;

    EmbeddedType(){

    };

    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == SCRIPT) { type = "SCRIPT"; }
        else if (this == IFRAME) { type = "IFRAME"; }
        else if (this == WORDPRESS) { type = "WORDPRESS"; }
        return type;
    }

    /**
     *
     * @param type
     * @return
     */
    public static EmbeddedType getEmbeddedType(final String type) {
        if (null == type) { return SCRIPT; }
        else if (type.equalsIgnoreCase("SCRIPT")) { return SCRIPT; }
        else if (type.equalsIgnoreCase("WORDPRESS")) { return WORDPRESS; }
        else if (type.equalsIgnoreCase("IFRAME")) { return IFRAME; }
        return SCRIPT;
    }



}

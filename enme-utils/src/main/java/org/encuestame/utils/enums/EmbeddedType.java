package org.encuestame.utils.enums;

public enum EmbeddedType {
	SCRIPT, IFRAME,
	
	EmbeddedType(){

    };

    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == SCRIPT) { type = "SCRIPT"; }
        else if (this == IFRAME) { type = "IFRAME"; }
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
        else if (type.equalsIgnoreCase("IFRAME")) { return IFRAME; }
		return SCRIPT;
    }



}

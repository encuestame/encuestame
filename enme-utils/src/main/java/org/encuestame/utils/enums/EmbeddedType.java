package org.encuestame.utils.enums;

public enum EmbeddedType {
    SCRIPT("SCRIPT"),
    IFRAME("IFRAME"),
    WORDPRESS("WORDPRESS");

    private String embeddedTypeAsString;

    EmbeddedType(final String embeddedTypeAsString){
        this.embeddedTypeAsString = embeddedTypeAsString;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return this.embeddedTypeAsString;
    }
}

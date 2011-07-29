package org.encuestame.core.util;

/**
 * Picture Type.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2011
 */
public enum PictureType {
    ICON,
    THUMBNAIL,
    DEFAULT,
    PROFILE,
    PREVIEW,
    WEB;

    private PictureType() {
    }

    /**
     * To String.
     */
    public String toString() {
        String pictureSize = "_64";
        if (this == ICON) { pictureSize = "_22"; }
        else if (this == THUMBNAIL) { pictureSize = "_64"; }
        else if (this == DEFAULT) { pictureSize = "_128"; }
        else if (this == PROFILE) { pictureSize = "_256"; }
        else if (this == PREVIEW) { pictureSize = "_375"; }
        else if (this == WEB) { pictureSize = "_900"; }
        return pictureSize;
    }

    /**
     * To integer.
     * @return
     */
    public Integer toInt() {
        Integer pictureSize = 64;
        if (this == ICON) { pictureSize = 22; }
        else if (this == THUMBNAIL) { pictureSize = 64; }
        else if (this == DEFAULT) { pictureSize = 128; }
        else if (this == PROFILE) { pictureSize = 256; }
        else if (this == PREVIEW) { pictureSize = 375; }
        else if (this == WEB) { pictureSize = 900; }
        return pictureSize;
    }
}
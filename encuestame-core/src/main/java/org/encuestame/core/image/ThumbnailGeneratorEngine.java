package org.encuestame.core.image;

import java.io.InputStream;

/**
 * An engine in charge of generating thumbnails for files.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 22, 2011 7:51:44 PM
 * @version $Id:$
 */
public interface ThumbnailGeneratorEngine {

    /**
     * @param fileNamePrefix
     *            the prefix for the generated thumbnails
     * @param inputStream
     *            the stream to generate thumbnails for
     * @param contentType
     *            the content type of this input stream for example image/jpeg
     */
    void generateThumbnails(
            final String fileNamePrefix,
            final InputStream inputStream,
            final String contentType,
            final String thumbnailsLocation);
}

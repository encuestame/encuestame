package org.encuestame.core.image;

import org.apache.log4j.Logger;

import org.encuestame.core.image.ThumbnailGenerator;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Thumbnail Generator Engine.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 22, 2011 7:44:21 PM
 * @version $Id:$
 */
public class ThumbnailGeneratorEngineImpl implements ThumbnailGeneratorEngine {

    private final static Logger log = Logger
            .getLogger(ThumbnailGeneratorEngineImpl.class);

    private String generatedExtension;

    /**
     * @param generatedExtension
     *            The extension for the generated thumbnails
     */
    public void setGeneratedExtension(String generatedExtension) {
        this.generatedExtension = generatedExtension;
    }

    public String getGeneratedExtension() {
        return generatedExtension;
    }

    private Map<String, ThumbnailGenerator> thumbnailGenerators;

    /**
     * @param thumbnailGenerators
     *            The thumbnail generators known by this engine mapped to a
     *            content type
     */
    public void setThumbnailGenerators(
            Map<String, ThumbnailGenerator> thumbnailGenerators) {
        this.thumbnailGenerators = thumbnailGenerators;
    }

    private List<Integer> supportedSizes;

    /**
     * @param supportedSizes
     *            The suported sizes for the batch of generated thumbs
     */
    public void setSupportedSizes(List<Integer> supportedSizes) {
        this.supportedSizes = supportedSizes;
    }

    private ThumbnailGenerator defaultThumbnailGenerator;

    /**
     * @param defaultThumbnailGenerator
     *            the default thumbnail generator to be used for unregistered
     *            mime types
     */
    public void setDefaultThumbnailGenerator(
            ThumbnailGenerator defaultThumbnailGenerator) {
        this.defaultThumbnailGenerator = defaultThumbnailGenerator;
    }

    /**
     * @param fileNamePrefix
     *            the prefix for the generated thumbnails
     * @param inputStream
     *            the stream to generate thumbnails for
     * @param contentType
     *            the content type of this input stream for example image/jpeg
     */
    public void generateThumbnails(final String fileNamePrefix,
            final InputStream inputStream, final String contentType,
            final String thumbnailsLocation) {
        ThumbnailGenerator thumbnailGenerator = thumbnailGenerators.get(contentType);
        thumbnailGenerator = thumbnailGenerator != null ? thumbnailGenerator
                : defaultThumbnailGenerator;
        if (thumbnailGenerator != null) {
            Object hint = null;
            for (int dimension : supportedSizes) {
                File fileOut = new File(thumbnailsLocation, fileNamePrefix
                        + "_" + dimension + generatedExtension);
                try {
                    hint = thumbnailGenerator.createThumbnail(inputStream,
                            fileOut, dimension, hint);
                    log.debug("Generated thumbnail for: " + inputStream
                            + " in " + fileOut + " for type " + contentType);
                } catch (Exception e) {
                    log.error("Error generating thumbnail for: " + inputStream
                            + " in " + fileOut + " for type " + contentType, e);
                }
                log.debug("Created Thumbnail on:"+fileOut.getAbsolutePath());
                log.debug("Created Thumbnail on:"+fileOut.getPath());
            }
        } else {
            log.warn("Thumbnail generator not found for content type: "
                    + contentType + " and no default generator was provided");
        }
    }
}

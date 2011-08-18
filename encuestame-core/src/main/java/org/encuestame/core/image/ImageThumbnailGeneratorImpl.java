package org.encuestame.core.image;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;


/**
 * Generate thumbnails for images.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 22, 2011 7:48:24 PM
 * @version $Id:$
 */
public class ImageThumbnailGeneratorImpl implements ThumbnailGenerator {

    private final static Logger log = Logger.getLogger(ImageThumbnailGeneratorImpl.class);

    /**
     *
     */
    public Object createThumbnail(
                  InputStream inputStream,
                  File fileOut,
                  int largestDimension, Object hint) throws IOException {

        // What's the base image that we are starting with?  If there's a hint, that's the scaled image
        // from the last time around, use that... (since we know we always iterate downwards in scale)
        Image imageIn;
        if (hint instanceof Image) {
            imageIn = (Image) hint;
            log.info("createThumbnail(" + fileOut + ") reusing prior result image...");
        }
        else {
            log.info("createThumbnail(" + fileOut + ") reading image from stream " + inputStream);
            imageIn = ImageIO.read(inputStream);
        }
        if (imageIn == null) {
            log.warn("Could not read image file: " + inputStream);
            return hint;
        }
        BufferedImage imageOut = createThumbnailImage(imageIn, fileOut, largestDimension);
        // Return this image now as the hint for the next scaling iteration
        if (imageOut != null)
            hint = imageOut;

        return hint;
    }


    /**
     * Create a thumbnail image and save it to disk.
     *
     * This algorithm is based on:
     *      http://www.philreeve.com/java_high_quality_thumbnails.php
     *
     * @param imageIn           The image you want to scale.
     * @param fileOut           The output file.
     * @param largestDimension  The largest dimension, so that neither the width nor height
     *                          will exceed this value.
     *
     * @return the image that was created, null if imageIn or fileOut is null.
     * @throws java.io.IOException if something goes wrong when saving as jpeg
     */
    public BufferedImage createThumbnailImage(Image imageIn, File fileOut, int largestDimension) throws IOException {
        if ((imageIn == null) || (fileOut == null)) {
            return null;
        }
        //it seems to not return the right size until the methods get called for the first time
        imageIn.getWidth(null);
        imageIn.getHeight(null);

        // Find biggest dimension
        int     nImageWidth = imageIn.getWidth(null);
        int     nImageHeight = imageIn.getHeight(null);
        int     nImageLargestDim = Math.max(nImageWidth, nImageHeight);
        double  scale = (double) largestDimension / (double) nImageLargestDim;
        int     sizeDifference = nImageLargestDim - largestDimension;

        //create an image buffer to draw to
        BufferedImage imageOut = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); // 8-bit RGB
        Graphics2D g2d;
        AffineTransform tx;

        // Use a few steps if the sizes are drastically different, and only scale
        // if the desired size is smaller than the original.
        int numSteps = 0;
        if (scale < 1.0d) {
            // Make sure we have at least 1 step
            numSteps = Math.max(1, (sizeDifference / 100));
        }

        if (numSteps > 0) {
            int stepSize = sizeDifference / numSteps;
            int stepWeight = stepSize / 2;
            int heavierStepSize = stepSize + stepWeight;
            int lighterStepSize = stepSize - stepWeight;
            int currentStepSize, centerStep;
            double scaledW = imageIn.getWidth(null);
            double scaledH = imageIn.getHeight(null);

            if ((numSteps % 2) == 1) //if there's an odd number of steps
                centerStep = (int) Math.ceil((double) numSteps / 2d); //find the center step
            else
                centerStep = -1; //set it to -1 so it's ignored later

            Integer intermediateSize;
            Integer previousIntermediateSize = nImageLargestDim;

            for (Integer i = 0; i < numSteps; i++) {
                if (i + 1 != centerStep) {
                    //if this isn't the center step

                    if (i == numSteps - 1) {
                        //if this is the last step
                        //fix the stepsize to account for decimal place errors previously
                        currentStepSize = previousIntermediateSize - largestDimension;
                    }
                    else {
                        if (numSteps - i > numSteps / 2) //if we're in the first half of the reductions
                            currentStepSize = heavierStepSize;
                        else
                            currentStepSize = lighterStepSize;
                    }
                }
                else {
                    //center step, use natural step size
                    currentStepSize = stepSize;
                }

                intermediateSize = previousIntermediateSize - currentStepSize;
                scale = intermediateSize / (double) previousIntermediateSize;
                scaledW = Math.max((int)(scaledW * scale), 1);
                scaledH = Math.max((int)(scaledH * scale), 1);

                log.info("step " + i + ": scaling to " + scaledW + " x " + scaledH);
                imageOut = new BufferedImage((int) scaledW, (int) scaledH, BufferedImage.TYPE_INT_RGB); // 8 bit RGB
                g2d = imageOut.createGraphics();
                g2d.setBackground(Color.WHITE);
                g2d.clearRect(0, 0, imageOut.getWidth(), imageOut .getHeight());
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                tx = new AffineTransform();
                tx.scale(scale, scale);
                g2d.drawImage(imageIn, tx, null);
                g2d.dispose();
                imageIn = new ImageIcon(imageOut).getImage();
                previousIntermediateSize = intermediateSize;
            }
        }
        else {
            // This enforces a rule that we always have an 8-bit image with white background for the thumbnail.  Plus, for large
            // images, this makes subsequent downscaling really fast because we are working on a large 8-bit image
            // instead of a large 12 or 24 bit image, so the downstream effect is very noticable.
            imageOut = new BufferedImage(imageIn.getWidth(null), imageIn.getHeight(null), BufferedImage.TYPE_INT_RGB);
            g2d = imageOut.createGraphics();
            g2d.setBackground(Color.WHITE);
            g2d.clearRect(0, 0, imageOut.getWidth(), imageOut.getHeight());
            tx = new AffineTransform();
            tx.setToIdentity(); //use identity matrix so image is copied exactly
            g2d.drawImage(imageIn, tx, null);
            g2d.dispose();
        }
        //saveImageAsJPEG(imageOut, fileOut);
        ImageIO.write(imageOut, "jpg", fileOut);
        return imageOut;
    }

}

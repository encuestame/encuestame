package org.encuestame.utils;

import org.encuestame.utils.exception.EnMeGenericException;
import org.junit.Test;

import junit.framework.TestCase;

public class TestPictureUtils extends TestCase{

    @Test
    public void testColor() throws EnMeGenericException{
        assertNotNull(PictureUtils.generateRGBRandomColor());
        assertNotNull(PictureUtils.generateRGBRandomColor().getRGB());
        assertNotNull(PictureUtils.getRandomHexColor());
        assertNotNull(PictureUtils.getGravatar("admin@admin.com", 40));
        assertNotNull(PictureUtils.downloadGravatar("admin@admin.com", 40));
    }

}

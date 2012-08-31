/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils;

import junit.framework.TestCase;

import org.encuestame.utils.categories.test.InternetTest;
import org.encuestame.utils.exception.EnMeGenericException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test for {@link PictureUtils}.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
@Category(InternetTest.class)
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

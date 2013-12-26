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
package org.encuestame.core.image;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;


/**
 * Interface implemented by all thumbnail generators.
 * <p>The thumbnail generation process is always performed in order with the largest thumbnails first.
 * Therefore it may be very handy for the generator to create smaller thumbnails using the results from
 * the prior iteration instead of always using the full-sized image as the source.  To accomplish this,
 * the generator can return a "hint" object that it can use in subsequent iterations, containing whatever
 * helpers it might want (such as the prior image already loaded in memory, etc).
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 22, 2011 7:47:55 PM
 * @version $Id:$
 */
public interface ThumbnailGenerator {

    /**
     * Create the thumbnail.  The thumbnail should always save as a JPEG file.
     *
     * @param inputStream       The source data.
     * @param fileOut           The output file.
     * @param largestDimension  The max width and height.  The generator should size the thumbnail so
     *                          that the width and height both stay within this limit.
     * @param hint              Optional hint that was returned from the prior thumbnail generation
     *                          on this same file, null if none was returned or if this is the first
     *                          thumbnail in this context.
     *
     * @return an optional hint object that will be passed to subsequent thumbnail generation calls
     *         for this same source data.  Return null if you don't use hints, otherwise return some
     *         object which allows you to communicate extra information to the next round, such as
     *         the scaled image already loaded.
     * @throws java.io.IOException if something goes wrong handling the io
     */
    Object createThumbnail(final InputStream inputStream,
            final File fileOut,
            int largestDimension,
            final Object hint) throws IOException;

}

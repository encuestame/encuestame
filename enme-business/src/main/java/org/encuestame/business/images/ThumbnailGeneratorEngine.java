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
package org.encuestame.business.images;

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

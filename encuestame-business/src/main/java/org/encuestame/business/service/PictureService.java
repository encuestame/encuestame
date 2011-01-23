/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.IPictureService;

/**
 * Picture / Image Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 16, 2011 4:12:19 PM
 * @version $Id:$
 */
public class PictureService extends AbstractBaseService implements IPictureService{

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Picure Path.
     */
    private String picturePath;

    /**
     * @return the picturePath
     */
    public String getPicturePath() {
        return picturePath;
    }

    /**
     * Get Profile Picture.
     * @param id
     * @param username
     * @param pictureType
     * @return
     * @throws IOException
     */
    public byte[] getProfilePicture(
            final String id,
            final String username,
            final PictureType pictureType) throws IOException{
        final String url = getAccountUserPicturePath(username);
        log.debug("getProfileURl "+url);
        final File file = new File(url + "3261353607_3bf3a23053_o.jpg");
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
     // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        log.debug("getProfileURl "+bytes);
        return bytes;

    }

    /**
     * Return real path folder for user account.
     * @return
     */
    public String getAccountUserPicturePath(final String username){
        //TODO: should be real user account path.
        return this.getPicturePath();
    }

    /**
     * @param picturePath the picturePath to set
     */
    public void setPicturePath(final String picturePath) {
        this.picturePath = picturePath;
    }

    public enum PictureType {
        THUMBNAIL, DEFAULT, PREVIEW, WEB;
    }
}
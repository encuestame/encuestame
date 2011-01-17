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

import org.encuestame.business.service.imp.IPictureService;

/**
 * Picture / Image Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 16, 2011 4:12:19 PM
 * @version $Id:$
 */
public class PictureService extends AbstractBaseService implements IPictureService{

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
     */
    public byte[] getProfilePicture(
            final String id,
            final String username,
            final PictureType pictureType){
        final String url = getAccountUserPicturePath(username);
        final File file = new File(url + "3261353607_3bf3a23053_o.jpg");

        return null;

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
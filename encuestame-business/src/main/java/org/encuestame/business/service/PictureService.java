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
import org.encuestame.business.setup.DirectorySetupOperations;
import org.encuestame.core.files.PathUtil;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.UserAccount.PictureSource;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.PictureUtils;
import org.encuestame.utils.exception.EnMeGenericException;
import org.springframework.stereotype.Service;

import com.sun.java.swing.plaf.gtk.GTKConstants.WidgetType;

/**
 * Picture / Image Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 16, 2011 4:12:19 PM
 * @version $Id:$
 */
@Service
public class PictureService extends AbstractBaseService implements IPictureService{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());



    /**
     * Create Picture Path.
     * @param account
     */
    private String getPicturePath(final Account account){
        final StringBuilder d = new StringBuilder(DirectorySetupOperations.getProfilesDirectory(account.getUid().toString()));
        d.append(PathUtil.profile);
        final File profileDir =  new File(d.toString());
        if (!profileDir.exists()) {
            profileDir.mkdirs();
        }
        return d.toString();
    }

    /**
     * Return a gravatar picture url.
     * @param email
     * @param size
     * @return
     * @throws EnMeGenericException
     */
    private byte[] getGravatarPicture(final String email, final PictureType size) throws EnMeGenericException {
        log.debug("getGravatarPicture "+size);
        log.debug("getGravatarPicture "+email);
        return PictureUtils.downloadGravatar(email, size.toInt());
    }

    /**
     *
     * @param size
     * @param account
     * @return
     * @throws IOException
     */
    private byte[] getProfilePicture(final PictureType size, final UserAccount account) throws IOException{
        final StringBuilder url = new StringBuilder(this.getPicturePath(account.getAccount()));
        url.append("/file");
        url.append(size.toInt().toString());
        url.append(".jpg");
        log.debug("getProfileURl "+url);
        final File file = new File(url.toString());
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            log.error("File is too large");
            //TODO: add customize exception.
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
     * Get Profile Picture.
     * @param id
     * @param username
     * @param pictureType
     * @return
     * @throws IOException
     * @throws EnMeGenericException
     */
    public byte[] getProfilePicture(
            final String username,
            final PictureType pictureType) throws IOException, EnMeGenericException {
        log.debug("getProfilePicture "+username);
        log.debug("getProfilePicture "+pictureType.toString());
        final UserAccount user = getUserAccount(username);
        if (user.getPictureSource().equals(PictureSource.UPLOADED)) {
            return this.getProfilePicture(pictureType, user);
        } else if (user.getPictureSource().equals(PictureSource.GRAVATAR)) {
            return this.getGravatarPicture(user.getUserEmail(), pictureType);
        } else {
            return this.getGravatarPicture(user.getUserEmail(), pictureType);
        }
    }


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
}

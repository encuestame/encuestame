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
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.DirectorySetupOperations;
import org.encuestame.core.service.imp.IPictureService;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.UserAccount.PictureSource;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.PictureUtils;
import org.encuestame.utils.enums.PictureType;
import org.encuestame.utils.exception.EnMeGenericException;
import org.springframework.stereotype.Service;

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
     * @param account Account
     * @throws EnmeFailOperation
     */
    public String getPicturePath(final UserAccount account) throws EnmeFailOperation {
        final StringBuilder profilesPicturePath = new StringBuilder();
        if (account != null) {
            profilesPicturePath.append(getAccountUserPicturePath(account));
        }
        return profilesPicturePath.toString();
    }

    /**
     * Return a gravatar picture url.
     * @param email
     * @param size
     * @return
     * @throws EnMeGenericException
     */
    private byte[] getGravatarPicture(final String email, final PictureType size) throws EnMeGenericException {
        log.debug("getGravatarPicture:{ "+size);
        log.debug("getGravatarPicture:{ "+email);
        return PictureUtils.downloadGravatar(email, size.toInt());
    }

    /**
     *
     * @param userAccount
     * @return
     * @throws EnmeFailOperation
     */
    public String getAccountUserPicturePath(final UserAccount userAccount) throws EnmeFailOperation{
        final StringBuffer user = new StringBuffer(DirectorySetupOperations.getPictureDirectory());
        user.append("/");
        user.append(userAccount.getUid());
        user.append("/");
        log.debug("getAccountUserPicturePath:{"+user.toString());
        return user.toString();
    }

    /**
     *
     * @param size
     * @param account
     * @return
     * @throws IOException
     * @throws EnmeFailOperation
     */
    private byte[] getProfilePicture(final PictureType size, final UserAccount account) throws IOException, EnmeFailOperation{
        final StringBuilder url = new StringBuilder(this.getPicturePath(account));
        url.append("/");
        url.append(PathUtil.DEFAUL_PICTURE_PREFIX);
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
    public byte[] getProfilePicture(final String username, final PictureType pictureType) throws IOException, EnMeGenericException {
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
}

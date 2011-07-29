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
package org.encuestame.mvc.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.encuestame.core.util.PictureType;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.exception.EnMeGenericException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Render Image.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 16, 2011 3:56:50 PM
 * @version Id:
 */
@Controller
public class PictureProfileFactoryController extends AbstractBaseOperations {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());


    /**
     *
     * @param username
     * @param pictureType
     * @return
     */
    private byte[] getPicture(String username, final PictureType pictureType){
        byte[] bytes = {};
        username = filterValue(username);
        try {
            bytes = getPictureService().getProfilePicture(username, pictureType);
        } catch (FileNotFoundException e) {
            log.error("file not found "+e);
        } catch (IOException e) {
            log.error("IOException "+e);
        } catch (EnMeGenericException e) {
            log.error("EnMeGenericException "+e);
        }
        return bytes;
    }

    /**
     * Returns the byte[] that contains the requested thumbnail image (128x128 constrained).
     * @return A byte[] that contains the requested image
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/thumbnail", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureThumbnail(
            @PathVariable String username){
        return this.getPicture(username, PictureType.THUMBNAIL);
    }

    /**
     * Returns the byte[] that contains the requested default image.
     * @return A byte[] that contains the requested image
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/default", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureMaster(
            @PathVariable String username ) {
        return this.getPicture(username, PictureType.DEFAULT);
    }

    /**
     * Returns the byte[] that contains the requested preview image (256x256 constrained)
     * @param username
     * @return
     */
    @RequestMapping( value = "/picture/profile/{username}/profile", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureProfile(
            @PathVariable String username ) {
        return this.getPicture(username, PictureType.PROFILE);
    }

    /**
     * Returns the byte[] that contains the requested preview image (375x375 constrained)
     * @return A byte[] that contains the requested image
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/preview", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPicturePreview(
            @PathVariable String username ){
          return this.getPicture(username, PictureType.PREVIEW);
    }

    /**
     * Returns the byte[] that contains the requested preview image (900x900 constrained)
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/web", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPictureWeb(
            @PathVariable String username ){
          return this.getPicture(username, PictureType.WEB);
    }
}

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

import org.encuestame.business.service.PictureService.PictureType;
import org.encuestame.mvc.controller.BaseController;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class PictureProfileFactoryController extends BaseController {

    /**
     * Returns the byte[] that contains the requested thumbnail image (128x128 constrained).
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     * @throws EnMeDomainNotFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/thumbnail", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureThumbnail(
            @PathVariable String username ) throws EnMeDomainNotFoundException{
        byte[] bytes = {};
        try {
            bytes = getPictureService().getProfilePicture(username, PictureType.THUMBNAIL);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Returns the byte[] that contains the requested master image.
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     * @throws EnMeDomainNotFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/default", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureMaster(
            @PathVariable String username ) throws EnMeDomainNotFoundException{
        byte[] bytes = {};
        try {
            bytes = getPictureService().getProfilePicture(username, PictureType.DEFAULT);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Returns the byte[] that contains the requested preview image (800x800 constrained)
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     * @throws EnMeDomainNotFoundException
     */
    @RequestMapping( value = "/picture/profile/{username}/preview", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPicturePreview(
            @PathVariable String username ) throws EnMeDomainNotFoundException{
        byte[] bytes = {};
        try {
            bytes = getPictureService().getProfilePicture(username,  PictureType.PREVIEW);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }
}

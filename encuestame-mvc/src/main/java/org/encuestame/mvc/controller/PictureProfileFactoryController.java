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
package org.encuestame.mvc.controller;

import org.encuestame.business.service.PictureService.PictureType;
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
public class PictureProfileFactoryController extends BaseController {

    /**
     * Returns the byte[] that contains the requested thumbnail image (128x128 constrained).
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     */
    @RequestMapping( value = "/user/picture/{username}/thumbnail/{id}", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureThumbnail(
            @PathVariable String id,
            @PathVariable String username ){
        return getPictureService().getProfilePicture(id, username, PictureType.THUMBNAIL);
    }

    /**
     * Returns the byte[] that contains the requested master image.
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     */
    @RequestMapping( value = "/user/picture/{username}/default/{id}", method = RequestMethod.GET )
    @ResponseBody
    public byte[] getPictureMaster(
            @PathVariable String id,
            @PathVariable String username ){
        return getPictureService().getProfilePicture(id, username, PictureType.DEFAULT);
    }

    /**
     * Returns the byte[] that contains the requested preview image (800x800 constrained)
     * @param id The identifier of the image
     * @return A byte[] that contains the requested image
     */
    @RequestMapping( value = "/user/picture/{username}/preview/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPicturePreview(
            @PathVariable String id,
            @PathVariable String username ){
        return getPictureService().getProfilePicture(id, username,  PictureType.PREVIEW);
    }

    /**
     * Returns the byte[] that contains the requested web image (500x500 constrained)
     * @param id  The identifier of the image
     * @return A byte[] that contains the requested image
     */
    @RequestMapping( value = "/user/picture/{username}/web/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPictureWeb(
            @PathVariable String id,
            @PathVariable String username  ){
        return getPictureService().getProfilePicture(id, username, PictureType.WEB);
    }

}

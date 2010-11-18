/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.json.location;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.utils.web.UnitLocationFolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Location Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 14, 2010 7:23:31 PM
 * @version $Id:$
 */
@Controller
public class JsonLocation extends AbstractJsonController {

    /**
     * Get Root Folders.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/admon/location/folders.json", method = RequestMethod.GET)
    public ModelMap get(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final List<UnitLocationFolder> items1 = getLocationService().retrieveLocationFolderByUser(getUserPrincipalUsername());
                setItemReadStoreResponse("name", "id", items1);
            } catch (EnMeDomainNotFoundException e) {
                setError(e.getMessage(), response);
                e.printStackTrace();
                log.error(e);
            }
        return returnData();
    }

}

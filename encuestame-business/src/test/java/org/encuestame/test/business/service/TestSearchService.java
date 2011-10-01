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
package org.encuestame.test.business.service;

import java.io.File;
import java.io.IOException;

import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.junit.Test;

/**
 * Lucene Search service test case.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class TestSearchService extends AbstractServiceBase {


    private String filepathUpload = "/home/dmorales/encuestame/profiles/1/Pro Apache Log4j.pdf";

    @Test
    public void testAddAttachment() throws IOException{
        File file = new File(filepathUpload);
        log.debug("Getting filename -->"+ file.getName());
        log.debug("Getting "+file.getCanonicalPath());
        //final UnitAttachment unitAttachment = createUnitAttachment(filename, uploadDate, projectBean);

    }



}

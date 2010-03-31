/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans.location;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.web.beans.MasterBean;
/**
 * Tree Node Test.
 * @author Picado, Juan juan@encuestame.org
 * @since  26/05/2009 12:39:45
 * @version $Id$
 */
public class TreeNodeTest extends MasterBean {

    private static String SRC_PATH = "/WEB-INF/classes";
    private FileSystemNode[] srcRoots;
    private Log log = LogFactory.getLog(this.getClass());

    public final synchronized FileSystemNode[] getSourceRoots() {
        if (srcRoots == null) {
            srcRoots = new FileSystemNode(SRC_PATH).getNodes();
            log.info("srcRoots->"+srcRoots);
        }

        return srcRoots;
    }
}

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
package org.encuestame.business.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.encuestame.core.search.DirectoryIndexStore;

/**
 * Index ram directory.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 8, 2011
 */
public class IndexRAMDirectory implements DirectoryIndexStore {

    /** Log. **/
    private static final Log log = LogFactory.getLog(IndexRAMDirectory.class);

    /** {@link Directory}. **/
     private Directory directory;

     /**
      *
      */
     public IndexRAMDirectory() {
        this.directory =  new RAMDirectory();
     }

    /* (non-Javadoc)
    * @see org.encuestame.business.service.imp.IDirectory#getDirectory()
    */
    public Directory getDirectory() {
        // TODO Auto-generated method stub
        return directory;
    }

}

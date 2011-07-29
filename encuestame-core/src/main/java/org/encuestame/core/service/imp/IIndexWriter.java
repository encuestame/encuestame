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
package org.encuestame.core.service.imp;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

/**
 * Class description.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 7, 2011
 */
public interface IIndexWriter {

    /**
    * Open index writer.
    * @throws IOException
    */
    void openIndexWriter() throws IOException;

    /**
     * Close index writer.
     * @throws CorruptIndexException
     * @throws IOException
     */
    void closeIndexWriter() throws CorruptIndexException, IOException;


    /**
     *
     * @return
     */
    IndexWriter getIndexWriter() ;
}

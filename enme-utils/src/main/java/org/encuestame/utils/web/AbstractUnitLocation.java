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
package org.encuestame.utils.web;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since May 19, 2010 8:38:47 PM
 * @version Id:
 */
public abstract class AbstractUnitLocation {

     private String name;

     private Long id;

     /**
      * @return the name
      */
     public String getName() {
         return name;
     }

     /**
      * @param name the name to set
      */
     public void setName(final String name) {
         this.name = name;
     }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}

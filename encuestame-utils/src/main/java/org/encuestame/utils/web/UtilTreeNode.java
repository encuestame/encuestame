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

import java.io.Serializable;

/**
 * Unit Tree Node.
 * @author Picado, Juan juanATencuestame.org
 * @since May 20, 2010 9:33:58 PM
 * @version $Id:$
 */
public class UtilTreeNode implements Serializable{

    /** **/
    private static final long serialVersionUID = 4114757339131583646L;

    private Long id;

    private TypeTreeNode node;

    private String name;

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

    /**
     * @return the node
     */
    public TypeTreeNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(TypeTreeNode node) {
        this.node = node;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
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

import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * File System Node.
 * @author Picado, Juan juan@encuestame.org
 * @since  26/05/2009 13:56:14
 * @version $Id$
 **/
public class FileSystemNode {
    private String path;
    private static FileSystemNode[] CHILDREN_ABSENT = new FileSystemNode[0];
    private FileSystemNode[] children;
    private String shortPath;
    //private Log log = LogFactory.getLog(this.getClass());

    public FileSystemNode(String path) {
        this.path = path;
        int idx = path.lastIndexOf('/');
        if (idx != -1) {
            //log.info("1this.idx->"+idx);
            shortPath = path.substring(idx + 1);
        } else {
            //log.info("2this.path->"+path);
            shortPath = path;
        }
        //log.info("this.shortPath->"+shortPath);
    }

    public final synchronized FileSystemNode[] getNodes() {
        if (children == null) {
            //log.info("this.path->"+this.path);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            //log.info("externalContext->"
            //		+ externalContext.getResourcePaths(this.path));
            Set resourcePaths = externalContext.getResourcePaths(this.path);
            //log.info("resourcePaths->" + resourcePaths);
            if (resourcePaths != null) {
                Object[] nodes = (Object[]) resourcePaths.toArray();
                children = new FileSystemNode[nodes.length];
                for (int i = 0; i < nodes.length; i++) {
                    String nodePath = nodes[i].toString();
                    //.info("nodePath->" + nodePath);
                    if (nodePath.endsWith("/")) {
                        nodePath = nodePath.substring(0, nodePath.length() - 1);
                    }
                    children[i] = new FileSystemNode(nodePath);
                }
            } else {
                children = CHILDREN_ABSENT;
            }
        }
        return children;
    }

    public final String toString() {
        return shortPath;
    }
}

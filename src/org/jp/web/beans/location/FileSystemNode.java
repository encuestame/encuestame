package org.jp.web.beans.location;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: FileSystemNode.java Date: 26/05/2009 13:56:14
 * @author juanpicado
 * package: org.jp.web.beans.location
 * @version 1.0
 */
import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	public synchronized FileSystemNode[] getNodes() {
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

	public String toString() {
		return shortPath;
	}
}
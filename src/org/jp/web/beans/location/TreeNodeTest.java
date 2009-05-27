package org.jp.web.beans.location;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;
import org.richfaces.model.TreeNode;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: TreeNodeTest.java Date: 26/05/2009 12:39:45
 * 
 * @author juanpicado package: org.jp.web.beans.territory
 * @version 1.0
 */
public class TreeNodeTest extends MasterBean {

	private static String SRC_PATH = "/WEB-INF/classes";
	private FileSystemNode[] srcRoots;
	private Log log = LogFactory.getLog(this.getClass());
	
	public synchronized FileSystemNode[] getSourceRoots() {
		if (srcRoots == null) {
			srcRoots = new FileSystemNode(SRC_PATH).getNodes();
			log.info("srcRoots->"+srcRoots);
		}
		
		return srcRoots;
	}
}

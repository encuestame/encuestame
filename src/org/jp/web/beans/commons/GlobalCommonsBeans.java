package org.jp.web.beans.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.pojo.CatState;
import org.jp.web.beans.MasterBean;
import org.jp.web.beans.admon.UnitPermission;

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
 * Id: GlobalCommonsBeans.java Date: 11/05/2009 16:39:17
 * 
 * @author juanpicado package: org.jp.web.beans.commons
 * @version 1.0
 */

public class GlobalCommonsBeans extends MasterBean {

	private Collection lista = null;
	private List<SelectItem> select = null;
	private Log log = LogFactory.getLog(this.getClass());

	public GlobalCommonsBeans() {
		log.info("init GlobalCommonsBeans");

	}

	/**
	 * load selectItem state
	 * 
	 * @return
	 */
	public List<SelectItem> getLoadListState() {
		select = new ArrayList<SelectItem>();
		log.info("get load list state");
		select.add(new SelectItem(null, ""));
		lista = getServicemanagerBean().getDataService().getStateDao()
				.findAll();
		log.info("get load list state total->" + lista.size());
		if (lista != null && lista.size() != 0) {
			Iterator iterd = lista.iterator();
			while (iterd.hasNext()) {
				CatState state = (CatState) iterd.next();
				select.add(new SelectItem(state.getIdState(), state
						.getDescState()));
			}
		}
		log.info("state select->" + select);
		return select;
	}

	public List<SelectItem> getLoadListPermissions() {
		select = new LinkedList<SelectItem>();
		select.add(new SelectItem(null, ""));
		lista = getServicemanagerBean().getSecurityService()
				.loadAllListPermission();
		if (lista != null && lista.size() != 0) {
			Iterator iterd = lista.iterator();
			while (iterd.hasNext()) {
				UnitPermission permission = (UnitPermission) iterd.next();
				select.add(new SelectItem(permission.getId(), permission
						.getDescription()));
			}
		}
		log.info("state select->" + select);
		return select;
	}

}

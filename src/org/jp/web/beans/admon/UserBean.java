package org.jp.web.beans.admon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.component.UIData;
import javax.faces.component.UISelectBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;

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
 * Id: UserBean.java Date: 11/05/2009 13:52:28
 * 
 * @author juanpicado package: org.jp.web.beans.admon
 * @version 1.0
 */
public class UserBean extends MasterBean {

	private UnitUserBean unitUserBean;
	private Collection<UnitUserBean> list_unitBeans;
	private Log log = LogFactory.getLog(this.getClass());
	private Integer processedUserId;
	private Integer selectedPermissionId;
	private String selectedAction;
	private UIData uiDataUserTable;
	private UISelectBoolean addItem;

	/**
	 * @return the list_unitBeans
	 * @throws Exception
	 */
	public Collection<UnitUserBean> loadListUsers() throws Exception {
		list_unitBeans = new LinkedList<UnitUserBean>();
		return list_unitBeans = getServicemanagerBean().getSecurityService()
				.loadListUsers();
	}

	/**
	 * @param list_unitBeans
	 *            the list_unitBeans to set
	 */
	public void setList_unitBeans(Collection<UnitUserBean> list_unitBeans) {
		this.list_unitBeans = list_unitBeans;
	}

	/**
	 * get list_unit_beans
	 * 
	 * @return
	 */
	public Collection<UnitUserBean> getList_unitBeans() {
		try {
			loadListUsers();
			if (list_unitBeans.size() > 0)
				setOneRow(true);
			else
				setOneRow(false);
			return list_unitBeans;
		} catch (Exception e) {
			addErrorMessage("Error Cargando Datos->" + e.getMessage(), e
					.getMessage());
			return null;
		}
	}

	public void assingPermissions() {
		try {
			log.info("CHECK->" + getAddItem());
			log.info("uiDataUserTable->" + uiDataUserTable.getChildren());
			log.info("uiDataUserTable row count->" + uiDataUserTable.getRowCount());
			log.info("1SELETED childCount//->"
					+ uiDataUserTable.getChildCount());
			// log.info("2SELETED total//->" + uiDataUserTable.getRowData());
			ArrayList selected = new ArrayList();
			int first = uiDataUserTable.getFirst();
			log.info("1SELETED first//->" + first);
			int rows = uiDataUserTable.getRows();
			log.info("2SELETED//->" + rows);
			for (int i = first; i < (first + rows); i++) {
				this.uiDataUserTable.setRowIndex(i);
				if (this.addItem.isSelected()) {
					log.info("3SELETED//->" + this.uiDataUserTable);
					selected.add(this.uiDataUserTable.getRowData());
				}
			}
		} catch (Exception e) {
			log.error("ERROR->" + e.getMessage());
			addErrorMessage(e.getMessage(), e.getMessage());
		}
	}

	/**
	 * @return the isOneRow
	 */
	public boolean isOneRow() {
		return isOneRow;
	}

	/**
	 * @param isOneRow
	 *            the isOneRow to set
	 */
	public void setOneRow(boolean isOneRow) {
		this.isOneRow = isOneRow;
	}

	public void setUnitUserBean(UnitUserBean unitUserBean) {
		this.unitUserBean = unitUserBean;
	}

	public Integer getProcessedUserId() {
		return processedUserId;
	}

	public void setProcessedUserId(Integer processedUserId) {
		this.processedUserId = processedUserId;
	}

	/**
	 * @return the selectedPermissionId
	 */
	public Integer getSelectedPermissionId() {
		return selectedPermissionId;
	}

	/**
	 * @param selectedPermissionId
	 *            the selectedPermissionId to set
	 */
	public void setSelectedPermissionId(Integer selectedPermissionId) {
		this.selectedPermissionId = selectedPermissionId;
	}

	/**
	 * @return the selectedAction
	 */
	public String getSelectedAction() {
		return selectedAction;
	}

	/**
	 * @param selectedAction
	 *            the selectedAction to set
	 */
	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

	/**
	 * @return the uiDataUserTable
	 */
	public UIData getUiDataUserTable() {
		log.info("DATA TABLE-?" + uiDataUserTable);
		return uiDataUserTable;
	}

	/**
	 * @param uiDataUserTable
	 *            the uiDataUserTable to set
	 */
	public void setUiDataUserTable(UIData uiDataUserTable) {
		this.uiDataUserTable = uiDataUserTable;
	}

	/**
	 * @return the addItem
	 */
	public UISelectBoolean getAddItem() {
		return addItem;
	}

	/**
	 * @param addItem
	 *            the addItem to set
	 */
	public void setAddItem(UISelectBoolean addItem) {
		this.addItem = addItem;
	}

}

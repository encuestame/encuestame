package org.jp.web.beans.admon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.faces.component.UIData;
import javax.faces.component.UISelectBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.pojo.SecPermission;
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
	private UnitUserBean newUnitUserBean = new UnitUserBean();
	private Collection<UnitUserBean> list_unitBeans;
	private Log log = LogFactory.getLog(this.getClass());
	private String processedUserId;
	private Integer selectedPermissionId;
	private String selectedAction;
	private UIData uiDataUserTable;
	private UISelectBoolean checked = null;

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
	 * create user
	 */
	public void createUser() {
		try {
			getServicemanagerBean().getSecurityService().createUser(
					getNewUnitUserBean());
			addInfoMessage("Usuario Creado Tuani", "");
		} catch (Exception e) {
			addErrorMessage("Error creando usuario->" + e, e.getMessage());
		}
	}

	/**
	 * invite user
	 */
	public void inviteUser() {

	}

	/**
	 * search LDAP user
	 */
	public void searchLDAPUser() {

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
		log.info(selectedUsers());
	}

	/**
	 * get list of user select in datatable
	 * 
	 * @return
	 */
	private Collection<UnitUserBean> selectedUsers() {
		Collection<UnitUserBean> listSelectedUsers = new LinkedList<UnitUserBean>();
		try {
			int n = uiDataUserTable.getRowCount();
			for (int i = 0; i < n; i++) {
				uiDataUserTable.setRowIndex(i);
				if (checked.isSelected()) {
					UnitUserBean userUnit = (UnitUserBean) uiDataUserTable
							.getRowData();
					listSelectedUsers.add(userUnit);
				}
			}
			return listSelectedUsers;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e.getMessage());
			log.error("ERROR->" + e.getMessage());
			return listSelectedUsers;
		}
	}

	/**
	 * delete user
	 * 
	 * @param user
	 */
	private void deleteUser(UnitUserBean user) {
		try {
			getServicemanagerBean().getSecurityService().deleteUser(user);
			log.info("Se borro bien->" + user.getUsername());
			addInfoMessage("Se borro bien->" + user.getUsername(), "");
		} catch (Exception e) {
			log.info("No borro bien->" + user.getUsername());
			addErrorMessage("No Se borro ->" + user.getUsername() + "por->"
					+ e.getMessage(), "");
		}

	}

	/**
	 * init action
	 */
	public void initAction() {
		log.info("init action->" + getSelectedAction());
		try {
			if (getSelectedAction() != null) {
				switch (new Integer(getSelectedAction())) {
				case 1:
					for (Iterator<UnitUserBean> i = selectedUsers().iterator(); i
							.hasNext();) {
						UnitUserBean user = i.next();
						log.info("delete action->" + user.getUsername());
						deleteUser(user);
					}
					break;
				case 2:
					log.info("action 2" + selectedUsers());
					break;
				case 3:
					log.info("action 3" + selectedUsers());
					break;

				default:
					addErrorMessage("Acción Invalida", "");
					log.error("invalid action -" + getSelectedAction());
					break;
				}

			} else {
				addInfoMessage("Seleccione una Acción", "");
				log.info("init action->" + getSelectedAction());
			}
		} catch (Exception e) {
			addErrorMessage("Error en la Acción", e.getMessage());
			log.error("Error en la Acción -" + e.getMessage());
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

	public String getProcessedUserId() {
		return processedUserId;
	}

	public void setProcessedUserId(String processedUserId) {
		log.info("setProcessedUserId->" + processedUserId);
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

	public UISelectBoolean getChecked() {
		return checked;
	}

	public void setChecked(UISelectBoolean checked) {
		this.checked = checked;
	}

	/**
	 * get unit user bean
	 * 
	 * @return
	 */
	public UnitUserBean getUnitUserBean() {
		try {
			unitUserBean = null;
			if (getProcessedUserId() != null) {
				unitUserBean = getServicemanagerBean().getSecurityService()
						.searchUserByUsername(getProcessedUserId());
				log.info("Selected Users->" + unitUserBean);
			}
		} catch (Exception e) {
			addErrorMessage("Error Cargando Datos Usuario"
					+ getProcessedUserId(), "");
			log.error("Error Cargando Datoss Usuario " + e.getMessage());
			return null;
		}
		return unitUserBean;
	}

	public UnitUserBean getNewUnitUserBean() {
		return newUnitUserBean;
	}

	public void setNewUnitUserBean(UnitUserBean newUnitUserBean) {
		this.newUnitUserBean = newUnitUserBean;
	}

}

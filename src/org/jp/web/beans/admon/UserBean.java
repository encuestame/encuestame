package org.jp.web.beans.admon;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIData;
import javax.faces.component.UISelectBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jp.core.exception.EnMeExpcetion;
import org.jp.core.security.util.EmailUtils;
import org.jp.web.beans.MasterBean;
import org.springframework.mail.MailSendException;

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
	private UISelectBoolean checked = new UISelectBoolean();
	private String listUsers;

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
		} catch (MailSendException e) {
			addErrorMessage("Error notifiando usuario->" + e, e.getMessage());
		} catch (HibernateException e) {
			addErrorMessage("Error HB creando usuario->" + e, e.getMessage());
		} catch (EnMeExpcetion e) {
			addErrorMessage(
					"No se pudo recuperar información de configuración->" + e,
					e.getMessage());
		} catch (Exception e) {
			addErrorMessage("Error Desconocido crear usuario->" + e, e
					.getMessage());
		}
	}

	/**
	 * update user
	 */
	public void updateUser() {
		try {
			getServicemanagerBean().getSecurityService().updateUser(
					this.unitUserBean);
		} catch (HibernateException e) {
			addErrorMessage("Error HibernateException update User"
					+ e.getMessage(), e.getMessage());
			log.error("Error HibernateException User->" + e);
		} catch (Exception e) {
			addErrorMessage("Error Desconocido update User" + e.getMessage(), e
					.getMessage());
			log.error("Error Exception User->" + e);
		}
	}

	/**
	 * invite user
	 * 
	 * @throws Exception
	 */
	public void inviteUser() {

		if (!getListUsers().isEmpty()) {
			List<String> emails = new LinkedList<String>();
			String strDatos = getListUsers().trim();
			StringTokenizer tokens = new StringTokenizer(strDatos, ",");
			int i = 0;
			while (tokens.hasMoreTokens()) {
				String str = tokens.nextToken();
				emails.add(str.trim());
				i++;
			}
			if (emails.size() > 0) {
				Iterator<String> it = emails.iterator();
				while (it.hasNext()) {
					String email = (String) it.next();
					if (EmailUtils.validateEmail(email)) {
						try {
							String code = getServicemanagerBean()
									.getSecurityService()
									.generateHashCodeInvitation();
							getServicemanagerBean().getSecurityService()
									.inviteUser(email, code);
							addInfoMessage("Invitacion enviada para " + email
									+ " Satisfactoriamente", "");
						} catch (Exception e) {
							addErrorMessage(
									"Lo siento,ocurrio un error al enviar este correo->"
											+ email + " error->" + e, e
											.getMessage());
						}
					} else {
						log.info("email invalido ->" + email);
						addWarningMessage("email invalido ->" + email, "");
					}
				}
				setListUsers(null);
			} else {
				addWarningMessage("Lo siento, la lista esta vacia", "");
			}
		} else {
			addWarningMessage("Lo siento, la lista esta vacia", "");
		}

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
		try {
			log.info(selectedUsers());
			if (selectedUsers().size() > 0) {
				if (getSelectedPermissionId() != null) {
					UnitPermission permission = new UnitPermission();
					permission.setId(getSelectedPermissionId());
					for (Iterator<UnitUserBean> i = selectedUsers().iterator(); i
							.hasNext();) {
						UnitUserBean user = i.next();
						assingPermission(user, permission);
						addInfoMessage("Usuario " + user.getUsername()
								+ "se le asigno su rol.", "");
					}

				} else {
					new EnMeExpcetion("Error Seleccionado selectedGroupId");
				}
			} else {
				addWarningMessage("Selecciona Usuarios Para Asignar Roles", "");
			}
		} catch (HibernateException e) {
			addErrorMessage("Error Carga de Datos->" + e, e.getMessage());
		} catch (EnMeExpcetion e) {
			addErrorMessage("Error Local ->" + e, e.getMessage());
		} catch (Exception e) {
			addErrorMessage("Error Desconocido->" + e, e.getMessage());
		}
	}

	/**
	 * assing permission to user
	 * 
	 * @param user
	 *            user
	 * @param permission
	 *            permission
	 * @throws EnMeExpcetion
	 *             if the default permission dont exist
	 * @throws HibernateException
	 *             error db
	 */
	private void assingPermission(UnitUserBean user, UnitPermission permission)
			throws EnMeExpcetion, HibernateException {
		getServicemanagerBean().getSecurityService().assignPermission(user,
				permission);

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
			log.info("uiDataUserTable getRowCount->"+n);
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
		} catch (HibernateException e) {
			log.info("Error SQL->" + e);
			addErrorMessage("No Se borro ->" + user.getUsername() + "por->"
					+ e.getMessage(), "");
		} catch (MailSendException e) {
			log.info("No se pudo notificar->" + e);
			addErrorMessage("No Se borro ->" + user.getUsername() + "por->"
					+ e.getMessage(), "");
		} catch (Exception e) {
			log.info("No borro bien->" + e);
			addErrorMessage("No Se borro ->" + user.getUsername() + "por->"
					+ e.getMessage(), "");
		}
	}

	/**
	 * renew password
	 * 
	 * @param user
	 */
	private void renewPassword(UnitUserBean user) {
		try {
			getServicemanagerBean().getSecurityService().renewPassword(user);
			addInfoMessage("Se envio la nueva contraseña a ->"
					+ user.getUsername(), " a su correo " + user.getEmail());
		} catch (MailSendException e) {
			log.info("No recordo bien la contraseña a->" + user.getUsername());
			addErrorMessage("No pudo recordar a ->" + user.getUsername()
					+ "por->" + e.getMessage(), "");
		} catch (Exception e) {
			addErrorMessage("Otro Error renewPassword a ->"
					+ user.getUsername() + "por->" + e.getMessage(), "");
		}
	}

	/**
	 * init action
	 */
	public void initAction() {
		log.info("init action->" + getSelectedAction());
		try {
			if (getSelectedAction() != null) {
				if (selectedUsers().size() > 0) {
					switch (new Integer(getSelectedAction())) {
					case 1:
						for (Iterator<UnitUserBean> i = selectedUsers()
								.iterator(); i.hasNext();) {
							UnitUserBean user = i.next();
							log.info("delete action->" + user.getUsername());
							deleteUser(user);
						}
						break;
					case 2:
						log.info("renew password" + selectedUsers());
						// Recordar Contraseña
						for (Iterator<UnitUserBean> i = selectedUsers()
								.iterator(); i.hasNext();) {
							UnitUserBean user = i.next();
							log.info("recordar password action->"
									+ user.getUsername());
							renewPassword(user);
						}
						break;
					case 3:
						log.info("action 3" + selectedUsers());
						// Editor

						break;

					default:
						addErrorMessage("Acción Invalida", "");
						log.error("invalid action -" + getSelectedAction());
						break;
					}
				} else {
					addWarningMessage("Seleccione Usuarios Primero", "");
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
		log.info("setUnitUserBean->" + unitUserBean);
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
		
		return uiDataUserTable;
	}

	/**
	 * @param uiDataUserTable
	 *            the uiDataUserTable to set
	 */
	public void setUiDataUserTable(UIData uiDataUserTable) {
		log.info("uiDataUserTable ->"+uiDataUserTable);
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
		return unitUserBean;
	}

	/**
	 * load user selected in datatable
	 */
	public void loadSelectUser() {
		try {
			if (getProcessedUserId() != null) {
				unitUserBean = null;
				UnitUserBean unitUserBeanLocal = getServicemanagerBean()
						.getSecurityService().searchUserByUsername(
								getProcessedUserId());
				setUnitUserBean(unitUserBeanLocal);
			} else {
				addErrorMessage(
						"Lo siento, no se pudo cargar la info del usuario", "");
			}
		} catch (Exception e) {
			addErrorMessage("Error Cargando Datos Usuario"
					+ getProcessedUserId(), "");
			log.error("Error Cargando Datos Usuario " + e.getMessage());
		}
	}

	public UnitUserBean getNewUnitUserBean() {
		return newUnitUserBean;
	}

	public void setNewUnitUserBean(UnitUserBean newUnitUserBean) {
		this.newUnitUserBean = newUnitUserBean;
	}

	public String getListUsers() {
		return listUsers;
	}

	public void setListUsers(String listUsers) {
		this.listUsers = listUsers;
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

}

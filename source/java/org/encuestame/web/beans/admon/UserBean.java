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
 */
package org.encuestame.web.beans.admon;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIData;
import javax.faces.component.UISelectBoolean;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.security.util.EmailUtils;
import org.encuestame.web.beans.MasterBean;
import org.hibernate.HibernateException;
import org.richfaces.component.html.HtmlDataTable;
import org.springframework.mail.MailSendException;

/**
 * Security User Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 13:52:28
 * @version $Id$
 */

public class UserBean extends MasterBean {

    private UnitUserBean unitUserBean;
    private UnitUserBean newUnitUserBean = new UnitUserBean();
    private List<UnitUserBean> listUnitBeans;
    private String processedUserId;
    private Integer selectedPermissionId;
    private String selectedAction;
    private UIData uiDataUserTable;
    private HtmlDataTable dataTable;
    private UISelectBoolean checked = new UISelectBoolean();
    private String listUsers;

    /**
     * @return the listUnitBeans
     * @throws Exception exception
     */
    public List<UnitUserBean> loadListUsers() throws Exception {
        return listUnitBeans = getSecurityService().loadListUsers();
    }



    /**
     * Create user.
     */
    public void createUser() {
        try {
            getNewUnitUserBean().setPrimaryUserId(
                    getSecurityService().findUserByUserName(getUserPrincipalUsername()).getSecUser().getUid());
            getSecurityService().createUser(getNewUnitUserBean());
            addInfoMessage("User created", "");
        } catch (MailSendException e) {
            addErrorMessage("notification error " + e, e.getMessage());
        } catch (HibernateException e) {
            addErrorMessage("" + e, e.getMessage());
        } catch (EnMeExpcetion e) {
            addErrorMessage(
                    "error on get user " + e,
                    e.getMessage());
        } catch (Exception e) {
            addErrorMessage("error created user " + e, e
                    .getMessage());
        }
    }

    /**
     * Update user.
     */
    public void updateUser() {
        try {
            getServicemanager().getApplicationServices().getSecurityService().updateUser(
                    this.unitUserBean);
        } catch (HibernateException e) {
            addErrorMessage("error update user "
                    + e.getMessage(), e.getMessage());
            log.error("error update user: " + e);
        } catch (Exception e) {
            addErrorMessage("error update user " + e.getMessage(), e
                    .getMessage());
            log.error("error update user: " + e);
        }
    }

    /**
     * Invite user.
     */
    public void inviteUser() {
        if (!getListUsers().isEmpty()) {
            final List<String> emails = new LinkedList<String>();
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
                            String code = getServicemanager()
                                    .getApplicationServices()
                                    .getSecurityService()
                                    .generateHashCodeInvitation();
                            getServicemanager()
                            .getApplicationServices()
                            .getSecurityService()
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
                        addWarningMessage("invalid email: " + email, "");
                    }
                }
                setListUsers(null);
            } else {
                addWarningMessage("sorry, no results", "");
            }
        } else {
            addWarningMessage("sorry, no results", "");
        }

    }

    /**
     * Search LDAP user.
     */
    public void searchLDAPUser() {
        //TODO: need implement.
    }

    /**
     * @param listUnitBeans
     *            the listUnitBeans to set
     */
    public void setlistUnitBeans(List<UnitUserBean> listUnitBeans) {
        this.listUnitBeans = listUnitBeans;
    }

    /**
     * get list_unit_beans
     *
     * @return dsadsa
     */
    public List<UnitUserBean> getListUnitBeans() {
        try {
            loadListUsers();
            if (listUnitBeans.size() > 0)
                setOneRow(true);
            else
                setOneRow(false);
            return listUnitBeans;
        } catch (Exception e) {
            addErrorMessage("Error Cargando Datos->" + e.getMessage(), e
                    .getMessage());
            return null;
        }
    }

    /**
     * Assing permissions.
     */
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
     * Assing permission to user.
     * @param user  user
     * @param permission  permission
     * @throws EnMeExpcetion  if the default permission dont exist
     * @throws HibernateException error db
     */
    private void assingPermission(
            final UnitUserBean user,
            final UnitPermission permission)
            throws EnMeExpcetion, HibernateException {
        getServicemanager().getApplicationServices().getSecurityService().assignPermission(user,
                permission);

    }

    /**
     * Get list of user select in datatable.
     * @return
     */
    private List<UnitUserBean> selectedUsers() {
        List<UnitUserBean> listSelectedUsers = new LinkedList<UnitUserBean>();
        try {
            final Integer uiDataUserTableCount = uiDataUserTable.getRowCount();
            log.info("k getRowCount: "+uiDataUserTableCount);
            for (int i = 0; i < uiDataUserTableCount; i++) {
                uiDataUserTable.setRowIndex(i);
                if (checked.isSelected()) {
                    final UnitUserBean userUnit = (UnitUserBean) uiDataUserTable
                            .getRowData();
                    listSelectedUsers.add(userUnit);
                }
            }
        } catch (Exception e) {
            addErrorMessage(e.getMessage(), e.getMessage());
            log.error("ERROR->" + e.getMessage());

        }
        return listSelectedUsers;
    }

    /**
     * delete user
     *
     * @param user
     */
    private void deleteUser(UnitUserBean user) {
        try {
            getServicemanager().getApplicationServices().getSecurityService().deleteUser(user);
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
            getServicemanager().getApplicationServices().getSecurityService().renewPassword(user);
            addInfoMessage("Se envio la nueva contrase�a a ->"
                    + user.getUsername(), " a su correo " + user.getEmail());
        } catch (MailSendException e) {
            log.info("No recordo bien la contrase�a a->" + user.getUsername());
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
                        // Recordar Contrase�a
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
                        addErrorMessage("Acci�n Invalida", "");
                        log.error("invalid action -" + getSelectedAction());
                        break;
                    }
                } else {
                    addWarningMessage("Seleccione Usuarios Primero", "");
                }
            } else {
                addInfoMessage("Seleccione una Acci�n", "");
                log.info("init action->" + getSelectedAction());
            }
        } catch (Exception e) {
            addErrorMessage("Error en la Acci�n", e.getMessage());
            log.error("Error en la Acci�n -" + e.getMessage());
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

    /**
     * @param unitUserBean {@link UnitUserBean}
     */
    public void setUnitUserBean(final UnitUserBean unitUserBean) {
        log.info("setUnitUserBean->" + unitUserBean);
        this.unitUserBean = unitUserBean;
    }

    /**
     * @return id
     */
    public String getProcessedUserId() {
        return processedUserId;
    }

    /**
     * @param processedUserId id.
     */
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
        log.info("uiDataUserTable: "+uiDataUserTable);
        this.uiDataUserTable = uiDataUserTable;
    }

    /**
     * @return {@link UISelectBoolean}
     */
    public UISelectBoolean getChecked() {
        return checked;
    }

    /**
     * @param checked {@link UISelectBoolean}
     */
    public void setChecked(final UISelectBoolean checked) {
        this.checked = checked;
    }

    /**
     * Gettter.
     * @return {@link UnitUserBean}
     */
    public UnitUserBean getUnitUserBean() {
        return unitUserBean;
    }

    /**
     * Load user selected in datatable
     */
    public void loadSelectUser() {
        try {
            if (getProcessedUserId() != null) {
                unitUserBean = null;
                UnitUserBean unitUserBeanLocal = getServicemanager()
                        .getApplicationServices().getSecurityService().searchUserByUsername(
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

    /**
     * @return sda
     */
    public UnitUserBean getNewUnitUserBean() {
        return newUnitUserBean;
    }

    /**
     * @param newUnitUserBean das
     */
    public void setNewUnitUserBean(UnitUserBean newUnitUserBean) {
        this.newUnitUserBean = newUnitUserBean;
    }

    /**
     * @return dsa
     */
    public String getListUsers() {
        return listUsers;
    }

    /**
     * @param listUsers das
     */
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



    /**
     * @return the dataTable
     */
    public HtmlDataTable getDataTable() {
        return dataTable;
    }



    /**
     * @param dataTable the dataTable to set
     */
    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }



}

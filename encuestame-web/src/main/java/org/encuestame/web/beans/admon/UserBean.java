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
package org.encuestame.web.beans.admon;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIData;
import javax.faces.component.UISelectBoolean;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.security.util.EmailUtils;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitUserBean;
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
    private Long selectedPermissionId;
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
     * Create secondary user, is notificated is desactivated the password is returned and should be,
     * showed on screen.
     */
    public void createUser() {
        try {
            getNewUnitUserBean().setPrimaryUserId(
                    getSecurityService().findUserByUserName(getUserPrincipalUsername()).getSecUser().getUid());
            getNewUnitUserBean().setPassword(getSecurityService().createUser(getNewUnitUserBean()));
            addInfoMessage("User "+getNewUnitUserBean().getUsername()+" saved", "");
        } catch (EnMeExpcetion e) {
            addErrorMessage(
                    e.getMessage(), e.getMessage());
        }
    }

    /**
     * Update secondary user.
     */
    public void updateUser() {
        log.debug("update secondary user.");
        try {
            getServicemanager().getApplicationServices().getSecurityService().updateUser(
                    getUnitUserBean());
            addInfoMessage("User "+getNewUnitUserBean().getUsername()+" updated", "");
        } catch (EnMeExpcetion e) {
            addErrorMessage("error update user ", e.getMessage());
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
        //TODO: need implement ldap search.
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
            addErrorMessage("error loading grid " + e.getMessage(), e
                    .getMessage());
            return null;
        }
    }

    /**
     * Assing permissions to secondary user.
     */
    public void assingPermissions() {
        try {
            log.info("assing permissions to user");
            if (selectedUsers().size() > 0) {
                if (getSelectedPermissionId() != null) {
                    final UnitPermission permission = new UnitPermission();
                    permission.setId(getSelectedPermissionId());
                    for (Iterator<UnitUserBean> i = selectedUsers().iterator(); i
                            .hasNext();) {
                        final UnitUserBean user = i.next();
                        //assing permission
                        assingPermission(user, permission);
                    }
                     addInfoMessage("Permissions added ","P");
                } else {
                    new EnMeExpcetion("error on selected user");
                }
            } else {
                addWarningMessage("Select user first to assing permissions", "");
            }
        } catch (EnMeExpcetion e) {
            addErrorMessage(e.getMessage(), e.getMessage());
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
            throws EnMeExpcetion{
        getServicemanager().getApplicationServices().getSecurityService().assignPermission(user,
                permission);

    }

    /**
     * Get list of user select in datatable.
     * @return list of selected {@link UnitUserBean}
     */
    private List<UnitUserBean> selectedUsers() {
        final List<UnitUserBean> listSelectedUsers = new LinkedList<UnitUserBean>();
        try {
            final Integer uiDataUserTableCount = uiDataUserTable.getRowCount();
            for (int i = 0; i < uiDataUserTableCount; i++) {
                uiDataUserTable.setRowIndex(i);
                if (checked.isSelected()) {
                    final UnitUserBean userUnit = (UnitUserBean) uiDataUserTable
                            .getRowData();
                    log.debug("selected "+userUnit.getUsername());
                    listSelectedUsers.add(userUnit);
                }
                log.debug("total selected "+listSelectedUsers.size());
            }
        }
        catch (Exception e) {
            addErrorMessage("Error on selected operation", "");
            log.error("error selecting users" + e.getMessage());
        }
        return listSelectedUsers;
    }


    /**
     * Delete user.
     * @param user {@link UnitUserBean}
     */
    private void deleteUser(final UnitUserBean user) {
        try {
            getServicemanager().getApplicationServices().getSecurityService().deleteUser(user);
            log.debug("user "+user.getUsername()+" deleted");
        } catch (EnMeExpcetion e) {
            log.error("Error on delete user. Trace:"+ e.getMessage());
            addErrorMessage("Error on delete user","");
        }
    }

    /**
     * Renew password.
     * @param user {@link UnitUserBean}
     */
    private void renewPassword(final UnitUserBean user) {
        try {
            getServicemanager().getApplicationServices().getSecurityService().renewPassword(user, user.getPassword());
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
     * Execute Actions for all user selected.
     */
    public void initAction() {
        log.debug("action selected->" + getSelectedAction());
        try {
            if (getSelectedAction() != null) {
                if (selectedUsers().size() > 0) {
                    switch (new Integer(getSelectedAction())) {
                    case 1:
                        for (Iterator<UnitUserBean> i = selectedUsers()
                                .iterator(); i.hasNext();) {
                            final UnitUserBean user = i.next();
                            log.info("delete action->" + user.getUsername());
                            this.deleteUser(user);
                        }
                        addInfoMessage("User/s deleted.","");
                        break;
                    case 2:
                        log.debug("renew passwords");
                        for (Iterator<UnitUserBean> i = selectedUsers()
                                .iterator(); i.hasNext();) {
                            final UnitUserBean user = i.next();
                            log.info("recordar password action->"
                                    + user.getUsername());
                            this.renewPassword(user);
                        }
                        addInfoMessage("New passwords sended","");
                        break;
                    case 3:
                        log.info("editor");
                        // TODO: need implement editor.
                        break;

                    default:
                        addErrorMessage("Invalid action", "");
                        log.error("Invalid action");
                        break;
                    }
                }
                else {
                    addWarningMessage("You need select users first", "");
                }
            }
            else {
                addWarningMessage("You need selected action first", "");
                log.error("iYou need selected action first");
            }
        }
        catch (Exception e) {
            addErrorMessage("Error on actions","");
            log.error("Error on actions " + e.getMessage());
        }
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
    public void setProcessedUserId(final String processedUserId) {
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
    public void setSelectedAction(final String selectedAction) {
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
    public void setUiDataUserTable(final UIData uiDataUserTable) {
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
     * Getter.
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
    public void setNewUnitUserBean(final UnitUserBean newUnitUserBean) {
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
    public void setListUsers(final String listUsers) {
        this.listUsers = listUsers;
    }

    /**
     * @return the selectedPermissionId
     */
    public Long getSelectedPermissionId() {
        return selectedPermissionId;
    }

    /**
     * @param selectedPermissionId
     *            the selectedPermissionId to set
     */
    public void setSelectedPermissionId(final Long selectedPermissionId) {
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
    public void setDataTable(final HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }
}

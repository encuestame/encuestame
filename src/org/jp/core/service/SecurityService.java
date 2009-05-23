package org.jp.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jp.core.mail.MailServiceImpl;
import org.jp.core.persistence.dao.SecGroupDaoImp;
import org.jp.core.persistence.dao.SecPermissionDaoImp;
import org.jp.core.persistence.dao.UserDaoImp;
import org.jp.core.persistence.pojo.SecGroupUser;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.core.persistence.pojo.SecPermission;
import org.jp.core.persistence.pojo.SecUserPermission;
import org.jp.core.persistence.pojo.SecUsers;
import org.jp.core.security.util.PasswordGenerator;
import org.jp.web.beans.admon.UnitGroupBean;
import org.jp.web.beans.admon.UnitPermission;
import org.jp.web.beans.admon.UnitUserBean;
import org.springframework.mail.MailSendException;

/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
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
 * Id: SecurityService.java Date: 27/04/2009
 * 
 * @author juanpicado package: org.jp.core.service
 * @version 1.0
 */
public class SecurityService extends MasterService implements ISecurityService {

	private Log log = LogFactory.getLog(this.getClass());
	private UserDaoImp userDao;
	private SecGroupDaoImp groupDao;
	private SecPermissionDaoImp permissionDao;
	private MailServiceImpl serviceMail;
	private String defaultUserPermission;
	private Boolean suspendedNotification;

	public UserDaoImp getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoImp userDao) {
		this.userDao = userDao;
	}

	public SecGroupDaoImp getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(SecGroupDaoImp groupDao) {
		this.groupDao = groupDao;
	}

	public SecPermissionDaoImp getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(SecPermissionDaoImp permissionDao) {
		this.permissionDao = permissionDao;
	}

	/**
	 * load list of users
	 * 
	 * @return list of users with groups and permission
	 * @throws Exception
	 */
	public Collection<UnitUserBean> loadListUsers() throws Exception {
		Collection<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
		Collection<SecUsers> listUsers = getUserDao().findAll();
		// log.info("LOADED USERS-->" + listUsers.size());
		if (listUsers != null && listUsers.size() > 0) {
			for (Iterator<SecUsers> i = listUsers.iterator(); i.hasNext();) {
				UnitUserBean userB = new UnitUserBean();
				SecUsers user = i.next();
				userB.setId(user.getUid());
				userB.setName(user.getName());
				userB.setEmail(user.getEmail());
				userB.setUsername(user.getUsername());
				userB.setPublisher(user.getPublisher());
				userB.setListGroups(convertSetToUnitGroupBean(user.getUid()));
				userB.setListPermission(convertSetToUnitPermission(user
						.getUid()));
				loadListUsers.add(userB);
			}
		}
		return loadListUsers;
	}

	/**
	 * search user by username
	 * 
	 * @param username
	 * @return
	 */
	public UnitUserBean searchUserByUsername(String username) {
		SecUsers userD = getUserDao().getUser(username);
		log.info("Usuario Encontrado por Nombnre->" + userD);
		if (userD != null) {
			UnitUserBean user = convertUserDaoToUserBean(userD);
			log.info("Conversión Usuario to Bean ->" + user);
			return user;
		} else {
			log.error("No se encontro usuario");
			return null;
		}

	}

	private UnitUserBean convertUserDaoToUserBean(SecUsers userD) {
		UnitUserBean user = new UnitUserBean();
		try {
			user.setName(userD.getName());
			user.setUsername(userD.getUsername());
			user.setEmail(userD.getEmail());
			user.setId(userD.getUid());
			user.setStatus(userD.isStatus());
			user.setDate_new(userD.getDateNew());
			user.setInvite_code(userD.getInviteCode());
			user.setPublisher(userD.getPublisher());
		} catch (Exception e) {
			log.error("Error convirtiendo a User BEan -" + e.getMessage());
		}
		return user;
	}

	/**
	 * convert set to unit group bean
	 * 
	 * @param set
	 * @return
	 * @throws Exception
	 */
	private Collection<UnitGroupBean> convertSetToUnitGroupBean(Integer id)
			throws Exception {
		Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
		if (id != null) {
			UnitGroupBean group = new UnitGroupBean();
			Collection<SecGroupUser> listSecGru = getGroupDao()
					.loadGroupsByUser(id);
			for (Iterator<SecGroupUser> i = listSecGru.iterator(); i.hasNext();) {
				SecGroupUser userg = i.next();
				group.setGroupName(userg.getSecGroups().getName());
				group.setGroupDescription(userg.getSecGroups().getDesInfo());
				group.setId(userg.getSecGroups().getGroupId());
				group.setStateId(Integer.toString(userg.getSecGroups()
						.getIdState()));
				loadListGroups.add(group);
			}
		}
		return loadListGroups;
	}

	/**
	 * convert dao permission in permission bean
	 * 
	 * @param set
	 * @return
	 * @throws Exception
	 */
	private Collection<UnitPermission> convertSetToUnitPermission(Integer id)
			throws Exception {
		Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
		if (id != null) {
			UnitPermission per = new UnitPermission();
			Collection<SecUserPermission> listSecGru = getPermissionDao()
					.loadPermissionByUser(id);
			for (Iterator<SecUserPermission> i = listSecGru.iterator(); i
					.hasNext();) {
				SecUserPermission permission = i.next();
				per.setId(permission.getSecPermission().getIdPermission());
				per
						.setPermission(permission.getSecPermission()
								.getPermission());
				per.setDescription(permission.getSecPermission()
						.getDescription());
				loadListPermission.add(per);
			}
		}
		return loadListPermission;
	}

	/**
	 * load all list of permisssions
	 * 
	 * @return list of permisssions
	 */
	public Collection<UnitPermission> loadAllListPermission() {
		Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
		Collection<SecPermission> listSecPermission = getPermissionDao()
				.loadAllPermissions();
		log.info("Permissions Total->" + listSecPermission.size());
		for (Iterator<SecPermission> i = listSecPermission.iterator(); i
				.hasNext();) {
			UnitPermission per = new UnitPermission();
			SecPermission permission = i.next();
			per.setId(permission.getIdPermission());
			per.setPermission(permission.getPermission());
			per.setDescription(permission.getDescription());
			loadListPermission.add(per);
		}
		return loadListPermission;

	}

	/**
	 * Delete Group
	 * 
	 * @param group
	 */
	public void deleteGroup(UnitGroupBean group) {
		SecGroups g = getGroupDao().find(group.getId());
		getGroupDao().delete(g);
	}

	/**
	 * delete user
	 * 
	 * @param user
	 *            to delete
	 */
	public void deleteUser(UnitUserBean user) throws MailSendException,
			HibernateException, Exception {
		SecUsers g = getUser(user.getUsername().trim());
		log.info("delete deleteUserDao->" + g.getUsername());
		if (getSuspendedNotification()) {
			log.info("Notificar->" + user.getEmail());
			getServiceMail().sendDeleteNotification(user.getEmail(),
					getMessageProperties("MessageDeleteNotification"));
		}
		log.info("user to delete ->" + g.getEmail());
		getUserDao().delete(g);
		log.info("user delete");
	}

	/**
	 * renew password
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void renewPassword(UnitUserBean user) throws MailSendException {
		SecUsers g = getUser(user.getUsername().trim());
		if (g.getPassword() != null) {
			String newPassowrd = generatePassword();
			g.setPassword(encryptPassworD(newPassowrd));
			try {
				sendUserPassword(g.getEmail().trim(), newPassowrd);
				getUserDao().updateUser(g);
			} catch (MailSendException ex) {
				throw new MailSendException(ex.getMessage());
			}
		} else {
			throw new MailSendException("test");
		}
	}

	/**
	 * get user by username
	 * 
	 * @param username
	 * @return
	 */
	private SecUsers getUser(String username) {
		return getUserDao().getUser(username.trim());
	}

	/**
	 * Update a Group
	 * 
	 * @param group
	 */
	public void updateGroup(UnitGroupBean uGroup) {
		SecGroups group = getGroupDao().find(uGroup.getId());
		if (group != null) {
			group.setName(uGroup.getGroupName());
			group.setDesInfo(uGroup.getGroupDescription());
			group.setIdState((new Integer(uGroup.getStateId())));
		}
		getGroupDao().update(group);
	}

	/**
	 * update user
	 * 
	 * @param userD
	 */
	public void updateUser(UnitUserBean userD)  {
		SecUsers updateUser = getUserDao().getUser(userD.getUsername());
		if (updateUser != null) {
			updateUser.setEmail(userD.getEmail());
			updateUser.setName(userD.getName());
			updateUser.setStatus(userD.getStatus());
			updateUser.setPublisher(userD.getPublisher());
		}
		getUserDao().updateUser(updateUser);
	}

	/**
	 * Create a new Group
	 * 
	 * @param group
	 */
	public void createGroup(UnitGroupBean group) {
		SecGroups newG = new SecGroups();
		newG.setDesInfo(group.getGroupDescription());
		newG.setName(group.getGroupName());
		newG.setIdState(new Integer(group.getStateId()).intValue());
		getGroupDao().newGroup(newG);
	}

	/**
	 * create a user
	 * 
	 * @param user
	 */
	public void createUser(UnitUserBean user) throws MailSendException {
		SecUsers userBd = new SecUsers();
		if (user.getEmail() != null) {
			userBd.setEmail(user.getEmail());
		} else {
			throw new MailSendException("email nulo");
		}
		String password = generatePassword();
		userBd.setPassword(encryptPassworD(password));
		userBd.setPublisher(user.getPublisher());
		userBd.setName(user.getName());
		userBd.setStatus(user.getStatus());
		userBd.setDateNew(new Date());
		userBd.setUsername(user.getUsername());
		try {
			// send to user first password
			sendUserPassword(user.getEmail(), password);
			// create user
			getUserDao().createUser(userBd);
			// assing first permissions and default group
			assignPermission(getUser(user.getUsername()),
					loadPermission(getDefaultUserPermission()));
			// assing firs default group to user
			log
					.info("getDefaultUserPermission ->"
							+ getDefaultUserPermission());
			log
					.info("Message Source->"
							+ getMessageProperties("Mailsuccesful"));

		} catch (MailSendException e) {
			throw new MailSendException(
					"no se pudo notificar el nuevo usuario ->" + e);
		}
	}

	/**
	 * assign permission to user
	 * 
	 * @param user
	 * @param permission
	 */
	private void assignPermission(SecUsers user, SecPermission permission)
			throws HibernateException {

	}

	/**
	 * load Permission bean
	 * 
	 * @param permission
	 * @return
	 */
	public UnitPermission loadBeanPermission(String permission)
			throws HibernateException {
		SecPermission per = getPermissionDao().loadPermission(permission);
		UnitPermission uPer = new UnitPermission();
		if (per != null) {
			uPer.setId(per.getIdPermission());
			uPer.setPermission(per.getPermission());
			uPer.setDescription(per.getDescription());
		}
		return uPer;
	}

	/**
	 * load dao permission
	 * 
	 * @param permission
	 * @return
	 * @throws HibernateException
	 */
	public SecPermission loadPermission(String permission)
			throws HibernateException {
		SecPermission per = getPermissionDao().loadPermission(permission);
		return per;
	}

	/**
	 * invite some users to register in the system
	 * 
	 * @param emails
	 *            list of users
	 * @throws Exception
	 */
	public void inviteUser(String email, String code) throws Exception {
		log.info("Correo Usuarios->" + email);
		getServiceMail().sendInvitation(email, code);

	}

	/**
	 * generate hash code invitation
	 * 
	 * @return
	 */
	public String generateHashCodeInvitation() {
		return generatePassword();
	}

	/**
	 * send user to user
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	private void sendUserPassword(String email, String password)
			throws MailSendException {
		getServiceMail().send(email, getMessageProperties("NewPassWordMail"),
				password);
	}

	/**
	 * generate a password
	 * 
	 * @return
	 */
	private String generatePassword() {
		String passGenerate = PasswordGenerator.getPassword(
				PasswordGenerator.LOWERCASE + PasswordGenerator.CAPITALS, 10);
		return passGenerate;
	}

	/**
	 * encrypt the password
	 * 
	 * @param password
	 * @return
	 */
	private String encryptPassworD(String password) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(password);
		return encryptedPassword;
	}

	public MailServiceImpl getServiceMail() {
		return serviceMail;
	}

	public void setServiceMail(MailServiceImpl serviceMail) {
		this.serviceMail = serviceMail;
	}

	private String getDefaultUserPermission() {
		return defaultUserPermission;
	}

	public void setDefaultUserPermission(String defaultUserPermission) {
		this.defaultUserPermission = defaultUserPermission;
	}

	private Boolean getSuspendedNotification() {
		log.info("suspendedNotification->" + suspendedNotification);
		return suspendedNotification;
	}

	public void setSuspendedNotification(Boolean suspendedNotification) {
		this.suspendedNotification = suspendedNotification;
	}

}

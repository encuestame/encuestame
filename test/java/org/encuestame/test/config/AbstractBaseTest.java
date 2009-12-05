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
package org.encuestame.test.config;

import java.util.Date;

import org.encuestame.core.persistence.dao.SurveyFormatDao;
import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.dao.imp.IQuestionDao;
import org.encuestame.core.persistence.dao.imp.ISecGroups;
import org.encuestame.core.persistence.dao.imp.ISecPermissionDao;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.dao.imp.ISurvey;
import org.encuestame.core.persistence.dao.imp.ISurveyFormatDao;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupPermissionId;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecGroupUserId;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUserPermissionId;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.context.annotation.Scope;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Class to Test Cases.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 15, 2009
 * File name: $HeadURL$
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager" ,defaultRollback = true)
@Transactional
@Scope("singleton")
@ContextConfiguration(locations = {
        "classpath:encuestame-service-context.xml",
        "classpath:encuestame-dao-context.xml",
        "classpath:encuestame-beans-jsf-context.xml",
        "classpath:encuestame-hibernate-context.xml",
        "classpath:encuestame-email-context.xml",
        "classpath:encuestame-param-test-context.xml"
         })
public class AbstractBaseTest extends AbstractTransactionalDataSourceSpringContextTests {

     /** SurveyFormat  Dao. **/
    @Autowired
    private ISurveyFormatDao surveyformatDaoImp;

    /** State Catalog Dao. **/
    @Autowired
    private ICatState catStateDaoImp;

    /** User Security Dao. **/
    @Autowired
    private ISecUserDao secUserDao;

    /**Group Security Dao*/
    @Autowired
    private ISecGroups secGroupDaoImp;

    /** Security Permissions Dao. **/
    @Autowired
    private ISecPermissionDao secPermissionDaoImp;

    /** Catalog Location Dao. **/
    @Autowired
    private ICatLocation catLocationDao;

    /** Project Dao Imp **/
    @Autowired
    private IProject projectDaoImp;

    /** Survey Dao Imp. **/
    @Autowired
    private ISurvey surveyDaoImp;

    /** Question Dao Imp. **/
    @Autowired
    private IQuestionDao questionDaoImp;

    /**
     * Getter.
     * @return the surveyFormatDaoImp
     */
    public ISurveyFormatDao getSurveyFormatDaoImp() {
        return surveyformatDaoImp;
    }

    /**
     * @param surveyformatDaoImp {@link ISurveyFormatDao}
     */
    public void setSurveyFormatDaoImp(final ISurveyFormatDao surveyformatDaoImp) {
        this.surveyformatDaoImp = surveyformatDaoImp;
    }


    /**
     * Getter.
     * @return the catStateDaoImp
     */
    public ICatState getCatStateDaoImp() {
        return catStateDaoImp;
    }

    /**
     * @param catStateDaoImp the catStateDaoImp to set
     */
    public void setCatStateDaoImp(final ICatState catStateDaoImp) {
        this.catStateDaoImp = catStateDaoImp;
    }

    /**
     * @return the userDao
     */
    public ISecUserDao getSecUserDao() {
        return secUserDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setSecUserDao(final ISecUserDao userDao) {
        this.secUserDao = userDao;
    }

    /**
     * @return {@link ISecGroups}
     */
    public ISecGroups getSecGroup(){
        return secGroupDaoImp;
    }

    /**
     * @param secGroupDaoImp  {@link ISecGroups}
     */
    public void setgroupDao(final ISecGroups secGroupDaoImp){
        this.secGroupDaoImp = secGroupDaoImp;
    }

    /**
     * @return the secPermissionDaoImp
     */
    public ISecPermissionDao getSecPermissionDaoImp() {
        return secPermissionDaoImp;
    }

    /**
     * @param secPermissionDaoImp the secPermissionDaoImp to set
     */
    public void setSecPermissionDaoImp(final ISecPermissionDao secPermissionDaoImp) {
        this.secPermissionDaoImp = secPermissionDaoImp;
    }

    /**
     * @return the secGroupDaoImp
     */
    public ISecGroups getSecGroupDaoImp() {
        return secGroupDaoImp;
    }

    /**
     * @param secGroupDaoImp the secGroupDaoImp to set
     */
    public void setSecGroupDaoImp(final ISecGroups secGroupDaoImp) {
        this.secGroupDaoImp = secGroupDaoImp;
    }

    /**
     * @return the catLocationDao
     */
    public ICatLocation getCatLocationDao() {
        return catLocationDao;
    }

    /**
     * @param catLocationDao the catLocationDao to set
     */
    public void setCatLocationDao(final ICatLocation catLocationDao) {
        this.catLocationDao = catLocationDao;
    }

    /**
     * @return the projectDaoImp
     */
    public IProject getProjectDaoImp() {
        return projectDaoImp;
    }

    /**
     * @param projectDaoImp the projectDaoImp to set
     */
    public void setProjectDaoImp(final IProject projectDaoImp) {
        this.projectDaoImp = projectDaoImp;
    }

    /**
     * @return the surveyDaoImp
     */
    public ISurvey getSurveyDaoImp() {
        return surveyDaoImp;
    }

    /**
     * @param surveyDaoImp the surveyDaoImp to set
     */
    public void setSurveyDaoImp(final ISurvey surveyDaoImp) {
        this.surveyDaoImp = surveyDaoImp;
    }

    /**
     * @return the questionDaoImp
     */
    public IQuestionDao getQuestionDaoImp() {
        return questionDaoImp;
    }

    /**
     * @param questionDaoImp the questionDaoImp to set
     */
    public void setQuestionDaoImp(final IQuestionDao questionDaoImp) {
        this.questionDaoImp = questionDaoImp;
    }

    /**
     * Helper to create state.
     * @param name name of state
     * @return state
     */
    public CatState createState(final String name){
        final CatState state = new CatState();
        state.setDescState(name);
        state.setImage("image.jpg");
        catStateDaoImp.saveOrUpdate(state);
        return state;
    }

    /**
     * Create project.
     * @param name name of project.
     * @return {@link Project}
     */
    public Project createProject(final String name) {
          Project project = new Project();
          project.setCatState(createState("active"));
          project.setDateFinish(new Date());
          project.setDateStart(new Date());
          project.setInfo("info");
          project.setDescription("description");
          projectDaoImp.saveOrUpdate(project);
          return project;
    }

    /**
     * Helper to create User.
     * @param name user name
     * @return state
     */
    public SecUsers createUsers(final String name){
        final SecUsers user= new SecUsers();
        user.setName(name);
        user.setUsername(name);
        user.setPassword("12345");
        user.setEmail(name+"@users.com");
        user.setDateNew(new Date());
        user.setStatus(true);
        getSecUserDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Helper to create LocationType.
     * @param catLocationType cat location type
     * @return locationType
     */

    public CatLocationType createCatLocationType(final String catLocationType){
        final CatLocationType catLocatType = new CatLocationType();
        catLocatType.setDescription(catLocationType);
        catLocatType.setLevel(1);
        //getCatLocationType().saveOrUpdate(catLocatType);
        return catLocatType;
    }


    /**
     * Helper to create Group.
     * @param groupname user name
     * @return state
     */
    public SecGroups createGroups(final String groupname){
        final SecGroups group = new SecGroups();
        group.setName(groupname);
        group.setIdState(1L);
        group.setDesInfo("Primer Grupo");
        getSecGroup().saveOrUpdate(group);
        return group;
    }

    /**
     * Helper to create Permission.
     * @param permissionName name
     * @return Permission
     */
    public SecPermission createPermission(final String permissionName){
        final SecPermission permission = new SecPermission();
        permission.setDescription(permissionName);
        permission.setPermission(permissionName);
        getSecPermissionDaoImp().saveOrUpdate(permission);
        return permission;
    }

    /**
     * Helper to add permission to user.
     * @param user user
     * @param permission permission
     */
    public void addPermissionToUser(final SecUsers user, final SecPermission permission){
        final SecUserPermission userPerId = new SecUserPermission();
        final SecUserPermissionId id = new SecUserPermissionId();
        id.setIdPermission(permission.getIdPermission());
        id.setUid(user.getUid());
        userPerId.setId(id);
        userPerId.setState(true);
        getSecUserDao().saveOrUpdate(userPerId);
    }

    /**
     * Helper to add user to group.
     * @param user user
     * @param group group
     */
    public void addGroupUser(
            final SecUsers user,
            final SecGroups group)
    {
        final SecGroupUserId id = new SecGroupUserId();
        id.setGroupId(group.getGroupId());
        id.setUid(user.getUid());
        final SecGroupUser secGroupUser = new SecGroupUser();
        secGroupUser.setId(id);
        secGroupUser.setSecUsers(user);
        secGroupUser.setSecGroups(group);
        getSecUserDao().assingGroupToUser(secGroupUser);
    }

    /**
     * Helper permission to group.
     * @param permission permission
     * @param group group
     */
    public void addPermissionToGroup(
            final SecPermission permission,
            final SecGroups group)
    {
        final SecGroupPermissionId groupPermissionId = new SecGroupPermissionId();
        groupPermissionId.setGroupId(group.getGroupId());
        groupPermissionId.setIdPermission(permission.getIdPermission());
        final SecGroupPermission groupPermission = new SecGroupPermission();
        groupPermission.setId(groupPermissionId);
        groupPermission.setSecGroups(group);
        groupPermission.setSecPermission(permission);
        getSecGroup().saveOrUpdate(groupPermission);
    }
}

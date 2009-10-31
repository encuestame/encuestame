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

import org.encuestame.core.persistence.dao.SecGroupDaoImp;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.ISecGroups;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager" ,defaultRollback = true)
@Transactional
@Scope("singleton")
@ContextConfiguration(locations = {
        "classpath:encuestame-application-context.xml",
        "classpath:encuestame-beans-jsf-context.xml",
        "classpath:encuestame-hibernate-context.xml",
        "classpath:encuestame-email-context.xml",
        "classpath:encuestame-param-context.xml"
         })
public class AbstractBaseTest extends AbstractTransactionalDataSourceSpringContextTests {

    /** State Catalog Dao. **/
    @Autowired
    private ICatState catStateDaoImp;

    /** User Security Dao. **/
    @Autowired
    private ISecUserDao secUserDao;

    /**Group Security Dao*/
    @Autowired
    private ISecGroups groupDao;


    /**
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

    public ISecGroups getSecGroup(){
        return groupDao;
    }

    public void setgroupDao(final ISecGroups groupDao){
        this.groupDao = groupDao;
    }

    /**
     * Helper to create state.
     * @param name name of state
     * @return state
     */
    public CatState createState(final String name){
        CatState state = new CatState();
        state.setDescState(name);
        state.setImage("image.jpg");
        catStateDaoImp.save(state);
        return state;
    }
    /**
     * Helper to create User.
     * @param name user name
     * @return state
     */
    public SecUsers createUsers(final String name){
        SecUsers user= new SecUsers();
        user.setName(name);
        user.setUsername(name+"_encuestame_test");
        user.setPassword("12345");
        user.setEmail(name+"@users.com");
        user.setDateNew(new Date());
        user.setStatus(true);
        getSecUserDao().saveOrCreateUser(user);
        return user;
    }
    /**
     * Helper to create Group.
     * @param name user name
     * @return state
     */
    public SecGroups createGroups(final String groupname){
        SecGroups group = new SecGroups();
        group.setName(groupname);
        group.setIdState(1);
        group.setDesInfo("Primer Grupo");
        getSecGroup().newGroup(group);
        return group;
    }
}

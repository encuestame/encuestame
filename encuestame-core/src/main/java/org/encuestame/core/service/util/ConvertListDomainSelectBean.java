/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.service.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Client;
import org.encuestame.core.persistence.pojo.SecGroup;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUser;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.utils.web.UnitUserBean;

/**
 * Convert List of Domains to {@link SelectItem} List.
 * @author Picado, Juan juan@encuestame.org
 * @since Jan 25, 2010 1:14:11 AM
 * @version $Id$
 */
public class ConvertListDomainSelectBean {

    private static final Log log = LogFactory.getLog(ConvertDomainBean.class);

    /**
     * Convert {@link Client} domains to {@link SelectItem}.
     * @param clients {@link Client}
     * @return select items.
     */
    public static final  List<SelectItem> convertListClientsDomainToSelect(final List<Client> clients){
        final List<SelectItem> items = new ArrayList<SelectItem>();
        for (final Client client : clients) {
            if(client.getClientName() != null){
                items.add(new SelectItem(client.getClientId(), client.getClientName()));
            }
        }
        log.debug("clients items loaded: "+items.size());
        return items;
    }

    /**
     * Convert {@link CatState} domains to {@link SelectItem}.
     * @param states {@link CatState}
     * @return select items.
     */
    public static final List<SelectItem> convertListCatStateDomainToSelect(final List<CatState> states){
        final List<SelectItem> items = new ArrayList<SelectItem>();
        for (final CatState state : states) {
            if(state.getDescState()!=null){
                items.add(new SelectItem(state.getIdState(), state.getDescState()));
            }
        }
        log.debug("clients items loaded: "+items.size());
        return items;
    }

    /**
     * Convert {@link SecUserSecondary} domains to {@link SelectItem}.
     * @param users {@link SecUserSecondary}
     * @return select items.
     */
    public static final List<SelectItem> convertListSecondaryUsersDomainToSelect(final List<SecUserSecondary> users){
        final List<SelectItem> items = new ArrayList<SelectItem>();
        for (final SecUserSecondary user : users) {
            if(user.getCompleteName()!=null){
                items.add(new SelectItem(user.getUid(), user.getCompleteName()));
            }
        }
        log.debug("sec user items loaded: "+items.size());
        return items;
    }

    /**
     *
     * @param users
     * @return
     */
    public static final List<SelectItem> convertListUnitUserBeanDomainToSelect(final List<UnitUserBean> users){
        final List<SelectItem> items = new ArrayList<SelectItem>();
        for (final UnitUserBean user : users) {
            if(user.getUsername()!=null){
                items.add(new SelectItem(user.getId(), user.getUsername()));
            }
        }
        log.debug("sec user items loaded: "+items.size());
        return items;
    }

    /**
     * Convert {@link SecGroup} domains to {@link SelectItem}.
     * @param groups {@link SecGroup}
     * @return select items.
     */
    public static final List<SelectItem> convertListGroupDomainToSelect(final Set<SecGroup> groups){
        final List<SelectItem> items = new ArrayList<SelectItem>();
        for (final SecGroup group : groups) {
            if(group.getGroupName()!=null){
                items.add(new SelectItem(group.getGroupId(), group.getGroupName()));
            }
        }
        log.debug("group items loaded: "+items.size());
        return items;
    }

    /**
     * Convert {@link SecPermission} to {@link SelectItem}
     * @param permissions Set of {@link SecPermission}.
     * @return
     */
    public static final List<SelectItem> convertListPermissionsToSelect(final Set<SecPermission> permissions){
        final List<SelectItem> items = new ArrayList<SelectItem>();
        for (final SecPermission permission : permissions) {
            if(permission.getPermission()!=null){
                items.add(new SelectItem(permission.getIdPermission(), permission.getPermission()));
            }
        }
        log.debug("permissions select items loaded: "+items.size());
        return items;
    }

    /**
     * Load {@link SecGroup} by Username.
     * @param currentUsername username
     * @return
     */
    public List<SelectItem> loadSelectItemGroups(final List<SecGroup> groups){
        final Set<SecGroup> groupsCollection = new HashSet<SecGroup>(groups);
        return ConvertListDomainSelectBean.convertListGroupDomainToSelect(groupsCollection);
    }


    /**
     * Load {@link SelectItem} permissions.
     */
    public List<SelectItem> loadSelectItemPermissions( Set permissionCollection){
        return ConvertListDomainSelectBean.convertListPermissionsToSelect(permissionCollection);
    }

    /**
     *
     * @param permissionCollection
     * @return
     */
    public List<SelectItem> loadSelectItemGroups(final Set permissionCollection ){
        return ConvertListDomainSelectBean.convertListGroupDomainToSelect(permissionCollection);
    }
}

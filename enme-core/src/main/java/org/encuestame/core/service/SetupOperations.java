/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.io.IOException;
import java.util.List;

import org.encuestame.core.config.AdministratorProfile;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.social.SocialNetworkBean;
import org.encuestame.utils.web.UserAccountBean;

/**
 * Setup operations support.
 * <p>
 *  This class is the responsible to setup workflow, install, drop, update xml config file during
 *  installation process.
 * </p>
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 25, 2011
 */
public interface SetupOperations {

    /**
     * Install database.
     * @return
     * @throws EnmeFailOperation
     * @throws IOException
     */
    String installDatabase() throws EnmeFailOperation, IOException;

    Boolean removeTables();

    void demoInstall();

    void upgradeDatabase();
    
    void finishInstall();

    String getSQLExecuted();

    String checkStatus();

    void validateInstall();

    UserAccountBean createUserAdministration(final AdministratorProfile administratorProfile);

    java.util.List<String> loadInstallParameters();

    Boolean checkDatabase();

    void addNewSocialNetworkConfiguration();

    void removeSocialNetworkConfiguration();

    List<SocialNetworkBean> listAllNetworkConfigurationSocial();
}
